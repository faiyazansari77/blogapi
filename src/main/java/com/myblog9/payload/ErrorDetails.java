package com.myblog9.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorDetails {
    private Date date;
    private String message;
    private String description;
}
