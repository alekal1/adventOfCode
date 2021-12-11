package ee.alekal.adventOfCode.day5;

import ee.alekal.adventOfCode.day5.dto.Coordinates;
import ee.alekal.adventOfCode.day5.dto.Point;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ee.alekal.adventOfCode.day5.dto.data.MetaData.getHorizontalVerticalDataInstance;
import static ee.alekal.adventOfCode.util.Constants.ONE;
import static ee.alekal.adventOfCode.util.Constants.ZERO;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputAsCoordinates;

public class HydrothermalVenture {

    private static final List<Coordinates> coordinatesList = getInputAsCoordinates("/day5/coordinates.txt");

    private static long onlyHorizontalAndVertical() {

        var pointsMap = new HashMap<String, Integer>();
        var filteredOutput = getFilteredOutput();

        for (var coordinate : filteredOutput) {
            getOverLapPoints(coordinate, pointsMap);
        }

        return pointsMap.entrySet().stream()
                .filter(entry -> entry.getValue() > ONE)
                .count();
    }

    private static List<Coordinates> getFilteredOutput() {
        Predicate<Coordinates> xEquals = coordinates ->
                coordinates.getStartPoint().getX() == coordinates.getEndPoint().getX();
        Predicate<Coordinates> yEquals = coordinates ->
                coordinates.getStartPoint().getY() == coordinates.getEndPoint().getY();

        return coordinatesList.stream()
                .filter(xEquals.or(yEquals))
                .collect(Collectors.toList());
    }

    private static void getOverLapPoints(Coordinates coordinates, HashMap<String, Integer> overLapPoints) {
        var startPoint = coordinates.getStartPoint();
        var endPoint = coordinates.getEndPoint();

        var data = getHorizontalVerticalDataInstance(startPoint, endPoint);
        var counter = data.getCounter();

        while (counter - data.getLoopStatement() != -1) {
            var pointBuilder = Point.builder();
            Point tempPoint = Point.builder().build();

            switch (data.getDecreaseType()) {
                case X_DECREASE:
                    tempPoint = pointBuilder
                            .x(counter)
                            .y(startPoint.getY())
                            .build();
                    break;
                case Y_DECREASE:
                    tempPoint = pointBuilder
                            .x(startPoint.getX())
                            .y(counter)
                            .build();
                    break;
            }
            var value = overLapPoints.getOrDefault(tempPoint.toString(), ZERO);
            overLapPoints.put(tempPoint.toString(), value + ONE);
            counter--;
        }
    }

    public static void main(String[] args) {
        LOG.debug("Answer for part one {}", onlyHorizontalAndVertical());
    }
}
