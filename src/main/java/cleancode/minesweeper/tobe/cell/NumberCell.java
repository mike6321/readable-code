package cleancode.minesweeper.tobe.cell;

public class NumberCell extends Cell2 {

    private int nearByLandMineCount;

    @Override
    public void turnOnLandMine() {
        throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
    }

    @Override
    public void updateNearByLandMineCount(int count) {
        this.nearByLandMineCount = count;
    }

    @Override
    public boolean hasLandMineCount() {
        return true;
    }

    @Override
    public String getSign() {
        if (isOpened) {
            return String.valueOf(nearByLandMineCount);
        }
        if (isFlagged) {
            return FLAG_SIGN;
        }
        return UNCHECKED_SIGN;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

}
