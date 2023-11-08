package com.rating.ratingmanagementsystem.validation;

public class RatingsValidator {
    public boolean validateForRange(double rating)
    {
        if(rating < 0.0 || rating > 5.0)
            return false;
        return true;
    }
    public boolean validateForValue(double rating) {
        if(rating % 1 == 0 || rating % 0.5 == 0)
            return true;
        return false;
    }
}
