package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {

    private final List<CellPosition> positions;

    public CellPositions(List<CellPosition> positions) {
        this.positions = positions;
    }

    public static CellPositions of(List<CellPosition> cellPositions) {
        return new CellPositions(cellPositions);
    }

    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                CellPosition cellPosition = CellPosition.of(row, column);
                cellPositions.add(cellPosition);
            }
        }

        return CellPositions.of(cellPositions);
    }

    public List<CellPosition> extractRandomPositions(int count) {
        List<CellPosition> cellPositions = new ArrayList<>(positions);
        Collections.shuffle(cellPositions);

        return cellPositions.subList(0, count);
    }

    public List<CellPosition> subtract(List<CellPosition> positionListToSubtract) {
        List<CellPosition> cellPositions = new ArrayList<>(this.positions);

        CellPositions positionToSubtract = CellPositions.of(positionListToSubtract);

        return cellPositions.stream()
                .filter(positionToSubtract::doesNotContain)
                .toList();
    }

    private boolean doesNotContain(CellPosition position) {
        return !positions.contains(position);
    }

    public List<CellPosition> getPositions() {
        return new ArrayList<>(positions);
    }
}
