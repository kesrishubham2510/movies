package com.myreflectionthoughts.moviereviewservice.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.myreflectionthoughts.library.dto.response.ReviewResponse;
import com.myreflectionthoughts.moviereviewservice.ConstructUtils;
import com.myreflectionthoughts.moviereviewservice.dto.request.AddReview;
import com.myreflectionthoughts.moviereviewservice.dto.response.DeleteReviewResponse;
import com.myreflectionthoughts.moviereviewservice.exceptions.ReviewNotFoundException;
import com.myreflectionthoughts.moviereviewservice.models.Review;
import com.myreflectionthoughts.moviereviewservice.repositories.ReviewRepository;
import com.myreflectionthoughts.moviereviewservice.services.ReviewService;
import com.myreflectionthoughts.moviereviewservice.utils.MovieReviewMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ReviewServiceTest {
    
    @Mock
    private ReviewRepository reviewRepositoryMock;

    @Mock
    private MovieReviewMapper movieReviewMapperMock;

    @InjectMocks
    private ReviewService reviewService;

    private String movieInfoId;
    private String review;
    private String reviewId;
    private Double rating;

    ReviewServiceTest(){
        movieInfoId = "movieId@MongoDB";
        review = "The movie is not upto the mark, it's based on false incidents.";
        reviewId = "reviewId@mongoDB";
        rating = 3.5;
    }

    @Test
    void testSave(){
      
        when(movieReviewMapperMock.toEntity(any(AddReview.class))).thenReturn(ConstructUtils.constructReview(null, movieInfoId, review, rating));
        when(reviewRepositoryMock.save(any(Review.class))).thenReturn(Mono.just(ConstructUtils.constructReview(reviewId,movieInfoId,review,rating)));
        when(movieReviewMapperMock.toReviewResponse(any(Review.class))).thenReturn(ConstructUtils.constructReviewResponse(reviewId, movieInfoId, review, rating));

        Mono<ReviewResponse> savedResponseMono = reviewService.save(Mono.just(ConstructUtils.constructAddReviewPayload()));
        
        StepVerifier.create(savedResponseMono).consumeNextWith(savedResponse->{
            
            assertEquals(reviewId, savedResponse.getReviewId());
            assertEquals(movieInfoId, savedResponse.getMovieInfoId());
            assertEquals(review, savedResponse.getReview());
            assertEquals(rating, savedResponse.getRating());
        }).verifyComplete();

        verify(movieReviewMapperMock,times(1)).toEntity(any(AddReview.class));
        verify(reviewRepositoryMock,times(1)).save(any(Review.class));
        verify(movieReviewMapperMock,times(1)).toReviewResponse(any(Review.class));

    }

    @Test
    void testFind_Success(){

        when(reviewRepositoryMock.findById(anyString())).thenReturn(Mono.just(ConstructUtils.constructReview(reviewId, movieInfoId, review, rating)));
        when(movieReviewMapperMock.toReviewResponse(any(Review.class))).thenReturn(ConstructUtils.constructReviewResponse(reviewId, movieInfoId, review, rating));

        Mono<ReviewResponse> receivedResponseMono = reviewService.find(reviewId);

        StepVerifier.create(receivedResponseMono).consumeNextWith(receivedResponse->{
            assertEquals(reviewId, receivedResponse.getReviewId());
            assertEquals(movieInfoId, receivedResponse.getMovieInfoId());
            assertEquals(review, receivedResponse.getReview());
            assertEquals(rating, receivedResponse.getRating());
        }).verifyComplete();
       
        verify(reviewRepositoryMock,times(1)).findById(anyString());
        verify(movieReviewMapperMock,times(1)).toReviewResponse(any(Review.class));
    }

    @Test
    void testFind_Failure(){

        when(reviewRepositoryMock.findById(anyString())).thenReturn(Mono.empty());
        when(movieReviewMapperMock.toReviewResponse(any(Review.class))).thenReturn(ConstructUtils.constructReviewResponse(reviewId, movieInfoId,review,rating));

        StepVerifier.create(reviewService.find(reviewId)).expectError(ReviewNotFoundException.class).verify();
    
        verify(reviewRepositoryMock,times(1)).findById(anyString());
        verify(movieReviewMapperMock,times(0)).toReviewResponse(any(Review.class));        
    }

    @Test
    void testGetAll(){

       var expectedMovieReviewResponse = ConstructUtils.constructReviewResponse(reviewId, movieInfoId, review, rating);

        when(reviewRepositoryMock.findAll()).thenReturn(Flux.just(ConstructUtils.constructReview(reviewId, movieInfoId, review, rating)));
        when(movieReviewMapperMock.toReviewResponse(any(Review.class))).thenReturn(expectedMovieReviewResponse);

        Flux<ReviewResponse> receivedReviewsFlux = reviewService.getAll();

        StepVerifier.create(receivedReviewsFlux).expectNextCount(1).verifyComplete();

       verify(reviewRepositoryMock, times(1)).findAll();
       verify(movieReviewMapperMock,times(1)).toReviewResponse(any(Review.class));
    }

    @Test
    void testUpdateEntity_Success(){

        var updatedReview = "This review has been updated";
        var updatedRating = 3.5;
         
        when(reviewRepositoryMock.findById(anyString())).thenReturn(Mono.just(ConstructUtils.constructReview(reviewId, movieInfoId, review, rating)));
        when(reviewRepositoryMock.save(any(Review.class))).thenReturn(Mono.just(ConstructUtils.constructReview(reviewId, movieInfoId, updatedReview, updatedRating)));
        when(movieReviewMapperMock.toReviewResponse(any(Review.class))).thenReturn(ConstructUtils.constructReviewResponse(reviewId, movieInfoId, updatedReview, updatedRating));

        Mono<ReviewResponse> updatedReviewMono = reviewService.updateEntity(Mono.just(ConstructUtils.constructUpdateReviewPayload(reviewId, movieInfoId, updatedReview, updatedRating)));
       
        StepVerifier.create(updatedReviewMono).consumeNextWith(updatedReviewResponse->{
            assertEquals(reviewId, updatedReviewResponse.getReviewId());
            assertEquals(movieInfoId, updatedReviewResponse.getMovieInfoId());
            assertEquals(updatedReview, updatedReviewResponse.getReview());
            assertEquals(updatedRating, updatedReviewResponse.getRating());
        }).verifyComplete();

       verify(reviewRepositoryMock,times(1)).findById(anyString());
       verify(reviewRepositoryMock,times(1)).save(any(Review.class));
       verify(movieReviewMapperMock,times(1)).toReviewResponse(any(Review.class));
    }

    @Test
    void testUpdateEntity_Failure(){

         var updatedReview = "This review has been updated";
         var updatedRating = 3.6;

         when(reviewRepositoryMock.findById(anyString())).thenReturn(Mono.empty());
         when(reviewRepositoryMock.save(any(Review.class))).thenReturn(Mono.just(ConstructUtils.constructReview(reviewId, movieInfoId, updatedReview, updatedRating)));
         when(movieReviewMapperMock.toReviewResponse(any(Review.class))).thenReturn(ConstructUtils.constructReviewResponse(reviewId, movieInfoId, updatedReview, updatedRating));

         Mono<ReviewResponse>  updatedReviewResponse =  reviewService.updateEntity(Mono.just(ConstructUtils.constructUpdateReviewPayload(reviewId, movieInfoId, updatedReview, updatedRating)));
         StepVerifier.create(updatedReviewResponse).expectError(ReviewNotFoundException.class).verify();

         verify(reviewRepositoryMock,times(1)).findById(anyString());
         verify(reviewRepositoryMock,times(0)).save(any(Review.class));   
         verify(movieReviewMapperMock, times(0)).toReviewResponse(any(Review.class));   
        
        }

    @Test
    void testDelete_Success(){

        when(reviewRepositoryMock.findById(anyString())).thenReturn(Mono.just(ConstructUtils.constructReview(reviewId, movieInfoId, review, rating)));
        when(reviewRepositoryMock.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<DeleteReviewResponse> deleteReviewResponseMono = reviewService.delete(reviewId);

        StepVerifier.create(deleteReviewResponseMono).consumeNextWith(deleteReviewResponse-> {
            assertEquals(reviewId, deleteReviewResponse.getReviewId());
            assertEquals(String.format("The Requested review (id:- %s) has been successfully deleted.",reviewId), deleteReviewResponse.getMessage());
        }).verifyComplete();

        verify(reviewRepositoryMock,times(1)).findById(anyString());
        verify(reviewRepositoryMock,times(1)).deleteById(anyString());

    }

    @Test
    void testDelete_Failure(){

        when(reviewRepositoryMock.findById(anyString())).thenReturn(Mono.empty());
        when(reviewRepositoryMock.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<DeleteReviewResponse> deleteReviewResponseMono = reviewService.delete(reviewId);

        StepVerifier.create(deleteReviewResponseMono).expectError(ReviewNotFoundException.class).verify();
        verify(reviewRepositoryMock,times(1)).findById(anyString());
        verify(reviewRepositoryMock,times(0)).deleteById(anyString());
    }

    @Test
    void testFindForId_ReturnsListOfReviews(){
        
        when(reviewRepositoryMock.findByMovieInfoId(anyString())).thenReturn(Flux.just(ConstructUtils.constructReview(reviewId, movieInfoId, review, rating)));
        when(movieReviewMapperMock.toReviewResponse(any(Review.class))).thenReturn(ConstructUtils.constructReviewResponse(reviewId, movieInfoId, review, rating));
        
        Flux<ReviewResponse> reviewsForMovieFlux = reviewService.findForId(movieInfoId);
        
        StepVerifier.create(reviewsForMovieFlux).expectNextCount(1).verifyComplete();

        verify(reviewRepositoryMock, times(1)).findByMovieInfoId(anyString());
        verify(movieReviewMapperMock,times(1)).toReviewResponse(any(Review.class));
    }

    @Test
    void testFindForId_ReturnsEmptyListOfReviews(){
        when(reviewRepositoryMock.findByMovieInfoId(anyString())).thenReturn(Flux.just());
        when(movieReviewMapperMock.toReviewResponse(any(Review.class))).thenReturn(ConstructUtils.constructReviewResponse(reviewId, movieInfoId, review, rating));
        
        Flux<ReviewResponse> reviewsForMovieFlux = reviewService.findForId(movieInfoId);
        
        StepVerifier.create(reviewsForMovieFlux).expectNextCount(0).verifyComplete();

        verify(reviewRepositoryMock, times(1)).findByMovieInfoId(anyString());
        verify(movieReviewMapperMock,times(0)).toReviewResponse(any(Review.class));
    }


}
