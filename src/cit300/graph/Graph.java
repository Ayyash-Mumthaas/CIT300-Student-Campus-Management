package cit300.graph;

/**
 * Campus Graph implementation using an Adjacency List.
 * Student: M.F.F. Aaysha (23DA2-0834)
 */
public class Graph {

    
    private class Node {
        String location;
        Node next;

        Node(String location) {
            this.location = location;
            this.next = null;
        }
    }

    private static final int MAX_LOCATIONS = 20;
    private String[] locations;
    private Node[] headNodes;
    private int locationCount;

    public Graph() {
        locations = new String[MAX_LOCATIONS];
        headNodes = new Node[MAX_LOCATIONS];
        locationCount = 0;
    }

    
    private int getIndex(String name) {
        if (name == null) return -1;
        for (int i = 0; i < locationCount; i++) {
            if (locations[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }


    private boolean connectionExists(int srcIndex, String destName) {
        Node current = headNodes[srcIndex];
        while (current != null) {
            if (current.location.equalsIgnoreCase(destName)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    public boolean addLocation(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Location name cannot be empty.");
            return false;
        }
        if (getIndex(name) != -1) {
            System.out.println("Error: Location '" + name + "' already exists.");
            return false;
        }
        if (locationCount >= MAX_LOCATIONS) {
            System.out.println("Error: Maximum graph capacity reached.");
            return false;
        }

        locations[locationCount] = name;
        headNodes[locationCount] = null;
        locationCount++;
        System.out.println("Location added successfully: " + name);
        return true;
    }

    
    public boolean addConnection(String loc1, String loc2) {
        int index1 = getIndex(loc1);
        int index2 = getIndex(loc2);

        if (index1 == -1 || index2 == -1) {
            System.out.println("Error: Connection failed. One or both locations do not exist (" + loc1 + ", " + loc2 + ").");
            return false;
        }
        if (index1 == index2) {
            System.out.println("Error: Cannot connect location '" + loc1 + "' to itself.");
            return false;
        }
        if (connectionExists(index1, locations[index2])) {
            System.out.println("Error: Connection between '" + locations[index1] + "' and '" + locations[index2] + "' already exists.");
            return false;
        }

        Node node1 = new Node(locations[index2]);
        node1.next = headNodes[index1];
        headNodes[index1] = node1;

        Node node2 = new Node(locations[index1]);
        node2.next = headNodes[index2];
        headNodes[index2] = node2;

        System.out.println("Connection added: " + locations[index1] + " <---> " + locations[index2]);
        return true;
    }

    
    private boolean removeEdgeFromList(int srcIndex, String destName) {
        Node current = headNodes[srcIndex];
        Node prev = null;

        while (current != null) {
            if (current.location.equalsIgnoreCase(destName)) {
                if (prev == null) {
                    headNodes[srcIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    
    public boolean removeConnection(String loc1, String loc2) {
        int index1 = getIndex(loc1);
        int index2 = getIndex(loc2);

        if (index1 == -1 || index2 == -1) {
            System.out.println("Error: Cannot remove connection. Location(s) do not exist.");
            return false;
        }

        if (!connectionExists(index1, locations[index2])) {
            System.out.println("Error: No connection exists between '" + locations[index1] + "' and '" + locations[index2] + "'.");
            return false;
        }

        removeEdgeFromList(index1, locations[index2]);
        removeEdgeFromList(index2, locations[index1]);

        System.out.println("Connection removed: " + locations[index1] + " <---> " + locations[index2]);
        return true;
    }

    
    public boolean removeLocation(String name) {
        int targetIndex = getIndex(name);

        if (targetIndex == -1) {
            System.out.println("Error: Cannot remove location. '" + name + "' does not exist.");
            return false;
        }

        String targetName = locations[targetIndex];

        for (int i = 0; i < locationCount; i++) {
            if (i != targetIndex) {
                removeEdgeFromList(i, targetName);
            }
        }

        for (int i = targetIndex; i < locationCount - 1; i++) {
            locations[i] = locations[i + 1];
            headNodes[i] = headNodes[i + 1];
        }

        locations[locationCount - 1] = null;
        headNodes[locationCount - 1] = null;
        locationCount--;

        System.out.println("Location removed successfully: " + targetName);
        return true;
    }

    
    public void bfsTraversal(String startLocation) {
        int startIdx = getIndex(startLocation);
        if (startIdx == -1) {
            System.out.println("Error: Cannot perform BFS. Start location '" + startLocation + "' does not exist.");
            return;
        }

        boolean[] visited = new boolean[locationCount];
        int[] queue = new int[MAX_LOCATIONS];
        int front = 0;
        int rear = 0;

        
        queue[rear++] = startIdx;
        visited[startIdx] = true;

        System.out.println("\n--- Breadth-First Search (BFS) Traversal Starting From: " + locations[startIdx] + " ---");
        System.out.print("Traversal Path: ");

        boolean first = true;
        while (front < rear) {
            int currentIdx = queue[front++];
            if (!first) {
                System.out.print(" -> ");
            }
            System.out.print(locations[currentIdx]);
            first = false;

            // Visit all unvisited adjacent neighbors
            Node current = headNodes[currentIdx];
            while (current != null) {
                int neighborIdx = getIndex(current.location);
                if (neighborIdx != -1 && !visited[neighborIdx]) {
                    visited[neighborIdx] = true;
                    queue[rear++] = neighborIdx;
                }
                current = current.next;
            }
        }
        System.out.println("\n---------------------------------------------------------------\n");
    }

    
    public void displayConnections() {
        System.out.println("\n--- Campus Locations & Connections ---");
        if (locationCount == 0) {
            System.out.println("No locations registered in the system.");
            return;
        }

        for (int i = 0; i < locationCount; i++) {
            System.out.print(locations[i] + " -> ");
            Node current = headNodes[i];
            if (current == null) {
                System.out.print("[No direct connections]");
            }
            while (current != null) {
                System.out.print(current.location + (current.next != null ? ", " : ""));
                current = current.next;
            }
            System.out.println();
        }
        System.out.println("-------------------------------------\n");
    }

    public static void main(String[] args) {
        Graph campusGraph = new Graph();

        System.out.println("=== FULL CAMPUS GRAPH TESTING ===");

        
        campusGraph.addLocation("Main Gate");
        campusGraph.addLocation("Library");
        campusGraph.addLocation("Cafeteria");
        campusGraph.addLocation("Lecture Hall");
        campusGraph.addLocation("Laboratory");
        campusGraph.addLocation("Administration");

        
        campusGraph.addConnection("Main Gate", "Library");
        campusGraph.addConnection("Main Gate", "Administration");
        campusGraph.addConnection("Library", "Cafeteria");
        campusGraph.addConnection("Library", "Lecture Hall");
        campusGraph.addConnection("Lecture Hall", "Laboratory");

    
        campusGraph.displayConnections();

        
        campusGraph.bfsTraversal("Main Gate");

        
        campusGraph.bfsTraversal("Sports Complex");
    }
}