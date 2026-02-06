package cleancode.minesweeper.tobe;

public class GameBoard {

    private final Cell[][] board;

    public GameBoard(int rowSize, int columnSize) {
        board = new Cell[rowSize][columnSize];
    }

}
