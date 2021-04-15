package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Piece {
    protected final Color color;
    protected final EAnimalType EAnimalType;
    protected final EAnimalType fear;
    protected Location location;

    public Piece(Color color, EAnimalType EAnimalType, EAnimalType fear, Location location) {
        this.color = color;
        this.EAnimalType = EAnimalType;
        this.fear = fear;
        this.location = location;
    }


    public Color getColor() {
        return color;
    }

    public EAnimalType getAnimalType() {
        return EAnimalType;
    }

    public EAnimalType getFear() {
        return fear;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public abstract List<Position> getLegalPositions(Board boardGame);

    public abstract Piece clone();

    public static List<Position> getLinearPositions(Board boardGame, Piece piece) {
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

    public static List<Position> getDiagonalPositions(Board boardGame, Piece piece) {
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
    /*
    public static List<Position> getFearPositions(List<Position> positions, Board boardGame){
        return getFearPositions(positions,boardGame);
    }
    public static List<Position> getFearPositions(List<Position> positions, Board boardGame, Piece piece){
        List<Position> fearPositions = new ArrayList<>();
        List<List<Position>> board = boardGame.getBoard();

        for (Position currentPosition : positions) {
            if (piece == null)
                piece = currentPosition.getPieceOccupying();

            int row = currentPosition.getLocation().getRow();
            int column = currentPosition.getLocation().getColumn();
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
            int rowN = row - 1;
            int rowS = row + 1;
            int columnW = column - 1;
            int columnE = column + 1;

            if (rowNW >= firstRow && columnNW >= firsColumn) {
                if (board.get(rowNW).get(columnNW).isOccupied() && board.get(rowNW).get(columnNW).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowNW).get(columnNW).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        fearPositions.add(currentPosition);
                        continue;
                    }

                }
            }

            if (rowSW < lastRow && columnSW >= firsColumn) {
                if (board.get(rowSW).get(columnSW).isOccupied() && board.get(rowSW).get(columnSW).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowSW).get(columnSW).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        fearPositions.add(currentPosition);
                        continue;
                    }
                }
            }

            if (rowSE < lastRow && columnSE < lastColumn) {
                if (board.get(rowSE).get(columnSE).isOccupied() && board.get(rowSE).get(columnSE).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowSE).get(columnSE).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        fearPositions.add(currentPosition);
                        continue;
                    }
                }
            }

            if (rowNE >= firstRow && columnNE < lastColumn) {
                if (board.get(rowNE).get(columnNE).isOccupied() && board.get(rowNE).get(columnNE).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowNE).get(columnNE).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        fearPositions.add(currentPosition);
                        continue;
                    }
                }
            }

            if (rowN >= firstRow) {
                if (board.get(rowN).get(column).isOccupied() && board.get(rowN).get(column).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowN).get(column).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        fearPositions.add(currentPosition);
                        continue;
                    }
                }
            }
            if (rowS < lastRow) {
                if (board.get(rowS).get(column).isOccupied() && board.get(rowS).get(column).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowS).get(column).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        fearPositions.add(currentPosition);
                        continue;
                    }
                }
            }
            if (columnW >= firsColumn) {
                if (board.get(row).get(columnW).isOccupied() && board.get(row).get(columnW).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(row).get(columnW).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        fearPositions.add(currentPosition);
                        continue;
                    }
                }
            }
            if (columnE < lastColumn) {
                if (board.get(row).get(columnE).isOccupied() && board.get(row).get(columnE).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(row).get(columnE).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        fearPositions.add(currentPosition);
                    }
                }
            }
        }
        return fearPositions;
    }
    public static List<Position>  removeFearPositions(List<Position> legalPosition, Board boardGame, Piece piece) {
        List<Position> positionsFear = getFearPositions(legalPosition,boardGame,piece);
        legalPosition.removeAll(positionsFear);
        return legalPosition;
    }
/*
    public static void removeFearPositions(List<Position> legalPosition, Board boardGame, Piece piece) {
        List<List<Position>> board = boardGame.getBoard();
        Iterator<Position> it = legalPosition.iterator();

        while (it.hasNext()) {
            Position currentPosition = it.next();
            int row = currentPosition.getLocation().getRow();
            int column = currentPosition.getLocation().getColumn();
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
            int rowN = row - 1;
            int rowS = row + 1;
            int columnW = column - 1;
            int columnE = column + 1;

            if (rowNW >= firstRow && columnNW >= firsColumn) {
                if (board.get(rowNW).get(columnNW).isOccupied() && board.get(rowNW).get(columnNW).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowNW).get(columnNW).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        it.remove();
                        continue;
                    }

                }
            }

            if (rowSW < lastRow && columnSW >= firsColumn) {
                if (board.get(rowSW).get(columnSW).isOccupied() && board.get(rowSW).get(columnSW).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowSW).get(columnSW).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        it.remove();
                        continue;
                    }
                }
            }

            if (rowSE < lastRow && columnSE < lastColumn) {
                if (board.get(rowSE).get(columnSE).isOccupied() && board.get(rowSE).get(columnSE).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowSE).get(columnSE).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        it.remove();
                        continue;
                    }
                }
            }

            if (rowNE >= firstRow && columnNE < lastColumn) {
                if (board.get(rowNE).get(columnNE).isOccupied() && board.get(rowNE).get(columnNE).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowNE).get(columnNE).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        it.remove();
                        continue;
                    }
                }
            }

            if (rowN >= firstRow) {
                if (board.get(rowN).get(column).isOccupied() && board.get(rowN).get(column).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowN).get(column).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        it.remove();
                        continue;
                    }
                }
            }
            if (rowS < lastRow) {
                if (board.get(rowS).get(column).isOccupied() && board.get(rowS).get(column).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(rowS).get(column).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        it.remove();
                        continue;
                    }
                }
            }
            if (columnW >= firsColumn) {
                if (board.get(row).get(columnW).isOccupied() && board.get(row).get(columnW).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(row).get(columnW).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        it.remove();
                        continue;
                    }
                }
            }
            if (columnE < lastColumn) {
                if (board.get(row).get(columnE).isOccupied() && board.get(row).get(columnE).getPieceOccupying().getColor() != piece.getColor()) {
                    if (board.get(row).get(columnE).getPieceOccupying().getAnimalType() == piece.getFear()) {
                        it.remove();
                    }
                }
            }
        }
    }

 */

}
