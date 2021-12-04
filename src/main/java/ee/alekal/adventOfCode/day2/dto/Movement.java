package ee.alekal.adventOfCode.day2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Movement {
    private final MovementType movementType;
    private final Integer movementValue;

    @Override
    public String toString() {
        return "Movement{" +
                "movementType=" + movementType +
                ", movementValue=" + movementValue +
                '}';
    }
}
