package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.GameBoard;
import cleancode.minesweeper.tobe.GameException;

public interface OutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard board) ;

    void printGameWinningComment();


    void printGameLosingComment();


    void printCommentForSelectingCell();


    void printCommentForUserAction();


    void pringExceptionMessage(GameException e);


    void printSimpleMessage(String message);
    
}
