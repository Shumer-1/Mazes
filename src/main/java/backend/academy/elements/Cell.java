package backend.academy.elements;

import lombok.Getter;

@Getter
public enum Cell {
    WALL("\uD83C\uDF33", Integer.MAX_VALUE),
    USUAL("\uD83D\uDFEB", 1),
    SWAMP("\uD83C\uDF0A", 5),
    COIN("\uD83E\uDE99", -5);

    private final String cellImage;
    private final int priority;

    Cell(String cellImage, int priority) {
        this.cellImage = cellImage;
        this.priority = priority;
    }
}
