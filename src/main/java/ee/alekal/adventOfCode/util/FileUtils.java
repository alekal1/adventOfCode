package ee.alekal.adventOfCode.util;

import ee.alekal.adventOfCode.day1.Puzzle01;
import ee.alekal.adventOfCode.day2.dto.MovementType;
import ee.alekal.adventOfCode.day2.dto.Movement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<Integer> getMeasurements(String resource) {
        var inputs = new ArrayList<Integer>();
        try {
            var streamResource = Puzzle01.class.getResourceAsStream(resource);
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

    public static List<Movement> getDepth(String resource) {
        var depth = new ArrayList<Movement>();
        try {
            var streamResource = Puzzle01.class.getResourceAsStream(resource);
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
            throw new RuntimeException("Error reading resource " + resource);
        }
        return depth;
    }
}