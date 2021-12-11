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

    private static HashMap<String, Integer> findOverLapPointsNonDiagonal(List<Coordinates> listOfCoordinates) {

        var pointsMap = new HashMap<String, Integer>();

        for (var coordinate : listOfCoordinates) {
            getOverLapPointsNonDiagonal(coordinate, pointsMap);
        }

        return pointsMap;
    }

    private static HashMap<String, Integer> findOverLapPointsWithDiagonal(List<Coordinates> listOfCoordinates,
                                                                          HashMap<String, Integer> overLapPoints) {

        for (var c : listOfCoordinates) {
            getOverLapPointsDiagonal(c, overLapPoints);
        }
        return overLapPoints;
    }


    private static void getOverLapPointsDiagonal(Coordinates coordinates, HashMap<String, Integer> overLapPoints) {
        var startPoint = coordinates.getStartPoint();
        var endPoint = coordinates.getEndPoint();

        increasePointValue(overLapPoints, startPoint);

        int counter = 1;
        var tempPoint = Point.builder().build();
        while (tempPoint.getX() != endPoint.getX()
            || tempPoint.getY() != endPoint.getY()) {
            tempPoint = getTempHorizontalPoint(startPoint, endPoint, counter);
            increasePointValue(overLapPoints, tempPoint);
            counter++;
        }
    }

    private static void getOverLapPointsNonDiagonal(Coordinates coordinates,
                                                    HashMap<String, Integer> overLapPoints) {
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
            increasePointValue(overLapPoints, tempPoint);
            counter--;
        }
    }

    private static List<Coordinates> getFilteredHorizontalVerticalOutput() {
        Predicate<Coordinates> xEquals = coordinates ->
                coordinates.getStartPoint().getX() == coordinates.getEndPoint().getX();
        Predicate<Coordinates> yEquals = coordinates ->
                coordinates.getStartPoint().getY() == coordinates.getEndPoint().getY();

        return coordinatesList.stream()
                .filter(xEquals.or(yEquals))
                .collect(Collectors.toList());
    }


    private static Point getTempHorizontalPoint(Point startPoint, Point endPoint, int counter) {
        if (startPoint.getX() < endPoint.getX()) {
            if (startPoint.getY() < endPoint.getY()) {
                return Point.builder()
                        .x(startPoint.getX() + counter)
                        .y(startPoint.getY() + counter)
                        .build();
            } else {
                return Point.builder()
                        .x(startPoint.getX() + counter)
                        .y(startPoint.getY() - counter)
                        .build();
            }
        }
        if (startPoint.getX() > endPoint.getX()) {
            if (startPoint.getY() < endPoint.getY()) {
                return Point.builder()
                        .x(startPoint.getX() - counter)
                        .y(startPoint.getY() + counter)
                        .build();
            } else {
                return Point.builder()
                        .x(startPoint.getX() - counter)
                        .y(startPoint.getY() - counter)
                        .build();
            }
        }
        throw new IllegalArgumentException("Cannot create horizontal point.");
    }

    private static void increasePointValue(HashMap<String, Integer> map, Point point) {
        var value = map.getOrDefault(point.toString(), ZERO);
        map.put(point.toString(), value + ONE);
    }

    public static void main(String[] args) {
        var horizontalVerticalFilterOutput = getFilteredHorizontalVerticalOutput();

        var partOneOverLapPoints = findOverLapPointsNonDiagonal(horizontalVerticalFilterOutput);

        var partOneAnswer = partOneOverLapPoints.entrySet().stream()
                .filter(entry -> entry.getValue() > ONE)
                .count();
        LOG.debug("Answer for part one {}", partOneAnswer);


        coordinatesList.removeAll(horizontalVerticalFilterOutput);
        var partTwoAnswer = findOverLapPointsWithDiagonal(coordinatesList, partOneOverLapPoints)
                .entrySet().stream()
                .filter(entry -> entry.getValue() > ONE)
                .count();
        LOG.debug("Answer for part two {}", partTwoAnswer);
    }
}
