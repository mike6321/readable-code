package cleancode.minesweeper.tobe.cell;

import java.util.List;

public class Cells {

    private final List<Cell> cells;

    public Cells(List<Cell> cells) {
        this.cells = cells;
    }

    public static Cells of(List<Cell> cells) {
        return new Cells(cells);
    }

}
