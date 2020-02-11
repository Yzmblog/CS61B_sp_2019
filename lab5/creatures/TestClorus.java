package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {

        Clorus c = new Clorus(2);
        Clorus BabyC = c.replicate();
        assertEquals(1, BabyC.energy(), 0.01);
        Clorus GrandsonP = BabyC.replicate();
        assertEquals(0.5, GrandsonP.energy(), 0.01);

    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // If any Plips are seen, the Clorus will ATTACK one of them randomly.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, new Plip(1.2));
        topPlip.put(Direction.BOTTOM, new Impassible());
        topPlip.put(Direction.LEFT, new Impassible());
        topPlip.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(topPlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);


        // If the Clorus has energy greater than or equal to one,
        // it will REPLICATE to a random empty square.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> leftEmpty = new HashMap<Direction, Occupant>();
        leftEmpty.put(Direction.TOP, new Impassible());
        leftEmpty.put(Direction.BOTTOM, new Impassible());
        leftEmpty.put(Direction.LEFT, new Empty());
        leftEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(leftEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.LEFT);

        assertEquals(expected, actual);


        // Energy < 1; the Clorus will MOVE to a random empty square.
        c = new Clorus(.99);
        HashMap<Direction, Occupant> bottomEmpty = new HashMap<Direction, Occupant>();
        bottomEmpty.put(Direction.TOP, new Impassible());
        bottomEmpty.put(Direction.BOTTOM, new Empty());
        bottomEmpty.put(Direction.LEFT, new Impassible());
        bottomEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(bottomEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);

        assertEquals(expected, actual);

    }
}
