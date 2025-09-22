package cleancode.minesweeper.tobe.cell;

public class NumberCell extends Cell2 {

    private int nearbyLandMineCount;

    @Override
    public void turnOnlandMine() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateNearbyLandMinCount(int count) {
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
