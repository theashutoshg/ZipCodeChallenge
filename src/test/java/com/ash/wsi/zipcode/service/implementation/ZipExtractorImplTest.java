package com.ash.wsi.zipcode.service.implementation;

import com.ash.wsi.zipcode.domain.ZipRange;
import com.ash.wsi.zipcode.service.ZipExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ZipExtractorImplTest {

    private ZipExtractorService classUnderTest;
    private ZipRange testZipRange;

    @Before
    public void setUp() {
        testZipRange = new ZipRange(45655, 46656);
        classUnderTest = new ZipExtractorImpl();
    }

    @Test
    public void testExtractZipRange() {
        Set<ConstraintViolation<ZipRange>> constraintViolations =
                classUnderTest.validateZipRange(testZipRange);
        Assert.assertTrue("Failed to validate", constraintViolations.isEmpty());
    }

    @Test
    public void testExtractZipRangeWithViolation() {
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
}
