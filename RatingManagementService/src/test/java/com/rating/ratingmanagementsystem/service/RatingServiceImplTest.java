package com.rating.ratingmanagementsystem.service;

import com.rating.ratingmanagementsystem.entity.Rating;
import com.rating.ratingmanagementsystem.exception.RatingsException;
import com.rating.ratingmanagementsystem.repo.RatingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;
    private RatingService ratingService;
    AutoCloseable autoCloseable;
    Rating rating;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        ratingService = new RatingServiceImpl(ratingRepository);
        rating = new Rating();
        rating.setRating(5.0);
        rating.setId("123");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testSubmitRating() {
        mock(Rating.class);
        mock(RatingRepository.class);

        when(ratingRepository.save(rating)).thenReturn(rating);
        assertThat(ratingService.submitRating(rating)).
                isEqualTo(rating);

    }
    @Test
    void testSubmitException1Rating() {
        rating.setRating(6.0);
        Assertions.assertThrows(RatingsException.class,
                () -> ratingService.submitRating(rating));

    }
    @Test
    void testSubmitException2Rating() {
        rating.setRating(4.3);
        Assertions.assertThrows(RatingsException.class,
                () -> ratingService.submitRating(rating));

    }
//
    @Test
    void testUpdateRating() {
        doReturn(Optional.of(rating)).when(ratingRepository).findById(any());
        doReturn(rating).when(ratingRepository).save(any());

        Rating rating1 = ratingService.updateRating("123", rating);
        assertEquals(rating.getRating(), rating1.getRating());

    }
    @Test
    void testUpdateExceptionRating() {

        Assertions.assertThrows(RatingsException.class,
                () -> ratingService.updateRating("1",rating));

    }
    @Test
    void testUpdateException1Rating() {
        doReturn(Optional.of(rating)).when(ratingRepository).findById("123");
        doReturn(rating).when(ratingRepository).save(rating);
        rating.setRating(4.2);
       // Rating rating1 = ratingService.updateRating("123", rating);
        Assertions.assertThrows(RatingsException.class,
                () -> ratingService.updateRating("123",rating));

    }

    @Test
    void testUpdateException2Rating() {
        doReturn(Optional.of(rating)).when(ratingRepository).findById("123");
        doReturn(rating).when(ratingRepository).save(rating);
        rating.setRating(5.2);
        // Rating rating1 = ratingService.updateRating("123", rating);
        Assertions.assertThrows(RatingsException.class,
                () -> ratingService.updateRating("123",rating));

    }
    @Test
    void testDeleteRating() {
        doReturn(Optional.of(rating)).when(ratingRepository).findById("123");
        doReturn(rating).when(ratingRepository).save(rating);

        String s = ratingService.deleteRating("123");
        assertEquals(s, "Rating with id:123 deleted");
    }
    @Test
    void testDeleteExceptionRating() {
        mock(Rating.class);
        mock(RatingRepository.class);
        doAnswer(Answers.CALLS_REAL_METHODS).when(
                ratingRepository).deleteById(any());

        Assertions.assertThrows(RatingsException.class,
                () -> ratingService.deleteRating("653aaf5819c4077c9fda64b0"));
    }


    @Test
    void avgRatings() {
        assertEquals(ratingService.avg(),0.0);
    }

    @Test
    void countByRating() {
      Map<Double,Integer> m = new HashMap<>();
        assertEquals(ratingService.countByRating(),m);
    }
}