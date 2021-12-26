package ee.alekal.adventOfCode.day9.dto;

import static ee.alekal.adventOfCode.day9.SmokeBasin.smokeBasinInput;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.ALL;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.DL;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.DR;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.LDR;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.LUR;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.UL;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.ULD;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.UR;
import static ee.alekal.adventOfCode.day9.dto.CheckStrategy.URD;

public class StrategyRunner {

    public boolean runStrategy(int listIndex, int arrayIndex, int listEnd, int arrayEnd, int currValue) {
        var strategy = getCheckStrategy(listIndex, arrayIndex, listEnd, arrayEnd);
        switch (strategy) {
            case ALL:
                return checkUp(listIndex, arrayIndex, currValue)
                        && checkDown(listIndex, arrayIndex, currValue)
                        && checkRight(listIndex, arrayIndex, currValue)
                        && checkLeft(listIndex, arrayIndex, currValue);
            case UL:
                return checkUp(listIndex, arrayIndex, currValue)
                        && checkLeft(listIndex, arrayIndex, currValue);
            case LUR:
                return checkLeft(listIndex, arrayIndex, currValue)
                        && checkUp(listIndex, arrayIndex, currValue)
                        && checkRight(listIndex, arrayIndex, currValue);
            case URD:
                return checkUp(listIndex, arrayIndex, currValue)
                        && checkRight(listIndex, arrayIndex, currValue)
                        && checkDown(listIndex, arrayIndex, currValue);
            case LDR:
                return checkLeft(listIndex, arrayIndex, currValue)
                        && checkDown(listIndex, arrayIndex, currValue)
                        && checkRight(listIndex, arrayIndex, currValue);
            case DR:
                return checkDown(listIndex, arrayIndex, currValue)
                        && checkRight(listIndex, arrayIndex, currValue);
            case DL:
                return checkDown(listIndex, arrayIndex, currValue)
                        && checkLeft(listIndex, arrayIndex, currValue);
            case UR:
                return checkUp(listIndex, arrayIndex, currValue)
                        && checkRight(listIndex, arrayIndex, currValue);
            case ULD:
                return checkUp(listIndex, arrayIndex, currValue)
                        && checkLeft(listIndex, arrayIndex, currValue)
                        && checkDown(listIndex, arrayIndex, currValue);
            default:
                throw new RuntimeException("Error occurred with check strategy");
        }
    }

    private CheckStrategy getCheckStrategy(int listIndex, int arrayIndex, int listEnd, int arrayEnd) {
        if (listIndex == 0) {
            if (arrayIndex == 0) {
                return DR; // Left up corner
            } else if (arrayIndex == arrayEnd) {
                return DL; // Right up corner
            } else {
                return LDR; // Up border exclude corners
            }
        }

        if (listIndex == listEnd) {
            if (arrayIndex == arrayEnd) {
                return UL; // Right down corner
            } else if (arrayIndex == 0) {
                return UR; // Left down corner
            } else {
                return LUR; // Down border exclude corners
            }
        }

        if (arrayIndex == arrayEnd) {
            return ULD; // Right border
        }

        if (arrayIndex == 0) {
            return URD; // Left border
        }

        return ALL; // Everything else
    }

    private boolean checkUp(int listIndex, int arrayIndex, int currValue) {
        var upValue = smokeBasinInput.get(listIndex - 1)[arrayIndex];
        return currValue < upValue;
    }

    private boolean checkDown(int listIndex, int arrayIndex, int currValue) {
        var downValue = smokeBasinInput.get(listIndex + 1)[arrayIndex];
        return currValue < downValue;
    }

    private boolean checkLeft(int listIndex, int arrayIndex, int currValue) {
        var leftValue = smokeBasinInput.get(listIndex)[arrayIndex - 1];
        return currValue < leftValue;
    }

    private boolean checkRight(int listIndex, int arrayIndex, int currValue) {
        var rightValue = smokeBasinInput.get(listIndex)[arrayIndex + 1];
        return currValue < rightValue;
    }
}
