package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Advanced;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class MinesweeperGame {

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper(new Advanced(), new ConsoleInputHandler(), new ConsoleOutputHandler());
        minesweeper.initialize();
        minesweeper.run();
    }
}
