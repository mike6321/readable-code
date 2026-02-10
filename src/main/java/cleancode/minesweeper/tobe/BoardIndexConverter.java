package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COLUMN = 'a';

    public int getSelectedRowIndex(String cellInput) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow);
    }

    public int getSelectedColumnIndex(String cellInput) {
        char cellInputColumn = cellInput.charAt(0);
        return convertColumnFrom(cellInputColumn);
    }

    private int convertRowFrom(String cellInputRow) { // "10"
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (rowIndex < 0) {
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColumnFrom(char cellInputColumn) {
        int columnIndex = cellInputColumn - BASE_CHAR_FOR_COLUMN;
        if (columnIndex < 0) {
            throw new GameException("잘못된 입력입니다.");
        }

        return columnIndex;
    }

}
