package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;

public interface InputHandler {

    String getUserInput();

    UserAction getUserActionFromUser();

    CellPosition getCellPositionFromUser();

}
