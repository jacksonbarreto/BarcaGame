package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface IPiece extends Serializable {

    Color getColor();
    EAnimalType getAnimalType();
    EAnimalType getFear();
    Location getLocation();
    void setLocation(Location location);

    List<Position> getLegalPositions(Board boardGame);
    IPiece clone();

    static List<Position> getLinearPositions(Board boardGame, Piece piece) {
        List<Position> legalPosition = new ArrayList<>();
        List<List<Position>> board = boardGame.getBoard();
        int row = piece.getLocation().getRow();
        int column = piece.getLocation().getColumn();
        int firstRow = 0;
        int lastRow = boardGame.getRows();
        int firsColumn = 0;
        int lastColumn = boardGame.getColumns();

        int currentRow = row - 1;
        while (currentRow >= firstRow) {
            if (board.get(currentRow).get(column).isOccupied())
                break;
            else {
                legalPosition.add(board.get(currentRow).get(column));
                currentRow--;
            }
        }

        currentRow = row + 1;
        while (currentRow < lastRow) {
            if (board.get(currentRow).get(column).isOccupied())
                break;
            else {
                legalPosition.add(board.get(currentRow).get(column));
                currentRow++;
            }
        }

        int currentColumn = column - 1;
        while (currentColumn >= firsColumn) {
            if (board.get(row).get(currentColumn).isOccupied())
                break;
            else {
                legalPosition.add(board.get(row).get(currentColumn));
                currentColumn--;
            }
        }
        currentColumn = column + 1;
        while (currentColumn < lastColumn) {
            if (board.get(row).get(currentColumn).isOccupied())
                break;
            else {
                legalPosition.add(board.get(row).get(currentColumn));
                currentColumn++;
            }
        }

        return legalPosition;
    }

    static List<Position> getDiagonalPositions(Board boardGame, Piece piece) {
        List<Position> legalPosition = new ArrayList<>();
        List<List<Position>> board = boardGame.getBoard();
        int row = piece.getLocation().getRow();
        int column = piece.getLocation().getColumn();
        int firstRow = 0;
        int lastRow = boardGame.getRows();
        int firsColumn = 0;
        int lastColumn = boardGame.getColumns();
        int rowNW = row - 1;
        int rowSW = row + 1;
        int rowNE = row - 1;
        int rowSE = row + 1;
        int columnNW = column - 1;
        int columnSW = column - 1;
        int columnNE = column + 1;
        int columnSE = column + 1;

        while (rowNW >= firstRow && columnNW >= firsColumn) {
            if (board.get(rowNW).get(columnNW).isOccupied()) {
                break;
            } else {
                legalPosition.add(board.get(rowNW).get(columnNW));
                columnNW--;
                rowNW--;
            }
        }

        while (rowSW < lastRow && columnSW >= firsColumn) {
            if (board.get(rowSW).get(columnSW).isOccupied()) {
                break;
            } else {
                legalPosition.add(board.get(rowSW).get(columnSW));
                columnSW--;
                rowSW++;
            }
        }

        while (rowSE < lastRow && columnSE < lastColumn) {
            if (board.get(rowSE).get(columnSE).isOccupied()) {
                break;
            } else {
                legalPosition.add(board.get(rowSE).get(columnSE));
                columnSE++;
                rowSE++;
            }
        }

        while (rowNE >= firstRow && columnNE < lastColumn) {
            if (board.get(rowNE).get(columnNE).isOccupied()) {
                break;
            } else {
                legalPosition.add(board.get(rowNE).get(columnNE));
                columnNE++;
                rowNE--;
            }
        }
        return legalPosition;
    }

}
