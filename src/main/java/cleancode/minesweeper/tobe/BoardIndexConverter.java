package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COLUMN = 'a';

    public int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow, rowSize);
    }

    public int getSelectedColumnIndex(String cellInput, int columnSize) {
        char cellInputColumn = cellInput.charAt(0);
        return convertColumnFrom(cellInputColumn, columnSize);
    }

    private int convertRowFrom(String cellInputRow, int rowSize) { // "10"
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (rowIndex < 0 || rowIndex > rowSize) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColumnFrom(char cellInputColumn, int columnSize) {
        int columnIndex = cellInputColumn - BASE_CHAR_FOR_COLUMN;
        if (columnIndex < 0 || columnIndex > columnSize) {
            throw new GameException("잘못된 입력입니다.");
        }

        return columnIndex;
    }

}
