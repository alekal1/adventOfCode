package ee.alekal.adventOfCode.day2;

import lombok.val;

import static ee.alekal.adventOfCode.day2.dto.MovementType.DOWN;
import static ee.alekal.adventOfCode.day2.dto.MovementType.FORWARD;
import static ee.alekal.adventOfCode.day2.dto.MovementType.UP;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.DefaultLogger.logMovementTypeCount;
import static ee.alekal.adventOfCode.util.FileUtils.getDepth;

public class Puzzle02 {

    private static final String RESOURCE = "/day2/depth.txt";

    public static int multiplyHorizontalAndDepth() {
        var depthInput = getDepth(RESOURCE);

        LOG.debug("Depth input size {}", depthInput.size());
        logMovementTypeCount(depthInput, FORWARD);
        logMovementTypeCount(depthInput, UP);
        logMovementTypeCount(depthInput, DOWN);

        int horizontal = 0;
        int depth = 0;
        for (var movement : depthInput) {
            val value = movement.movementValue;
            switch (movement.movementType) {
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

    public static void main(String[] args) {
        LOG.info("The answer for first part is {}", multiplyHorizontalAndDepth());
    }
}
