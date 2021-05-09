package model;

import model.factories.PositionFactory;

import java.util.*;
import java.util.function.Predicate;

import static model.Color.*;
import static model.EGameStatus.*;
import static model.EPositionType.*;
import static model.factories.PieceFactory.*;

public class Board {
    private static final int TOTAL_PIECES = 12;
    private static final int SIZE_DEFAULT = 10;
    private static final Predicate<Integer> ODD = i -> i % 2 != 0;
    private static final List<Location> FOUNTAINS_IN_BOARD = new ArrayList<>(
            Arrays.asList(new Location(3, 3),
                    new Location(3, 6),
                    new Location(6, 3),
                    new Location(6, 6)));
    private static final Predicate<Location> FOUNTAIN_LOCATION = FOUNTAINS_IN_BOARD::contains;
    private final int rows;
    private final int columns;
    private final List<List<Position>> board;

    public Board() {
        this.rows = SIZE_DEFAULT;
        this.columns = SIZE_DEFAULT;

        this.board = initializesBoard();

        initializesPositions();
        loadPieces();
    }

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        this.board = initializesBoard();

        initializesPositions();
        loadPieces();
    }

    private Board(Board board) {
        this.rows = board.rows;
        this.columns = board.columns;
        this.board = board.copyBoard();
    }

    public Board clone() {
        return new Board(this);
    }

    public static Board createBoard() {
        return new Board();
    }

    public List<List<Position>> getBoard() {
        return copyBoard();
    }

    public EGameStatus getGameStatus() {
        int blackPiecesInFountain = 0;
        int blackWhiteInFountain = 0;
        List<Position> fountains = getFountains();

        for (Position position : fountains) {
            if (position.isOccupied() && position.getPieceOccupying().getColor() == BLACK) {
                blackPiecesInFountain++;
            } else if (position.isOccupied() && position.getPieceOccupying().getColor() == WHITE) {
                blackWhiteInFountain++;
            }
        }

        if (blackPiecesInFountain >= 3)
            return BLACK_WINNER;
        if (blackWhiteInFountain >= 3)
            return WHITE_WINNER;
        return GAMING;
    }

    //por boolean
    public void moveTo(Movement movement) {
        if (isValidMovement(movement)) {
            Position positionOrigin = this.board.get(movement.getOrigin().getRow()).get(movement.getOrigin().getColumn());
            Position positionDestination = this.board.get(movement.getDestination().getRow()).get(movement.getDestination().getColumn());
            IPiece piece = positionOrigin.getPieceOccupying();
            piece.setLocation(new Location(movement.getDestination().getRow(), movement.getDestination().getColumn()));
            positionDestination.setPiece(piece);
            positionOrigin.dump();
        }
    }

    public boolean isValidMovement(Movement movement) {
        if (movement == null)
            return false;
        if ((movement.getOrigin().getRow() < 0 || movement.getOrigin().getRow() > this.rows - 1) ||
                (movement.getOrigin().getColumn() < 0 || movement.getOrigin().getColumn() > this.columns - 1) ||
                (movement.getDestination().getRow() < 0 || movement.getDestination().getRow() > this.rows - 1) ||
                (movement.getDestination().getColumn() < 0 || movement.getDestination().getColumn() > this.columns - 1))
            return false;
        Position positionOrigin = this.getBoard().get(movement.getOrigin().getRow()).get(movement.getOrigin().getColumn());
        Position positionDestination = this.getBoard().get(movement.getDestination().getRow()).get(movement.getDestination().getColumn());
        if (!positionOrigin.isOccupied())
            return false;
        if (positionDestination.isOccupied())
            return false;

        IPiece piece = positionOrigin.getPieceOccupying();
        List<Position> legalPositionsWithoutFearfulPositions = removeFearPositions(piece.getLegalPositions(this), piece);


        if (legalPositionsWithoutFearfulPositions.isEmpty())
            return piece.getLegalPositions(this).contains(positionDestination);
        else
            return legalPositionsWithoutFearfulPositions.contains(positionDestination);
    }

    public List<Position> getPositionWithColorPieces(Color color) {
        Predicate<Position> isThatColor = (p) -> p.isOccupied() && p.getPieceOccupying().getColor() == color;
        return getPositionMeets(isThatColor);
    }

    public List<Position> getFountains() {
        Predicate<Position> isFountain = (p) -> p.getPositionType() == FOUNTAIN;
        return getPositionMeets(isFountain);
    }

    private List<Position> getPositionMeets(Predicate<Position> predicate) {
        List<Position> positionsMatch = new ArrayList<>(TOTAL_PIECES);

        for (List<Position> currentRow : this.board) {
            for (Position currentPosition : currentRow) {
                if (predicate.test(currentPosition))
                    positionsMatch.add(currentPosition);
            }
        }
        return positionsMatch;
    }

    public Position getPosition(Location location) {
        int row = location.getRow();
        int column = location.getColumn();

        return this.board.get(row).get(column).clone();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Position> getFearPositions(List<Position> positions) {
        return getFearPositions(positions, null);
    }

    public List<Position> getFearPositions(List<Position> positions, IPiece piece) {
        List<Position> fearPositions = new ArrayList<>();
        List<List<Position>> board = this.board;
        boolean isNotRemoveFear = piece == null;
        for (Position currentPosition : positions) {
            if (isNotRemoveFear) {
                piece = currentPosition.getPieceOccupying();
            }
            int row = currentPosition.getLocation().getRow();
            int column = currentPosition.getLocation().getColumn();
            int firstRow = 0;
            int lastRow = this.rows;
            int firsColumn = 0;
            int lastColumn = this.columns;
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

    public List<Position> removeFearPositions(List<Position> legalPosition, IPiece piece) {
        List<Position> positionsFear = getFearPositions(legalPosition, piece);
        legalPosition.removeAll(positionsFear);
        return legalPosition;
    }


    private List<List<Position>> copyBoard() {
        List<List<Position>> copyBoard = initializesBoard();

        for (int row = 0; row < rows; row++) {
            List<Position> currentRow = this.board.get(row);
            List<Position> currentRowOfCopyBoard = copyBoard.get(row);
            for (int column = 0; column < columns; column++) {
                currentRowOfCopyBoard.add(column, currentRow.get(column).clone());
            }
        }
        return copyBoard;
    }


    private List<List<Position>> initializesBoard() {
        List<List<Position>> board = new Vector<>(rows);
        for (int i = 0; i < rows; i++)
            board.add(new Vector<>(columns));
        return board;
    }

    private void initializesPositions() {
        Color colorPosition;
        for (int row = 0; row < rows; row++) {
            List<Position> currentRow = this.board.get(row);
            for (int column = 0; column < columns; column++) {
                Location location = new Location(row, column);
                colorPosition = WHITE;
                if (ODD.test(column + row))
                    colorPosition = BLUE;
                if (FOUNTAIN_LOCATION.test(location))
                    currentRow.add(column, PositionFactory.createPositionFountain(location, colorPosition));
                else
                    currentRow.add(column, PositionFactory.createPositionCommon(location, colorPosition));
            }
        }
    }

    private void loadPieces() {
        List<Position> row0 = this.board.get(0);
        List<Position> row1 = this.board.get(1);
        List<Position> row8 = this.board.get(8);
        List<Position> row9 = this.board.get(9);

        row0.get(4).setPiece(createElephant(WHITE, new Location(0, 4)));
        row0.get(5).setPiece(createElephant(WHITE, new Location(0, 5)));
        row1.get(4).setPiece(createMouse(WHITE, new Location(1, 4)));
        row1.get(5).setPiece(createMouse(WHITE, new Location(1, 5)));
        row1.get(3).setPiece(createLion(WHITE, new Location(1, 3)));
        row1.get(6).setPiece(createLion(WHITE, new Location(1, 6)));

        row9.get(4).setPiece(createElephant(BLACK, new Location(9, 4)));
        row9.get(5).setPiece(createElephant(BLACK, new Location(9, 5)));
        row8.get(4).setPiece(createMouse(BLACK, new Location(8, 4)));
        row8.get(5).setPiece(createMouse(BLACK, new Location(8, 5)));
        row8.get(3).setPiece(createLion(BLACK, new Location(8, 3)));
        row8.get(6).setPiece(createLion(BLACK, new Location(8, 6)));
    }
}
