package cleancode.minesweeper.tobe.cell;

public class EmptyCell extends Cell2 {

    private static final String EMPTY_SIGN = "■";

    @Override
    public void turnOnlandMine() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNearbyLandMinCount(int count) {
        throw new UnsupportedOperationException();
    }

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
