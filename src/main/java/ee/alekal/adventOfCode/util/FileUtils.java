package ee.alekal.adventOfCode.util;

import ee.alekal.adventOfCode.day1.FindMeasurements;
import ee.alekal.adventOfCode.day2.dto.Movement;
import ee.alekal.adventOfCode.day2.dto.MovementType;
import ee.alekal.adventOfCode.day4.dto.BingoBoard;
import ee.alekal.adventOfCode.day4.service.GameEngine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ee.alekal.adventOfCode.util.CommonUtils.getInputsStringAsArray;
import static ee.alekal.adventOfCode.util.Constants.DEFAULT_GAME_SCALE;
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

    public static List<String> getInputAsString(String resource) {
        var inputs = new ArrayList<String>();
        try {
            var streamResource = FindMeasurements.class.getResourceAsStream(resource);
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
            var streamResource = FindMeasurements.class.getResourceAsStream(resource);
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

    public static GameEngine getInputAsBingoGameEngine(String resource) {
        var boards = new ArrayList<BingoBoard>();
        var managerBuilder = GameEngine.GameEngineBuilder.builder();
        var firstSpace = false;
        try {
            var streamResource = FindMeasurements.class.getResourceAsStream(resource);
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
