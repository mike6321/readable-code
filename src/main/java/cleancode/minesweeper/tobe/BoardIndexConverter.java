package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COLUMN = 'a';

    public int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow, rowSize);
    }

    public int getSelectedColumnIndex(String cellInput, int colSize) {
        char cellInputColumn = cellInput.charAt(0);
        return convertColumnFrom(cellInputColumn, colSize);
    }

    private int convertRowFrom(String cellInputRow, int rowSize) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (rowIndex < 0 || rowIndex >= rowSize) {
            throw new GameException("잚못된 입력 입니다.");
        }
        return rowIndex;
    }

    // 열 선택: cmd + shift + 8
    private int convertColumnFrom(char cellInputColumn, int colSize) {
        int columnIndex = cellInputColumn - BASE_CHAR_FOR_COLUMN;
        if (columnIndex < 0 || columnIndex >= colSize) {
            throw new GameException("잘못된 입력 입니다.");
        }
        switch (cellInputColumn) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new GameException("잘못된 입력 입니다.");
        }
    }
    
}
