package com.quantcast.activecookie.executor;

import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.filter.CookieFilter;
import com.quantcast.activecookie.model.CommandItem;
import com.quantcast.activecookie.parser.CommandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static java.lang.System.exit;

@Component
class CommandLineExecutor implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineRunner.class);

    @Autowired
    private CookieFilter cookieFilter;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Program Started !!");
        try {
            CommandItem commandInput = CommandParser.parseCommandInput(args);
            cookieFilter.filterMostActiveCookies(commandInput);
            LOGGER.info("Program Succeed !!");
            exit(SpringApplication.exit(applicationContext));
        } catch (ParsingException | RuntimeException e) {
            LOGGER.error("Program failed!", e);
        }
    }
}