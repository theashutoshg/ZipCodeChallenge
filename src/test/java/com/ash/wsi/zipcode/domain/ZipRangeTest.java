package com.ash.wsi.zipcode.domain;

import com.ash.wsi.zipcode.exception.ParamException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.*;

public class ZipRangeTest {

    @Rule
    public ExpectedException expect = ExpectedException.none();

    private ZipRange classUnderTest = null;
    private String expected = null;

    @Before
    public void setUp() {
        classUnderTest = new ZipRange(55664, 66544);
        expected = "[" + classUnderTest.getLowerBound() + "," + classUnderTest.getUpperBound() + "]";
    }

    @Test
    public void testToString() {
        assertEquals(expected, classUnderTest.toString());
    }

    @Test
    public void testEquals() {
        ZipRange zipRange = new ZipRange(55664, 66544);
        boolean isEquals = classUnderTest.equals(zipRange);
        assertTrue("Failed to check the equality", isEquals);
    }

    @Test
    public void testZipCode() {

        ZipRange zipRange = new ZipRange(66455, 67744);

        assertNotNull("Zip failed to create", (zipRange));
    }

    @Test
    public void testValidZipCodeRange() {
        expect.expect(ParamException.class);
        expect.expectMessage("Param not valid. Lower bound has to be lower then upper bound.");

        // Prepare the data which is invalid
        new ZipRange(66455, 66444);
    }

    @Test
    public void checkZipMinLengthValid() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        classUnderTest = new ZipRange(0, 66444);

        Set<ConstraintViolation<ZipRange>> constraintViolations = validator.validate(classUnderTest);

        assertTrue(constraintViolations.size() > 0);
        assertEquals(
                Constants.CONTRAINTS_RANGE_MESSAGE,
                ((ConstraintViolation) constraintViolations.toArray()[0]).getMessage());
    }
}
