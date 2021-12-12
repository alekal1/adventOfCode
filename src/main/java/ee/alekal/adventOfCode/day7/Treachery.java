package ee.alekal.adventOfCode.day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputOfSingleLineIntegers;

public class Treachery {
    private static final List<Integer> crabs = getInputOfSingleLineIntegers("/day7/crabs.txt");

    private static int moveCrabs(List<Integer> horizontalPos) {
        int minFuel = Integer.MAX_VALUE;
        for (var horizontal : horizontalPos) {
            var listOfFuels = new ArrayList<Integer>();
            for (var crabPos : crabs) {
                listOfFuels.add(Math.abs(crabPos - horizontal));
            }
            var totalFuel = calculateTotalFuel(listOfFuels);
            if (totalFuel < minFuel) {
                minFuel = totalFuel;
            }
        }
        return minFuel;
    }

    private static int calculateTotalFuel(List<Integer> fuels) {
        return fuels.stream().mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) {
        var copyOfInput = crabs;
        Collections.sort(copyOfInput);
        var horizontalPos = copyOfInput.stream()
                .distinct()
                .collect(Collectors.toList());

        LOG.debug("Answer for part one is {}", moveCrabs(horizontalPos));
    }
}
