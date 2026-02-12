package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int columnSize = gameLevel.getColumnSize();
        board = new Cell[rowSize][columnSize];

        this.landMineCount = gameLevel.getLandMineCount();
    }

    public void initializeGame() {
        CellPositions cellPositions = CellPositions.from(board);
        List<CellPosition> allPositions = cellPositions.getPositions();
        updateCellsAt(allPositions, new EmptyCell());

        int rowSize = getRowSize();
        int columnSize = getColumnSize();

        List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMineCount);
        updateCellsAt(landMinePositions, new LandMineCell ());

        List<CellPosition> numberPositions = cellPositions.subtract(landMinePositions);

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < columnSize; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                if (isLandMineCell(cellPosition)) {
                    continue;
                }
                int count = countNearbyLandMins(cellPosition);
                if (count == 0) {
                    continue;
                }
                updateCellAt(cellPosition, new NumberCell(count));
            }
        }
    }

    private void updateCellsAt(List<CellPosition> allPositions, Cell cell) {
        for (CellPosition position : allPositions) {
            updateCellAt(position, cell);
        }
    }

    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColumnIndex()] = cell;
    }

    private int countNearbyLandMins(CellPosition cellPosition) {
        return Math.toIntExact(calculateSurroundedPositions(cellPosition).stream()
                .filter(this::isLandMineCell)
                .count());
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(getRowSize()))
                .filter(position -> position.isColumnIndexLessThan(getColumnSize()))
                .toList();
    }

    public int getRowSize() {
        return this.board.length;
    }

    public int getColumnSize() {
        return this.board[0].length;
    }

    public String getSign(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSign();
    }

    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColumnIndex()];
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int columnSize = getColumnSize();

        return cellPosition.isRowIndexMoreThanOrEqual(rowSize) ||
                cellPosition.isColumnIndexMoreThanOrEqual(columnSize);
    }

    boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    public void openAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.opened();
    }

    public boolean isAllCellChecked() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
    }

    public void openSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }

        openAt(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }

        calculateSurroundedPositions(cellPosition)
                .forEach(this::openSurroundedCells);
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        return findCell(cellPosition)
                .hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        return findCell(cellPosition).isOpened();
    }

}
