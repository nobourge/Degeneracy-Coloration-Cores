

import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.Graph;

public class WFC {

    static int chromaticNBR = -1;
    static int M=0;
    static LinkedList<Integer> L = new LinkedList<>();
    static Map<Integer, Integer> coloration = new LinkedHashMap<>();
    static LinkedList<Integer>[] entropy = new LinkedList[]{};
    static List<Integer> uncolored = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length == 2) {
            Graph G = GraphGenerator.generateGraph(args[0], args[1]);
            solve(G);
        }
        else {
            //String filename = "ressources/graph/SNAP/roadNet-PA.txt/roadNet-PA.txt";
            //String filename = "Email-EuAll.txt";
            //String delimiter = "\t";
            String filename = "idk.csv";
            String delimiter = " ";
            //String filename = "src/musae_FR_edges.csv";
            //String delimiter = ",";
            Graph G = GraphGenerator.generateGraph(filename, delimiter);
            System.out.println("solving...");
            long start2 = System.currentTimeMillis();
            solve(G);
            long end2 = System.currentTimeMillis();
            System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
        }
    }

    public static void resetAlgo() {
        M = 0;
        L.clear();
        coloration = new LinkedHashMap<>();
        uncolored = new ArrayList<>();
    }

    public static void initAlgo(Graph G) {
        entropy = new LinkedList[G.V()];
        int origin=0;
        for (int i=0; i<G.V(); i++) {
            int degree = G.degree(i);
            if (degree > M) {
                M = degree;
                origin = i;
            }
            uncolored.add(i);
        }
        for (int i=0; i<M; i++) { L.add(i); }
        for (int i=0; i<G.V(); i++) { entropy[i] = new LinkedList<>(); }

        colorize(origin, collapse(origin), true);
        propagate(G, origin);
    }

    public static int solve(Graph G) {
        while(chromaticNBR == -1) {
            initAlgo(G);
            while (!uncolored.isEmpty()) {
                int newOrigin = observe();
                if (newOrigin == -1) {
                    chromaticNBR = -1;
                    resetAlgo();
                    M += 1;
                    System.out.println("Restart with M = "+M);
                    break;
                }
                colorize(newOrigin, collapse(newOrigin), true);
                propagate(G, newOrigin);
                if (uncolored.size() % 1000 == 0) {
                    System.out.println(uncolored.size()+" left. color used : "+chromaticNBR); }
            }
        }
        //System.out.println(coloration);
        System.out.println(chromaticNBR);
        resetAlgo();
        return chromaticNBR;
    }

    public static void colorize(int v, int color, boolean overwrite) {
        coloration.put(v, color);
        uncolored.remove(Integer.valueOf(v));
        chromaticNBR = Math.max(chromaticNBR, color);

        //if (overwrite) { domain[v].removeIf(n -> (n != color)); }
    }

    public static int observe() {
        int lowestEntropy = -1; int lowestVertex = -1; int curr_entropy;
        for (int vertex: uncolored) {
            curr_entropy = entropy(vertex);
            if (curr_entropy > lowestEntropy) {
                lowestEntropy = curr_entropy;
                lowestVertex = vertex;
            }
        }
        if (lowestEntropy == M) { lowestVertex = -1; }
        return lowestVertex;
    }

    public static void propagate(Graph G, int v) {
        Stack<Integer> notPropagated = new Stack<>();
        notPropagated.add(v);
        int propagationOrigin;
        while (!notPropagated.isEmpty()) {
            propagationOrigin = notPropagated.pop();
            int colorOrigin = color(propagationOrigin);
            for (int neigh: G.adj(propagationOrigin)) {
                //if (entropy(neigh) > 1) { domain[neigh].remove(Integer.valueOf(color(propagationOrigin))); }
                if (!coloration.containsKey(neigh)) {
                    if (entropy(neigh) < M-1) {
                        entropy[neigh].add(colorOrigin); }
                    if (entropy(neigh) == M-1) {
                        colorize(neigh, collapse(neigh), false);
                        notPropagated.add(neigh);
                    }
                }
        }   }
    }

    public static int entropy(int v) { return entropy[v].size(); }

    public static int color(int v) { return coloration.get(v); }

    public static int collapse(int v) //{ return domain[v].get(0); }
    {
        for (int i=0; i<M; i++) {
            if (!entropy[v].contains(i)) {
                return i; }
        }
        return -1;
    }
}
