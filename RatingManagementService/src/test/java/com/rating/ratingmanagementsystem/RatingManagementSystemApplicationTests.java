package com.rating.ratingmanagementsystem;


import com.rating.ratingmanagementsystem.entity.Rating;
import com.rating.ratingmanagementsystem.exception.RatingsException;
import com.rating.ratingmanagementsystem.repo.RatingRepository;
import com.rating.ratingmanagementsystem.service.RatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest()
@ExtendWith(SpringExtension.class)
@Import({RatingServiceImpl.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RatingManagementSystemApplicationTests {

@Autowired
    private RatingRepository ratingRepository;
@Autowired
    private RatingServiceImpl ratingService;
	@Test
	void testIntegrationSubmitRating() {
		Rating rating = new Rating();
		rating.setId("1");
		rating.setRating(1.5);
		Rating savedRating = ratingService.submitRating(rating);
		Rating foundRating = ratingRepository.findById("1").get();
        assertEquals(savedRating.getId(), foundRating.getId());
		assertEquals(savedRating.getRating(), foundRating.getRating());
	}
	@Test
	void testIntegrationUpdateRating() {
		Rating rating = new Rating();
		rating.setId("1");
		rating.setRating(2.5);

		ratingRepository.save(rating);
		rating.setRating(5.0);
		Rating updatedRating = ratingRepository.save(rating);
		assertEquals(updatedRating.getId(), rating.getId());
		assertEquals(updatedRating.getRating(), rating.getRating());
	}

	@Test
	void testIntegrationAvgRating() {
		Rating rating = new Rating();
		rating.setId("1");
		rating.setRating(2);


		ratingRepository.save(rating);


		assertEquals(ratingService.avg(),2.0);

	}
	@Test
	void testIntegrationCountByRating() {
		Rating rating = new Rating();
		rating.setId("1");
		rating.setRating(0.0);
		Rating rating2 = new Rating();
		rating.setId("2");
		rating.setRating(0.0);
		Rating rating3 = new Rating();
		rating.setId("3");
		rating.setRating(3);
		ratingRepository.save(rating);
		ratingRepository.save(rating2);
		ratingRepository.save(rating3);
		Map<Double,Integer> m = new HashMap<>();
		m.put(0.0,2);
		m.put(3.0,1);
		assertEquals(ratingService.countByRating(),m);

	}

	@Test
	void testIntegrationDeleteRating() {
		Rating rating = new Rating();
		rating.setId("1");
		rating.setRating(0.0);

		ratingRepository.save(rating);

		assertEquals(ratingService.deleteRating("1"),"Rating with id:1 deleted");

	}

	@Test
	void testIntegrationDeleteExceptionRating() {
		Rating rating = new Rating();
		rating.setId("1");
		rating.setRating(0.0);

		ratingRepository.save(rating);

		assertThrows(RatingsException.class,() -> ratingService.deleteRating("2"));

	}

	@Test
	void testIntegrationUpdateExceptionRating() {
		Rating rating = new Rating();
		rating.setId("1");
		rating.setRating(0.0);

		ratingRepository.save(rating);

		assertThrows(RatingsException.class,() -> ratingService.updateRating("2",rating));

	}
}
