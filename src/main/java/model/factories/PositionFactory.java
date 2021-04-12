package model.factories;

import model.Color;
import model.Location;
import model.Position;

import static model.PositionType.*;

public class PositionFactory {

    public static Position createPositionCommon(Location location, Color positionColor) {
        return new Position(location, COMMON, positionColor);
    }

    public static Position createPositionFountain(Location location, Color positionColor) {
        return new Position(location, FOUNTAIN, positionColor);
    }
}
