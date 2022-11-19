package com.quantcast.activecookie.executor;

import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.filter.CookieFilter;
import com.quantcast.activecookie.model.CommandItem;
import com.quantcast.activecookie.parser.CommandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.quantcast.activecookie.executor.ProcessStatus.*;

/** Implementation for the interface ProcessExecutor */
public class ProcessExecutorImpl implements ProcessExecutor {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutorImpl.class);
  private CookieFilter cookieFilter;

  public ProcessExecutorImpl(CookieFilter cookieFilter) {
    this.cookieFilter = cookieFilter;
  }

  @Override
  public int executeProcess(String[] args) {
    try {
      CommandItem commandInput = CommandParser.parseCommandInput(args);
      cookieFilter.filterMostActiveCookies(commandInput);
      return SUCCESS.getValue();
    } catch (ParsingException | RuntimeException e) {
      LOGGER.error("Program failed!", e);
    }
    return PROGRAM_FAILED.getValue();
  }
}
