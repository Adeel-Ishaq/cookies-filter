package com.quantcast.activecookie.filter;

import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.model.CommandItem;

/** Interface for filtering the most active cookies */
public interface CookieFilter {
  void filterMostActiveCookies(CommandItem commandInput) throws ParsingException;
}
