package view;

import model.*;

import static model.Color.BLACK;
import static model.Color.WHITE;

public class TesterBoard {

    public static void main(String[] args) {
        PlayerMinMax playerMinMax = new PlayerMinMax("Yoda", BLACK, 1);
        PlayerMonteCarlos playerMonteCarlos = new PlayerMonteCarlos("Turing", BLACK, 2, 30);
        PlayerRandom playerRandom = new PlayerRandom("Stupid", WHITE);
        GameController gameController = new GameController(playerMonteCarlos, playerRandom, 1000);

        int rounds = 10;
        int black=0;
        int white=0;
        int turns = 0;
        for (int i=0; i < rounds; i++) {
            if (gameController.startGame() == EGameStatus.BLACK_WINNER)
                black++;
            else
                white++;

            turns += gameController.getTurnsCount();
        }

        System.out.println("********** Score **********");
        System.out.println("TURNS AVG: " + (int) turns/rounds);
        System.out.println("Yoda (MinMax): " + black);
        System.out.println("Stupid (Random): " + white);
        System.out.println("***************************");
        //gameController.startGame();
    }
}
