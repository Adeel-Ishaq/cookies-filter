package com.quantcast.activecookie;

import com.quantcast.activecookie.config.AppConfig;
import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.filter.CookieFilterImpl;
import com.quantcast.activecookie.model.CommandItem;
import com.quantcast.activecookie.parser.CommandParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommandParserTests {

    @Test
    public void parseCommandInput_WithValidFilePathAndDate_ParseCorrectly()
            throws ParsingException {
        var commandItem = CommandParser.parseCommandInput(new String[]{"-f", "src/logs/cookie_log", "-d", "2018-12-09"});
        assertThat(commandItem.getFilePath().contains("AtY0laUfhglK3lC7"));

        DateTimeFormatter format = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        CharSequence givenDate = "2018-12-09T14:19:00+00:00";
        var expectedDate = LocalDateTime.parse( givenDate, format);
        assertThat(commandItem.getChosenDate().equals(expectedDate));
    }

    @Test
    public void parseCommandInput_WithInvalidOptions_ThrowsException() throws ParsingException {
        var args = new String[]{"-g", "src/logs/cookie_log", "-e", "2018-12-09"};
        assertThatThrownBy(() -> CommandParser.parseCommandInput(args))
                .isInstanceOf(ParsingException.class);
    }
}
