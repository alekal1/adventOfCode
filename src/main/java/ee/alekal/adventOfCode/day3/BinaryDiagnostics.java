package ee.alekal.adventOfCode.day3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ee.alekal.adventOfCode.util.Constants.ZERO;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputAsString;


public class BinaryDiagnostics {

    private static final String RESOURCE = "/day3/diagnostics.txt";
    private static final List<String> BINARY_INPUT = getInputAsString(RESOURCE);

    private static HashMap<Integer, List<String>> collectValuesByIndex() {
        var valuesMap = new HashMap<Integer, List<String>>();
        for (var binary : BINARY_INPUT) {
            var charArray = binary.toCharArray();

            for (int i = ZERO; i < charArray.length; i++) {
                var stringChar = String.valueOf(charArray[i]);

                var curList = valuesMap.getOrDefault(i, new ArrayList<>());
                curList.add(stringChar);
                valuesMap.put(i, curList);
            }
        }
        return valuesMap;
    }

    private static String getBinaryGammaRate() {
        StringBuilder stringBuilder = new StringBuilder();
        var map = collectValuesByIndex();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            stringBuilder.append(findMostCommon(entry.getValue()));
        }
        return stringBuilder.toString();
    }

    private static String getBinaryEpsilonRate() {
        StringBuilder stringBuilder = new StringBuilder();
        var map = collectValuesByIndex();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            stringBuilder.append(findLessCommon(entry.getValue()));
        }
        return stringBuilder.toString();
    }

    private static String findMostCommon(List<String> list) {
        Map<String, Long> temp = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new HashSet<>(temp.values()).size() < temp.size() ?
                null : temp.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get();
    }

    private static String findLessCommon(List<String> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().min(((o1, o2) -> o1.getValue().compareTo(o2.getValue())))
                .map(Map.Entry::getKey).orElse(null);
    }

    private static long findGammaRateDecimal() {
        var binaryGamma = getBinaryGammaRate();
        LOG.debug("Binary gamma is {}", binaryGamma);
        return Long.parseLong(binaryGamma, 2);
    }

    private static long findEpsilonRateDecimal() {
        var binaryEpsilon = getBinaryEpsilonRate();
        LOG.debug("Binary epsilon is {}", binaryEpsilon);
        return Long.parseLong(binaryEpsilon, 2);
    }

    public static void main(String[] args) {
        var gammaDecimal = findGammaRateDecimal();
        var epsilonDecimal = findEpsilonRateDecimal();
        LOG.debug("Answer for part one is {}", gammaDecimal * epsilonDecimal);
    }
}
