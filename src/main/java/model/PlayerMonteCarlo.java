package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.EGameStatus.GAMING;

public class PlayerMonteCarlo extends PlayerMinMax {
    private final int percentageOfSample;

    public PlayerMonteCarlo(String name, Color color, int searchDepth, int percentageOfSample) {
        super(name, color, searchDepth);
        this.percentageOfSample = percentageOfSample;
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
            if (position.getPieceOccupying().getLegalPositions(board).isEmpty())
                continue;
            int sampleSize = (int) Math.ceil((double) position.getPieceOccupying().getLegalPositions(board).size() * (double) percentageOfSample / 100);
            List<Position> researchSpace = new ArrayList<>();

            for (int i = 0; i < sampleSize; i++)
                researchSpace.add(position.getPieceOccupying().getLegalPositions(board).get(new Random().nextInt(position.getPieceOccupying().getLegalPositions(board).size())));

            for (Position legalPosition : researchSpace) {
                Board newBoard = board.clone();
                movementToEvaluate = new Movement(position.getLocation(), legalPosition.getLocation());

                if (newBoard.isValidMovement(movementToEvaluate)) {
                    newBoard.moveTo(movementToEvaluate);
                    newScore = minMax(newBoard, searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                    if (newScore > score) {
                        greatMovement = movementToEvaluate;
                        score = newScore;
                    }
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
            if (position.getPieceOccupying().getLegalPositions(board).isEmpty())
                continue;
            int sampleSize = (int) Math.ceil((double) position.getPieceOccupying().getLegalPositions(board).size() * percentageOfSample / 100);
            List<Position> researchSpace = new ArrayList<>();

            for (int i = 0; i < sampleSize; i++)
                researchSpace.add(position.getPieceOccupying().getLegalPositions(board).get(new Random().nextInt(position.getPieceOccupying().getLegalPositions(board).size())));

            for (Position legalPosition : researchSpace) {
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

}
