package com.quantcast.activecookie.filter;

import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.model.CommandItem;
import com.quantcast.activecookie.model.LogItem;
import com.quantcast.activecookie.parser.CsvParser;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/** Implementation for the interface Filter */
@Service
public class CookieFilterImpl implements CookieFilter {
  @Override
  public void filterMostActiveCookies(CommandItem commandInput) throws ParsingException {

    InputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(commandInput.getFilePath());
    } catch (FileNotFoundException e) {
      throw new ParsingException(e);
    }
    // Parse input and take the list of cookie entries
    List<LogItem> cookieEntryList = CsvParser.csvToLogs(fileInputStream);

    // Group Cookies by date
    Map<LocalDate, List<LogItem>> groupOfCookiesByDate = cookieEntryList.stream()
            .collect(Collectors.groupingBy(item -> item.getTimestamp().toLocalDate()));

    // cookies against chosen date
    var chosenDate = commandInput.getChosenDate();

    // Group cookies by selected date
    List<String> cookiesOfChosenDate = groupOfCookiesByDate.getOrDefault(chosenDate, new ArrayList<LogItem>()).stream().map(LogItem::getCookie)
            .collect(Collectors.toList());

    // Use HashMap to count the frequencies of cookies, <key, value> = <cookie, number of occurrence> */
    Map<String, Long> frequencies = getFrequencyMap(cookiesOfChosenDate);

    long max = getMaxFrequency(frequencies);
    System.out.println("maximum frequency = " + max);

    // Output the most active cookies to the terminal
    mostFrequentlyDuplicateLetters(frequencies, max).forEach(System.out::println);
  }

  public static Map<String, Long> getFrequencyMap(List<String> letters) {
    return letters.stream()
            .collect(Collectors.groupingBy(UnaryOperator.identity(), Collectors.counting()));
  }

  /**
   * Return the frequency of most active cookies, which is the max values of all the occurrences
   */
  public static long getMaxFrequency(Map<String, Long> frequencies) {
    return frequencies.values().stream()
            .mapToLong(Long::longValue)
            .max()
            .orElse(0);
  }

  /**
   * Scan through the list of cookies and output the ones which have the max frequency value
   */
  public static List<String> mostFrequentlyDuplicateLetters(Map<String, Long> frequencies,
                                                            long frequency) {
    return frequencies.entrySet().stream()
            .filter(entry -> entry.getValue() == frequency)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
  }
}