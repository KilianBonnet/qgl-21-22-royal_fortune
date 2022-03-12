package fr.unice.polytech.si3.qgl.royal_fortune;

import fr.unice.polytech.si3.qgl.royal_fortune.captain.Crewmates.Sailor;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.FictitiousCheckpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.PreCalculator;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.SeaMap;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Wind;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Deck;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Ship;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Entities;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.Checkpoint;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Goal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PreCalculatorTest {

    @Test
    void needSailorToOarToCheckpointTest() {
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        List<Entities> entities = new ArrayList<>();
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        entities.add(new Sail(4, 2, false));

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        List<Checkpoint> checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(100, 0, 0), new Circle("Circle", 10)));

        SeaMap seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null);

        PreCalculator preCalculator = new PreCalculator(ship, sailors, seaMap, new Wind(0, 0));
        assertTrue(preCalculator.needSailorToOarToCheckpoint(ship.getNbrOar()));
    }

    @Test
    void doNotNeedSailorToOarToCheckpointTest() {
        List<Sailor> sailors = new ArrayList<>();
        sailors.add(new Sailor(0, 0, 0, "sailor0"));
        sailors.add(new Sailor(1, 0, 1, "sailor1"));
        sailors.add(new Sailor(2, 1, 0, "sailor2"));
        sailors.add(new Sailor(3, 1, 1, "sailor3"));

        List<Entities> entities = new ArrayList<>();
        entities.add(new Oar(0, 0));
        entities.add(new Oar(1, 0));
        entities.add(new Oar(2, 0));

        entities.add(new Oar(0, 3));
        entities.add(new Oar(1, 3));
        entities.add(new Oar(2, 3));

        entities.add(new Sail(4, 2, false));

        Ship ship = new Ship(
                "ship",
                100,
                new Position(0, 0, 0),
                "ShipTest",
                new Deck(3, 4),
                entities,
                new Rectangle("rectangle", 3, 4, 0));

        List<Checkpoint> checkpoints = new ArrayList<>();
        checkpoints.add(new Checkpoint(new Position(100, 0, 0), new Circle("Circle", 100)));

        SeaMap seaMap = new SeaMap(new Goal("",checkpoints), new FictitiousCheckpoint(checkpoints), ship.getPosition(),null);

        PreCalculator preCalculator = new PreCalculator(ship, sailors, seaMap, new Wind(0, 0));
        assertFalse(preCalculator.needSailorToOarToCheckpoint(ship.getNbrOar()));
    }

    @Test
    void numberOfSailorToOarToCheckPointTest() {
        Ship ship = new Ship(null, 100, null, null, null, null, null);
        SeaMap seaMap = new SeaMap(null, null, null,null);
    }
}
