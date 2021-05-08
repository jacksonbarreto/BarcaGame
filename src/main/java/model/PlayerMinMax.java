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
        Movement greatMovement = null;
        Movement testMovement;
        List<Position> pieces = board.getPositionWithColorPieces(this.getColor());
        List<Position> fearPositions = board.getFearPositions(pieces);
        List<Position> piecesAvailableToPlay = fearPositions.isEmpty() ? pieces : fearPositions;

        for (Position position : piecesAvailableToPlay) {
            for (Position legalPosition : position.getPieceOccupying().getLegalPositions(board)) {
                Board newBoard = board.clone();
                testMovement = new Movement(position.getLocation(), legalPosition.getLocation());

                if (newBoard.isValidMovement(testMovement)) {
                    newBoard.moveTo(testMovement);
                    newScore = minMax(newBoard, searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                    if (newScore > score) {
                        greatMovement = testMovement;
                        score = newScore;
                    }
                }
            }
        }

        return greatMovement;
    }

    protected int minMax(Board board, int deep, int alpha, int beta, boolean maximize) {
        if (board.getGameStatus() != GAMING) {
            if ((this.getColor() == BLACK && board.getGameStatus() == BLACK_WINNER) ||
                    (this.getColor() == WHITE && board.getGameStatus() == WHITE_WINNER)) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        } else if (deep == 0) {
            return heuristic(board, maximize);
        }

        int score = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Color adversary = this.getColor() == BLACK ? BLACK : WHITE;
        Color color = maximize ? this.getColor() : adversary;
        Movement testMovement;
        List<Position> pieces = board.getPositionWithColorPieces(color);
        List<Position> fearPositions = board.getFearPositions(pieces);
        List<Position> piecesAvailableToPlay = fearPositions.isEmpty() ? pieces : fearPositions;

        for (Position position : piecesAvailableToPlay) {
            for (Position legalPosition : position.getPieceOccupying().getLegalPositions(board)) {
                Board newBoard = board.clone();
                testMovement = new Movement(position.getLocation(), legalPosition.getLocation());

                if (newBoard.isValidMovement(testMovement)) {
                    newBoard.moveTo(testMovement);
                    if (maximize) {
                        score = Math.max(score, minMax(newBoard, deep - 1, alpha, beta, false));
                        alpha = Math.max(alpha, score);
                        if (alpha >= beta) break;
                    } else {
                        score = Math.min(score, minMax(newBoard, deep - 1, alpha, beta, true));
                        beta = Math.min(beta, score);
                        if (beta <= alpha) break;
                    }
                }
            }
        }
        return score;
    }

    protected int heuristic(Board board, boolean maximize) {
        int value = 0;
        Color adversary = this.getColor() == BLACK ? BLACK : WHITE;
        Color color = maximize ? this.getColor() : adversary;
        for (Position position : board.getPositionWithColorPieces(color)) {
            if (board.getFountains().contains(position))
                value++;
        }
        return maximize ? value : value * (-1);
    }
}
