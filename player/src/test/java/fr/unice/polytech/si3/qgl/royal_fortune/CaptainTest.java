package fr.unice.polytech.si3.qgl.royal_fortune;


import fr.unice.polytech.si3.qgl.royal_fortune.action.Action;
import fr.unice.polytech.si3.qgl.royal_fortune.captain.Captain;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.shape.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaptainTest {
    private Ship ship;
    private Captain captain;
    private Checkpoint checkpoint;
    private ArrayList<Sailor> sailors;
    private ArrayList<Entities> entities;

    @BeforeEach
    public void init(){
        sailors = new ArrayList<>();
        entities = new ArrayList<>();
    }

    @Test
    public void associateSailorsToOarPITest(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        // Initialize 6 oars
        // Left oars
        entities.add(new Oar("oar", 0, 0));
        entities.add(new Oar("oar", 1, 0));
        entities.add(new Oar("oar", 2, 0));

        // Right oars
        entities.add(new Oar("oar", 0, 3));
        entities.add(new Oar("oar", 1, 3));
        entities.add(new Oar("oar", 2, 3));

        // Initialize a ship
        ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        captain = new Captain(ship, sailors, null);

        // For a rotation of pi, we need 4 sailors on the left side, but there is only 3 oars available.
        captain.associateSailorToOar(Math.PI);
        assertEquals(4, sailors.size());
        assertEquals(3, sailors.stream().filter(sailor -> sailor.getTargetEntity() != null).count());
        sailors.stream().filter(sailor -> sailor.getTargetEntity() != null).forEach(sailor -> assertTrue(0 != sailor.getTargetEntity().getY()));
    }

    @Test
    public void associateSailorsToOarPi2Test(){
        // Initialize 4 sailors
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        // Initialize 6 oars
        // Left oars
        entities.add(new Oar("oar", 0, 0));
        entities.add(new Oar("oar", 1, 0));
        entities.add(new Oar("oar", 2, 0));

        // Right oars
        entities.add(new Oar("oar", 0, 3));
        entities.add(new Oar("oar", 1, 3));
        entities.add(new Oar("oar", 2, 3));

        ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        captain = new Captain(ship, sailors, null);

        // For a rotation of pi/4, we need 1 sailor on the left side
        captain.associateSailorToOar(- Math.PI/4);
        assertEquals(4, sailors.size());
        assertEquals(1, sailors.stream().filter(sailor -> sailor.getTargetEntity() != null).count());
    }



    @Test
    public void askSailorToMoveTest(){
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        entities.add(new Oar("oar", 0, 0));
        entities.add(new Oar("oar", 1, 0));
        entities.add(new Oar("oar", 2, 0));

        entities.add(new Oar("oar", 0, 3));
        entities.add(new Oar("oar", 1, 3));
        entities.add(new Oar("oar", 2, 3));

        ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        captain = new Captain(ship, sailors, null);
        captain.associateSailorToOar(- Math.PI);

        assertEquals(4, sailors.size());
        assertEquals(3, sailors.stream().filter(sailor -> sailor.getTargetEntity() != null).count());
        sailors.stream().filter(sailor -> sailor.getTargetEntity() != null).forEach(sailor -> assertEquals(0, sailor.getTargetEntity().getY()));

        captain.askSailorsToMove();
        // Size is 2 because a sailor is already on its target entity
        assertEquals(2, captain.getRoundActions().size());
    }

    @Test
    public void associateSailorToOarEvenlyTest(){
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));
        sailors.add(new Sailor(4, 1, 2, "sailor4"));
        sailors.add(new Sailor(5, 2, 2, "sailor5"));

        entities.add(new Oar("oar", 0, 0));
        entities.add(new Oar("oar", 1, 0));
        entities.add(new Oar("oar", 2, 0));

        entities.add(new Oar("oar", 0, 3));
        entities.add(new Oar("oar", 1, 3));

        ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        captain = new Captain(ship, sailors, null);
        captain.associateSailorToOarEvenly();

        assertEquals(6, sailors.size());
        assertEquals(4, sailors.stream().filter(sailor -> sailor.getTargetEntity() != null).count());
    }





    @Test
    //Moving straight
    void roundDecisionsTest() {
        ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 1.5),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        entities.add(new Oar("oar", 0, 0));
        entities.add(new Oar("oar", 0, 1));
        entities.add(new Oar("oar", 1, 0));
        entities.add(new Oar("oar", 1, 1));

        checkpoint = new Checkpoint(new Position(0,500,0), new Circle("Circle", 50));
        ArrayList<Checkpoint> checkpointArrayList = new ArrayList<>();
        checkpointArrayList.add(checkpoint);
        Goal goal = new Goal("REGATTA", checkpointArrayList);

        captain = new Captain(ship, sailors, goal);
        captain.roundDecisions();

        assertEquals(8, captain.getRoundActions().size()); 
    }

    @Test
    //Turning
    void roundDecisions2Test() {
        ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        entities.add(new Oar("oar", 0, 0));
        entities.add(new Oar("oar", 0, 1));
        entities.add(new Oar("oar", 1, 0));
        entities.add(new Oar("oar", 1, 1));

        checkpoint = new Checkpoint(new Position(0,500,0), new Circle("Circle", 50));
        ArrayList<Checkpoint> checkpointArrayList = new ArrayList<>();
        checkpointArrayList.add(checkpoint);
        Goal goal = new Goal("REGATTA", checkpointArrayList);

        captain = new Captain(ship, sailors, goal);
        captain.roundDecisions();

        assertEquals(4, captain.getRoundActions().size());
    }

}