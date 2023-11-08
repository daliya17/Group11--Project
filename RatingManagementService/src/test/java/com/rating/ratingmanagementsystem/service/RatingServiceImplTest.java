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
    void testDeleteExceptionRating() {
        mock(Rating.class);
        mock(RatingRepository.class);
        doAnswer(Answers.CALLS_REAL_METHODS).when(
                ratingRepository).deleteById(any());

        Assertions.assertThrows(RatingsException.class,
                () -> ratingService.deleteRating("653aaf5819c4077c9fda64b0"));
    }
//
    @Test
    void countRatings() {

        mock(Rating.class);
        mock(RatingRepository.class);

        Rating rating1 = new Rating();
        rating1.setRating(4.0);
        Rating rating2 = new Rating();
        rating2.setRating(4.0);
        when(ratingRepository.count()).thenReturn(3L);
        assertThat(ratingService.countRatings()).
                isEqualTo(3L);
    }

    @Test
    void avgRatings() {
        mock(Rating.class);
        mock(RatingRepository.class);

        Rating rating1 = new Rating();
        rating1.setRating(4.0);
        Rating rating2 = new Rating();
        rating2.setRating(3.0);
        when(ratingRepository.avg()).thenReturn(4.0);
        assertThat(ratingService.avg()).
                isEqualTo(4.0);
    }

    @Test
    void countByRating() {
        mock(Rating.class);
        mock(RatingRepository.class);

        Rating rating1 = new Rating();
        rating1.setRating(4.0);
        Rating rating2 = new Rating();
        rating2.setRating(4.0);
        ratingRepository.save(rating1);
        ratingRepository.save(rating2);
        Map<Double,Integer> m = new HashMap<>();
        m.put(4.0,2);
        //m.put(3.0,1);
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating1);
        ratingList.add(rating2);
        when(ratingRepository.findAll()).thenReturn(ratingList);
        assertThat(ratingService.countByRating()).
                isEqualTo(m);
    }
}