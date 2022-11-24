package com.quantcast.activecookie.parser;

import com.quantcast.activecookie.config.AppConfig;
import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.model.LogItem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class CsvParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvParser.class);

    private static final String TYPE = "text/csv";
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<LogItem> csvToLogs(InputStream is) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.builder().setHeader()
                             .setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build());) {

            var logItems = new ArrayList<LogItem>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                var logITem = new LogItem();
                logITem.setCookie(csvRecord.get(AppConfig.COOKIE_COLUMN));
                try {
                    DateTimeFormatter format = DateTimeFormatter.ISO_ZONED_DATE_TIME;
                    logITem.setTimestamp(LocalDateTime.parse(csvRecord.get(AppConfig.TIME_STAMP_COLUMN), format));
                } catch (DateTimeParseException e) {
                    throw new DateTimeParseException(e.getMessage(), "", 0, e);
                }
                logItems.add(logITem);
            }
            return logItems;
        } catch (IOException | DateTimeParseException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
