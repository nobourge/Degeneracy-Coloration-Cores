/*
* Given a graph G and an integer K,
* K-cores of the graph are connected components
* that are left after all vertices of degree less than k have been removed
*
* */

//import org.jgrapht.Graph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.IndexMultiwayMinPQ;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

public class Cores {
    private Graph g;

    static Map<Integer, Integer> depths;

    static int degeneracy = 0;

    /*

    // A recursive function to print DFS starting from v.
    // It returns true if degree of v after processing is less
    // than k else false
    // It also updates degree of adjacent if degree of v
    // is less than k.
    // And if degree of a processed adjacent
    // becomes less than k, then it reduces of degree of v also

    boolean DFSUtil(Graph g
                    int v,
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

     */

    // removes vertices with degree less than k and adjaccent vertices

    //boolean print_k_cores_by_vertice_deletion(int k)


    /*
    // Prints k cores of an undirected graph
    boolean printKCores(Graph g,
                        int k,
                        int startvertex,
                        int mindeg,
                        boolean[] depthed,
                        boolean degeneracy_reached)
    {
        int V = g.V();
        // INITIALIZATION
        // Mark all the vertices as not visited and not
        // processed.

        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);

        // Store degrees of all vertices
        int[] vDegree = new int[V];
        for (int i = 0; i < V; i++)
        {
            vDegree[i] = g.adj[i].size();

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

                for (int i : g.adj[v])
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

     */

    /*
    // prints k cores for every k until degeneracy is reached
    void printAllKCores(Graph g,
                        int startvertex,
                        int mindeg)
    {
        boolean[] depthed = new boolean[g.V()];
        Arrays.fill(depthed, false);
        int k=0;
        boolean degeneracy_reached = false;
        while (!degeneracy_reached)
        {
            degeneracy_reached = printKCores(g,
                                            k++,
                                            startvertex,
                                            mindeg,
                                            depthed,
                                            degeneracy_reached);
        }
        System.out.printf("\n input graph is %d -degenerate", k-1);

    }

     */

    /*
    //For a vertex v of G,
    // we can define its depth c(v)
    // as the largest number k such that v belongs to a k-core.
    void print_depths_of_vertices_of(Graph g)
     */



    //initialize the IndexMultiwayMinPQ from graph
    public static IndexMultiwayMinPQ<Integer>
    initialize_IndexMultiwayMinPQ_from_graph(Graph g) {
        IndexMultiwayMinPQ<Integer> pq = new IndexMultiwayMinPQ<Integer>(g.V(), g.E() - 1);

        for (int i = 0; i < g.V(); i++) {
            //pq.insert(i, g.adj(i));
        }
        return pq;
    }

    /*
    //initialize the IndexMultiwayMinPQ from file_name
    public static IndexMultiwayMinPQ<Integer>
    initialize_IndexMultiwayMinPQ_from(String file_name,
                                       String order,
                                       Graph g1)
                                       //IndexMultiwayMinPQ<Integer> pq)
    {
        IndexMultiwayMinPQ<Integer> pq;
        try {
            System.out.println("initialize_IndexMultiwayMinPQ_from input file: " + file_name);

            Scanner sc = new Scanner(new File(file_name));
            System.out.println("Reading file...");
            int V = sc.nextInt();
            int E = sc.nextInt();
            System.out.println("V: " + V);
            System.out.println("E: " + E);


            //pq = new IndexMultiwayMinPQ<Integer>(V, 2);
            pq = new IndexMultiwayMinPQ<Integer>(V, E - 1);
            if (order.equals("vertex")) {
                System.out.println("order: vertex");

                int u = sc.nextInt();
                int v = sc.nextInt();
                System.out.println("u: " + u);
                System.out.println("v: " + v);

                g1.addEdge(u, v);

                int current_u = u;
                int position_map_index = 0;


                for (int i = 0; i < E-1; i++) {

                    u = sc.nextInt();
                    v = sc.nextInt();

                    //System.out.println("u: " + u);
                    //System.out.println("v: " + v);

                    g1.addEdge(u, v);
                    //System.out.println("g1.adj["+u+"]: " + g1.adj[u]);

                    if (u != current_u) {
                        //System.out.println("current_u: " + current_u);
                        System.out.println("g1.adj["+current_u+"]: " + g1.adj[current_u]);
                        System.out.println("inserting size " + g1.adj[current_u].size() + " of " + current_u + " at " + position_map_index + " into pq");
                        pq.insert(position_map_index, g1.adj[current_u].size());
                        current_u = u;
                        //System.out.println("current_u: " + current_u);

                        position_map_index++;

                        //System.out.println("position_map_index: " + position_map_index);
                        g1.addEdge(u, v);
                    }
                }
                pq.insert(position_map_index, g1.adj[current_u].size());
                System.out.println("inserting size " + g1.adj[current_u].size() + " of " + current_u + " at " + position_map_index + " into pq");


                System.out.println("returning pq");
                return pq;
            }
            System.out.println("returning pq");
            return pq;
        } catch (Exception e) {
            System.out.println("Error while reading file");
        }
        return null;
        //return pq;
    }

     */

