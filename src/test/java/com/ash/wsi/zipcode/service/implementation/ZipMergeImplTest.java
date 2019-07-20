package com.ash.wsi.zipcode.service.implementation;

import com.ash.wsi.zipcode.domain.ZipRange;
import com.ash.wsi.zipcode.exception.ParamException;
import com.ash.wsi.zipcode.service.ZipMergeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test class to verify the code for zip class
 */
public class ZipMergeImplTest {

    @Rule
    public ExpectedException expect = ExpectedException.none();
    private ZipMergeService classUnderTest = new ZipMergeImpl();
    private Collection<ZipRange> zipRanges;

    /**
     * Prepare test data
     */
    @Before
    public void setUp() {
        classUnderTest = new ZipMergeImpl();
        zipRanges = new HashSet<>();
        zipRanges.add(new ZipRange(56455, 56944));
        zipRanges.add(new ZipRange(86455, 86944));
        zipRanges.add(new ZipRange(46455, 96444));
    }

    /**
     * Find out the zipCode ranges which valid for this test
     */
    @Test
    public void testMergeZipRanges() {
        Collection<ZipRange> resultRange = classUnderTest.mergeZipRanges(this.zipRanges);

        assertThat(resultRange, notNullValue());
        assertTrue(resultRange.size() > 0);
        assertThat(resultRange.size(), is(1));
    }

    /**
     * Test to check the param exception
     */
    @Test
    public void testMergeZipRanges_param_exception() {

        expect.expect(ParamException.class);
        expect.expectMessage("Param not valid. Lower bound has to be lower then upper bound.");

        // Prepare the data which is invalid
        zipRanges.add(new ZipRange(66455, 66444));

        classUnderTest.mergeZipRanges(this.zipRanges);
    }
}
