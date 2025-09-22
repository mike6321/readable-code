package cleancode.minesweeper.tobe.cell;

public class NumberCell extends Cell {

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
        if (this.isOpened) {
            return String.valueOf(this.nearbyLandMineCount);
        }
        if (this.isFlagged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }

}