    //initialize the graph from file_name
    public static Graph initialize_Graph_from(String file_name)
    {

        try {
            System.out.println("initialize_Graph_from input file: " + file_name);

            Scanner sc = new Scanner(new File(file_name));
            System.out.println("Reading file...");
            int V = sc.nextInt();
            int E = sc.nextInt();
            System.out.println("V: " + V);
            System.out.println("E: " + E);

            Graph g = new Graph(V);

            for (int i = 0; i < E; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                g.addEdge(u, v);
            }

            System.out.println("returning g1");
            return g;
        } catch (Exception e) {
            System.out.println("Error while reading file");
        }
        return null;
    }


    /*
     * @return the depths of the vertices in a graph
     */
    public static Map<Integer, Integer> getDepths(String file_name,
                                                  String delimiter)
    {

        getDegeneracyAndDepths_from(file_name,
                delimiter);
        return depths;
    }

    /*
     * @return the depth of a vertex
     */
    public static Integer getVertexDepth(String file_name,
                                         String delimiter,
                                         int v)
    {

        getDepths(file_name, delimiter);
        return depths.get(v);
    }


    /*
    * @return the degeneracy of a graph
     */
    public static int getDegeneracy(String file_name, String delimiter)
    {

        getDegeneracyAndDepths_from(file_name, delimiter);
        return degeneracy;
    }

    /*
    calculates the degeneracy of a graph and its vertices depths
     */
    public static void getDegeneracyAndDepths_from(String file_name, String delimiter)
    {
        //Graph g = initialize_Graph_from(file_name);
        Graph g = GraphGenerator.generateGraph(file_name, delimiter);
        System.out.println("Graph created");

        /*
        Coreness JgraphTc = new Coreness();
        System.out.println("JgraphT Degeneracy : " + JgraphTc.getDegeneracy());


         */

        /*
        //IndexMultiwayMinPQ<Integer> pq = initialize_IndexMultiwayMinPQ_from(file_name, order, g1);
        IndexMultiwayMinPQ<Integer> pq = initialize_IndexMultiwayMinPQ_from_graph(g1);
        System.out.println("pq initialized");
        assert pq != null;
        System.out.println("pq.size(): " + pq.size());
        assert pq != null;
        System.out.println(pq.size());

        while (!pq.isEmpty())
        {
            int u = pq.delMin();
            if (g1.adj[u].size() < mindeg)
            {
                mindeg = g1.adj[u].size();
                startvertex = u;
            }
        }
         */

        int degeneracy = 0;

        Map<Integer, Integer> depths = new HashMap<>();

        int maxDegree = g.V() - 1;
        int minDegree = g.V(); // todo int.MAX_VALUE;
        Set[] degreeIndexedPriorityQueue = (Set[]) Array.newInstance(Set.class, maxDegree + 1);
        for (int i = 0; i < degreeIndexedPriorityQueue.length; i++) {
            degreeIndexedPriorityQueue[i] = new HashSet<>();
        }


        Map<Integer, Integer> degrees = new HashMap<>();
        for (int v = 0 ; v < g.V() ; v++) {
            int d = g.degree(v);
            degreeIndexedPriorityQueue[d].add(v);
            degrees.put(v, d);
            minDegree = Math.min(minDegree, d);
        }

        /*
         * Extract from degreeIndexedPriorityQueue
         */
        while (minDegree < g.V()) {
            Set minDegreeKey = degreeIndexedPriorityQueue[minDegree];
            if (minDegreeKey.isEmpty()) {
                minDegree++;
                continue;
            }

            int vertex_to_remove = (int) minDegreeKey.iterator().next();
            minDegreeKey.remove(vertex_to_remove);
            depths.put(vertex_to_remove, minDegree);
            degeneracy = Math.max(degeneracy, minDegree);

            for (int u : g.adj(vertex_to_remove)) {
                int uDegree = degrees.get(u);
                if (uDegree > minDegree && !depths.containsKey(u)) {
                    degreeIndexedPriorityQueue[uDegree].remove(u);
                    uDegree--;
                    degrees.put(u, uDegree);
                    degreeIndexedPriorityQueue[uDegree].add(u);
                    minDegree = Math.min(minDegree, uDegree);
                }
            }
        }
        System.out.println("degeneracy: " + degeneracy);
        //System.out.println("depths: " + depths);
        System.out.println("depth of 5: " + depths.get(5));
        //System.out.println("minDegree: " + minDegree);


    }


    // Driver Code
    public static void main(String[] args)
    {
        //String file_name = "ressources/graph/SNAP/facebook/facebook_combined.txt/facebook_combined.txt";
        //String delimiter = " ";

        String file_name = "ressources/graph/SNAP/roadNet-PA.txt/roadNet-PA.txt";
        String delimiter = "\t";

        getDegeneracyAndDepths_from(file_name, delimiter);

        /*

        int degeneracy = getDegeneracy(file_name);

        System.out.println("Degeneracy: " + degeneracy);

        int depth_of_5 = getVertexDepth(file_name, 5);
        System.out.println("Depth of 5: " + depth_of_5);

         */

    }

}
