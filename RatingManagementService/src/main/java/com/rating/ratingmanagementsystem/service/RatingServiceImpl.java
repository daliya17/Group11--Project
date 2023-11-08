package com.rating.ratingmanagementsystem.service;

import com.rating.ratingmanagementsystem.entity.Rating;
import com.rating.ratingmanagementsystem.exception.RatingsException;
import com.rating.ratingmanagementsystem.repo.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toMap;

//add exceptions & validations
@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;
    //private MongoTemplate mongoTemplate;
    @Override
    public Rating submitRating(Rating rating) {
      return ratingRepository.save(rating);

    }

    @Override
    public Rating updateRating(String id, Rating rating)throws RatingsException {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if(optionalRating.isPresent()){
       Rating toUpdateRating = optionalRating.get();
        toUpdateRating.setRating(rating.getRating());
        ratingRepository.save(toUpdateRating);
        return toUpdateRating;}
        throw new RatingsException("Rating with id: "+id+
                " not found");
    }


    @Override
    public String deleteRating(String id) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if(optionalRating.isPresent()){
            ratingRepository.deleteById(id);
            return "Rating with id:"+id+" deleted";
        }
        throw new RatingsException("Rating with id: "+id+
                " not found");



    }

    @Override
    public long countRatings() {
    return ratingRepository.count();
    }

    @Override
    public double avg() {
        return ratingRepository.avg();
    }

    @Override
    public Map<Double,Integer> countByRating() {

        List<Rating> ratingList =ratingRepository.findAll();
        Map<Double,Integer> map = new HashMap<>();
        StringBuilder s = new StringBuilder();
        for(Rating rating:ratingList)
        {  if(map.containsKey(rating.getRating()))
                map.put(rating.getRating(),map.get(rating.getRating())+1);
            else
                map.put(rating.getRating(),1);}

        return map.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

    }
}
