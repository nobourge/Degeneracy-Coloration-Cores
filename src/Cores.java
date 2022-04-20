/*
* Given a graph G and an integer K,
* K-cores of the graph are connected components
* that are left after all vertices of degree less than k have been removed
* */


import edu.princeton.cs.algs4.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Cores {

    // This class represents a undirected graph using adjacency
    // list representation
    static class Graph
    {
        int V; // No. of vertices

        // Pointer to an array containing adjacency lists
        Vector<Integer>[] adj;

        @SuppressWarnings("unchecked")
        Graph(int V)
        {
            this.V = V;
            this.adj = new Vector[V];

            for (int i = 0; i < V; i++)
                adj[i] = new Vector<>();
        }

        // function to add an edge to graph
        void addEdge(int u, int v)
        {
            this.adj[u].add(v);
            this.adj[v].add(u);
        }

        // A recursive function to print DFS starting from v.
        // It returns true if degree of v after processing is less
        // than k else false
        // It also updates degree of adjacent if degree of v
        // is less than k.
        // And if degree of a processed adjacent
        // becomes less than k, then it reduces of degree of v also

        boolean DFSUtil(int v,
                        boolean[] visited,
                        int[] vDegree,
                        int k)
        {

            // Mark the current node as visited and print it
            visited[v] = true;

            // Recur for all the vertices adjacent to this vertex
            for (int i : adj[v])
            {
                if (vDegree[v] < k)
                    // degree of v is less than k
                    // degree of adjacent must be reduced
                    vDegree[i]--;

                // If adjacent is not processed, process it
                if (!visited[i])
                {
                    // If degree of adjacent after processing becomes
                    // less than k, then reduce degree of v also.
                    DFSUtil(i, visited, vDegree, k);
                }
            }

            // Return true if degree of v is less than k
            return (vDegree[v] < k);
        }

        //todo: print k cores by vertice deletion

        // Prints k cores of an undirected graph
        boolean printKCores(int k, boolean[] depthed)
        {
            // INITIALIZATION
            // Mark all the vertices as not visited and not
            // processed.
            boolean degeneracy_reached = false;
            boolean[] visited = new boolean[V];
            boolean[] processed = new boolean[V];
            Arrays.fill(visited, false);
            Arrays.fill(processed, false);

            int mindeg = Integer.MAX_VALUE;
            int startvertex = 0;

            int degeneracy = 0;

            // Store degrees of all vertices
            int[] vDegree = new int[V];
            for (int i = 0; i < V; i++)
            {
                vDegree[i] = adj[i].size();

                if (vDegree[i] < mindeg)
                {
                    mindeg = vDegree[i];
                    startvertex = i;
                }
            }
            DFSUtil(startvertex, visited, vDegree, k);

            // DFS traversal to update degrees of all
            // vertices.
            for (int i = 0; i < V; i++)
                if (!visited[i])
                    DFSUtil(i, visited, vDegree, k);

            // PRINTING K CORES
            System.out.printf("\n\n[%d] -Cores : ", k);
            int printed = 0;
            for (int v = 0; v < V; v++)
            {
                // Only considering those vertices which have degree
                // >= K after BFS
                if (vDegree[v] >= k)
                {
                    System.out.printf("\n[%d]", v);
                    printed++;

                    // Traverse adjacency list of v and print only
                    // those adjacent which have vDegree >= k after
                    // BFS.
                    boolean adj_printed = false;

                    for (int i : adj[v])
                        if (vDegree[i] >= k)
                            System.out.printf(" -> %d", i);
                            boolean printed_adj = true;
                }
                else
                {
                    if(!depthed[v]){
                        System.out.printf("\n[%d] has depth [%d]", v, k-1);
                        depthed[v] = true;
                    }
                }
            }
            if (printed == 0){
            degeneracy_reached = true;}

            return degeneracy_reached;
        }

        // prints k cores for every k until degeneracy is reached
        void printAllKCores()
        {
            boolean[] depthed = new boolean[V];
            Arrays.fill(depthed, false);
            int k=0;
            boolean degeneracy_reached = false;
            while (!degeneracy_reached)
            {
                degeneracy_reached = printKCores(k++, depthed);
            }
            System.out.printf("\n input graph is %d -degenerate", k-1);

        }

        /*
        //For a vertex v of G,
        // we can define its depth c(v)
        // as the largest number k such that v belongs to a k-core.
        void print_depths_of_vertices_of(Graph g)
        {
            int[] depth = new int[g.V];
            Arrays.fill(depth, 0);
            for (int i = 0; i < g.V; i++)
            {
            }
         */
    }
    // Driver Code
    public static void main(String[] args)
    {
        int k = 4;
        Graph g1 = new Graph(9);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(1, 5);
        g1.addEdge(2, 3);
        g1.addEdge(2, 4);
        g1.addEdge(2, 5);
        g1.addEdge(2, 6);
        g1.addEdge(3, 4);
        g1.addEdge(3, 6);
        g1.addEdge(3, 7);
        g1.addEdge(4, 6);
        g1.addEdge(4, 7);
        g1.addEdge(5, 6);
        g1.addEdge(5, 8);
        g1.addEdge(6, 7);
        g1.addEdge(6, 8);
        g1.printAllKCores();

        System.out.println();

        Graph g2 = new Graph(13);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(0, 3);
        g2.addEdge(1, 4);
        g2.addEdge(1, 5);
        g2.addEdge(1, 6);
        g2.addEdge(2, 7);
        g2.addEdge(2, 8);
        g2.addEdge(2, 9);
        g2.addEdge(3, 10);
        g2.addEdge(3, 11);
        g2.addEdge(3, 12);
        g2.printAllKCores();
    }

}
