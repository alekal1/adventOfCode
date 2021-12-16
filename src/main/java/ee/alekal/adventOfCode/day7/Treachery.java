package ee.alekal.adventOfCode.day7;

import ee.alekal.adventOfCode.day7.dto.CrabsMovementType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ee.alekal.adventOfCode.day7.dto.CrabsMovementType.CHEAP;
import static ee.alekal.adventOfCode.day7.dto.CrabsMovementType.EXPENSIVE;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputOfSingleLineIntegers;

public class Treachery {
    private static final List<Integer> crabs = getInputOfSingleLineIntegers("/day7/crabs.txt");

    // Brute force
    private static int moveCrabs(List<Integer> horizontalPos, CrabsMovementType movementType) {
        int minFuel = Integer.MAX_VALUE;
        for (var horizontal : horizontalPos) {
            var listOfFuels = new ArrayList<Integer>();
            for (var crabPos : crabs) {
                var posDiff = Math.abs(crabPos - horizontal);
                if (CHEAP.equals(movementType)) {
                    listOfFuels.add(posDiff);
                } else if (EXPENSIVE.equals(movementType)) {
                    listOfFuels.add(calculateFuelForCrab(posDiff));
                }
            }
            var totalFuel = calculateTotalFuel(listOfFuels);
            if (totalFuel < minFuel) {
                minFuel = totalFuel;
            }
        }
        return minFuel;
    }

    private static int calculateFuelForCrab(long diff) {
        var fuelPerStep = new ArrayList<Integer>();
        int i = 1;
        while (i != diff + 1) {
            fuelPerStep.add(i);
            i++;
        }
        return fuelPerStep.stream().mapToInt(Integer::intValue).sum();
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

        LOG.debug("Answer for part one is {}", moveCrabs(horizontalPos, CHEAP));
        LOG.debug("Answer fot part two is {}", moveCrabs(horizontalPos, EXPENSIVE));

    }
}
