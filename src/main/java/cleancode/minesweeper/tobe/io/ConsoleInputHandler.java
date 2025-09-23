package cleancode.minesweeper.tobe.io;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

    private final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String getUerInput() {
        return SCANNER.nextLine();
    }
}
