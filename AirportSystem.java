import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
public class AirportSystem {

    /**
     * The adjacency list of the cities
     * Each node is a city, and each connecting line indicates a flight between two cities
     * The inner List<Edge> represents a vertex
     */
    private List<Vertex> connections = new ArrayList<Vertex>();

    private LinkedList<Integer> adjacents[];

    /**
     * Adds a new edge to the connections list
     * @param source the origin city of the flight
     * @param destination the destination city of the flight
     * @param weight the weight of the flight, which is the distance between the two cities
     * @return false if the edge already exists or the weight is negative
     * @return true if the edge was successfully added
     */
    public boolean addEdge(String source, String destination, int weight) {
        
        // return false if the weight is negative or the edge already exists
        if (weight < 0) {
            return false;
        } else {
            for (Vertex vertex : connections) {
                if (vertex.edges.toString().equals("[" + source + ", " + destination + "]")) {
                    return false;
                }
            }
        }

        // Add 2 Vertices and edges to/from each Vertex
        List<Edge> temp = new ArrayList<Edge>();
        temp.add(new Edge(source, destination, weight));
        connections.add(new Vertex(source, temp));
        temp.clear();
        temp.add(new Edge(destination, source, weight));
        connections.add(new Vertex(destination, temp));
        return true;
    }

    /**
     * Returns the shortest distance between city A and city B using Dijkstra's algorithm
     * @param cityA the city of the flight's origin 
     * @param cityB the city of the flight's destination
     * @return the shortest distance between city A and city B
     */
    public int shortestDistance(String cityA, String cityB) {
        
        

    }

    /**
     * Uses Prim's algorithm to find the minimum spanning tree of the airport system
     * @return
     */
    public List<Edge> minimumSpanningTree() {

    }

    /**
     * Returns a list of all the cities from the start using BFS
     * This is assuming the start vertex exists
     * Order of the cities in the same level does not matter
     * @param start the city of origin
     * @return a list of String representing the cities from the start using BFS
     */
    public List<String> breadthFirstSearch(String start) {

        adjacents = new LinkedList[connections.size()];

        for (int i = 0; i < connections.size(); ++i) {
            adjacents[i] = new LinkedList();
        }

        List<String> cities = new ArrayList<>();

        boolean visited[] = new boolean[connections.size()];

        LinkedList<String> queue = new LinkedList<>();

        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }

        for (Vertex vertex : connections) {
            if (vertex.id == start) {
                queue.add(vertex.id);
                visited[connections.indexOf(vertex)] = true;
            }
        }

        while (queue.size() != 0) {
            String city = queue.poll();
            cities.add(city);
            
            // I don't think queue is right because queue will keep changing for indices?
            Iterator<Integer> i = adjacents[queue.indexOf(city)].listIterator();
            while (i.hasNext()) {
                int index = i.next();
                if (!visited[index]) {
                    queue.add(connections.get(index).id);
                    visited[index] = true;
                }
            }
        }

        return cities;

    }
    
    /**
     * Prints the graph in a readable format and it is clear which edge belongs to which vertex
     */
    public void printGraph() {
        
    }
    
    /**
     * Private Edge class that contains the implementation for an edge between two vertices
     */
    private class Edge {

        // the starting location of this edge as a string
        private String source;

        // the end destination of this edge as a string
        private String destination;

        // the distance between the two cities
        private int distance;

        /**
         * Constructor for the Edge class
         * @param source the starting location of this edge
         * @param destination the end destination of this edge
         */
        public Edge(String source, String destination, int distance) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
        }

        @Override
        // the string representation of the edge in the format of "[start, destination]"
        public String toString() {
            return "[" + source + ", " + destination + "]";
        }
    }

    /**
     * Private Vertex class that contains the implementation for a vertex in the graph
     */
    private class Vertex {
        // the city name
        private String id;

        // the cities that are connected to this city by the airport system
        private List<Edge> edges;

        /**
         * Constructor for the Vertex class
         * @param id the city name
         * @param edges the list of cities that are connected to this city
         */
        public Vertex(String id, List<Edge> edges) {
            this.id = id;
            this.edges = edges;
        }

        @Override
        // the string representation of the vertex in the format of "id"
        public String toString() {
            return id;
        }
    }

}