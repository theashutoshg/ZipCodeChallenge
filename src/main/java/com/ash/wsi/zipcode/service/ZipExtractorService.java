package com.ash.wsi.zipcode.service;

import com.ash.wsi.zipcode.domain.ZipRange;
import com.ash.wsi.zipcode.exception.InvalidException;
import com.ash.wsi.zipcode.exception.ParamException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

public interface ZipExtractorService<T> {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * @param zipRange for extracting the zip range from raw input
     * @return Collections of the zip ranges
     * @throws ParamException   when there is invalid param exception.
     * @throws InvalidException when there is invalid exception
     */
    Collection<ZipRange> extractZipRange(String[] zipRange) throws ParamException, InvalidException;

    /**
     * Setting up the validation for all zip an default method so that no one need to implement.
     *
     * @param zipRange Range to validate
     * @return Set of constraints of ZipRanges
     * @throws InvalidException when there is invalid exception
     */
    default Set<ConstraintViolation<T>> validateZipRange(T zipRange) throws InvalidException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(zipRange);
        if (null != constraintViolations && constraintViolations.size() > 0) {
            return constraintViolations;
        }
        return constraintViolations;
    }
}
