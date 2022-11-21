package com.quantcast.activecookie.parser;

import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.config.AppConfig;
import com.quantcast.activecookie.model.CommandItem;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import static java.time.LocalDate.parse;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandParser.class);

    /** Parsing command line input */
    public static CommandItem parseCommandInput(String[] args) throws ParsingException {
        CommandItem commandInput = new CommandItem();
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = parseCommandOption();

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            commandInput.setFilePath(commandLine.getOptionValue(AppConfig.FILE_COMMAND));
            commandInput.setChosenDate(parse(commandLine.getOptionValue(AppConfig.DATE_COMMAND)));
            return commandInput;
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            outputCommandHelp(options);
            throw new ParsingException(e);
        }
    }

    /** Parsing command line options (file name and selected date) */
    private static Options parseCommandOption() {
        Options commandOptions = new Options();

        // File name of cookie log
        var fileName = Option.builder("f")
                .required(true)
                .desc("The path of cookie log file")
                .longOpt(AppConfig.FILE_COMMAND)
                .hasArg(true)
                .build();
        commandOptions.addOption(fileName);

        // Selected date to get the most active cookie
        var selectedDate = Option.builder("d")
                .required(true)
                .desc("The specific date to get most active cookie")
                .longOpt(AppConfig.DATE_COMMAND)
                .hasArg(true)
                .build();
        commandOptions.addOption(selectedDate);

        return commandOptions;
    }

    /** Help message for command line options */
    private static void outputCommandHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Cookie Log Filter", options);
    }
}
