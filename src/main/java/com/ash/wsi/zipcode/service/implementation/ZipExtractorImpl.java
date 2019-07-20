package com.ash.wsi.zipcode.service.implementation;

import com.ash.wsi.zipcode.domain.ZipRange;
import com.ash.wsi.zipcode.exception.InvalidException;
import com.ash.wsi.zipcode.exception.ParamException;
import com.ash.wsi.zipcode.service.ZipExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ZipExtractorImpl implements ZipExtractorService<ZipRange> {

    public static final Pattern ZIPCODE_PATTERN = Pattern.compile("\\[(\\d{5}),(\\d{5})\\]");

    /**
     * @param zipRange Input of zipRange
     * @return Return the collections of Zip Range
     * @throws ParamException   Invalid param exception
     * @throws InvalidException Invalid exception
     */
    @Override
    public Collection<ZipRange> extractZipRange(String[] zipRange)
            throws ParamException, InvalidException {
        Collection<ZipRange> zipRangeSet = new HashSet<>();

        if (zipRange == null) {
            // Invalid param exception for the zipRanges
            throw new ParamException("Invalid input param exception");
        } else {
            IntStream.range(0, zipRange.length)
                    .forEach(
                            index -> {
                                // Remove all the white spaces if there
                                Matcher matcher = ZIPCODE_PATTERN.matcher(zipRange[index].replaceAll(" ", ""));
                                if (matcher.find()) {
                                    final int lowerBound = Integer.parseInt(matcher.group(1));
                                    final int upperBound = Integer.parseInt(matcher.group(2));
                                    // Get zip code using constructor
                                    ZipRange range = new ZipRange(lowerBound, upperBound);

                                    // Validating the zip code
                                    Set<ConstraintViolation<ZipRange>> constraintViolations = validateZipRange(range);
                                    if (!CollectionUtils.isEmpty(constraintViolations)) {
                                        throw new InvalidException(
                                                constraintViolations.stream()
                                                        .map(ConstraintViolation::getMessage)
                                                        .collect(Collectors.joining(",")));
                                    }

                                    // if everything valid then add to collections
                                    zipRangeSet.add(range);
                                } else {
                                    throw new InvalidException(
                                            String.join(" ", "Invalid parameter{}", zipRange[index]));
                                }
                            });
        }
        return zipRangeSet;
    }
}
