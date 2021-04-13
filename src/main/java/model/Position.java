package model;

import java.util.Objects;

public class Position {
    private final Location location;
    private final PositionType positionType;
    private final Color positionColor;
    private int heuristicValue;
    private Piece pieceOccupying;

    public Position(Location location, PositionType positionType, Color positionColor) {
        this.location = location;
        this.positionType = positionType;
        this.positionColor = positionColor;
    }

    private Position(Position position){
        this.location = position.location;
        this.positionType = position.positionType;
        this.positionColor = position.positionColor;
        this.heuristicValue = position.heuristicValue;
        this.pieceOccupying = position.pieceOccupying;
    }

    public boolean isOccupied(){
        return this.pieceOccupying != null;
    }

    public void setPiece(Piece piece){
        this.pieceOccupying = piece.clone();
    }
    public void dump(){
        this.pieceOccupying = null;
    }

    public Piece getPieceOccupying() {
        if (this.pieceOccupying == null)
            return null;
        return pieceOccupying.clone();
    }

    public PositionType getPositionType() {
        return positionType;
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
}
