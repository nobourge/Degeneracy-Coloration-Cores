/*
* Given a graph G and an integer K,
* K-cores of the graph are connected components
* that are left after all vertices of degree less than k have been removed
* */

//import org.jgrapht.Graph;
import edu.princeton.cs.algs4.Graph;

import java.lang.reflect.Array;
import java.util.*;

public class Cores {
    private Graph g;

    static Map<Integer, Integer> depths;

    static int degeneracy = 0;


    /*For a vertex v of G,
     we can define its depth c(v)
     as the largest number k such that v belongs to a k-core.
     * @return the depths of the vertices in a graph
     */

    /*
     * @return the depth of a vertex
     */
    public static Integer getVertexDepth(Graph g,
                                         int v)
    {
        getDepths(g);
        return depths.get(v);
    }

    //from depths, returns a map of each dephs to the number of vertices at that depth, the k-cores sizes
    public static Map<Integer, Integer> getKCoreSizes(Graph g)
    {
        Map<Integer, Integer> depths = getDepths(g);
        Map<Integer, Integer> k_cores_sizes = new HashMap<>();
        for (int depth : depths.values()) {
            if (k_cores_sizes.containsKey(depth)) {
                k_cores_sizes.put(depth, k_cores_sizes.get(depth) + 1);
            } else {
                k_cores_sizes.put(depth, 1);
            }
        }
        return k_cores_sizes;
    }

    /*
    calculates the degeneracy of a graph and its vertices depths
     */
    public static Map<Integer, Integer> getDepths(Graph g)
    {
        Map<Integer, Integer> depths = new HashMap<>();
        int maxDegree = g.V() - 1;

        //ConcurrentSkipListSet[] degreeIndexedPriorityQueue = (ConcurrentSkipListSet[]) Array.newInstance(ConcurrentSkipListSet.class, maxDegree + 1);
        TreeSet[] degreeIndexedPriorityQueue = (TreeSet[]) Array.newInstance(TreeSet.class, maxDegree + 1);
        //IndexMultiwayMinPQ<TreeSet> degreeIndexedPriorityQueue = new IndexMultiwayMinPQ<>(g.V(), g.E() - 1);

        for (int i = 0; i < degreeIndexedPriorityQueue.length; i++) {
        //for (int i = 0; i < degreeIndexedPriorityQueue.size(); i++) {
            //degreeIndexedPriorityQueue[i] = new ConcurrentSkipListSet<>();
            degreeIndexedPriorityQueue[i] = new TreeSet<>();
            //degreeIndexedPriorityQueue.insert(i, new TreeSet<>());
        }
        int minDegree = g.V();
        Map<Integer, Integer> degreesMap = new HashMap<>();
        for (int v = 0 ; v < g.V() ; v++) {
            int d = g.degree(v);
            degreeIndexedPriorityQueue[d].add(v);
            //degreeIndexedPriorityQueue.keyOf(d).add(v);
            degreesMap.put(v, d);
            minDegree = Math.min(minDegree, d);
        }

        /*
         * Extract from degreeIndexedPriorityQueue
         */
        while (minDegree < g.V()) {
            /// minimum degree is smaller than the number of vertices
            //ConcurrentSkipListSet<Integer> minDegreeKey = degreeIndexedPriorityQueue[minDegree];
            TreeSet<Integer> minDegreeKey = degreeIndexedPriorityQueue[minDegree];
            //TreeSet<Integer> minDegreeKey = degreeIndexedPriorityQueue.minKey();

            if (minDegreeKey.isEmpty()) {
                //System.out.println("minDegreeKey of degree " + minDegree + " is empty");
                minDegree++;
                continue;
            }

            int vertex_to_remove = minDegreeKey.first();
            // remove the vertex from the set O(log(n))
            minDegreeKey.remove(vertex_to_remove);
            //remove the vertex from the hashset O(1)
            depths.put(vertex_to_remove, minDegree);
            for (int u : g.adj(vertex_to_remove)) {
                int uDegree = degreesMap.get(u);

                if (minDegree < uDegree && !depths.containsKey(u)) { // O(1)
                    degreeIndexedPriorityQueue[uDegree].remove(u);
                    uDegree--;
                    //// update u Degree in degreesMap
                    degreesMap.put(u, uDegree);
                    /// update u position in degreeIndexedPriorityQueue
                    degreeIndexedPriorityQueue[uDegree].add(u);

                    ///update minDegree
                    minDegree = Math.min(minDegree, uDegree);
                }
            }
        }
        return depths;
    }

    // from a given filename, returns the graph degeneracy, vertices depths and k-cores sizes
    // todo


    // Driver Code
    public static void main(String[] args)
    {
        Graph g;
        Map<Integer, Integer> depths = new HashMap<>();
        Map<Integer, Integer> kCoresSizes = new HashMap<>();
        if (args.length == 2) {
            g = GraphGenerator.generateGraph(args[0], args[1]);

        }
        else {

            //String file_name = "graphtest";String delimiter = " ";
            String file_name = "ressources/graph/SNAP/facebook/facebook_combined.txt/facebook_combined.txt";String delimiter = " ";
            //String file_name = "ressources/graph/SNAP/com-LiveJournal.txt";String delimiter = " ";
            //String file_name = "ressources/graph/SNAP/roadNet-PA.txt/roadNet-PA.txt";String delimiter = "\t";
            //String file_name = "ressources/graph/SNAP/roadNet-CA.txt";String delimiter = "\t";
            //String file_name = "ressources/graph/SNAP/com-friendster.ungraph.txt";String delimiter = "\t";

            g = GraphGenerator.generateGraph(file_name, delimiter);
        }
        long start1 = System.nanoTime();
        depths = getDepths(g);
        //int vdepth = getVertexDepth(g,5);
        //kCoresSizes = getKCoreSizes(g);

        long end1 = System.nanoTime();

        System.out.println("Elapsed Time in nano seconds: " + (end1 - start1));

        System.out.println("depths: " + depths);
        //System.out.println("Vertex depth: " + vdepth);
        //System.out.println(kCoresSizes);

    }
}
