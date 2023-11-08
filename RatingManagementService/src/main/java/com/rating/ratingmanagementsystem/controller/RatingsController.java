package com.rating.ratingmanagementsystem.controller;

import com.rating.ratingmanagementsystem.entity.Rating;
import com.rating.ratingmanagementsystem.service.RatingService;
import com.rating.ratingmanagementsystem.validation.RatingsValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;

@RestController
@RequestMapping("/api/rating")
@AllArgsConstructor
public class RatingsController {

    private RatingService ratingService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitRating(@RequestBody Rating rating)throws RuntimeException
    {
        RatingsValidator r = new RatingsValidator();
        if(r.validateForRange(rating.getRating())&& r.validateForValue(rating.getRating()))
            return new ResponseEntity<>(ratingService.submitRating(rating), HttpStatus.CREATED);
        if(!r.validateForRange(rating.getRating()))
            return new ResponseEntity<>("Kindly give a rating between 0 and 5", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("This rating is not acceptable. Please provide rating as a multiple of 0.5", HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRating(@PathVariable String id,
                               @RequestBody Rating rating)
    {
        RatingsValidator r = new RatingsValidator();
        if(r.validateForRange(rating.getRating())&& r.validateForValue(rating.getRating()))
            return new ResponseEntity<>(ratingService.updateRating(id,rating),HttpStatus.OK);
        if(!r.validateForRange(rating.getRating()))
            return new ResponseEntity<>("Kindly give a rating between 0 and 5", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("This rating is not acceptable. Please provide rating as a multiple of 0.5", HttpStatus.BAD_REQUEST);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable String id)
    {

        return new ResponseEntity<>(ratingService.deleteRating(id),HttpStatus.OK);
    }

//    @GetMapping("/count")
//    public ResponseEntity<?> noOfRatings()
//    {
//
//        return new ResponseEntity<>(ratingService.countRatings(),HttpStatus.OK);
//    }

    @GetMapping("/avg")
    public ResponseEntity<?> averageRating()
    {  if(ratingService.countRatings()==0)
    {
        return new ResponseEntity<>(ratingService.countRatings(),HttpStatus.NO_CONTENT);
    }
    DecimalFormat decimalFormat = new DecimalFormat("0.0");

    return new ResponseEntity<>(Double.parseDouble(decimalFormat.format(ratingService.avg())),HttpStatus.OK);
    }

    @GetMapping("/counter")
    public ResponseEntity<?> noOfRatingsByRating()
    {

        return new ResponseEntity<>(ratingService.countByRating(),HttpStatus.OK);
    }
}
