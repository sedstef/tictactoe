package at.sedelmaier.tictactoe.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Player {
    RED("red"),
    BLUE("blue");

    private final String name;

    private Player(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
