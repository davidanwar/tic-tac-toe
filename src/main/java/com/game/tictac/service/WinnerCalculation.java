package com.game.tictac.service;

public class WinnerCalculation implements GameCalculator {

    private final String[][] boardInput;

    public WinnerCalculation(String[][] boardInput) {
        this.boardInput = boardInput;
    }

    @Override
    public String getWinner(String symbol) {
        String[][] board = this.boardInput;
        int row;
        int column;
        String current;
        String next;

        for (int i = 0; i < board.length; i++) {

            if (i == 0) {
                row = 1;
                column = 1;
                current = board[0][0];
                next = board[1][1];

                if (symbol.equals(board[0][0])
                        && isWinnerDiagonalTopLeftBottomRight(row, column, current, next, 0)) {
                    return board[0][0];
                }

                row = board.length - 2;
                current = board[board.length - 1][0];
                next = board[board.length - 2][1];

                if (symbol.equals(board[board.length - 1][0])
                        && isWinnerDiagonalBottomLeftTopRight(row, column, current, next, 0)) {
                    return board[board.length - 1][0];
                }
            }

            row = i;
            column = 1;
            current = board[i][0];
            next = board[i][1];

            if (symbol.equals(board[i][0])
                    && isWinnerHorizontal(row, column, current, next, 0)) {
                return board[i][0];
            }

            row = 1;
            column = i;
            current = board[0][i];
            next = board[1][i];

            if (symbol.equals(board[0][i])
                    && isWinnerVertical(row, column, current, next, 0)) {
                return board[0][i];
            }
        }
        return null;
    }

    private boolean isWinnerDiagonalBottomLeftTopRight(int row, int column, String previous, String current, int count) {
        String[][] board = this.boardInput;
        boolean endOfColumn = column == board.length - 1;
        boolean endOfRow = row == 0;
        boolean isPreviousEqualWithCurrent = previous.equals(current);

        if (isPreviousEqualWithCurrent) {
            count += 1;
            column += 1;
            row -= 1;
            if (!endOfColumn && !endOfRow) {
                return isWinnerDiagonalBottomLeftTopRight(row, column, current, board[row][column], count);
            }
        }
        return count == 2;
    }

    private boolean isWinnerDiagonalTopLeftBottomRight(int row, int column, String previous, String current, int count) {
        String[][] board = this.boardInput;
        boolean endOfColumn = column == board.length - 1;
        boolean endOfRow = row == board.length - 1;
        boolean isPreviousEqualWithCurrent = previous.equals(current);

        if (isPreviousEqualWithCurrent) {
            count += 1;
            column += 1;
            row += 1;
            if (!endOfColumn && !endOfRow) {
                return isWinnerDiagonalTopLeftBottomRight(row, column, current, board[row][column], count);
            }
        }
        return count == 2;
    }

    private boolean isWinnerHorizontal(int row, int column, String previous, String current, int count) {
        String[][] board = this.boardInput;
        boolean endOfColumn = column == board.length - 1;
        boolean isPreviousEqualWithCurrent = previous.equals(current);

        if (isPreviousEqualWithCurrent) {
            count += 1;
            column += 1;

            if (!endOfColumn) {
                return isWinnerHorizontal(row, column, current, board[row][column], count);
            }
        }
        return count == 2;
    }

    private boolean isWinnerVertical(int row, int column, String previous, String current, int count) {
        String[][] board = this.boardInput;
        boolean endOfRow = row == board.length - 1;
        boolean isPreviousEqualWithCurrent = previous.equals(current);
        if (isPreviousEqualWithCurrent) {
            count += 1;
            row += 1;

            if (!endOfRow) {
                return isWinnerVertical(row, column, current, board[row][column], count);
            }
        }
        return count == 2;
    }
}
