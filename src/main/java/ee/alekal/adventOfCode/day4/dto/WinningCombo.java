package ee.alekal.adventOfCode.day4.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WinningCombo {
    private final BingoBoard board;
    private final int winningNumber;
}
