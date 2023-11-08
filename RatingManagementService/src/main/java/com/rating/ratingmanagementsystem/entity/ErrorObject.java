package com.rating.ratingmanagementsystem.entity;

import lombok.Data;

@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
}
