import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Queue;
public class AirportSystem {

    /**
     * The adjacency list of the cities
     * Each node is a city, and each connecting line indicates a flight between two cities
     * The inner List<Edge> represents a vertex
     */
    private static List<Vertex> connections = new ArrayList<Vertex>();

    private static ArrayList<List<Edge>> adjacents = new ArrayList<List<Edge>>();

    /**
     * Adds a new edge to the connections list
     * @param source the origin city of the flight
     * @param destination the destination city of the flight
     * @param weight the weight of the flight, which is the distance between the two cities
     * @return false if the edge already exists or the weight is negative
     * @return true if the edge was successfully added
     */
    public boolean addEdge(String source, String destination, int weight) {

        if (weight < 0) {
            // return false if the weight is negative or the edge already exists
            return false;
        } else if (connections.size() == 0) {
            Vertex temp0 = new Vertex(source, new ArrayList<>());
            Vertex temp1 = new Vertex(destination, new ArrayList<>());
            temp0.edges.add(new Edge(source, destination, weight));
            temp1.edges.add(new Edge(destination, source, weight));
            connections.add(temp0);
            connections.add(temp1);
            return true;
        } else {
            for (Vertex vertex : connections) {
                for (Edge edge : vertex.edges) {
                    if (edge.toString().equals("[" + source + ", " + destination + "]")) {
                        // return false if the edge already exists
                        return false;
                    }
                }
            }
        }

        for (Vertex sourceVertex : connections) {
            if (sourceVertex.toString().equals(source)) {
                for (Vertex destinationVertex : connections) {
                    if (destinationVertex.toString().equals(destination)) {
                        // both source and destination exist, add edges to each other
                        sourceVertex.edges.add(new Edge(source, destination, weight));
                        destinationVertex.edges.add(new Edge(destination, source, weight));
                        return true;
                    }
                }
                // source exists, but not destination, add edges to both after creation
                Vertex temp = new Vertex(destination, new ArrayList<>());
                temp.edges.add(new Edge(destination, source, weight));
                sourceVertex.edges.add(new Edge(source, destination, weight));
                connections.add(temp);
                return true;
            }
        }
        
        // neither source nor destination exist, add edges to both after creation
        Vertex temp0 = new Vertex(source, new ArrayList<>());
        Vertex temp1 = new Vertex(destination, new ArrayList<>());
        temp0.edges.add(new Edge(source, destination, weight));
        temp1.edges.add(new Edge(destination, source, weight));
        connections.add(temp0);
        connections.add(temp1);
        return true;

    }

    /**
     * Returns the shortest distance between city A and city B using Dijkstra's algorithm
     * @param cityA the city of the flight's origin 
     * @param cityB the city of the flight's destination
     * @return the shortest distance between city A and city B
     */
    public int shortestDistance(String cityA, String cityB) {
        
        return -1;

    }

    /**
     * Uses Prim's algorithm to find the minimum spanning tree of the airport system
     * @return
     */
    public List<Edge> minimumSpanningTree() {

        return null;

    }

    /**
     * Returns a list of all the cities from the start using BFS
     * This is assuming the start vertex exists
     * Order of the cities in the same level does not matter
     * @param start the city of origin
     * @return a list of String representing the cities from the start using BFS
     */
    public List<String> breadthFirstSearch(String start) {

        // list of all adjacent values to the value in the current position
        // adjacents = new ArrayList<List>();

        // create a list to store all the cities from the start using BFS
        List<String> cities = new ArrayList<>();

        // create a boolean array to keep track of visited cities
        boolean visited[] = new boolean[connections.size()];

        // create a queue to store the cities to visit next
        PriorityQueue<String> queue = new PriorityQueue<>();

        // marks all cities as not visited
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }

        // add all of the connections to the adjacency list
        for (Vertex connection : connections) {
            adjacents.add(connections.indexOf(connection), connection.edges);
        }

        // finds the starting city and visits it and adds it to the queue
        for (Vertex vertex : connections) {
            if (vertex.id == start) {
                // add all of the adjacent cities to the queue
                for (Edge edge : vertex.edges) {
                    queue.add(edge.destination);
                }
                queue.add(vertex.id);
                // mark the city as visited
                visited[connections.indexOf(vertex)] = true;
            }
        }

        while (!queue.isEmpty()) {
            System.out.println(queue);
            String city = queue.poll(); 
            cities.add(city);
            System.out.println(cities);

            // Find vertex object corresponding to city
            Vertex v = null;
            for (Vertex vertex : connections) {
                if (vertex.id.equals(city)) {
                    v = vertex;
                    break;
                }
            }
            
            // see if the idx value of the corresponding edge's destination in connections has been visited with the same index
            Vertex edgeVert = null;
            if (v != null) {
                for (Edge edge : v.edges) {
                    for (Vertex connection : connections) {
                        if (edge.destination == connection.id) {
                            edgeVert = connection;
                            break;
                        }
                    }
                    if (!visited[connections.indexOf(edgeVert)] && !queue.contains(edgeVert.id)) {
                        System.out.println(connections.indexOf(edgeVert));
                        visited[connections.indexOf(edgeVert)] = true;
                        queue.add(edge.destination); 
                    }
                }
            }
        }

        /*
        // visits all the cities in the queue and adds them to the cities list
        while (queue.size() != 0) {
            // get the head of the queue and add it to the return list
            // int idx = queue.indexOf(queue.peek());
            String city = queue.poll();
            cities.add(city);
            
            for (List<Edge> adjacent : adjacents) {
                for (Edge edge : adjacent) {
                    if (edge.source == queue.peek()) {

                    }
                }
            }
            // Iterator<Edge> i = adjacents.get(idx).listIterator();
            Iterator<Edge> i = queue.peek().listIterator();
            while (i.hasNext()) {
                String src = i.next().source;
                System.out.println(src);
                System.out.println(queue);
                int index = queue.indexOf(src);
                System.out.println(index);
                if (!visited[index]) {
                    queue.add(connections.get(index).id);
                    visited[index] = true;
                }
                // System.out.println('1');
            }
        } */

        return cities;

    }
    
    /**
     * Prints the graph in a readable format and it is clear which edge belongs to which vertex
     */
    public void printGraph() {
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : connections) {
            sb.append("V: ");
            sb.append(vertex.toString());
            sb.append(" | E: ");
            for (Edge edge : vertex.edges) {
                sb.append(edge.toString() + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
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
    
    public static void main(String[] args) {
        AirportSystem airport = new AirportSystem();
        airport.addEdge("Chicago", "New York", 10);
        airport.addEdge("Chicago", "Los Angeles", 20);
        airport.addEdge("Chicago", "San Francisco", 30);
        airport.addEdge("New York", "Los Angeles", 40);
        airport.addEdge("New York", "San Francisco", 50);
        airport.addEdge("Los Angeles", "San Francisco", 60);
        System.out.println(airport.breadthFirstSearch("Chicago"));
    }
    

}