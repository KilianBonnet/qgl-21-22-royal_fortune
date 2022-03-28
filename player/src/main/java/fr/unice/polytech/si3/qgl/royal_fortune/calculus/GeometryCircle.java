package fr.unice.polytech.si3.qgl.royal_fortune.calculus;

import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Circle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Rectangle;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Shape;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;

import java.util.ArrayList;
import java.util.List;

public class GeometryCircle {
    public GeometryCircle() {}

    /**
     * check if the rectangle is in the circle
     * @return true if the rectangle is the circle
     */
    public static boolean rectangleIsInCircle(Rectangle rectangle, Position currentPos, double radius){
        List<Position> positionList= GeometryRectangle.computeCorner(currentPos, rectangle);
        for(Position position:positionList){
            if(Mathematician.distanceFormula(currentPos,position)<=radius)
                return true;
        }
        return false;
    }

    /**
     * Generates two beacons on the circle, one at the opposite of the other:
     * the line between the two beacons is perpendicular to the line of the trajectory from the ship to the checkpoint
     * @param shipPosition ship position
     * @param checkpointPosition checkpoint position
     * @param reefPosition reef position
     * @param reefShape reef position
     * @return the list containing the two beacons
     */
    public static List<Beacon> generateBeacon(Position shipPosition, Position checkpointPosition, Position reefPosition, Circle reefShape){
        double beaconRadius = 50;
        double securityScaling = 20;
        int alignedBeacons = 3;

        List<Beacon> beaconList = new ArrayList<>();

        double vectorSheepCheckpointX = checkpointPosition.getX() - shipPosition.getX();
        double vectorSheepCheckpointY = checkpointPosition.getY() - shipPosition.getY();
        double normSheepCheckpoint = Math.sqrt(vectorSheepCheckpointX * vectorSheepCheckpointX + vectorSheepCheckpointY * vectorSheepCheckpointY);

        double normalVectorX = -vectorSheepCheckpointY / normSheepCheckpoint;
        double normalVectorY = vectorSheepCheckpointX / normSheepCheckpoint;

        double upBeaconX = reefPosition.getX() + normalVectorX * (securityScaling + reefShape.getRadius());
        double upBeaconY = reefPosition.getY() + normalVectorY * (securityScaling + reefShape.getRadius());
        double downBeaconX = reefPosition.getX() - normalVectorX * (securityScaling + reefShape.getRadius());;
        double downBeaconY = reefPosition.getY() - normalVectorY * (securityScaling + reefShape.getRadius());;

        for (int i = 0; i<alignedBeacons; i++){
            upBeaconX += normalVectorX * beaconRadius;
            upBeaconY += normalVectorY *  beaconRadius;

            beaconList.add(new Beacon(new Position(upBeaconX, upBeaconY), new Circle(beaconRadius)));

            downBeaconX -= normalVectorX * beaconRadius;
            downBeaconY -=  normalVectorY * beaconRadius;
            beaconList.add(new Beacon(new Position(downBeaconX, downBeaconY), new Circle(beaconRadius)));
        }

        return beaconList;
    }

    /**
     * Check if there are intersections between a given circle and line (from a segment)
     * @param circle a circle
     * @param segment a segment
     * @return the list of the intersections (2 intersections, 1 intersection or empty)
     */
    public static List<Position> computeIntersectionWith(Segment segment, Position circlePosition, Circle circle) {
        segment.getPointA().setX(segment.getPointA().getX() - circlePosition.getX());
        segment.getPointB().setX(segment.getPointB().getX() - circlePosition.getX());

        segment.getPointA().setY(segment.getPointA().getY() - circlePosition.getY());
        segment.getPointB().setY(segment.getPointB().getY() - circlePosition.getY());

        segment = new Segment(segment.getPointA(), segment.getPointB());

        List<Position> intersectionList = new ArrayList<>();
        double x=circlePosition.getX();
        double y=circlePosition.getY();
        double radius = circle.getRadius();
        double a = segment.getA();
        double b = segment.getB();

        double discriminant = 4 * Math.pow(a, 2) * Math.pow(b, 2) - 4 * (Math.pow(a, 2) + 1) * (Math.pow(b, 2) - Math.pow(radius, 2));

        if(discriminant > 0) {
            double firstSolution = (-2 * a * b + Math.sqrt(discriminant)) / (2 * (Math.pow(a, 2) + 1));
            Position position1 = new Position(firstSolution+x, a*firstSolution+b+y);
            if(segment.pointInSegment(position1))
                intersectionList.add(position1);

            double secondSolution = (-2 * a * b - Math.sqrt(discriminant)) / (2 * (Math.pow(a, 2) + 1));
            Position position2 = new Position(secondSolution+x, a*secondSolution+b+y);
            if(segment.pointInSegment(position2))
                intersectionList.add(position2);
        }

        else if (discriminant == 0) {
            double onlySolution = (-2 * a * b) / (2 * (Math.pow(a, 2) + 1));
            Position position = new Position(onlySolution+x, a*onlySolution+b+y);
            if(segment.pointInSegment(position))
                intersectionList.add(position);
        }

        return intersectionList;
    }
}
