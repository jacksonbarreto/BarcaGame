package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.Color.BLACK;
import static model.Color.WHITE;
import static model.EGameStatus.*;

public class PlayerMonteCarlos extends PlayerMinMax {
    private final int percentageOfSample;

    public PlayerMonteCarlos(String name, Color color, int searchDepth, int percentageOfSample) {
        super(name, color, searchDepth);
        this.percentageOfSample = percentageOfSample;
    }

    @Override
    public Movement getMove(Board board) {

        int score = Integer.MIN_VALUE;
        int newScore;
        Movement greatMovement = null;
        Movement testMovement= null;
        List<Position> pieces = board.getPositionWithColorPieces(this.getColor());
        List<Position> fearPositions = board.getFearPositions(pieces);
        List<Position> piecesAvailableToPlay = fearPositions.isEmpty() ? pieces : fearPositions;

        for (Position position : piecesAvailableToPlay) {
            if (position.getPieceOccupying().getLegalPositions(board).isEmpty())
                continue;
            int sampleSize = Math.round((float) position.getPieceOccupying().getLegalPositions(board).size() * percentageOfSample / 100);
            List<Position> researchSpace = new ArrayList<>();

            for (int i = 0; i < sampleSize; i++)
                researchSpace.add(position.getPieceOccupying().getLegalPositions(board).get(new Random().nextInt(position.getPieceOccupying().getLegalPositions(board).size())));

            for (Position legalPosition : researchSpace) {
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

        return greatMovement == null ? testMovement : greatMovement;
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
            if (position.getPieceOccupying().getLegalPositions(board).isEmpty())
                continue;
            int sampleSize = Math.round((float) position.getPieceOccupying().getLegalPositions(board).size() * percentageOfSample / 100);
            List<Position> researchSpace = new ArrayList<>();

            for (int i = 0; i < sampleSize; i++)
                researchSpace.add(position.getPieceOccupying().getLegalPositions(board).get(new Random().nextInt(position.getPieceOccupying().getLegalPositions(board).size())));

            for (Position legalPosition : researchSpace) {
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

}
