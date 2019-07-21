package com.ash.wsi.zipcode;

import com.ash.wsi.zipcode.domain.ZipRange;
import com.ash.wsi.zipcode.handlers.CommandLineProcessor;
import com.ash.wsi.zipcode.service.ZipExtractorService;
import com.ash.wsi.zipcode.service.implementation.ZipMergeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@SpringBootApplication
public class ZipCodeChallengeApplication implements CommandLineRunner {

    @Autowired
    private CommandLineProcessor argsProcessor;

    @Autowired
    private ZipExtractorService zipExtractorService;

    @Autowired
    private ZipMergeImpl mergeService;

    public static void main(String[] args) {
        SpringApplication.run(ZipCodeChallengeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Args received count {}", args.length);

        String[] inputParams = argsProcessor.inputProcessor(args);

        Collection<ZipRange> mergeZipRanges =
                mergeService.mergeZipRanges(zipExtractorService.extractZipRange(inputParams));

        log.info(
                "Consolidated Zip Ranges -  {}",
                mergeZipRanges.stream().map(ZipRange::toString).collect(Collectors.joining(" ")));
    }
}
