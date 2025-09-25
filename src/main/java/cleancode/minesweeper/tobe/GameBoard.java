package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int colSize = gameLevel.getColSize();
        int rowSize = gameLevel.getRowSize();
        this.board = new Cell[rowSize][colSize];

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
            board[landMineRow][landMineCol] = new LandMineCell();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < colSize; column++) {
                CellPosition cellPosition = CellPosition.of(row, column);
                if (isLandMineCell(cellPosition)) {
                    continue;
                }
                int count = countNearbyLandMines(cellPosition);
                if (count == 0) {
                    continue;
                }
                NumberCell numberCell = new NumberCell(count);
                board[row][column] = numberCell;
            }
        }
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = this.findCell(cellPosition);
        cell.flag();
    }

    public void openedAt(CellPosition cellPosition) {
        Cell cell = this.findCell(cellPosition);
        cell.opened();
    }

    public boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int colSize = getColSize();
        int rowSize = getRowSize();
        return cellPosition.isRowIndexMoreThanEqual(rowSize) ||
                cellPosition.isColIndexMoreThanEqual(colSize);
    }

    private int countNearbyLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        return Math.toIntExact(calculateSurroundedPositions(cellPosition, rowSize, colSize)
                .stream()
                .filter(this::isLandMineCell)
                .count());
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowLessThan(rowSize))
                .filter(position -> position.isColLessThan(colSize))
                .toList();
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getSign(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSign();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public void openSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }

        openedAt(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }

        calculateSurroundedPositions(cellPosition, getRowSize(), getColSize())
                .forEach(this::openSurroundedCells);
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition).hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

}
