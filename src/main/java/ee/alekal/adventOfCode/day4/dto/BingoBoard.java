package ee.alekal.adventOfCode.day4.dto;

import lombok.Getter;

import java.util.Arrays;

import static ee.alekal.adventOfCode.util.Constants.MARK;

public class BingoBoard {

    @Getter
    private final String[][] gameBoard;

    @Getter
    private final int scale;

    @Getter
    private final String[] winningCombination;

    private BingoBoard(BingoBoardBuilder bingoBoardBuilder) {
        this.gameBoard = bingoBoardBuilder.cachedBoard;
        this.scale = bingoBoardBuilder.gameScale;
        winningCombination = new String[this.scale];

        Arrays.fill(winningCombination, MARK);
    }

    public static class BingoBoardBuilder {

        private final String[][] cachedBoard;
        private final int gameScale;

        private BingoBoardBuilder(int gameScale) {
            this.gameScale = gameScale;
            this.cachedBoard = new String[gameScale][gameScale];
        }

        public static BingoBoardBuilder builder(int gameScale) {
            return new BingoBoardBuilder(gameScale);
        }

        public BingoBoardBuilder setRow(int rowNum, String[] values) {
            if (values.length > gameScale || rowNum > gameScale) {
                throw new RuntimeException("Row number cannot be longer than " + gameScale + " got " + values.length);
            }
            cachedBoard[rowNum] = values;
            return this;
        }

        public BingoBoard build() {
            return new BingoBoard(this);
        }
    }
}
