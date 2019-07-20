package com.ash.wsi.zipcode.handlers;

import org.apache.commons.cli.ParseException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CommandLineProcessorTest {

    @Rule
    public final ExpectedException expectedEx = ExpectedException.none();

    private CommandLineProcessor classUnderTest;

    private String args = null;

    @Before
    public void setUp() {
        args = "-z=[64588,95586] [45112,46556] [66356,67556]";
        classUnderTest = new CommandLineProcessor();
    }

    @Test
    public void testInputProcessor_successful() throws Exception {

        String[] processedData = classUnderTest.inputProcessor(args);

        assertTrue(processedData.length > 0);
        assertThat(processedData.length, Matchers.is(3));
    }

    @Test(expected = ParseException.class)
    public void testInputProcessor_invalid_params() throws Exception {
        args = "[64588,95586] [45112,46556] [66356,77556]";

        classUnderTest.inputProcessor(args);

        fail("Invalid params processed the params successfully.");
    }

    @Test
    public void testInputProcessor_invalid_params_with_exception() throws Exception {
        args = "[64588,95586] [45112,46556] [66356,62556]";

        expectedEx.expect(ParseException.class);
        expectedEx.expectMessage("Not enough parameters provided to process.");
        classUnderTest.inputProcessor(args);
    }
}
