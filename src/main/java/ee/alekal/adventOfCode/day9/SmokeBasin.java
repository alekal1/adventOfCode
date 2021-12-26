package ee.alekal.adventOfCode.day9;

import ee.alekal.adventOfCode.day9.dto.StrategyRunner;

import java.util.ArrayList;
import java.util.List;

import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getSmokeBasinInput;

public class SmokeBasin {

    public static final List<int[]> smokeBasinInput = getSmokeBasinInput("/day9/lava.txt");

    private static List<Integer> minValueAcrossAdjacent() {
        var lowerPoints = new ArrayList<Integer>();
        var runner = new StrategyRunner();
        for (int i = 0; i < smokeBasinInput.size(); i++) {
            var listEnd = smokeBasinInput.size() - 1;
            for (int j = 0; j < smokeBasinInput.get(i).length; j++) {
                var arrayEnd = smokeBasinInput.get(i).length - 1;
                var currValue = smokeBasinInput.get(i)[j];
                if (runner.runStrategy(
                        i,
                        j,
                        listEnd,
                        arrayEnd,
                        currValue
                )) {
                    lowerPoints.add(currValue);
                }
            }
        }
        return lowerPoints;
    }

    private static int sumHeight(List<Integer> minValueList) {
        return minValueList.stream()
                .map(integer -> integer + 1)
                .mapToInt(Integer::intValue)
                .sum();
    }


    public static void main(String[] args) {
        LOG.debug("Answer for part one is {}", sumHeight(minValueAcrossAdjacent()));
    }
}
