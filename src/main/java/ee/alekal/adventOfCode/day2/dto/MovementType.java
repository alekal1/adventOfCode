package ee.alekal.adventOfCode.day2.dto;

import lombok.Getter;

public enum MovementType {

    FORWARD("forward"),
    UP("up"),
    DOWN("down");

    @Getter
    private final String value;

    MovementType(String value) {
        this.value = value;
    }
}
