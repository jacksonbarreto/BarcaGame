package model;

public interface IPlayer extends Comparable<IPlayer>{

    Movement getMove(Board board);
    String getName();
    Color getColor();
}
