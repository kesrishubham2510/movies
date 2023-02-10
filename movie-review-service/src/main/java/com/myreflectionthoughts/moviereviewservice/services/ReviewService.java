package com.myreflectionthoughts.moviereviewservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myreflectionthoughts.moviereviewservice.contracts.DeleteEntity;
import com.myreflectionthoughts.moviereviewservice.contracts.FindAll;
import com.myreflectionthoughts.moviereviewservice.contracts.FindOne;
import com.myreflectionthoughts.moviereviewservice.contracts.FindReviews;
import com.myreflectionthoughts.moviereviewservice.contracts.SaveEntity;
import com.myreflectionthoughts.moviereviewservice.contracts.UpdateEntity;
import com.myreflectionthoughts.moviereviewservice.dto.request.AddReview;
import com.myreflectionthoughts.moviereviewservice.dto.request.UpdateReview;
import com.myreflectionthoughts.moviereviewservice.dto.response.DeleteReviewResponse;
import com.myreflectionthoughts.library.dto.response.ReviewResponse;
import com.myreflectionthoughts.moviereviewservice.exceptions.ReviewNotFoundException;
import com.myreflectionthoughts.moviereviewservice.repositories.ReviewRepository;
import com.myreflectionthoughts.moviereviewservice.utils.MovieReviewMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReviewService implements 
SaveEntity<AddReview,ReviewResponse>,
FindOne<ReviewResponse>,
FindAll<ReviewResponse>,
UpdateEntity<UpdateReview, ReviewResponse>,
DeleteEntity<DeleteReviewResponse>,
FindReviews<ReviewResponse>
{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieReviewMapper movieReviewMapper;

    @Override
    public Mono<ReviewResponse> save(Mono<AddReview> reqDTO) {
        return reqDTO.map(movieReviewMapper::toEntity).flatMap(reviewRepository::save).map(movieReviewMapper::toReviewResponse);
    }
    
    @Override
    public Mono<ReviewResponse> find(String id) {
        return reviewRepository
                               .findById(id)
                               .map(movieReviewMapper::toReviewResponse)
                               .switchIfEmpty(
                                    Mono.error(new ReviewNotFoundException(String.format("The Requested review (id:- %s) does not exist",id)))
                                );
    }

    @Override
    public Flux<ReviewResponse> getAll() {
        return reviewRepository.findAll().map(movieReviewMapper::toReviewResponse);
    }
 
    @Override
    public Mono<ReviewResponse> updateEntity(Mono<UpdateReview> reqDTO) {
        return reqDTO.flatMap(updateReviewReq->{
           return reviewRepository.findById(updateReviewReq.getReviewId()).flatMap(existingReview->{
                    // changing the possible values
                    existingReview.setRating(updateReviewReq.getRating());
                    existingReview.setReview(updateReviewReq.getReview()); 
                    return reviewRepository.save(existingReview).map(movieReviewMapper::toReviewResponse);
           }).switchIfEmpty(
            Mono.error(new ReviewNotFoundException(String.format("The Requested review (id:- %s) does not exist",updateReviewReq.getReviewId())))
           );
        }) ;
    }

    @Override
    public Mono<DeleteReviewResponse> delete(String id) {
        return reviewRepository.findById(id).flatMap(reviewToDelete->{
             var deleteReviewResponse  = new DeleteReviewResponse();
             deleteReviewResponse.setReviewId(id);
             deleteReviewResponse.setMessage(String.format("The Requested review (id:- %s) has been successfully deleted.",id));
             return reviewRepository.deleteById(id).thenReturn(deleteReviewResponse);
        }).switchIfEmpty(Mono.error( new ReviewNotFoundException(String.format("The Requested review (id:- %s) does not exist",id)))) ;

    }

    @Override
    public Flux<ReviewResponse> findForId(String id) {
        // Method returns all the existing reviews for a movie
        // Removed error throwing in case of empty reviews
        return  reviewRepository.findByMovieInfoId(id).map(movieReviewMapper::toReviewResponse);
    }

}