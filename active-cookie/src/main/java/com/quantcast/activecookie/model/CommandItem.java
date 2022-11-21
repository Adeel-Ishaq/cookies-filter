package com.quantcast.activecookie.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Data
public class CommandItem {
    @Autowired
    private String filePath;
    @Autowired
    private LocalDate chosenDate;

    public CommandItem() {}
    public CommandItem(String filePath, LocalDate chosenDate) {
        this.filePath = filePath;
        this.chosenDate = chosenDate;
    }
}
