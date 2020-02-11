package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.*;

public class Clorus extends Creature {
    /** The name of Clorus */
    private String name = "clorus";

    /** Red color */
    private int r;

    /** Green color */
    private int g;

    /** Blue color */
    private int b;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /**
     *
     */
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    /**
     * Clorus attack.
     */
    public void attack(Creature c) {
        energy += c.energy();

    }

    /**
     * Take a move action.
     */
    public void move() {
        energy -= 0.03;
        if(energy < 0) {
            energy = 0;
        }
    }

    /**
     * Stay action.
     */
    public void stay() {
        energy -= 0.01;
        if(energy < 0) {
            energy = 0;
        }
    }

    /**
     * Replicate action.
     */
    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy);
    }

    /**
     * Strategy to choose an action.
     *
     *1.If there are no empty squares, the Clorus will STAY
     * (even if there are Plips nearby they could attack since plip squares do not count as empty squares).
     *
     * 2.Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     *
     *3.Otherwise, if the Clorus has energy greater than or equal to one,
     * it will REPLICATE to a random empty square.
     *
     *4.Otherwise, the Clorus will MOVE to a random empty square.

     *
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        //Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> pilpNeighbors = new ArrayDeque<>();
        for (Direction s : Direction.values()) {
            if(neighbors.get(s).name().equals("empty")) {
                emptyNeighbors.addLast(s);
            }

            if(neighbors.get(s).name().equals("plip")) {
                pilpNeighbors.addLast(s);
            }

        }
        if(emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else {
            //Rule 2
            if(pilpNeighbors.size() != 0) {
                return new Action(Action.ActionType.ATTACK, randomEntry(pilpNeighbors));
            } else {
                // Rule 3
                if (energy >= 1) {
                    return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));

                } else {
                    // Rule 4
                    return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
                }
            }

        }

    }

    /** Return random direction of neighbors. */
    public Direction randomEntry(Deque<Direction> neighbors) {
        int length = neighbors.size();
        Random random = new Random();
        int randomIndex = Math.abs(random.nextInt() % length);

        Iterator<Direction> it = neighbors.iterator();

        if(randomIndex == 0) {
            return neighbors.getFirst();
        }

        while (randomIndex > 1) {
            it.next();
            randomIndex-= 1;
        }
        return it.next();

    }




}
