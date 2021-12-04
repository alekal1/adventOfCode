package ee.alekal.adventOfCode.day1;

import java.util.List;

import static ee.alekal.adventOfCode.util.Constants.ZERO;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputAsInteger;
import static ee.alekal.adventOfCode.util.IntegerUtils.getInputsAsArray;
import static ee.alekal.adventOfCode.util.IntegerUtils.sumFirstThreeElements;

public class FindMeasurements {

    private static final String RESOURCE = "/day1/measurements.txt";
    private static final List<Integer> MEASUREMENTS = getInputAsInteger(RESOURCE);

    public static int findLarges() {
        var inputsAsArray = getInputsAsArray(MEASUREMENTS);
        var different = ZERO;
        for (int i = ZERO; i < inputsAsArray.length - 1; i++) {
            if (inputsAsArray[i] < inputsAsArray[i + 1]) different++;
        }
        return different;
    }

    public static int findSum() {
        var sumIsLarger = ZERO;
        while (MEASUREMENTS.size() > 3) {
            var currentSum = sumFirstThreeElements(MEASUREMENTS);

            MEASUREMENTS.remove(ZERO);

            var nextSum = sumFirstThreeElements(MEASUREMENTS);

            if (nextSum > currentSum) {
                sumIsLarger++;
            }

        }
        return sumIsLarger;
    }

    public static void main(String[] args) {
        LOG.debug("The answer for part one is {}", findLarges());
        LOG.debug("The answer for part two is {}", findSum());
    }
}
