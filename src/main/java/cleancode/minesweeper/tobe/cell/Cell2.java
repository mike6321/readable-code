package cleancode.minesweeper.tobe.cell;

public abstract class Cell2 {

    public static final String FLAG_SIGN = "⚑";
    public static final String UNCHECKED_SIGN = "□";

    public boolean isFlagged;
    public boolean isOpened;

    public abstract void turnOnLandMine();

    public abstract void updateNearByLandMineCount(int count);

    public abstract boolean hasLandMineCount();

    public abstract String getSign();

    public abstract boolean isLandMine();

    public void flag() {
        this.isFlagged = true;
    }

    public void opened() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return this.isFlagged || this.isOpened;
    }

    public boolean isOpened() {
        return this.isOpened;
    }
}
