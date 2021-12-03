package ee.alekal.adventOfCode.day1;


import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static ee.alekal.adventOfCode.util.IntegerUtils.getInputsAsArray;
import static ee.alekal.adventOfCode.util.IntegerUtils.sumFirstThreeElements;

public class Puzzle01 {

    private final static Logger LOG = LoggerFactory.getLogger(Puzzle01.class);
    private final static String RESOURCE = "/puzzle1/measurements.txt";
    private final static Integer ZERO = 0;

    private static List<Integer> getInputs() {
        var inputs = new ArrayList<Integer>();
        try {
            var streamResource = Puzzle01.class.getResourceAsStream(RESOURCE);
            var bufferReader = new BufferedReader(new InputStreamReader(streamResource));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                inputs.add(Integer.parseInt(line));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource " + RESOURCE);
        }
        return inputs;
    }


    public static int findLarges() {
        var inputsAsArray = getInputsAsArray(getInputs());
        var different = ZERO;
        for (int i = ZERO; i < inputsAsArray.length - 1; i++) {
            if (inputsAsArray[i] < inputsAsArray[i + 1]) different++;
        }
        return different;
    }

    public static int findSum() {
        var inputs = getInputs();
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
        PropertyConfigurator.configure("log4j.properties");
        LOG.debug("The answer for part one is {}", Puzzle01.findLarges());
        LOG.debug("The answer for part two is {}", Puzzle01.findSum());
    }
}
