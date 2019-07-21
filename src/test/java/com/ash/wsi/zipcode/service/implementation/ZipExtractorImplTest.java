package com.ash.wsi.zipcode.service.implementation;

import com.ash.wsi.zipcode.domain.ZipRange;
import com.ash.wsi.zipcode.exception.InvalidException;
import com.ash.wsi.zipcode.exception.ParamException;
import com.ash.wsi.zipcode.service.ZipExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ZipExtractorImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ZipExtractorService classUnderTest;
    private ZipRange testZipRange;
    private String[] testZipRangesArr;

    @Before
    public void setUp() {
        testZipRange = new ZipRange(45655, 46656);
        classUnderTest = new ZipExtractorImpl();
        testZipRangesArr = new String[]{"[64588,95586]", "[45112,46556]", "[66356,67556]"};
    }

    @Test
    public void testZipRange_Successfully() {
        Set<ConstraintViolation<ZipRange>> constraintViolations =
                classUnderTest.validateZipRange(testZipRange);
        Assert.assertTrue("Failed to validate", constraintViolations.isEmpty());
    }

    @Test
    public void testZipRangeWithViolation() {
        testZipRange = new ZipRange(0, 456596);
        Set<ConstraintViolation<ZipRange>> constraintViolations =
                classUnderTest.validateZipRange(testZipRange);
        log.error(
                "Violations -> {}",
                constraintViolations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(",")));
        Assert.assertFalse("Expect violation but it processed", constraintViolations.isEmpty());
    }
    @Test
    public void testExtractZipRange() {
        Collection<ZipRange> extractZipRange = classUnderTest.extractZipRange(testZipRangesArr);
        log.error(
                "Processed Zip Ranges - {}",
                extractZipRange.stream().map(ZipRange::toString).collect(Collectors.joining(" ")));
        Assert.assertFalse("Ranges are empty which is not expected.", extractZipRange.isEmpty());
    }

    @Test
    public void testExtractZipRangeFailed() {
        Collection<ZipRange> extractZipRange = classUnderTest.extractZipRange(testZipRangesArr);
        log.error(
                "Processed Zip Ranges - {}",
                extractZipRange.stream().map(ZipRange::toString).collect(Collectors.joining(" ")));
        Assert.assertFalse("Ranges are empty which is not expected.", extractZipRange.isEmpty());
    }

    @Test
    public void testExtractZipRangeFailedInvalidParam() {
        testZipRangesArr = new String[]{};
        expectedException.expect(ParamException.class);
        expectedException.expectMessage("Invalid input param exception");
        Collection<ZipRange> extractZipRange = classUnderTest.extractZipRange(testZipRangesArr);
        log.error(
                "Processed Zip Ranges - {}",
                extractZipRange.stream().map(ZipRange::toString).collect(Collectors.joining(" ")));
        Assert.assertFalse("Ranges are empty which is not expected.", extractZipRange.isEmpty());
    }

    @Test
    public void testExtractZipRangeInvalidException() {
        testZipRangesArr = new String[]{"[64588,955861]", "[45112,46556]", "[66356,67556]"};
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Invalid parameter");
        Collection<ZipRange> extractZipRange = classUnderTest.extractZipRange(testZipRangesArr);
        log.error(
                "Processed Zip Ranges - {}",
                extractZipRange.stream().map(ZipRange::toString).collect(Collectors.joining(" ")));
        Assert.assertFalse("Ranges are empty which is not expected.", extractZipRange.isEmpty());
    }
    @Test
    public void testExtractZipRangeInvalidExceptionAndViolations() {
        testZipRangesArr = new String[]{"[00000,95586]", "[45112,46556]", "[66356,67556]"};
        expectedException.expect(InvalidException.class);
        expectedException.expectMessage("Zip should be between");
        Collection<ZipRange> extractZipRange = classUnderTest.extractZipRange(testZipRangesArr);
        log.error(
                "Processed Zip Ranges - {}",
                extractZipRange.stream().map(ZipRange::toString).collect(Collectors.joining(" ")));
        Assert.assertFalse("Ranges are empty which is not expected.", extractZipRange.isEmpty());
    }
}
