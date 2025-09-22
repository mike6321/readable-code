package cleancode.minesweeper.tobe.cell;

public class LandMineCell extends Cell2 {

    private boolean isLandMine;
    private static final String LAND_MINE_SIGN = "☼";

    @Override
    public void turnOnlandMine() {
        this.isLandMine = true;
    }

    @Override
    public void updateNearbyLandMinCount(int count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLandMine() {
        return true;
    }

    @Override
    public boolean hasLandMineCount() {
        return false;
    }

    @Override
    public String getSign() {
        if (this.isOpened) {
            return LAND_MINE_SIGN;
        }
        if (this.isFlagged) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
    }

}
