package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.VeryBeginner;

public class GameApplication {

    public static void main(String[] args) {
        VeryBeginner gameLevel = new VeryBeginner();
//        Middle gameLevel = new Middle();
//        Advanced gameLevel = new Advanced();

        MineSweeper mineSweeper = new MineSweeper(gameLevel);
        mineSweeper.run();
    }

}
