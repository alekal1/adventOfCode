package ee.alekal.adventOfCode.util;

import ee.alekal.adventOfCode.day1.FindMeasurements;
import ee.alekal.adventOfCode.day2.DepthMove;
import ee.alekal.adventOfCode.day2.dto.Movement;
import ee.alekal.adventOfCode.day2.dto.MovementType;
import ee.alekal.adventOfCode.day3.BinaryDiagnostics;
import ee.alekal.adventOfCode.day4.BingoSquid;
import ee.alekal.adventOfCode.day4.dto.BingoBoard;
import ee.alekal.adventOfCode.day4.service.GameEngine;
import ee.alekal.adventOfCode.day5.HydrothermalVenture;
import ee.alekal.adventOfCode.day5.dto.Coordinates;
import ee.alekal.adventOfCode.day6.LanternFish;
import ee.alekal.adventOfCode.day8.SegmentSearch;
import ee.alekal.adventOfCode.day8.dto.SegmentObject;
import ee.alekal.adventOfCode.day8.dto.SegmentOutput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ee.alekal.adventOfCode.util.CommonUtils.getInputsStringAsArray;
import static ee.alekal.adventOfCode.util.Constants.DEFAULT_GAME_SCALE;
import static ee.alekal.adventOfCode.util.Constants.ONE;
import static ee.alekal.adventOfCode.util.Constants.SPACE_REGEX;
import static ee.alekal.adventOfCode.util.Constants.ZERO;

public class FileUtils {

    public static List<Integer> getInputAsInteger(String resource) {
        var inputs = new ArrayList<Integer>();
        try {
            var streamResource = FindMeasurements.class.getResourceAsStream(resource);
            var bufferReader = new BufferedReader(new InputStreamReader(streamResource));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                inputs.add(Integer.parseInt(line));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource " + resource);
        }
        return inputs;
    }

    public static List<Integer> getInputOfSingleLineIntegers(String resource) {
        try {
            var streamResource = LanternFish.class.getResourceAsStream(resource);
            var bufferReader = new BufferedReader(new InputStreamReader(streamResource));
            var line = bufferReader.readLine().split(",");
            return Arrays.stream(line)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource " + resource);
        }
    }

    public static List<String> getInputAsString(String resource) {
        var inputs = new ArrayList<String>();
        try {
            var streamResource = BinaryDiagnostics.class.getResourceAsStream(resource);
            var bufferReader = new BufferedReader(new InputStreamReader(streamResource));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                inputs.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource " + resource);
        }
        return inputs;
    }

    public static List<Movement> getInputAsMovement(String resource) {
        var depth = new ArrayList<Movement>();
        try {
            var streamResource = DepthMove.class.getResourceAsStream(resource);
            var bufferReader = new BufferedReader(new InputStreamReader(streamResource));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                var splitedLine = line.split(" ");
                depth.add(new Movement(
                        MovementType.valueOf(splitedLine[0].toUpperCase()),
                        Integer.parseInt(splitedLine[1])
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource " + resource, e);
        }
        return depth;
    }

    public static List<SegmentObject> getInputAsSegmentObjects(String resource) {
        var segmentsObjects = new ArrayList<SegmentObject>();
        try {
            var streamResource = SegmentSearch.class.getResourceAsStream(resource);
            var bufferReader = new BufferedReader(new InputStreamReader(streamResource));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                var segmentBuilder = SegmentObject.Builder.builder();
                var splitBetweenInAndOut = line.split("\\|");
                var inputArray = splitBetweenInAndOut[0].split(" ");
                Arrays.stream(inputArray).forEach(segmentBuilder::addSegmentInput);

                var outputArray = splitBetweenInAndOut[1].split(" ");
                Arrays.stream(outputArray).forEach(s -> {
                    if (!s.isEmpty()) {
                        var outputBuilder = SegmentOutput.Builder.builder();
                        outputBuilder.setValue(s).setRepresents(s.length());
                        segmentBuilder.addSegmentOutput(outputBuilder.build());
                    }
                });
                segmentsObjects.add(segmentBuilder.build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource " + resource, e);
        }
        return segmentsObjects;
    }

    public static GameEngine getInputAsBingoGameEngine(String resource) {
        var boards = new ArrayList<BingoBoard>();
        var managerBuilder = GameEngine.GameEngineBuilder.builder();
        var firstSpace = false;
        try {
            var streamResource = BingoSquid.class.getResourceAsStream(resource);
            var bufferReader = new BufferedReader(new InputStreamReader(streamResource));
            String line;
            BingoBoard.BingoBoardBuilder boardBuilder = BingoBoard.BingoBoardBuilder.builder(DEFAULT_GAME_SCALE);
            int index = 0;
            while ((line = bufferReader.readLine()) != null) {
                if (!firstSpace) {
                    addWithDrawNumber(managerBuilder, line);
                    firstSpace = true;
                    continue;
                }

                if (line.isEmpty()) {
                    boards.add(boardBuilder.build());
                    boardBuilder = BingoBoard.BingoBoardBuilder.builder(DEFAULT_GAME_SCALE);
                    index = 0;
                } else {
                    var splited = Arrays.stream(removeInsideSpaces(line.trim().split(" ")))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    boardBuilder.setRow(index, getInputsStringAsArray(splited));
                    index++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource " + resource, e);
        }
        boards.remove(ZERO); // Remove first element, because it was initial empty line.
        return managerBuilder.addAllBoards(boards).build();
    }

    public static List<Coordinates> getInputAsCoordinates(String resource) {
        var coordinates = new ArrayList<Coordinates>();
        try {
            var streamResource = HydrothermalVenture.class.getResourceAsStream(resource);
            var bufferReader = new BufferedReader(new InputStreamReader(streamResource));
            String line;
            while ((line = bufferReader.readLine()) != null) {
                var builder = Coordinates.Builder.builder();
                var splitedLine = removeInsideSpaces(line.split("->"));

                var firstPair = Arrays.stream(splitedLine[ZERO].split(","))
                                    .mapToInt(Integer::parseInt).toArray();

                var secondPair = Arrays.stream(splitedLine[ONE].split(","))
                        .mapToInt(Integer::parseInt).toArray();

                var coordinate = builder
                        .setStartPoint(firstPair[ZERO], firstPair[ONE])
                        .setEndPoint(secondPair[ZERO], secondPair[ONE])
                        .build();

                coordinates.add(coordinate);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading resource " + resource, e);
        }
        return coordinates;
    }

    private static String[] removeInsideSpaces(String[] values) {
        var removedSpaceArray = new String[values.length];
        for (int i = ZERO; i < values.length; i++) {
            if (!values[i].isEmpty()) removedSpaceArray[i] = values[i].replaceAll(SPACE_REGEX, "");
        }
        return removedSpaceArray;
    }

    private static void addWithDrawNumber(GameEngine.GameEngineBuilder builder, String line) {
        var splitedLine = line.split(",");
        for (var numberAsString : splitedLine) {
            builder.addWithDrawNumber(Integer.parseInt(numberAsString));
        }
    }
}
