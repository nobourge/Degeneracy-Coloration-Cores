

import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

public class WFC {

    static int chromaticNBR = -1;
    static int M=0;
    static Map<Integer, Integer> coloration = new LinkedHashMap<>();
    static LinkedList<Integer>[] entropy = new LinkedList[]{};
    static List<Integer> uncolored = new ArrayList<>();
    static Graph G;

    public static void main(String[] args) {
        if (args.length == 2) {
            G = GraphGenerator.generateGraph(args[0], args[1]);
            solve();
        }
        else {
            //String filename = "ressources/graph/SNAP/roadNet-PA.txt/roadNet-PA.txt";
            //String filename = "Email-EuAll.txt";
            //String delimiter = "\t";
            String filename = "idk.csv";
            String delimiter = " ";
            //String filename = "src/musae_FR_edges.csv";
            //String delimiter = ",";
            G = GraphGenerator.generateGraph(filename, delimiter);
            System.out.println("solving...");
            long start2 = System.currentTimeMillis();
            solve();
            long end2 = System.currentTimeMillis();
            System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
        }
    }

    public static void resetAlgo() {
        coloration = new LinkedHashMap<>();
        uncolored = new ArrayList<>();
    }

    public static void initAlgo() {
        entropy = new LinkedList[G.V()];
        int origin=0; int degree;
        for (int i=0; i<G.V(); i++) { //O(V)
            degree = G.degree(i); //O(1)
            if (degree > M) {
                M = degree;
                origin = i;
            }
            uncolored.add(i); //O(1)
            entropy[i] = new LinkedList<>();
        }
        colorize(origin, collapse(origin)); //O(V)
        propagate(origin); //O(d*V)?
    }

    public static int solve() { //O(d*V*V^2)
        while(chromaticNBR == -1) {
            initAlgo();
            int newOrigin;
            while (!uncolored.isEmpty()) { //O(V)
                newOrigin = observe(); //O(V)
                if (newOrigin == -1) {
                    chromaticNBR = -1;
                    resetAlgo();
                    M += 1;
                    System.out.println("Restart with M = "+M);
                    break;
                }
                colorize(newOrigin, collapse(newOrigin));
                propagate(newOrigin); //O(d*V) ?
                if (uncolored.size() % 1000 == 0) { System.out.println(uncolored.size()+" left. color used : "+chromaticNBR); }
            }
        }
        //System.out.println(coloration);
        System.out.println(chromaticNBR);
        resetAlgo();
        return chromaticNBR;
    }

    public static void colorize(int v, int color) {
        coloration.put(v, color); //O(1)
        uncolored.remove(Integer.valueOf(v)); //O(V)
        chromaticNBR = Math.max(chromaticNBR, color);
    }

    public static int observe() {
        int lowestEntropy = -1; int lowestVertex = -1; int curr_entropy;
        for (int vertex: uncolored) { //O(V)
            curr_entropy = entropy(vertex);
            if (curr_entropy > lowestEntropy) {
                lowestEntropy = curr_entropy;
                lowestVertex = vertex;
            }
        }
        if (lowestEntropy == M) { lowestVertex = -1; }
        return lowestVertex;
    }

    public static void propagate(int v) { //O(d*V)
        Stack<Integer> notPropagated = new Stack<>();
        notPropagated.push(v); //O(1)

        int propagationOrigin; int colorOrigin;
        while (!notPropagated.isEmpty()) {
            propagationOrigin = notPropagated.pop(); //O(1)
            colorOrigin = color(propagationOrigin);
            for (int neigh: G.adj(propagationOrigin)) { //O(d)
                if (!coloration.containsKey(neigh)) { //O(V)
                    if (entropy(neigh) < M-1) { entropy[neigh].add(colorOrigin); }
                    if (entropy(neigh) == M-1) {
                        colorize(neigh, collapse(neigh));
                        notPropagated.push(neigh);
                    }
                }
        }   }
    }

    public static int entropy(int v) { return entropy[v].size(); } //O(1)

    public static int color(int v) { return coloration.get(v); } //O(1)

    public static int collapse(int v) {
        for (int i=0; i<M; i++) { if (!entropy[v].contains(i)) { return i; } } //O(D^2)
        return -1;
    }
}
