package view;

import model.*;

import java.util.ArrayList;
import java.util.List;

import static model.Color.BLACK;
import static model.Color.WHITE;

public class TesterBoard {

    public static void main(String[] args) {


        new Thread(() -> {
            PlayerMinMax player1 = new PlayerMinMax("MinMax Deep 1", BLACK, 1);
            PlayerRandom player2 = new PlayerRandom("Random", WHITE);
            tester(player1, player2, 5, "##### MinMax X Random #####");
        }).start();

        new Thread(() -> {
            PlayerMinMax player1 = new PlayerMinMax("MinMax Black Deep 1", BLACK, 1);
            PlayerMinMax player2 = new PlayerMinMax("MinMax White Deep 1", WHITE, 1);
            tester(player1, player2, 5, "##### MinMax X MinMax #####");
        }).start();

        new Thread(() -> {
            PlayerMonteCarlo player1 = new PlayerMonteCarlo("MonteCarlo 50%", BLACK, 1, 50);
            PlayerMinMax player2 = new PlayerMinMax("MinMax Deep 1", WHITE, 1);
            tester(player1, player2, 5, "##### Monte Carlo X MinMax #####");
        }).start();

        new Thread(() -> {
            PlayerMonteCarlo player1 = new PlayerMonteCarlo("MonteCarlo 50%", BLACK, 1, 50);
            PlayerRandom player2 = new PlayerRandom("Random", WHITE);
            tester(player1, player2, 5, "##### Monte Carlo X Random #####");
        }).start();

        new Thread(() -> {
            PlayerMonteCarlo player1 = new PlayerMonteCarlo("MonteCarlo 50%", BLACK, 1, 50);
            PlayerMonteCarlo player2 = new PlayerMonteCarlo("MonteCarlo 60%", WHITE, 1, 60);
            tester(player1, player2, 5, "##### Monte Carlo X Monte Carlo #####");
        }).start();


    }


    public static void tester(IPlayer player1, IPlayer player2, final int rounds, String title) {
        List<Integer> black = new ArrayList<>();
        List<Integer> white = new ArrayList<>();
        List<Integer> turns = new ArrayList<>();
        List<Thread> tasks = new ArrayList<>();
        for (int i = 0; i < rounds; i++) {
            Thread thread =
                    new Thread(() -> {
                        GameController gameController = new GameController(player1, player2, 500);
                        EGameStatus result = gameController.startGame();
                        if (result == EGameStatus.BLACK_WINNER) {
                            black.add(1);
                        } else {
                            white.add(1);
                        }
                        turns.add(gameController.getTurnsCount());

                    });
            tasks.add(thread);
            thread.start();
        }

        while (tasks.stream().anyMatch(Thread::isAlive)) ;

        int totalTurns = 0;
        for (int i : turns)
            totalTurns += i;
        System.out.println(title);
        System.out.println("********** Tests **********");
        System.out.println("Rounds: " + rounds);
        System.out.println("AVG TURNS: " + totalTurns / rounds);
        System.out.println(player1.getName() + ": " + (player1.getColor() == BLACK ? black.size() : white.size()));
        System.out.println(player2.getName() + ": " + (player2.getColor() == BLACK ? black.size() : white.size()));
        System.out.println("#################################");
    }
}

