package com.ash.wsi.zipcode.handlers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class CommandLineProcessor {
    /**
     * Added to process the cmd line parameters for input
     *
     * @param args cmd line args
     * @return String[] of processed param value
     * @throws ParseException Failed to parse
     */
    public String[] inputProcessor(String... args) throws ParseException {
        return validateAndFetchOptions(args);
    }

    private String[] validateAndFetchOptions(String[] args) throws ParseException {
        log.trace("Started validateAndFetchOptions(String[] args)");
        CommandLine cmd;
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        Option zipCodeRangeOptions = zipCodeRangeOptions();
        Options options = new Options();
        options.addOption(zipCodeRangeOptions);
        cmd = parser.parse(options, args);
        if (cmd.getOptionValues("z") == null || cmd.getOptions().length == 0) {
            formatter.printHelp("Zip code range option", options);
            log.error("Failed to process the params of length {}", args.length);
            throw new ParseException("Not enough parameters provided to process.");
        }

        log.info("Parsed the values and returning the array params");
        log.trace("End validateAndFetchOptions(String[] args)");
        return cmd.getOptionValue("z").split(" ");
    }

    /**
     * Building the option parser using builder pattern
     *
     * @return
     */
    private Option zipCodeRangeOptions() {
        return Option.builder("z")
                .longOpt("zipcodes")
                .argName("[zipcode1-zipcode2] [zipcode1-zipcode3] [zipcode4-zipcode5]")
                .desc("Zip Code range")
                .valueSeparator('=')
                .hasArg()
                .numberOfArgs(5) // sets that number of arguments is unlimited
                .build();
    }
}
