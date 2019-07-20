package com.ash.wsi.zipcode.service;

import com.ash.wsi.zipcode.domain.ZipRange;

import java.util.Collection;

/**
 * Service for Merging the zip codes if the range conflict otherwise return as is.
 */
public interface ZipMergeService {
    Collection<ZipRange> mergeZipRanges(Collection<ZipRange> zipRanges);
}
