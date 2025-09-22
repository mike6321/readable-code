package cleancode.minesweeper.tobe.cell;

public class EmptyCell extends Cell {

    private static final String EMPTY_SIGN = "■";

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public String getSign() {
        if (this.isOpened) {
            return EMPTY_SIGN;
        }
        if (this.isFlagged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }

}
