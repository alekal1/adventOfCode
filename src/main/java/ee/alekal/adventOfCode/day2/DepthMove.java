package ee.alekal.adventOfCode.day2;

import ee.alekal.adventOfCode.day2.dto.Movement;
import lombok.val;

import java.util.List;

import static ee.alekal.adventOfCode.day2.dto.MovementType.DOWN;
import static ee.alekal.adventOfCode.day2.dto.MovementType.FORWARD;
import static ee.alekal.adventOfCode.day2.dto.MovementType.UP;
import static ee.alekal.adventOfCode.util.Constants.ZERO;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.DefaultLogger.logMovementTypeCount;
import static ee.alekal.adventOfCode.util.FileUtils.getDepth;

public class DepthMove {

    private static final String RESOURCE = "/day2/depth.txt";
    private static final List<Movement> DEPTH_INPUT = getDepth(RESOURCE);
    static {
        LOG.debug("Depth input size {}", DEPTH_INPUT.size());
        logMovementTypeCount(DEPTH_INPUT, FORWARD);
        logMovementTypeCount(DEPTH_INPUT, UP);
        logMovementTypeCount(DEPTH_INPUT, DOWN);
    }

    private static int multiplyHorizontalAndDepth() {
        int horizontal = ZERO;
        int depth = ZERO;
        for (var movement : DEPTH_INPUT) {
            val value = movement.getMovementValue();
            switch (movement.getMovementType()) {
                case FORWARD:
                    horizontal += value;
                    break;
                case DOWN:
                    depth += value;
                    break;
                case UP:
                    depth -= value;
                    break;
                default:
                    throw new IllegalArgumentException("Cannot read movement=" + movement.toString());
            }
        }
        return horizontal * depth;
    }

    private static int multiplyHorizontalWithAim() {
        int aim = ZERO;
        int depth = ZERO;
        int horizontal = ZERO;

        for (var movement : DEPTH_INPUT) {
            val value = movement.getMovementValue();
            switch (movement.getMovementType()) {
                case DOWN:
                    aim += value;
                    break;
                case UP:
                    aim -= value;
                    break;
                case FORWARD:
                    horizontal += value;
                    depth += aim * value;
                    break;
                default:
                    throw new IllegalArgumentException("Cannot read movement=" + movement.toString());
            }
        }
        return horizontal * depth;
    }

    public static void main(String[] args) {
        LOG.info("The answer for first part is {}", multiplyHorizontalAndDepth());
        LOG.info("The answer for second part is {}", multiplyHorizontalWithAim());
    }
}
