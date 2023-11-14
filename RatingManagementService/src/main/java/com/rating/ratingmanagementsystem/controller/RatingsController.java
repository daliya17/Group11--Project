package com.rating.ratingmanagementsystem.controller;

import com.rating.ratingmanagementsystem.entity.Rating;
import com.rating.ratingmanagementsystem.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@AllArgsConstructor
public class RatingsController {

    private RatingService ratingService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitRating(@RequestBody Rating rating)
    {
            return new ResponseEntity<>(ratingService.submitRating(rating), HttpStatus.CREATED);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRating(@PathVariable String id,
                               @RequestBody Rating rating)
    {

            return new ResponseEntity<>(ratingService.updateRating(id,rating),HttpStatus.OK);


    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable String id)
    {

        return new ResponseEntity<>(ratingService.deleteRating(id),HttpStatus.OK);
    }

    @GetMapping("/avg")
    public ResponseEntity<?> averageRating()
    {


    return new ResponseEntity<>(ratingService.avg(),HttpStatus.OK);
    }

    @GetMapping("/counter")
    public ResponseEntity<?> noOfRatingsByRating()
    {

        return new ResponseEntity<>(ratingService.countByRating(),HttpStatus.OK);
    }
}
