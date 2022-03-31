package fr.unice.polytech.si3.qgl.royal_fortune.calculus.dijkstra;

import fr.unice.polytech.si3.qgl.royal_fortune.IPositionable;
import fr.unice.polytech.si3.qgl.royal_fortune.calculus.Cartologue;
import fr.unice.polytech.si3.qgl.royal_fortune.environment.shape.Segment;
import fr.unice.polytech.si3.qgl.royal_fortune.ship.Position;
import fr.unice.polytech.si3.qgl.royal_fortune.target.Beacon;
import org.w3c.dom.Node;

import java.util.*;

public class Dijkstra {
    /**
     * For a given list of checkpoints, will proceed the Dijkstra algorithm to find the shortest path.
     * @param departure - The position of the departure point.
     * @param arrival - The position of the arrival point.
     * @param cartologue - The class providing the list of reef.
     * @param beacons - The list of all the beacons on the map.
     * @return The shortest path as an array of Beacon.
     */
    public static List<Beacon> proceedDijkstra(Position departure, Position arrival, Cartologue cartologue, List <Beacon> beacons){
        List<DijkstraNode> availableNodes = generateDijkstraNodes(beacons);
        DijkstraNode arrivalNode = DijkstraNode.generateStartingNode(arrival);
        availableNodes.add(arrivalNode);

        DijkstraNode departureNode = DijkstraNode.generateStartingNode(departure);

        Set<DijkstraNode> updatedNodes = updateNodes(departureNode, availableNodes, cartologue);

        DijkstraNode minNode;
        while((minNode = Collections.min(updatedNodes)) != arrivalNode){
            updatedNodes.addAll(updateNodes(minNode, availableNodes, cartologue));
            availableNodes.remove(minNode);
            updatedNodes.remove(minNode);
        }

        return minNode.getPath();
    }

    /**
     * For a given node, will check every available neighbor nodes and update their node value if the new value
     * is lower than the previous one.
     *
     * @param currentNode - The node to update all the available neighbor nodes.
     * @param availableNodes - The list of all nodes available (nodes we don't already go through).
     * @param cartologue - The tool used to calculate the new node value.
     * @return The list of all updated nodes
     */
    private static Set<DijkstraNode> updateNodes(DijkstraNode currentNode, List<DijkstraNode> availableNodes, Cartologue cartologue){
        // Creating a list of all updated nodes.
        Set<DijkstraNode> updatedNodes = new HashSet<>();

        for (DijkstraNode node : availableNodes){
            // Creating a segment between the currentNode and one of the possible node.
            Segment segment = new Segment(currentNode.getNode().getPosition(), node.getNode().getPosition());

            // If the calculated path length is shorter than the older one, we update the node.
            double newNodeValue = cartologue.computeNumberOfRoundsNeeded(segment) + currentNode.getNodeValue();
            if(newNodeValue < node.getNodeValue()){
                node.setNodeValue(newNodeValue);
                node.setPreviousNode(currentNode);
                updatedNodes.add(node);
            }
        }

        return updatedNodes;
    }


    /**
     * For each beacon on the beacon list will generate a list of node containing the beacon.
     * @param beacons - The provides list of beacons.
     * @return The generated list of nodes.
     */
    private static List<DijkstraNode> generateDijkstraNodes(List<Beacon> beacons){
        List<DijkstraNode> dijkstraNodes = new ArrayList<>();
        beacons.forEach(beacon -> dijkstraNodes.add(new DijkstraNode(beacon)));
        return dijkstraNodes;
    }
}
