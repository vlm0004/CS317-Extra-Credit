/*
 * Victoria Mitchell
 * CS317 Extra Credit
 * Dijkstra.java 
 */
package Dijkstra;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implementation of Dijkstra's Algorithm for finding 
 * the shortest path between nodes in a graph.
 */
public class Dijkstra {

    /**
     * Main sets up the adjacency matrix, sets the source node and calls the
     * "solve" method for finding the shortest path with Dijkstra's Algorithm.
     */
    public static void main(String[] args){
            
        /* Test case: Problem 14 from HW 2 */
        int D = Integer.MAX_VALUE;

        /* Set up the adjacency matrix */            
        int[][] graph = new int[][]{
            { 0, 7,  3,  4,  D,  D,  D }, // A
            { 7, 0,  D,  5,  D,  3,  D }, // B
            { 3, D,  0, 11,  9,  D,  D }, // C
            { 4, 5, 11,  0,  2, 15, 10 }, // D
            { D, D,  9,  2,  0,  D, 12 }, // E
            { D, 3,  D, 15,  D,  0,  8 }, // F
            { D, D,  D, 10, 12,  8,  0 }  // G
        };
             
        /* Specify the source Vertex */
        int source = 0; // A
        
        /* Run Dijkstra's algorithm */
        solve(graph, source);

    }
 
    /**
     * This method actually executes Dijkstra's Algorithm.
     */
    public static void solve(int[][] graph, int source) {

        /* --- Initialization --- */
        
        /* Distances between Vertices */
        int[] dist = new int[graph.length];
        
        /* Use a hash set for vertices that have been visited */
        Set<Integer> visited = new HashSet<>();
        
        /* Use a Java priority Q with comparator to insert items into the path */
        DijkstraComparator comparator = new DijkstraComparator();       
        
        PriorityQueue<Vertex> Q = new PriorityQueue<>(graph.length, comparator);

        /* Set all distances to max */
        for ( int i = 0; i < graph.length; i++ ) { dist[i] = Integer.MAX_VALUE; }
 
        /* Add the source Vertex to the Q and set it's distance to 0 */
        Q.add(new Vertex(source, 0));  
        
        dist[source] = 0;
        
        
        /* --- Begin solution --- */

        /* While the Q is not empty (vertices to still be checked) */
        while ( !Q.isEmpty() ) {
            
            /* Remove and return the best Vertex */
            int u = Q.remove().node;
            
            /* Add best Vertex to the hash */
            visited.add(u);
            
            /* Check all the neighbors of Vertex u */
            for ( int v = 0; v < graph.length; v++ ) {
                
                /* If the vertex has not been visited */
                if ( !visited.contains(v) ) {
                    
                    /* If the vertex is adjacent */
                    if ( graph[u][v] != Integer.MAX_VALUE ) {
                        
                        /* Distance to the neighbor */
                        int neighborDistance = graph[u][v];
                        
                        /* Try alternate path */
                        int altDistance = dist[u] + neighborDistance;
                        
                        /* Is the new path a better score */
                        if ( altDistance < dist[v] ) { dist[v] = altDistance; }
                        
                        /* Add adjacent neighbor to the Q */
                        Q.add(new Vertex(v,dist[v]));
                        
                    }   
                }
            }            
        }
        
        /* Display the solution by Dijkstra's Algorithm */   
        System.out.format("Dijkstra's Algorith Results:\n\n");
        
        System.out.format("Shortest Path From Source Node: %d\n", source);

        for ( int i = 0; i < graph.length; i++ ) { 
            
            System.out.format(" To Node: %d = %d\n", i, dist[i]); 
            
        }

    }

    
    /**
     * Class for vertices in the graph
     */
    public static class Vertex {
        
        public int node;        
        public int score;

        public Vertex() {
            
        }

        public Vertex(int node, int cost) {  
            
            this.node = node;            
            this.score = cost;   
            
        }
    }
    
    /**
     * A comparator for inserting nodes into the priority queue
     */
    private static class DijkstraComparator implements Comparator<Vertex> {

        public DijkstraComparator() {

        }

        @Override
        public int compare(Vertex o1, Vertex o2) {
            
            if ( o1.score< o2.score ) {   
                
                return -1;         
                
            } else if ( o1.score > o2.score ) {    
                
                return 1;   
                
            }    
            
            return 0;
        }
    }
}
