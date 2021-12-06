package ee.alekal.adventOfCode.day4.service;


import ee.alekal.adventOfCode.day4.dto.BingoBoard;
import ee.alekal.adventOfCode.day4.dto.WinningCombo;
import ee.alekal.adventOfCode.day4.exception.WinningException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ee.alekal.adventOfCode.util.Constants.MARK;

@Getter
public class GameEngine {

    private final List<BingoBoard> boards;
    private final List<Integer> withDrawNumbers;
    private WinningCombo winningCombo;

    private GameEngine(GameEngineBuilder builder) {
        this.boards = builder.boards;
        this.withDrawNumbers = builder.withDrawNumbers;
    }

    public void markNumberIfPossible(int numberToMark) throws WinningException {
        var numMarkString = String.valueOf(numberToMark);
        for (var board : boards) {
            var actualBoard = board.getGameBoard();
            for (int upperIndex = 0; upperIndex < actualBoard.length; upperIndex++) {
                for (int subIndex = 0; subIndex < actualBoard[upperIndex].length; subIndex++) {
                    var number = actualBoard[upperIndex][subIndex];
                    if (numMarkString.equals(number)) {
                        actualBoard[upperIndex][subIndex] = MARK;
                        checkIfBoardWins(board, numberToMark);
                    }
                }
            }
        }
    }

    private void checkIfBoardWins(BingoBoard board, int lastNumber) {
        // Row check
        for (var row : board.getGameBoard()) {
            if (row == board.getWinningCombination()) {
                throw new WinningException("Board "
                        + board.toString()
                        + " has won, last number was " + lastNumber
                        + " board index is " + boards.indexOf(board) + 1);
            }
        }

        // Column check
        var valuesByColumn = new HashMap<Integer, List<String>>();
        for (int row = 0; row < board.getGameBoard().length; row++) {
            var currentRow = board.getGameBoard()[row];
            for (int col = 0; col < currentRow.length; col++) {
                var currValuesInCol = valuesByColumn.getOrDefault(col, new ArrayList<>());
                currValuesInCol.add(board.getGameBoard()[row][col]);
                valuesByColumn.put(col, currValuesInCol);
            }
        }

        for (Map.Entry<Integer, List<String>> entry : valuesByColumn.entrySet()) {
            if (entry.getValue().equals(Arrays.asList(board.getWinningCombination()))) {
                winnerNotification(board, lastNumber);
            }
        }
    }

    private void winnerNotification(BingoBoard board, int lastNumber) {
        this.winningCombo = new WinningCombo(board, lastNumber);
        throw new WinningException("We have a winner! "
                + "last number was "
                + lastNumber
                + ". \n Border: \n" + boardPrettyPrint(board));
    }

    private String boardPrettyPrint(BingoBoard board) {
        var builder = new StringBuilder();
        for (var row : board.getGameBoard()) {
            builder.append(Arrays.toString(row)).append("\n");
        }
        return builder.toString();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GameEngineBuilder {
        private final List<BingoBoard> boards = new ArrayList<>();
        private final List<Integer> withDrawNumbers = new ArrayList<>();

        public static GameEngineBuilder builder() {
            return new GameEngineBuilder();
        }

        public GameEngineBuilder addWithDrawNumber(int number) {
            withDrawNumbers.add(number);
            return this;
        }

        public GameEngineBuilder addAllBoards(List<BingoBoard> board) {
            boards.addAll(board);
            return this;
        }

        public GameEngine build() {
            return new GameEngine(this);
        }

    }

}
