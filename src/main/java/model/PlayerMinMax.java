package model;

import java.util.List;

import static model.Color.BLACK;
import static model.Color.WHITE;
import static model.EGameStatus.*;

public class PlayerMinMax extends Player {

    protected final int searchDepth;

    public PlayerMinMax(String name, Color color, int searchDepth) {
        super(name, color);
        this.searchDepth = searchDepth;
    }


    @Override
    public Movement getMove(Board board) {
        int score = Integer.MIN_VALUE;
        int newScore;
        Movement movementToEvaluate;

        List<Position> piecesAvailableToPlay = getAvailablePiece(board, true);

        // If one of the legal positions, of the pieces available to play, passes through a fountain,
        // it is played in this position.
        Movement greatMovement = playAtTheFountain(board, piecesAvailableToPlay);
        if (greatMovement != null)
            return greatMovement;

        for (Position position : piecesAvailableToPlay) {
            for (Position legalPosition : position.getPieceOccupying().getLegalPositions(board)) {
                Board newBoard = board.clone();
                movementToEvaluate = new Movement(position.getLocation(), legalPosition.getLocation());
                newBoard.moveTo(movementToEvaluate);
                newScore = minMax(newBoard, searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                if (newScore > score) {
                    greatMovement = movementToEvaluate;
                    score = newScore;
                }
            }
        }
        return greatMovement;
    }

    protected int minMax(Board board, int deep, int alpha, int beta, boolean maximize) {
        if (board.getGameStatus() != GAMING)
            return Integer.MAX_VALUE;
        if (deep == 0)
            return heuristic(board, maximize);

        int score = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Movement testMovement;

        List<Position> piecesAvailableToPlay = getAvailablePiece(board, maximize);

        for (Position position : piecesAvailableToPlay) {
            for (Position legalPosition : position.getPieceOccupying().getLegalPositions(board)) {
                Board newBoard = board.clone();
                testMovement = new Movement(position.getLocation(), legalPosition.getLocation());
                newBoard.moveTo(testMovement);
                if (maximize) {
                    // If that position is a fountain, then it is worth a lot.
                    if (board.getFountains().contains(legalPosition))
                        score = Math.max(score, 1000);
                    else
                        score = Math.max(score, minMax(newBoard, deep - 1, alpha, beta, false));
                    alpha = Math.max(alpha, score);
                } else {
                    // If that position is a fountain, then it is worth a lot.
                    if (board.getFountains().contains(legalPosition))
                        score = Math.min(score, 1000);
                    else
                        score = Math.min(score, minMax(newBoard, deep - 1, alpha, beta, true));
                    beta = Math.min(beta, score);
                }
                if (alpha >= beta) break;
            }
        }
        return score;
    }

    protected List<Position> getAvailablePiece(Board board, boolean maximize) {
        List<Position> allMyPieces = board.getPositionWithColorPieces(getCurrentColor(maximize));
        List<Position> scaredPieces = board.getFearPositions(allMyPieces);
        List<Position> piecesAvailableToPlay;

        if (scaredPieces.isEmpty()) {
            piecesAvailableToPlay = allMyPieces;
            // Removes the pieces that are already in the fountain.
            piecesAvailableToPlay.removeAll(board.getFountains());
        } else {
            // It forces you to play with the scared pieces.
            piecesAvailableToPlay = scaredPieces;
        }
        return piecesAvailableToPlay;
    }

    protected Movement playAtTheFountain(Board board, List<Position> piecesAvailableToPlay) {
        for (Position position : piecesAvailableToPlay) {
            for (Position legalPosition : position.getPieceOccupying().getLegalPositions(board)) {
                if (board.getFountains().contains(legalPosition))
                    return new Movement(position.getLocation(), legalPosition.getLocation());
            }
        }
        return null;
    }

    protected Color getCurrentColor(boolean maximize) {
        Color adversary = this.getColor() == BLACK ? WHITE : BLACK;
        return maximize ? this.getColor() : adversary;
    }

    protected int heuristic(Board board, boolean maximize) {
        int value = 0;
        Color color = getCurrentColor(maximize);
        List<Position> pieces = board.getPositionWithColorPieces(color);
        List<Position> fearPositions = board.getFearPositions(pieces);
        List<Position> piecesAvailableToPlay = fearPositions.isEmpty() ? pieces : fearPositions;

        for (Position position : board.getPositionWithColorPieces(color)) {
            if (board.getFountains().contains(position))
                value += 1000;
        }

        //Score for each possibility that a piece has to go to a fountain.
        for (Position position : piecesAvailableToPlay) {
            for (Position legalPosition : position.getPieceOccupying().getLegalPositions(board)) {
                if (board.getFountains().contains(legalPosition))
                    value += 100;
            }
        }

        return value;
    }
}
