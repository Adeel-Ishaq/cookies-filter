package com.quantcast.activecookie.parser;

import com.quantcast.activecookie.exception.ParsingException;
import com.quantcast.activecookie.model.CommandItem;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import static java.time.LocalDate.parse;

import org.apache.commons.cli.*;

public class CommandParser extends LogParser {
    /** Parsing command line input */
    public static CommandItem parseCommandInput(String[] args) throws ParsingException {
        CommandItem commandInput = new CommandItem();
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = parseCommandOption();

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            commandInput.setFilePath(commandLine.getOptionValue("file"));
            commandInput.setChosenDate(parse(commandLine.getOptionValue("date")));
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
        Option fileName = new Option("f", "file", true, "The path of cookie log file");
        fileName.setRequired(true);
        commandOptions.addOption(fileName);

        // Selected date to get the most active cookie
        Option selectedDate =
                new Option("d", "date", true, "The specific date to get most active cookie");
        selectedDate.setRequired(true);
        commandOptions.addOption(selectedDate);

        return commandOptions;
    }

    /** Help message for command line options */
    private static void outputCommandHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Cookie Log Filter", options);
    }
}
