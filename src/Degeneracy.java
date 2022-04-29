import edu.princeton.cs.algs4.Graph;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/*Dans ce qui suit, on consid´erera des graphes non-dirig´es, simples et sans boucle. Un
sous-graphe d’un tel graphe G est un graphe obtenu en supprimant certains sommets et
certaines arˆetes de G. Un graphe G est dit k-d´eg´en´er´e si pour tout sous-graphe de G, y
compris G lui-mˆeme, il existe un sommet de degr´e au plus k. La d´eg´en´erescence d’un
graphe est le plus petit nombre k tel que le graphe est k-d´eg´en´er´e. Un exemple de graphe
3-d´eg´en´er´e est donn´e en Figure 1.

 */
//given an object of class Graph, return the degeneracy of the graph
public class Degeneracy {

    public static int getDegeneracy(Graph g){
        int degeneracy = 0;
        Map<Integer, Integer> depths = new HashMap<>();
        int maxDegree = g.V() - 1;

        TreeSet[] degreeIndexedPriorityQueue = (TreeSet[]) Array.newInstance(TreeSet.class, maxDegree + 1);

        for (int i = 0; i < degreeIndexedPriorityQueue.length; i++) {
            degreeIndexedPriorityQueue[i] = new TreeSet<>();
        }
        System.out.println("degreeIndexedPriorityQueue.length: " + degreeIndexedPriorityQueue.length);
        System.out.println(" initialized with TreeSets");
        int minDegree = g.V();
        Map<Integer, Integer> degreesMap = new HashMap<>();
        for (int v = 0 ; v < g.V() ; v++) {
            int d = g.degree(v);
            degreeIndexedPriorityQueue[d].add(v);
            degreesMap.put(v, d);
            minDegree = Math.min(minDegree, d);
        }
        /*
         * Extract from degreeIndexedPriorityQueue
         */
        while (minDegree < g.V()) {
            /// minimum degree is smaller than the number of vertices
            TreeSet<Integer> minDegreeKey = degreeIndexedPriorityQueue[minDegree];
            if (minDegreeKey.isEmpty()) {
                //System.out.println("minDegreeKey of degree " + minDegree + " is empty");
                minDegree++;
                continue;
            }
            int vertex_to_remove = minDegreeKey.first();
            /// remove the vertex from the TreeSet O(log(n))
            minDegreeKey.remove(vertex_to_remove);
            /// put the min degree at hashset vertex_to_remove index  O(1)
            depths.put(vertex_to_remove, minDegree);
            degeneracy = Math.max(degeneracy, minDegree);

            for (int u : g.adj(vertex_to_remove)) {
                int uDegree = degreesMap.get(u);
                /// verify if the minDegree is smaller than the degree of u O(1) and if u is not already in the TreeSet O(log(n))
                if (minDegree < uDegree && !depths.containsKey(u)) { // O(1)
                    /// remove the vertex from the TreeSet O(log(n))
                    degreeIndexedPriorityQueue[uDegree].remove(u);
                    uDegree--;
                    //// update u Degree in degreesMap O(1)
                    degreesMap.put(u, uDegree);
                    /// update u position in degreeIndexedPriorityQueue O(log(n))
                    degreeIndexedPriorityQueue[uDegree].add(u);

                    ///update minDegree if necessary O(1)
                    minDegree = Math.min(minDegree, uDegree);
                }
            }
        }
        return degeneracy; // O(V+E)
    }

    public static void main(String[] args) {
        Graph g;
        if (args.length == 2) {
            g = GraphGenerator.generateGraph(args[0], args[1]);
        }
        else {

            //String file_name = "ressources/graph/1v.txt";String delimiter = " ";
            //String file_name = "ressources/graph/2v1e.txt";String delimiter = " ";
            //String file_name = "ressources/graph/graphtest";String delimiter = " ";

            //String file_name = "ressources/graph/SNAP/facebook/facebook_combined.txt/facebook_combined.txt";String delimiter = " ";
            //String file_name = "ressources/graph/SNAP/roadNet-PA.txt/roadNet-PA.txt";String delimiter = "\t";
            //String file_name = "ressources/graph/SNAP/roadNet-CA.txt";String delimiter = "\t";
            //String file_name = "ressources/graph/SNAP/com-LiveJournal.txt";String delimiter = " ";
            String file_name = "ressources/graph/SNAP/com-friendster.ungraph.txt";String delimiter = "";

            g = GraphGenerator.generateGraph(file_name, delimiter);
        }
        long start2 = System.currentTimeMillis();
        long start1 = System.nanoTime();
        int degeneracy = getDegeneracy(g);
        long end1 = System.nanoTime();
        long end2 = System.currentTimeMillis();
        System.out.println("Elapsed Time in nano seconds: " + (end1 - start1));
        System.out.println("Elapsed Time in milliseconds: " + (end2 - start2));

        System.out.println("Degeneracy of the graph is " + degeneracy);
    }
}

