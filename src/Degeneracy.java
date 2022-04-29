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
            /// remove the vertex from the set O(log(n))
            minDegreeKey.remove(vertex_to_remove);
            /// remove the vertex from the hashset O(1)
            depths.put(vertex_to_remove, minDegree);
            degeneracy = Math.max(degeneracy, minDegree);

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
        return degeneracy;
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

