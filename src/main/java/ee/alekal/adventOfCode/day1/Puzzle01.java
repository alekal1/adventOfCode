package ee.alekal.adventOfCode.day1;

import static ee.alekal.adventOfCode.util.Constants.ZERO;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getMeasurements;
import static ee.alekal.adventOfCode.util.IntegerUtils.getInputsAsArray;
import static ee.alekal.adventOfCode.util.IntegerUtils.sumFirstThreeElements;

public class Puzzle01 {

    private final static String RESOURCE = "/day1/measurements.txt";

    public static int findLarges() {
        var inputsAsArray = getInputsAsArray(getMeasurements(RESOURCE));
        var different = ZERO;
        for (int i = ZERO; i < inputsAsArray.length - 1; i++) {
            if (inputsAsArray[i] < inputsAsArray[i + 1]) different++;
        }
        return different;
    }

    public static int findSum() {
        var inputs = getMeasurements(RESOURCE);
        var sumIsLarger = ZERO;
        while (inputs.size() > 3) {
            var currentSum = sumFirstThreeElements(inputs);

            inputs.remove(0);

            var nextSum = sumFirstThreeElements(inputs);

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
