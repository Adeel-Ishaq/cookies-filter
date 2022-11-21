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
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/** Implementation for the interface Filter */
@Service
public class CookieFilterImpl implements CookieFilter {
  @Override
  public void filterMostActiveCookies(CommandItem commandInput) throws ParsingException {

    InputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(commandInput.getFilePath());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    // Parse input and take the list of cookie entries
    List<LogItem> cookieEntryList = CsvParser.csvToLogs(fileInputStream);

    // Group Cookies by date
    Map<LocalDate, List<LogItem>> groupOfCookiesByDate = cookieEntryList.stream()
            .collect(Collectors.groupingBy(item -> item.getTimestamp().toLocalDate()));

    // cookies against chosen date
    var chosenDate = commandInput.getChosenDate();
    List<String> cookiesOfChosenDate = groupOfCookiesByDate.get(chosenDate).stream().map(LogItem::getCookie)
            .collect(Collectors.toList());

    Map<String, Long> frequencies = getFrequencyMap(cookiesOfChosenDate);
    long max = getMaxFrequency(frequencies);
    System.out.println("maximum frequency = " + max);
    mostFrequentlyDuplicateLetters(frequencies, max).forEach(System.out::println);
    // Group cookies by selected date
//    Map<String, Long> groupCookieByDate =
//        groupCookieByDate(commandInput.getChosenDate(), cookieEntryList);
//
//    // Calculate the frequency of most active cookies
//    OptionalLong mostActiveCookieFreq = mostActiveCookieFreq(groupCookieByDate);
//
//    // Scan through the list of cookies and output the ones which have the max frequency value
//    mostActiveCookieFreq.ifPresent(maxFreq -> outputMostActiveCookies(groupCookieByDate, maxFreq));
  }

  public static Map<String, Long> getFrequencyMap(List<String> letters) {
    return letters.stream()
            .collect(Collectors.groupingBy(UnaryOperator.identity(), Collectors.counting()));
  }

  public static long getMaxFrequency(Map<String, Long> frequencies) {
    return frequencies.values().stream()
            .mapToLong(Long::longValue)
            .max()
            .orElse(0);
  }

  public static List<String> mostFrequentlyDuplicateLetters(Map<String, Long> frequencies,
                                                            long frequency) {
    return frequencies.entrySet().stream()
            .filter(entry -> entry.getValue() == frequency)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
  }
}
//  /** Use HashMap to group cookie entries, <key, value> = <cookie date, number of occurrence> */
//  private Map<String, Long> groupCookieByDate(LocalDate selectedDate, List<LogItem> cookieEntryList) {
//    return cookieEntryList.stream()
//        .filter(logItem -> selectedDate.isEqual(logItem.getTimestamp().toLocalDate()))
//        .collect(groupingBy(LogItem::getCookie, counting()));
//  }
//
//  /** Return the frequency of most active cookies, which is the max values of all the occurrences */
//  private OptionalLong mostActiveCookieFreq(Map<String, Long> groupOfCookieByDate) {
//    return groupOfCookieByDate.values().stream().mapToLong(count -> count).max();
//  }
//
//  /** Output the most active cookies to the terminal */
//  private void outputMostActiveCookies(Map<String, Long> groupOfCookieByDate, long mostActiveCookieFreq) {
//    groupOfCookieByDate.entrySet().stream()
//        .filter(x -> x.getValue().equals(mostActiveCookieFreq))
//        .map(Map.Entry::getKey)
//        .forEach(System.out::println);
//  }
//}
