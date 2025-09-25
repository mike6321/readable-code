package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.BoardIndexConverter;
import cleancode.minesweeper.tobe.position.CellPosition;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    private final Scanner SCANNER = new Scanner(System.in);
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public String getUerInput() {
        return SCANNER.nextLine();
    }

    @Override
    public CellPosition getCellPositionFromUser() {
        String userInput = SCANNER.nextLine();
        int colIndex = boardIndexConverter.getSelectedRowIndex(userInput);
        int rowIndex = boardIndexConverter.getSelectedColumnIndex(userInput);

        return CellPosition.of(rowIndex, colIndex);
    }

}
