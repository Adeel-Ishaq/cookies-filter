package com.quantcast.activecookie;

import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.model.LogItem;
import com.quantcast.activecookie.parser.CsvParser;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CsvParserTests {

    @Test
    public void csvToLogs_parseCorrectly() throws Exception {
       var fileInputStream = new FileInputStream("src/logs/cookie_log.csv");
        List<LogItem> cookieEntryList = CsvParser.csvToLogs(fileInputStream);
        assertThat(cookieEntryList.size()==8);
    }

    @Test
    public void csvToLogs_InvalidFilePath_ThrowException() throws Exception {
        assertThatThrownBy(() -> new FileInputStream("src/logs/cookie.csv"))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    public void csvToLogs_InvalidDateFormat_ThrowException() throws Exception {
        var fileInputStream = new FileInputStream("src/logs/cookie_wrongFormatDate.csv");
        assertThatThrownBy(() -> CsvParser.csvToLogs(fileInputStream))
                .isInstanceOf(RuntimeException.class);
    }
}
