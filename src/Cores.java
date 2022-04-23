/*
* Given a graph G and an integer K,
* K-cores of the graph are connected components
* that are left after all vertices of degree less than k have been removed
* */

//import org.jgrapht.Graph;
import edu.princeton.cs.algs4.Graph;

import java.io.File;
import java.util.*;

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

        // function to remove an edge from graph
        void removeEdge(int u, int v)
        {
            this.adj[u].remove(v);
            this.adj[v].remove(u);
        }

        void init_from_file(String file_name,
                            String order)
        {
            int degeneracy = 0;

            try
            {
                Scanner sc = new Scanner(new File(file_name));
                int nb_edges = sc.nextInt();
                if (order == "vertex")
                {

                    for (int i = 0; i < nb_edges; i++)
                    {
                        int u = sc.nextInt();
                        int v = sc.nextInt();
                        addEdge(u, v);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("Error while reading file");
            }
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

        // removes vertices with degree less than k and adjaccent vertices

        //boolean print_k_cores_by_vertice_deletion(int k)


        // Prints k cores of an undirected graph
        boolean printKCores(int k,
                            int startvertex,
                            int mindeg,
                            boolean[] depthed,
                            boolean degeneracy_reached)
        {
            // INITIALIZATION
            // Mark all the vertices as not visited and not
            // processed.

            boolean[] visited = new boolean[V];
            Arrays.fill(visited, false);

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
                    System.out.printf("(: degree [%d])", vDegree[v]);
                    printed++;

                    // Traverse adjacency list of v and print only
                    // those adjacent which have vDegree >= k after
                    // BFS.

                    //boolean adj_printed = false;

                    for (int i : adj[v])
                        if (vDegree[i] >= k)
                            System.out.printf(" -> %d", i);
                            boolean printed_adj = true;
                }
                else
                {
                    if(!depthed[v]){
                        System.out.printf("\n[%d] (: degree [%d]) has depth [%d]", v, vDegree[v], k-1);
                        depthed[v] = true;
                    }
                }
            }
            if (printed == 0){
            degeneracy_reached = true;}

            return degeneracy_reached;
        }

        // prints k cores for every k until degeneracy is reached
        void printAllKCores(int startvertex,
                            int mindeg)
        {
            boolean[] depthed = new boolean[V];
            Arrays.fill(depthed, false);
            int k=0;
            boolean degeneracy_reached = false;
            while (!degeneracy_reached)
            {
                degeneracy_reached = printKCores(k++,
                                                startvertex,
                                                mindeg,
                                                depthed,
                                                degeneracy_reached);
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
        //int k = 4;
        Graph g1 = new Graph(9);
        int mindeg = Integer.MAX_VALUE;
        int startvertex = 0;

        g1.init_from_file("facebook_combined-graph.txt", "vertex");
        g1.printAllKCores(startvertex,
                mindeg);

    }

}
