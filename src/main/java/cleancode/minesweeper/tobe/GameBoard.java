package cleancode.minesweeper.tobe;

import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private static final int LAND_MINE_COUNT = 10;

    public GameBoard(int rowSize, int columnSize) {
        board = new Cell[rowSize][columnSize];
    }

    public void initializeGame() {
        int rowSize = board.length;
        int columnSize = board[0].length;
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < columnSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int landMineColumn = new Random().nextInt(columnSize);
            int landMineRow = new Random().nextInt(rowSize);
            Cell landMineCell = findCell(landMineRow, landMineColumn);
            landMineCell.turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < columnSize; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearbyLandMins(row, col);
                Cell cell = findCell(row, col);
                cell.updateNearByLandMineCount(count);
            }
        }
    }

    private int countNearbyLandMins(int row, int col) {
        int rowSize = getRowSize();
        int columnSize = getColumnSize();
        int count = 0;

        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < columnSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < columnSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < columnSize && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    public int getRowSize() {
        return this.board.length;
    }

    public int getColumnSize() {
        return this.board[0].length;
    }

    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    private boolean isLandMineCell(int selectedRowIndex, int selectedColumnIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColumnIndex);
        return cell.isLandMine();
    }
}
