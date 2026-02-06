package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

import java.util.Arrays;

public class MineSweeper {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COLUMN_SIZE = 10;
    private static final GameBoard BOARD = new GameBoard(BOARD_ROW_SIZE, BOARD_COLUMN_SIZE);
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

    public void run() {
        consoleOutputHandler.showGameStartComments();
        BOARD.initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(BOARD);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (GameException e) {
                consoleOutputHandler.printErrorMessage(e);
            } catch (Exception e) {
                consoleOutputHandler.printSimpleMessage();
            }
        }
    }

    private void actOnCell(String cellInput, String userActionInput) {
        int selectedColumnIndex = getSelectedColumnIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doesUserChooseToPlantFlag(userActionInput)) {
            BOARD[selectedRowIndex][selectedColumnIndex].flag();
            checkIfGameOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColumnIndex)) {
                BOARD[selectedRowIndex][selectedColumnIndex].opened();
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColumnIndex);
            checkIfGameOver();
            return;
        }
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        return convertRowFrom(cellInputRow);
    }

    private int getSelectedColumnIndex(String cellInput) {
        char cellInputColumn = cellInput.charAt(0);
        return convertColumnFrom(cellInputColumn);
    }

    private String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
        return consoleInputHandler.getUserInput();
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private void checkIfGameOver() {
        boolean isAllOpened = isAllCellChecked();
        if (isAllOpened) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private boolean isAllCellChecked() {
        return Arrays.stream(BOARD)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if (rowIndex > BOARD_ROW_SIZE) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColumnFrom(char cellInputColumn) {
        return switch (cellInputColumn) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            case 'i' -> 8;
            case 'j' -> 9;
            default -> throw new GameException("잘못된 입력입니다.");
        };
    }

    private void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COLUMN_SIZE) {
            return;
        }
        if (BOARD[row][col].isOpened()) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        BOARD[row][col].opened();

        if (BOARD[row][col].hasLandMineCount()) {
            return;
        }

        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
