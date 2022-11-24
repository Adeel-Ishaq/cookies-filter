package com.quantcast.activecookie;

import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.filter.CookieFilterImpl;
import com.quantcast.activecookie.model.CommandItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CookieFilterImplTests {
    private CookieFilterImpl cookieFilter;
    private PrintStream stdout = System.out;
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        cookieFilter = new CookieFilterImpl();
        PrintStream output = new PrintStream(byteArrayOutputStream);
        System.setOut(output);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(stdout);
    }

    @Test
    public void filterMostActiveCookies_ValidFilePathAndDate_OutputCookieStrings()
            throws ParsingException {
        CommandItem commandInput = new CommandItem("src/logs/cookie_log.csv", parse("2018-12-09"));
        cookieFilter.filterMostActiveCookies(commandInput);
        assertThat(byteArrayOutputStream.toString().contains("AtY0laUfhglK3lC7"));
        assertThat(!byteArrayOutputStream.toString().contains("SAZuXPGUrfbcn5UA"));
        assertThat(!byteArrayOutputStream.toString().contains("5UAVanZf6UtGyKVS"));
        assertThat(!byteArrayOutputStream.toString().contains("AtY0laUfhglK3lC7"));
    }

    @Test
    public void filterMostActiveCookies_HasMoreThanOneMostActiveCookies_OutputAllOfThem()
            throws ParsingException {
        CommandItem commandInput = new CommandItem("src/logs/cookie_log.csv", parse("2018-12-08"));
        cookieFilter.filterMostActiveCookies(commandInput);
        assertThat(byteArrayOutputStream.toString().contains("fbcn5UAVanZf6UtG"));
        assertThat(byteArrayOutputStream.toString().contains("4sMM2LxV07bPJzwf"));
        assertThat(byteArrayOutputStream.toString().contains("4sMM2LxV07bPJzwf"));
        assertThat(!byteArrayOutputStream.toString().contains("A8SADNasdNadBBda"));
    }

    @Test
    public void filterMostActiveCookies_InvalidFilePath_ThrowException() {
        CommandItem commandInput = new CommandItem("/src/logs/dummy.csv", parse("2018-12-09"));
        assertThatThrownBy(() -> cookieFilter.filterMostActiveCookies(commandInput))
                .isInstanceOf(ParsingException.class);
    }

    @Test
    public void filterMostActiveCookies_InvalidDate_ThrowException() throws ParsingException {
        CommandItem commandInput = new CommandItem("src/logs/cookie_log.csv", parse("2021-12-09"));
        cookieFilter.filterMostActiveCookies(commandInput);
        assertThat(byteArrayOutputStream.toString().isEmpty());
    }
}
