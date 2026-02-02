package cleancode.minesweeper.tobe;

public class Cell {

    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String CLOSED_CELL_SIGN = "□";
    private static final String OPENDED_CELL_SIGN = "■";

    private final String sign;

    private Cell(String sign) {
        this.sign = sign;
    }

    public static Cell of(String sign) {
        return new Cell(sign);
    }

    public static Cell ofFlag() {
        return Cell.of(FLAG_SIGN);
    }

    public static Cell ofLandMine() {
        return Cell.of(LAND_MINE_SIGN);
    }

    public static Cell ofClosed() {
        return Cell.of(CLOSED_CELL_SIGN);
    }

    public static Cell ofOpened() {
        return Cell.of(OPENDED_CELL_SIGN);
    }

    public String getSign() {
        return this.sign;
    }

    public boolean isClosed() {
        return CLOSED_CELL_SIGN.equals(this.sign);
    }

    public boolean doesNotClosed() {
        return !isClosed();
    }

}
