package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.GameException;

import java.util.Objects;

public class CellPosition {

    private final int rowIndex;
    private final int colIndex;

    public CellPosition(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public static CellPosition of(int rowIndex, int colIndex) {
        if (rowIndex < 0 || colIndex < 0) {
            throw new IllegalArgumentException("올바르지 않은 좌표입니다.");
        }
        return new CellPosition(rowIndex, colIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return rowIndex == that.rowIndex && colIndex == that.colIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }

    public boolean isRowIndexMoreThanEqual(int rowIndex) {
        return this.rowIndex >= rowIndex;
    }

    public boolean isColIndexMoreThanEqual(int colIndex) {
        return this.colIndex >= colIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        if (canCalculatePositionBy(relativePosition)) {
            return CellPosition.of(
                    rowIndex + relativePosition.getDeltaRow(),
                    colIndex + relativePosition.getDeltaCol()
            );
        }
        throw new GameException("움직일 수 있는 좌표가 아닙니다.");
    }

    public boolean canCalculatePositionBy(RelativePosition relativePosition) {
        return rowIndex + relativePosition.getDeltaRow() >= 0
                && colIndex+ relativePosition.getDeltaCol() >= 0;
    }

    public boolean isRowLessThan(int rowIndex) {
        return this.rowIndex < rowIndex;
    }

    public boolean isColLessThan(int colIndex) {
        return this.colIndex < colIndex;
    }

}
