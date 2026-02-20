package cleancode.minesweeper.tobe.position;

import java.util.Objects;

public class CellPosition {

    private final int rowIndex;
    private final int columnIndex;

    public CellPosition(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || columnIndex < 0) {
            throw new IllegalArgumentException("올바르지 않은 좌표입니다.");
        }
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public static CellPosition of(int rowIndex, int columnIndex) {
        return new CellPosition(rowIndex, columnIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return rowIndex == that.rowIndex && columnIndex == that.columnIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }

    public boolean isRowIndexMoreThanOrEqual(int rowIndex) {
        return this.rowIndex > rowIndex;
    }

    public boolean isColumnIndexMoreThanOrEqual(int columnIndex) {
        return this.columnIndex > columnIndex;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public CellPosition calculatePositionBy(RelativePosition relativePosition) {
        if (canCalculatePositionBy(relativePosition)) {
            return CellPosition.of(
                    this.rowIndex + relativePosition.getDeltaRow(),
                    this.columnIndex + relativePosition.getDeltaColumn()
            );
        }

        throw new IllegalArgumentException("잘못된 좌표입니다.");
    }

    public boolean canCalculatePositionBy(RelativePosition relativePosition) {
        return this.rowIndex + relativePosition.getDeltaRow() >= 0
                && this.columnIndex + relativePosition.getDeltaColumn() >= 0;
    }

    public boolean isRowIndexLessThan(int rowIndex) {
        return this.rowIndex < rowIndex;
    }

    public boolean isColumnIndexLessThan(int columnIndex) {
        return this.columnIndex < columnIndex;
    }

}
