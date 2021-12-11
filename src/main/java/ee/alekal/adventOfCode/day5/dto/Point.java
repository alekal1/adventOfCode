package ee.alekal.adventOfCode.day5.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Point {
    private final int x;
    private final int y;

    @Override
    public String toString() {
        return String.join(",", String.valueOf(x), String.valueOf(y));
    }
}
