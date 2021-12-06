package ee.alekal.adventOfCode.day4;


import ee.alekal.adventOfCode.day4.dto.WinningCombo;
import ee.alekal.adventOfCode.day4.exception.WinningException;
import ee.alekal.adventOfCode.day4.service.GameEngine;


import java.util.ArrayList;

import static ee.alekal.adventOfCode.util.Constants.MARK;
import static ee.alekal.adventOfCode.util.DefaultLogger.LOG;
import static ee.alekal.adventOfCode.util.FileUtils.getInputAsBingoGameEngine;

public class BingoSquid {

    private static void runBingoGame(GameEngine manager) {
        LOG.debug("Withdraw numbers are {}", manager.getWithDrawNumbers());
        try {
            for (var number : manager.getWithDrawNumbers()) {
                manager.markNumberIfPossible(number);
            }
        } catch (WinningException e) {
            e.printStackTrace();
        } finally {
            LOG.debug("Answer for part one is {}", calculateNonMarkedFields(manager.getWinningCombo()));
        }
    }

    private static int calculateNonMarkedFields(WinningCombo winningCombo) {
        var board = winningCombo.getBoard();
        var nonMarkedFields = new ArrayList<String>();
        for (var row : board.getGameBoard()) {
            for (var col : row) {
                if (!MARK.equals(col)) {
                    nonMarkedFields.add(col);
                }
            }
        }
        return nonMarkedFields.stream().mapToInt(Integer::parseInt).sum() * winningCombo.getWinningNumber();
    }

    public static void main(String[] args) {
        var manager = getInputAsBingoGameEngine("/day4/bingo.txt");
        runBingoGame(manager);
    }
}
