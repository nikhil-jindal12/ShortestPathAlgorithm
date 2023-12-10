import java.util.Queue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.LinkedList;

/**
 * Class that contains the implementation for a graph-like airport system
 * @author Nikhil Jindal
 */
public class AirportSystem {

    // store all flight connections
    HashMap<String, Vertex> connections = new HashMap<>();

    // store number of airports
    int n = connections.size();

    /**
     * Adds an edge between two vertices in the graph with the given weight
     * The edges are bi-directional, so they are added to both the source and destination
     * @param source the source city of the flight
     * @param destination the destination city of the flight
     * @param weight the distance between the two cities
     * @return true if the edge was successfully added
     * @return false if the edge already exists, the weight is negative, or the source is also the destination
     */
    public boolean addEdge(String source, String destination, int weight) {

        // return false if the weight is less than 0, or the source is also the destination
        if (weight < 0 || source.equals(destination)) {
            return false;
        }

        // create new vertices for the source and destination if they don't exist
        connections.putIfAbsent(source, new Vertex(source));
        connections.putIfAbsent(destination, new Vertex(destination));
        
        // create new vertices for the source and destination if they don't exist
        Vertex sourceVertex = connections.get(source);
        Vertex destinationVertex = connections.get(destination);

        // return false if either of the vertices are still null
        if (sourceVertex == null || destinationVertex == null) {
            return false;
        }

        // creates a new list of edges if the source vertex does not contain any
        if (sourceVertex.getEdges() == null) {
            sourceVertex.setEdges(new ArrayList<>());
        }

        // creates a new list of edges if the destination vertex does not contain any
        if (destinationVertex.getEdges() == null) {
            destinationVertex.setEdges(new ArrayList<>());
        } else {
            // check every edge in the source to see if a connection already exists
            for (Edge edge : sourceVertex.getEdges()) {
                if (edge.getDestination().equals(destinationVertex)) {
                    return false;
                }
            }
        }

        // add the edge to both the source and destination vertex's list of edges, and add the edge to both the destination and source vertex's list of edge
        sourceVertex.getEdges().add(new Edge(sourceVertex, destinationVertex, weight));
        destinationVertex.getEdges().add(new Edge(destinationVertex, sourceVertex, weight));
        return true;

    }

    /**
     * Returns the shortest distance between both citys using Dijkstr's algorithm
     * @param cityA the source city
     * @param cityB the destination city
     * @return the distance between the two cities
     */
    public int shortestDistance(String cityA, String cityB) {
        
        // a HashMap with the city as the key and the distance as the value
        Map<Vertex, Integer> distances = new HashMap<>();

        // a heap that will be a queue for the vertices to check
        // need to implement Comparator so it knows how to compare vertices
        // place all vertices into the heap
        PriorityQueue<Vertex> queue =  new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // use a hashset to store the visited nodes
        Set<Vertex> visited = new HashSet<>();

        // get the source and destination vertices from list of cities using the city names as keys
        Vertex sourceVertex = connections.get(cityA);
        Vertex destinationVertex = connections.get(cityB);

        // put all of the cities in the connections list into the visited set with a max distance value
        for (Vertex vertex : connections.values()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }

        // set the source vertex's distance to 0
        distances.put(sourceVertex, 0);

        // add the source vertex to the queue
        queue.add(sourceVertex);

        // run until the queue is empty
        while (!queue.isEmpty()) {
            // create a temporary vertex to store the top item in the queue and set it to be visited
            Vertex tmp = queue.poll();
            visited.add(tmp);

            // check to see if we have reached the destination
            if (tmp.equals(destinationVertex)) {
                break;
            }

            // iterate through the edges of the current vertex
            for (Edge edge : tmp.getEdges()) {
                Vertex adjacent = edge.getDestination();
                
                // update the distance from the source to all of the adjacent vertices
                if (!visited.contains(adjacent)) {
                    if (tmp.equals(sourceVertex)) {
                        int newDistance = edge.getDistance();
                        if (newDistance < distances.get(adjacent)) {
                            distances.put(adjacent, newDistance);
                            queue.add(adjacent);
                        }
                    } else {
                        int newDistance = distances.get(tmp) + edge.getDistance();
                        if (newDistance < distances.get(adjacent)) {
                            distances.put(adjacent, newDistance);
                            queue.add(adjacent);
                        }
                    }
                }
            }
        }

        return distances.get(destinationVertex);

    }

    /**
     * Uses Prim's algorithm to create a minimum spanning tree of the airport system
     * @return a list of edges that make up the minimum spanning tree
     */
    public List<Edge> minimumSpanningTree() {

        // list that will store the MST
        List<Edge> mst = new ArrayList<>();

        // find a vertex in the tree
        Vertex start = connections.values().iterator().next();

        // a set to store the visited vertices
        Set<Vertex> visited = new HashSet<>();

        // heap to store the edges
        // using a Queue will insert by priority and process shortest edges first
        Queue<Edge> queue = new PriorityQueue<>();

        visited.add(start);
        // add all of the initial edges of the vertex
        queue.addAll(start.getEdges());

        while (!queue.isEmpty()) {
            // get the next edge
            Edge edge = queue.poll();

            // get the destination vertex of the edge
            Vertex source = connections.get(start.getId());
            Vertex destination = edge.getDestination();

            if (!visited.contains(source) || !visited.contains(destination)) {
                mst.add(edge); // Add the edge to the MST
                visited.add(destination); // Mark the destination vertex as visited
    
                // If the source vertex has not been visited, mark it as visited and add its edges to the queue
                if (!visited.contains(source)) {
                    visited.add(source);
                    queue.addAll(source.getEdges());
                }
                // If the destination vertex has not been visited, mark it as visited and add its edges to the queue
                if (!visited.contains(destination)) {
                    visited.add(destination);
                    queue.addAll(destination.getEdges());
                }

                // Add all edges of the destination vertex to the queue
                for (Edge adjacentEdge : destination.getEdges()) {
                    if (!visited.contains(adjacentEdge.getDestination())) {
                        queue.add(adjacentEdge);
                    }
                }
            }   
        }

        return mst;
    }

