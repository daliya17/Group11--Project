package com.rating.ratingmanagementsystem.service;

import com.rating.ratingmanagementsystem.entity.Rating;
import com.rating.ratingmanagementsystem.exception.RatingsException;
import com.rating.ratingmanagementsystem.repo.RatingRepository;
import com.rating.ratingmanagementsystem.validation.RatingsValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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
//      return ratingRepository.save(rating);
        RatingsValidator r = new RatingsValidator();
        if(r.validateForRange(rating.getRating())&& r.validateForValue(rating.getRating()))
            return ratingRepository.save(rating);
        if(!r.validateForRange(rating.getRating()))
            throw new RatingsException("Kindly give a rating between 0 and 5");
        throw new RatingsException("This rating is not acceptable. Please provide rating as a multiple of 0.5");

    }

    @Override
    public Rating updateRating(String id, Rating rating)throws RatingsException {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if(optionalRating.isEmpty())
            throw new RatingsException("Rating with id: "+id+" not found");
        else {
            Rating toUpdateRating = optionalRating.get();
            toUpdateRating.setRating(rating.getRating());
            RatingsValidator r = new RatingsValidator();
            if (r.validateForRange(toUpdateRating.getRating()) && r.validateForValue(toUpdateRating.getRating())) {
                ratingRepository.save(toUpdateRating);
                return toUpdateRating;
            }

            if (!r.validateForRange(toUpdateRating.getRating()))
                throw new RatingsException("Kindly give a rating between 0 and 5");
            throw new RatingsException("This rating is not acceptable. Please provide rating as a multiple of 0.5");

        }

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
    public double avg() {

        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return Double.parseDouble(decimalFormat.format(ratingRepository.avg()));
    }

    @Override
    public Map<Double,Integer> countByRating() {

        List<Rating> ratingList =ratingRepository.findAll();
        Map<Double,Integer> map = new HashMap<>();
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
