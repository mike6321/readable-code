package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell2;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final Cell2[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int colSize = gameLevel.getColSize();
        int rowSize = gameLevel.getRowSize();
        this.board = new Cell2[rowSize][colSize];

        this.landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {
        int rowSize = board.length;
        int colSize = board[0].length;
        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < colSize; column++) {
                board[row][column] = new EmptyCell();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(10);
            int landMineRow = new Random().nextInt(8);
            LandMineCell landMineCell = new LandMineCell();
            landMineCell.turnOnlandMine();
            board[landMineRow][landMineCol] = landMineCell;
        }

        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < colSize; column++) {
                int count = 0;
                if (isLandMineCell(row, column)) {
                    continue;
                }
                count = countNearbyLandMines(row, column, count);
                if (count == 0) {
                    continue;
                }
                NumberCell numberCell = new NumberCell();
                numberCell.updateNearbyLandMinCount(count);
                board[row][column] = numberCell;
            }
        }
    }

    public void flag(int rowIndex, int columnIndex) {
        Cell2 cell2 = this.findCell(rowIndex, columnIndex);
        cell2.flag();
    }

    public void opened(int rowIndex, int columnIndex) {
        Cell2 cell2 = this.findCell(rowIndex, columnIndex);
        cell2.opened();
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectColumnIndex) {
        Cell2 cell2 = findCell(selectedRowIndex, selectColumnIndex);
        return cell2.isLandMine();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell2::isChecked);
    }

    private int countNearbyLandMines(int row, int column, int count) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        if (row - 1 >= 0 && column - 1 >= 0 && isLandMineCell(row - 1, column - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, column)) {
            count++;
        }
        if (row - 1 >= 0 && column + 1 < colSize && isLandMineCell(row - 1, column + 1)) {
            count++;
        }
        if (column - 1 >= 0 && isLandMineCell(row, column - 1)) {
            count++;
        }
        if (column + 1 < colSize && isLandMineCell(row, column + 1)) {
            count++;
        }
        if (row + 1 < rowSize && column - 1 >= 0 && isLandMineCell(row + 1, column - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, column)) {
            count++;
        }
        if (row + 1 < rowSize && column + 1 < colSize && isLandMineCell(row + 1, column + 1)) {
            count++;
        }
        return count;
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(int row, int column) {
        Cell2 cell2 = findCell(row, column);
        return cell2.getSign();
    }

    private Cell2 findCell(int rowIndex, int columnIndex) {
        return board[rowIndex][columnIndex];
    }

    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= this.getRowSize() || col < 0 || col >= this.getColSize()) {
            return;
        }
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        opened(row, col);

        if (findCell(row, col).hasLandMineCount()) {
            return;
        }

        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }

}
