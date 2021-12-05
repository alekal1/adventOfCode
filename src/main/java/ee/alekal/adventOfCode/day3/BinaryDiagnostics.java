package ee.alekal.adventOfCode.day3;

import ee.alekal.adventOfCode.day3.dto.RatingType;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ee.alekal.adventOfCode.day3.dto.RatingType.CO2;
import static ee.alekal.adventOfCode.day3.dto.RatingType.OXYGEN;
import static ee.alekal.adventOfCode.util.Constants.BINARY_ONE;
import static ee.alekal.adventOfCode.util.Constants.BINARY_ZERO;
import static ee.alekal.adventOfCode.util.Constants.ONE;
import static ee.alekal.adventOfCode.util.Constants.ZERO;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputAsString;


public class BinaryDiagnostics {

    private static final String RESOURCE = "/day3/diagnostics.txt";
    private static final List<String> BINARY_INPUT = getInputAsString(RESOURCE);

    private static HashMap<Integer, List<String>> collectValuesByIndex(List<String> input) {
        var valuesMap = new HashMap<Integer, List<String>>();
        for (var binary : input) {
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
        var binaryInputMap = collectValuesByIndex(BINARY_INPUT);
        for (Map.Entry<Integer, List<String>> entry : binaryInputMap.entrySet()) {
            stringBuilder.append(findMostCommon(entry.getValue()));
        }
        return stringBuilder.toString();
    }

    private static String getBinaryEpsilonRate() {
        StringBuilder stringBuilder = new StringBuilder();
        var binaryInputMap = collectValuesByIndex(BINARY_INPUT);
        for (Map.Entry<Integer, List<String>> entry : binaryInputMap.entrySet()) {
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

    private static long getRatingTypeInDecimal(RatingType type) {

        var copyOfInput = BINARY_INPUT;
        val binaryCondition = getBinaryCondition(type);

        var size = copyOfInput.size();
        var i = ZERO;
        while (size != ONE){
            var tempInput = collectValuesByIndex(copyOfInput);

            var tempIndexList = tempInput.get(i);

            val index = i;
            if (zerosAreMoreFrequently(tempIndexList)) {
                copyOfInput = copyOfInput.stream()
                        .filter(s -> binaryCondition.get(ZERO).equals(String.valueOf(s.charAt(index))))
                        .collect(Collectors.toList());
            } else {
                copyOfInput = copyOfInput.stream()
                        .filter(s -> binaryCondition.get(ONE).equals(String.valueOf(s.charAt(index))))
                        .collect(Collectors.toList());
            }
            i++;
            size = copyOfInput.size();
        }

        return Long.parseLong(copyOfInput.get(ZERO), 2);
    }

    private static Map<Integer, String> getBinaryCondition(RatingType type) {
        var conditionMap = new HashMap<Integer, String>();
        String binaryFirstValue;
        String binarySecondValue;
        if (OXYGEN.equals(type)) {
            binaryFirstValue = BINARY_ZERO;
            binarySecondValue = BINARY_ONE;
        } else {
            binaryFirstValue = BINARY_ONE;
            binarySecondValue = BINARY_ZERO;
        }
        conditionMap.put(ZERO, binaryFirstValue);
        conditionMap.put(ONE, binarySecondValue);
        return conditionMap;
    }

    private static boolean zerosAreMoreFrequently(List<String> values) {
        var zeros = Collections.frequency(values, BINARY_ZERO);
        var ones = Collections.frequency(values, BINARY_ONE);
        return zeros > ones;
    }

    private static long part1() {
        var gammaDecimal = findGammaRateDecimal();
        var epsilonDecimal = findEpsilonRateDecimal();
        return gammaDecimal * epsilonDecimal;
    }

    private static long part2() {
        return getRatingTypeInDecimal(OXYGEN) * getRatingTypeInDecimal(CO2);
    }

    public static void main(String[] args) {
        LOG.debug("Answer for part one is {}", part1());
        LOG.debug("Answer for second part is {}", part2());
    }
}
