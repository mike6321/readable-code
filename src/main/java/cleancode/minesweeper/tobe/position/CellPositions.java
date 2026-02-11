package cleancode.minesweeper.tobe.position;

import java.util.List;

public class CellPositions {

    private final List<CellPosition> cellPositions;

    public CellPositions(List<CellPosition> cellPositions) {
        this.cellPositions = cellPositions;
    }

    public static CellPositions of(List<CellPosition> cellPositions) {
        new CellPositions(cellPositions);
    }

}
