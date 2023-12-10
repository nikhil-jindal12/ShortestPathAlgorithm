import org.junit.*;
import static org.junit.Assert.*;

/**
 * JUnit testing class for the AirportSystem class
 * @author Nikhil Jindal
 */
public class AirportSystemTesting {

    // create a new airport instance to test methods on
    AirportSystem airport = new AirportSystem();

    @Test
    /**
     * Tests the shortestDistance method of the AirportSystem
     */
    public void testShortestDistance() {
        
        airport.addEdge("Chicago", "New York", 10);

        // Case 1: One flight, one path
        assertEquals("One flight, one path", 10, airport.shortestDistance("Chicago", "New York"));

        airport.addEdge("Chicago", "Los Angeles", 20);
        airport.addEdge("Los Angeles", "San Francisco", 60);

        // Case 2: Multiple flights, but a singular path
        assertEquals("Multiple flights, but a singular path", 80, airport.shortestDistance("Chicago", "San Francisco"));

        airport.addEdge("Chicago", "San Francisco", 30);
        airport.addEdge("New York", "Los Angeles", 40);
        airport.addEdge("New York", "San Francisco", 50);
        
        // Case 3: Multiple flights, multiple paths
        assertEquals("Multiple flights, multiple paths", 30, airport.shortestDistance("Chicago", "San Francisco"));
    }

    @Test
    /**
     * Tests the minimumSpanningTree() method of the AirportSystem
     */
    public void testMinimumSpanningTree() {

        airport.addEdge("Chicago", "New York", 10);

        // Case 1: One flight
        assertEquals("one", "[[New York, Chicago]]", airport.minimumSpanningTree().toString());
        
        airport.addEdge("Los Angeles", "Chicago", 20);

        // Case 2: Multiple flights
        assertEquals("many", "[[New York, Chicago], [Chicago, Los Angeles]]", airport.minimumSpanningTree().toString());

        airport.addEdge("Phoenix", "Chicago", 40);

        // Case 3: More flights
        assertEquals("many", "[[New York, Chicago], [Chicago, Los Angeles], [Chicago, Phoenix]]", airport.minimumSpanningTree().toString());
    }
}
