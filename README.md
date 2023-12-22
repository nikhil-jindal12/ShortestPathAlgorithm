# ShortestPathAlgorithm

This repository contains two classes: `AirportSystem.java` and `AirportSystemTesting.java`. These two classes create a fake airport system/graph that organizes and creates an application with the following functionalities:
- Create an airport system that stores the city connection information
- Finds the shortest path from city A to city B using Dijkstra's algorithm
- Finds the minimum spanning tree of a graph using Prim's algorithm

----

### Implementation

#### Edge
Edge is a private nested class of `AirportSystem.java` that contains the following:
- `String toString()` - the String representation of the edge in the format of "[start, destination]"

#### Vertex
Vertex is another private nested class of `AirportSystem.java` that contains the following implementation:
- `String toString()` - the String representation of the vertex in the format of "id"

#### AirportSystem
- `HashMap<String, Vertex> connections` - an adjacency list of all of the flight connections
- `boolean addEdge(String source, String destination, int weight)` - adds a new edge to the connections list, returning false if the edge already exists or the weight is negative
- `int shortestDistance(String cityA, String cityB)` - returns the shortest distance between cityA and cityB using Dijkstra's algorithm
- `List<Edge> minimumSpanningTree()` - creates a minimum spanning tree of the airport system using Prim's algorithm
- `List<String> breadthFirstSearch(String start)` - returns a list of all of the cities from the starting city using BFS, assuming that the start vertex exists
- `void printGraph()` - prints the graph in a readable format to the terminal window

----

### JUnit Testing

The `AirportSystemTesting.java` class contains some JUnit tests for the `shortestDistance()` method and the `minimumSpanningTree()` method. All of the tests pass, which proves that both Dijkstra's and Prim's Algorithms are coded properly.