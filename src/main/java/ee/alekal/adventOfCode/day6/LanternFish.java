package ee.alekal.adventOfCode.day6;


import lombok.val;

import java.util.Arrays;
import java.util.List;

import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputAsFishDays;

public class LanternFish {

    private static long growthPredictionShortPeriod(int day, List<Integer> fishDays) {
        if (day == 0) return fishDays.size();

        int addNewDayCounter = 0;
        for (int i = 0; i < fishDays.size(); i++) {
            int newValue = fishDays.get(i) - 1;
            if (newValue == -1) {
                fishDays.set(i, 6);
                addNewDayCounter++;
            } else {
                fishDays.set(i, newValue);
            }
        }
        while (addNewDayCounter != 0) {
            fishDays.add(8);
            addNewDayCounter--;
        }
        growthPredictionShortPeriod(day - 1, fishDays);
        return fishDays.size();
    }

    private static long growthPredictionLongPeriod(int day, long[] fishDays) {
        for (int i = 0; i < day; i++) {
            fishDays[7] += fishDays[0];
            fishDays[9] = fishDays[0];
            System.arraycopy(fishDays, 1, fishDays, 0, 9);
            fishDays[9] = 0;
        }
        return Arrays.stream(fishDays).sum();
    }

    private static long[] getFishInputAsArray(List<Integer> fishDays) {
        long[] fish = new long[fishDays.size()];
        for (var f : fishDays) {
            fish[Math.toIntExact(f)]++;
        }
        return fish;
    }

    public static void main(String[] args) {
        val resource = "/day6/fish.txt";
        var fishDaysPartOne = getInputAsFishDays(resource);
        LOG.debug("Answer for part one is {}", growthPredictionShortPeriod(80, fishDaysPartOne));

        var fishDaysPartTwo = getInputAsFishDays(resource);
        var arrayOfFishDays = getFishInputAsArray(fishDaysPartTwo);
        LOG.debug("Answer for part two is {}", growthPredictionLongPeriod(256, arrayOfFishDays));
    }
}