    /**
     * Returns a list of cities from the start city using breadth-first search
     * This assumed the start vertex city exits
     * @param start the city to start the search in
     * @return a list of strings of the city names that appear in the BFS
     */
    public List<String> breadthFirstSearch(String start) {

        // find the starting vertex using the city name
        Vertex startVertex = connections.get(start);

        // all of the visited cities in breadth first search order
        List<String> cities = new ArrayList<>();
        
        if (startVertex == null) {
            return cities;
        }
        
        // set to remember which elements we have already visited
        Set<Vertex> visited = new HashSet<>();

        // queue to store the cities to visit (FIFO)
        Queue<Vertex> queue = new LinkedList<>();

        // add the starting city to the queue and set it as visited
        queue.add(startVertex);
        visited.add(startVertex);
        
        // run until the queue is empty
        while (!queue.isEmpty()) {
            // get the next city from the queue
            Vertex city = queue.poll();

            // add the city to the list of visited cities
            cities.add(city.getId());

            // iterate through the edges of the city
            for (Edge edge : city.getEdges()) {
                // get the destination city of the edge
                Vertex destination = edge.getDestination();

                // if the destination city has not been visited, add it to the queue and set it as visited
                if (!visited.contains(destination)) {
                    queue.add(destination);
                    visited.add(destination);
                }
            }
        }

        return cities;

    }

    /**
     * Prints a representation of the entire airport system and the connections from each city
     */
    public void printGraph() {

        // find a vertex in the tree
        Vertex start = connections.values().iterator().next();

        // perform a BFS of the tree to get a list of all the edges in the graph
        List<String> cities = breadthFirstSearch(start.getId());

        // go through each city and print its edges
        for (String city : cities) {
            Vertex vertex = connections.get(city);
            System.out.println(vertex + ": " + city + " | E: " + vertex.getEdges().toString());
        }
    }

    /**
     * Implementation of a private Edge class
     */
    private class Edge extends ComparableEdge {
        public Edge(Vertex source, Vertex destination, int distance) {
            super(source, destination, distance);
        }
    }

    /**
     * Implementation of a private ComparableEdge class that implements Comparable<ComparableEdge> and Comparator<ComparableEdge>
     */
    private class ComparableEdge implements Comparable<ComparableEdge>, Comparator<ComparableEdge> {
        
        // the source city of the flight
        Vertex source;
        
        // the destination city of the flight
        Vertex destination;

        // distance between the source and destination
        int distance;

        // constructor for the Edge class
        public ComparableEdge(Vertex source, Vertex destination, int distance) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
        }

        // returns the destination city
        public Vertex getDestination() {
            return this.destination;
        }

        // returns the distance between the two cities
        public int getDistance() {
            return this.distance;
        }

        @Override
        /**
         * Overriden version of the toString() method
         * @return a string representation of the edge in the format of "[source, destination]"
         */
        public String toString() {
            return "[" + this.source.getId() + ", " + this.destination.getId() + "]";
        }

        @Override
        /**
         * Overriden version of the compareTo() method
         * @param o the edge to compare to
         * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
         */
        public int compareTo(ComparableEdge o) {
            return Integer.compare(this.distance, o.distance);
        }

        @Override
        /**
         * Override version of the compare() method
         * @param o1 the first edge to compare
         * @param o2 the second edge to compare
         * @return a negative integer, zero, or a positive integer as the first edge is less than, equal to, or greater than the second edge
         */
        public int compare(ComparableEdge o1, ComparableEdge o2) {
            return Integer.compare(o1.distance, o2.distance);
        }

    }

    /**
     * Implementation of a private Vertex class
     */
    private class Vertex {
        
        // city source name of the vertex
        String id;

        // a list of all destination cities that connect to this source
        List<Edge> edges;

        // constructor for the Vertex class
        public Vertex (String id) {
            this.id = id;
        }

        // returns the source city of the Vertexy
        public String getId() {
            return this.id;
        }
        
        // gets the edges connected to this Vertex
        public List<Edge> getEdges() {
            return this.edges;
        }

        // sets the edges connected to this Vertex
        public void setEdges(List<Edge> edges) {
            this.edges = edges;
        }

        @Override
        /**
         * Overriden toString() method
         * @return a string representation of the Vertex in the format of "id"
         */
        public String toString() {
            return this.id;
        }

        @Override
        /**
         * Overriden equals() method
         * @param obj the object to compare to
         * @return true if the objects are equal
         * @return false otherwise
         */
        public boolean equals(Object obj) {
            if (obj instanceof Vertex) {
                return this.getId().equals(((Vertex) obj).getId());
            }
            return false;
        }

    }    

}