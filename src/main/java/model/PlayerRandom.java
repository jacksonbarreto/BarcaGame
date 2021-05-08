package model;

import java.util.List;
import java.util.Random;

public class PlayerRandom extends Player {
    public PlayerRandom(String name, Color color) {
        super(name, color);
    }

    @Override
    public Movement getMove(Board board) {
        List<Position> pieces = board.getPositionWithColorPieces(this.getColor());
        List<Position> fearPositions = board.getFearPositions(pieces);
        List<Position> piecesAvailableToPlay = fearPositions.isEmpty() ? pieces : fearPositions;
        Position originPosition;
        do {
            originPosition = piecesAvailableToPlay.get(new Random().nextInt(piecesAvailableToPlay.size()));
        } while (originPosition.getPieceOccupying().getLegalPositions(board).isEmpty());
        int destinyIndex = new Random().nextInt(originPosition.getPieceOccupying().getLegalPositions(board).size());

        Position destinyPosition = originPosition.getPieceOccupying().getLegalPositions(board).get(destinyIndex);
        return new Movement(originPosition.getLocation(), destinyPosition.getLocation());
    }
}
