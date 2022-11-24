package com.quantcast.activecookie.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
//@EqualsAndHashCode
public class LogItem {
    private String cookie;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private LocalDateTime timestamp;
}
