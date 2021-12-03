package ee.alekal.adventOfCode.util;

import java.util.List;

public class IntegerUtils {

    public static Integer[] getInputsAsArray(List<Integer> inputs) {
        var array = new Integer[inputs.size()];
        return inputs.toArray(array);
    }

    public static int sumFirstThreeElements(List<Integer> list) {
        return list.stream()
                .limit(3)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
