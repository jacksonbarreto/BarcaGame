package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static model.Board.*;
import static model.EGameStatus.*;


public class GameController {
    private int turnsCount;
    private int currentPlayer;
    private final int playLimit;
    private Board board;
    private final List<IPlayer> players;

    public GameController(Player player1, Player player2, int playLimit) {
        if (player1 == null || player2 == null || playLimit <= 10)
            throw new IllegalArgumentException();
        if (player1.getColor() == player2.getColor())
            throw new IllegalArgumentException();

        this.playLimit = playLimit;
        this.turnsCount = 0;
        this.currentPlayer = drawFirstPlayer();
        this.players = new ArrayList<>(Arrays.asList(player1, player2));
        this.board = createBoard();
    }

    public int getTurnsCount() {
        return turnsCount;
    }

    public IPlayer getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public Board getBoard() {
        return board;
    }

    public List<IPlayer> getPlayers() {
        return new ArrayList<>(players);
    }


    public EGameStatus startGame() {
        //System.out.println("Jogo Iniciado");
        //System.out.println("Primeiro Jogador: " + getCurrentPlayer());
        this.turnsCount = 0;
        this.currentPlayer = drawFirstPlayer();
        this.board = createBoard();
        do {
            Movement movement = players.get(this.currentPlayer).getMove(getBoard());
          //  System.out.println("turno: "+ turnsCount + " | " +getCurrentPlayer()+" :: Movimento: " + movement);
            this.board.moveTo(movement);
            changeTurn();
        } while (board.getGameStatus() == GAMING && this.turnsCount < this.playLimit);

        //chama tela de resultado da interface
        return this.board.getGameStatus();
        //System.out.println("Resultado: " + this.board.getGameStatus());
    }


    private int drawFirstPlayer() {
        return new Random().nextInt(2);
    }

    private void changeTurn() {
        this.turnsCount++;
        this.currentPlayer = this.currentPlayer == 0 ? 1 : 0;
    }
}
