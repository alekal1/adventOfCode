package ee.alekal.adventOfCode.day8;

import ee.alekal.adventOfCode.day8.dto.SegmentObject;

import java.util.List;

import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputAsSegmentObjects;

public class SegmentSearch {
    private static final List<SegmentObject> segmentObjects = getInputAsSegmentObjects("/day8/segments.txt");

    private static int countOutputValues(List<Integer> searchNumbers) {
        int outputCounter = 0;
        for (var obj : segmentObjects) {
            for (var output : obj.getSegmentsOutput()) {
                if (searchNumbers.contains(output.getRepresents())) {
                    outputCounter++;
                }
            }
        }
        return outputCounter;
    }

    public static void main(String[] args) {
        LOG.debug("Answer for part one is {}", countOutputValues(List.of(1, 4, 7, 8)));
    }
}
