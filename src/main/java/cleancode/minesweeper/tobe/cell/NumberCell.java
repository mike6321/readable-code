package cleancode.minesweeper.tobe.cell;

public class NumberCell implements Cell {

    private final CellState cellState = CellState.initialized();
    private final int nearbyLandMineCount;

    public NumberCell(int count) {
        this.nearbyLandMineCount = count;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }

    @Override
    public String getSign()  {
        if (this.cellState.isOpened()) {
            return String.valueOf(this.nearbyLandMineCount);
        }
        if (this.cellState.isFlagged()) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }

    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public void opened() {
        cellState.opened();
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }

}
