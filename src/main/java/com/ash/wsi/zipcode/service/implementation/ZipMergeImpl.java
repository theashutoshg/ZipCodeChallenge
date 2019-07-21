package com.ash.wsi.zipcode.service.implementation;

import com.ash.wsi.zipcode.domain.ZipRange;
import com.ash.wsi.zipcode.service.ZipMergeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ZipMergeImpl implements ZipMergeService {

    /**
     * Merge the zip according to the algo.
     *
     * @param zipRanges Collections of zipRanges to process
     * @return Returns the processed Collection of Zip Ranges
     */
    @Override
    public Collection<ZipRange> mergeZipRanges(Collection<ZipRange> zipRanges) {
        log.trace("Starting  mergeZipRanges(Collection<ZipRange> zipRanges)");
        if (null == zipRanges || zipRanges.size() == 0) {
            return Collections.EMPTY_LIST;
        }

        SortedSet<ZipRange> sortedSet = new TreeSet<>(zipRanges);

        ZipRange previousZipRange = null;
        Iterator<ZipRange> iterator = sortedSet.iterator();

        while (iterator.hasNext()) {
            ZipRange zipRange = iterator.next();
            if (previousZipRange == null) {
                previousZipRange = zipRange;
            } else if (isExclusive(previousZipRange, zipRange)) {
                previousZipRange = zipRange;
            } else if (isInclusiveRange(previousZipRange, zipRange)) {
                iterator.remove();
            } else {
                previousZipRange.setUpperBound(zipRange.getUpperBound());
                iterator.remove();
            }
        }
        log.trace("Ending  mergeZipRanges(Collection<ZipRange> zipRanges)");
        return sortedSet;
    }

    private boolean isInclusiveRange(ZipRange smallerZipRange, ZipRange largerZipRange) {
        return smallerZipRange.getUpperBound() >= largerZipRange.getUpperBound();
    }

    private boolean isExclusive(ZipRange smallerZipRange, ZipRange largerZipRange) {
        return smallerZipRange.getUpperBound() < largerZipRange.getLowerBound();
    }
}
