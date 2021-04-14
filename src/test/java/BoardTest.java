import model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.EAnimalType.*;
//import static model.Board.isValidMovement;
import static model.Color.*;
import static model.EGameStatus.*;
import static model.EPositionType.COMMON;
import static model.EPositionType.FOUNTAIN;
import static model.factories.PositionFactory.createPositionCommon;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BoardTest {
    Board obj1 = new Board();

    @Test
    public void shouldLoadLionsInCorrectInitialPosition() {
        assertSame(obj1.getPosition(new Location(1, 3)).getPieceOccupying().getAnimalType(), LION);
        assertSame(obj1.getPosition(new Location(1, 3)).getPieceOccupying().getColor(), WHITE);
        assertSame(obj1.getPosition(new Location(1, 6)).getPieceOccupying().getAnimalType(), LION);
        assertSame(obj1.getPosition(new Location(1, 6)).getPieceOccupying().getColor(), WHITE);

        assertSame(obj1.getPosition(new Location(8, 3)).getPieceOccupying().getAnimalType(), LION);
        assertSame(obj1.getPosition(new Location(8, 3)).getPieceOccupying().getColor(), BLACK);
        assertSame(obj1.getPosition(new Location(8, 6)).getPieceOccupying().getAnimalType(), LION);
        assertSame(obj1.getPosition(new Location(8, 6)).getPieceOccupying().getColor(), BLACK);
    }

    @Test
    public void shouldLoadElephantInCorrectInitialPosition() {
        assertSame(obj1.getPosition(new Location(0, 4)).getPieceOccupying().getAnimalType(), ELEPHANT);
        assertSame(obj1.getPosition(new Location(0, 4)).getPieceOccupying().getColor(), WHITE);
        assertSame(obj1.getPosition(new Location(0, 5)).getPieceOccupying().getAnimalType(), ELEPHANT);
        assertSame(obj1.getPosition(new Location(0, 5)).getPieceOccupying().getColor(), WHITE);
        assertSame(obj1.getPosition(new Location(9, 4)).getPieceOccupying().getAnimalType(), ELEPHANT);
        assertSame(obj1.getPosition(new Location(9, 4)).getPieceOccupying().getColor(), BLACK);
        assertSame(obj1.getPosition(new Location(9, 5)).getPieceOccupying().getAnimalType(), ELEPHANT);
        assertSame(obj1.getPosition(new Location(9, 5)).getPieceOccupying().getColor(), BLACK);
    }

    @Test
    public void shouldLoadMouseInCorrectInitialPosition() {
        assertSame(obj1.getPosition(new Location(1, 4)).getPieceOccupying().getAnimalType(), MOUSE);
        assertSame(obj1.getPosition(new Location(1, 4)).getPieceOccupying().getColor(), WHITE);
        assertSame(obj1.getPosition(new Location(1, 5)).getPieceOccupying().getAnimalType(), MOUSE);
        assertSame(obj1.getPosition(new Location(1, 5)).getPieceOccupying().getColor(), WHITE);
        assertSame(obj1.getPosition(new Location(8, 4)).getPieceOccupying().getAnimalType(), MOUSE);
        assertSame(obj1.getPosition(new Location(8, 4)).getPieceOccupying().getColor(), BLACK);
        assertSame(obj1.getPosition(new Location(8, 5)).getPieceOccupying().getAnimalType(), MOUSE);
        assertSame(obj1.getPosition(new Location(8, 5)).getPieceOccupying().getColor(), BLACK);
    }

    @Test
    public void shouldLoadPiecesWithLocationOfPieceCorrect() {
        assertEquals(new Location(8, 6), obj1.getPosition(new Location(8, 6)).getPieceOccupying().getLocation());
        assertEquals(new Location(9, 4), obj1.getPosition(new Location(9, 4)).getPieceOccupying().getLocation());
        assertEquals(new Location(1, 4), obj1.getPosition(new Location(1, 4)).getPieceOccupying().getLocation());
        assertEquals(new Location(0, 4), obj1.getPosition(new Location(0, 4)).getPieceOccupying().getLocation());
        assertNotEquals(new Location(8, 6), obj1.getPosition(new Location(0, 4)).getPieceOccupying().getLocation());
    }

    @Test
    public void shouldHaveFountainInCorrectPosition() {
        assertSame(FOUNTAIN, obj1.getPosition(new Location(3, 3)).getPositionType());
        assertSame(FOUNTAIN, obj1.getPosition(new Location(3, 6)).getPositionType());
        assertSame(FOUNTAIN, obj1.getPosition(new Location(6, 3)).getPositionType());
        assertSame(FOUNTAIN, obj1.getPosition(new Location(6, 6)).getPositionType());
    }

    @Test
    public void shouldHaveCorrectColorPosition() {
        assertSame(WHITE, obj1.getPosition(new Location(0, 0)).getPositionColor());
        assertSame(BLUE, obj1.getPosition(new Location(0, 1)).getPositionColor());
        assertSame(BLUE, obj1.getPosition(new Location(1, 0)).getPositionColor());
        assertSame(WHITE, obj1.getPosition(new Location(1, 1)).getPositionColor());
        assertSame(BLUE, obj1.getPosition(new Location(7, 8)).getPositionColor());
        assertNotEquals(BLUE, obj1.getPosition(new Location(5, 5)).getPositionColor());
    }

    @Test
    public void shouldMovePieceCorrectly() {
        obj1.moveTo(new Movement(new Location(1, 3), new Location(7, 9)));
        assertSame(LION, obj1.getPosition(new Location(7, 9)).getPieceOccupying().getAnimalType());
        assertSame(WHITE, obj1.getPosition(new Location(7, 9)).getPieceOccupying().getColor());
        assertSame(null, obj1.getPosition(new Location(1, 3)).getPieceOccupying());
    }

    @Test
    public void shouldUpdateLocationOfPieceAfterMovePiece() {
        Location origin = new Location(1, 4);
        Location destiny = new Location(3, 4);
        obj1.moveTo(new Movement(origin, destiny));
        assertEquals(destiny, obj1.getPosition(destiny).getPieceOccupying().getLocation());
    }

    @Test
    public void shouldValidateCorrectlyAMovementOfLion() {
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 3), new Location(7, 9))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 3), new Location(0, 2))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 3), new Location(3, 5))));

        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 6), new Location(0, 7))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 6), new Location(2, 5))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 6), new Location(4, 9))));

        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 3), new Location(4, 0))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 3), new Location(5, 3))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 3), new Location(0, 4))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 3), new Location(10, 3))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 3), new Location(0, 14))));
        obj1.moveTo(new Movement(new Location(1, 3), new Location(4, 0)));
        obj1.moveTo(new Movement(new Location(4, 0), new Location(7, 3)));
        obj1.moveTo(new Movement(new Location(7, 3), new Location(8, 2)));
        assertFalse(obj1.isValidMovement(new Movement(new Location(8, 2), new Location(9, 3))));
        obj1.moveTo(new Movement(new Location(1, 3), new Location(2, 2)));
        assertFalse(obj1.isValidMovement(new Movement(new Location(2, 2), new Location(0, 0))));

    }

    @Test
    public void shouldValidateCorrectlyAMovementOfElephant() {
        assertTrue(obj1.isValidMovement(new Movement(new Location(0, 4), new Location(0, 0))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(0, 5), new Location(0, 9))));

        assertFalse(obj1.isValidMovement(new Movement(new Location(0, 5), new Location(0, 10))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(0, 5), new Location(1, 5))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(0, 5), new Location(4, 6))));

        obj1.moveTo(new Movement(new Location(0, 4), new Location(0, 0)));
        obj1.moveTo(new Movement(new Location(0, 0), new Location(7, 0)));

        assertFalse(obj1.isValidMovement(new Movement(new Location(7, 0), new Location(7, 3))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(7, 0), new Location(7, 4))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(7, 0), new Location(7, 5))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(7, 0), new Location(7, 6))));
    }

    @Test
    public void shouldValidateCorrectlyAMovementOfMouse() {
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 4), new Location(5, 4))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 5), new Location(3, 5))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 5), new Location(6, 5))));

        obj1.moveTo(new Movement(new Location(1, 5), new Location(2, 5)));
        assertTrue(obj1.isValidMovement(new Movement(new Location(2, 5), new Location(2, 9))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(2, 5), new Location(2, 0))));


        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 4), new Location(7, 4))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 4), new Location(0, 4))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 4), new Location(1, 0))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 5), new Location(9, 9))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(1, 5), new Location(1, 4))));
    }

    @Test
    public void shouldReturnOnlyPositionWithBlackPieces() {
        List<Position> positionsWithBlackPieces = obj1.getPositionWithColorPieces(BLACK);
        assertEquals(6,positionsWithBlackPieces.size());
        assertTrue(positionsWithBlackPieces.contains(createPositionCommon(new Location(9, 4), BLUE)));
        assertTrue(positionsWithBlackPieces.contains(createPositionCommon(new Location(9, 5), BLUE)));
        assertTrue(positionsWithBlackPieces.contains(createPositionCommon(new Location(8, 4), BLUE)));
        assertTrue(positionsWithBlackPieces.contains(createPositionCommon(new Location(8, 5), BLUE)));
        assertTrue(positionsWithBlackPieces.contains(createPositionCommon(new Location(8, 3), BLUE)));
        assertTrue(positionsWithBlackPieces.contains(createPositionCommon(new Location(8, 6), BLUE)));
        obj1.moveTo(new Movement(new Location(8, 4), new Location(6, 4)));
        positionsWithBlackPieces = obj1.getPositionWithColorPieces(BLACK);
        assertFalse(positionsWithBlackPieces.contains(createPositionCommon(new Location(8, 4), BLUE)));
        assertTrue(positionsWithBlackPieces.contains(createPositionCommon(new Location(6, 4), BLUE)));
    }

    @Test
    public void shouldReturnOnlyPositionWithWithePieces() {
        List<Position> positionWithColorPieces = obj1.getPositionWithColorPieces(WHITE);
        assertEquals(6,positionWithColorPieces.size());
        assertTrue(positionWithColorPieces.contains(createPositionCommon(new Location(0, 4), BLUE)));
        assertTrue(positionWithColorPieces.contains(createPositionCommon(new Location(0, 5), BLUE)));
        assertTrue(positionWithColorPieces.contains(createPositionCommon(new Location(1, 4), BLUE)));
        assertTrue(positionWithColorPieces.contains(createPositionCommon(new Location(1, 5), BLUE)));
        assertTrue(positionWithColorPieces.contains(createPositionCommon(new Location(1, 3), BLUE)));
        assertTrue(positionWithColorPieces.contains(createPositionCommon(new Location(1, 6), BLUE)));
        obj1.moveTo(new Movement(new Location(1, 5), new Location(2, 5)));
        positionWithColorPieces = obj1.getPositionWithColorPieces(WHITE);
        assertTrue(positionWithColorPieces.contains(createPositionCommon(new Location(2, 5), BLUE)));
        assertFalse(positionWithColorPieces.contains(createPositionCommon(new Location(1, 5), BLUE)));
    }

    @Test
    public void shouldReturnOnlyFountainPositions() {
        assertEquals(4, obj1.getFountains().size());
    }

    @Test
    public void shouldBeAbleToMoveToAFriendlyFear() {
        assertTrue(obj1.isValidMovement(new Movement(new Location(1, 4), new Location(2, 4))));
    }

    @Test
    public void shouldBeAbleToMoveWhenLocked() {
        //elefante branco
        obj1.moveTo(new Movement(new Location(0, 5), new Location(0, 9)));
        obj1.moveTo(new Movement(new Location(0, 9), new Location(1, 9)));
//rato branco
        obj1.moveTo(new Movement(new Location(1, 5), new Location(2, 5)));
        obj1.moveTo(new Movement(new Location(2, 5), new Location(2, 9)));
        obj1.moveTo(new Movement(new Location(2, 9), new Location(3, 9)));
//leões pretos
        obj1.moveTo(new Movement(new Location(8, 6), new Location(5, 9)));
        obj1.moveTo(new Movement(new Location(8, 3), new Location(3, 8)));
//ta bloqueado
        assertTrue(obj1.isValidMovement(new Movement(new Location(3, 9), new Location(4, 9))));
        assertTrue(obj1.isValidMovement(new Movement(new Location(3, 9), new Location(2, 9))));
        obj1.moveTo(new Movement(new Location(3, 9), new Location(4, 9)));
        assertTrue(obj1.isValidMovement(new Movement(new Location(4, 9), new Location(4, 3))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(4, 9), new Location(3, 9))));
        assertFalse(obj1.isValidMovement(new Movement(new Location(4, 9), new Location(2, 9))));
    }

    @Test
    public void shouldReturnPositionsWithAnimalsScared() {
        assertTrue(obj1.getFearPositions(obj1.getPositionWithColorPieces(WHITE)).isEmpty());
        obj1.moveTo(new Movement(new Location(1, 4), new Location(2,4)));
        obj1.moveTo(new Movement(new Location(2, 4), new Location(2,9)));
        assertTrue(obj1.getFearPositions(obj1.getPositionWithColorPieces(WHITE)).isEmpty());
        assertTrue(obj1.isValidMovement(new Movement(new Location(8, 3), new Location(3,8))));
        obj1.moveTo(new Movement(new Location(8, 3), new Location(3,8)));
        assertEquals(1, obj1.getFearPositions(obj1.getPositionWithColorPieces(WHITE)).size());
        obj1.moveTo(new Movement(new Location(1, 5), new Location(6,5)));
        obj1.moveTo(new Movement(new Location(8, 6), new Location(7,5)));
        assertEquals(2, obj1.getFearPositions(obj1.getPositionWithColorPieces(WHITE)).size());
    }

    @Test
    public void shouldHaveTheGameStatusAsBlackVictory(){
        obj1.moveTo(new Movement(new Location(8,4),new Location(3,4)));
        obj1.moveTo(new Movement(new Location(3,4),new Location(3,3)));
        assertEquals(GAMING, obj1.getGameStatus());
        obj1.moveTo(new Movement(new Location(8,5),new Location(3,5)));
        obj1.moveTo(new Movement(new Location(3,5),new Location(3,6)));
        assertEquals(GAMING, obj1.getGameStatus());
        obj1.moveTo(new Movement(new Location(9,4),new Location(8,4)));
        obj1.moveTo(new Movement(new Location(8,4),new Location(6,6)));
        assertEquals(BLACK_WINNER, obj1.getGameStatus());
    }

    @Test
    public void shouldHaveTheGameStatusAsWitheVictory(){
        obj1.moveTo(new Movement(new Location(1,4),new Location(6,4)));
        obj1.moveTo(new Movement(new Location(6,4),new Location(6,3)));
        assertEquals(GAMING, obj1.getGameStatus());
        obj1.moveTo(new Movement(new Location(1,5),new Location(6,5)));
        obj1.moveTo(new Movement(new Location(6,5),new Location(6,6)));
        assertEquals(GAMING, obj1.getGameStatus());
        obj1.moveTo(new Movement(new Location(0,4),new Location(2,4)));
        obj1.moveTo(new Movement(new Location(2,4),new Location(3,3)));
        assertEquals(WHITE_WINNER, obj1.getGameStatus());
    }

    @Test
    public void shouldHaveTheGameStatusAsGaming(){
        assertEquals(GAMING, obj1.getGameStatus());
    }

     /*
        @Test
        public void shouldValidateCorrectlyAMovementOfLion() {
            assertTrue(isValidMovement(new Movement(new Location(1, 3), new Location(7, 9)), obj1));
            assertTrue(isValidMovement(new Movement(new Location(1, 3), new Location(0, 2)), obj1));
            assertTrue(isValidMovement(new Movement(new Location(1, 3), new Location(3, 5)), obj1));

            assertTrue(isValidMovement(new Movement(new Location(1, 6), new Location(0, 7)), obj1));
            assertTrue(isValidMovement(new Movement(new Location(1, 6), new Location(2, 5)), obj1));
            assertTrue(isValidMovement(new Movement(new Location(1, 6), new Location(4, 9)), obj1));

            assertTrue(isValidMovement(new Movement(new Location(1, 3), new Location(4, 0)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(1, 3), new Location(5, 3)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(1, 3), new Location(0, 4)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(1, 3), new Location(10, 3)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(1, 3), new Location(0, 14)), obj1));
            obj1.moveTo(new Movement(new Location(1, 3), new Location(4, 0)));
            obj1.moveTo(new Movement(new Location(4, 0), new Location(7, 3)));
            obj1.moveTo(new Movement(new Location(7, 3), new Location(8, 2)));
            assertFalse(isValidMovement(new Movement(new Location(8, 2), new Location(9, 3)), obj1));
            obj1.moveTo(new Movement(new Location(1,3), new Location(2,2)));
            assertFalse(isValidMovement(new Movement(new Location(2,2),new Location(0,0)),obj1));

        }

        @Test
        public void shouldValidateCorrectlyAMovementOfElephant() {
            assertTrue(isValidMovement(new Movement(new Location(0, 4), new Location(0, 0)), obj1));
            assertTrue(isValidMovement(new Movement(new Location(0, 5), new Location(0, 9)), obj1));

            assertFalse(isValidMovement(new Movement(new Location(0, 5), new Location(0, 10)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(0, 5), new Location(1, 5)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(0, 5), new Location(4, 6)), obj1));

            obj1.moveTo(new Movement(new Location(0, 4), new Location(0, 0)));
            obj1.moveTo(new Movement(new Location(0, 0), new Location(7, 0)));

            assertFalse(isValidMovement(new Movement(new Location(7, 0), new Location(7, 3)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(7, 0), new Location(7, 4)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(7, 0), new Location(7, 5)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(7, 0), new Location(7, 6)), obj1));
        }

        @Test
        public void shouldValidateCorrectlyAMovementOfMouse() {
            assertTrue(isValidMovement(new Movement(new Location(1, 4), new Location(5, 4)), obj1));
            assertTrue(isValidMovement(new Movement(new Location(1, 5), new Location(3, 5)), obj1));
            assertTrue(isValidMovement(new Movement(new Location(1, 5), new Location(6, 5)), obj1));

            obj1.moveTo(new Movement(new Location(1, 5), new Location(2, 5)));
            assertTrue(isValidMovement(new Movement(new Location(2, 5), new Location(2, 9)), obj1));
            assertTrue(isValidMovement(new Movement(new Location(2, 5), new Location(2, 0)), obj1));


            assertFalse(isValidMovement(new Movement(new Location(1, 4), new Location(7, 4)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(1, 4), new Location(0, 4)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(1, 4), new Location(1, 0)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(1, 5), new Location(9, 9)), obj1));
            assertFalse(isValidMovement(new Movement(new Location(1, 5), new Location(1, 4)), obj1));
        }


     */
}
