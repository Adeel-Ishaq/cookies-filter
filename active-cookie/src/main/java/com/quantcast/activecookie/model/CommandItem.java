package com.quantcast.activecookie.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommandItem {
    private String filePath;
    private LocalDate chosenDate;
}
