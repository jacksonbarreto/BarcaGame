package model;

import java.util.Objects;

public class Position {
    private final Location location;
    private final EPositionType EPositionType;
    private final Color positionColor;
    private int heuristicValue;
    private IPiece pieceOccupying;

    public Position(Location location, EPositionType EPositionType, Color positionColor) {
        this.location = location;
        this.EPositionType = EPositionType;
        this.positionColor = positionColor;
    }

    private Position(Position position){
        this.location = position.location;
        this.EPositionType = position.EPositionType;
        this.positionColor = position.positionColor;
        this.heuristicValue = position.heuristicValue;
        this.pieceOccupying = position.pieceOccupying;
    }

    public boolean isOccupied(){
        return this.pieceOccupying != null;
    }

    public void setPiece(IPiece piece){
        this.pieceOccupying = piece.clone();
    }
    public void dump(){
        this.pieceOccupying = null;
    }

    public IPiece getPieceOccupying() {
        if (this.pieceOccupying == null)
            return null;
        return pieceOccupying.clone();
    }

    public EPositionType getPositionType() {
        return this.EPositionType;
    }

    public Color getPositionColor() {
        return positionColor;
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public Location getLocation() {
        return location;
    }

    public Position clone(){
        return new Position(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return location.equals(position.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "Position{" +
                "location=" + location +
                '}';
    }
}
