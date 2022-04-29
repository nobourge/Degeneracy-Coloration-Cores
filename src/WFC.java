

import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.Graph;


public class WFC {

    static int chromaticNBR = -1;
    static int M=0;
    static Map<Integer, Integer> coloration = new LinkedHashMap<>();
    static LinkedList<Integer>[] entropy = new LinkedList[]{};
    static List<Integer> uncolored = new ArrayList<>();
    static Graph G;

    public static void main(String[] args) {
        if (args.length == 2) {
            System.out.println("Filename entered: "+args[0]+" with delimiter "+args[1]);
            G = GraphGenerator.generateGraph(args[0], args[1]);
        }
        else {
            System.out.println("No argument entered, proceeding with default graph ressources/graph/1v.txt");
            //String filename = "ressources/graph/SNAP/roadNet-PA.txt/roadNet-PA.txt";
            //String filename = "ressources/graph/SNAP/roadNet-CA.txt";
            //String filename = "ressources/graph/SNAP/com-lj.ungraph.txt";
            //String filename = "ressources/graph/graphtest";
            String filename = "ressources/graph/1v.txt";
            //String filename = "ressources/graph/2v1e.txt";
            //String filename = "Email-EuAll.txt";
            //String delimiter = "\t";
            //String filename = "idk.csv";
            String delimiter = " ";
            //String filename = "src/musae_FR_edges.csv";
            //String delimiter = ",";
            G = GraphGenerator.generateGraph(filename, delimiter);
            //G = new Graph(1);
            //System.out.println(G);
        }
        System.out.println("solving...");
        //long start2 = System.currentTimeMillis();
        long start2 = System.nanoTime();
        solve();
        //long end2 = System.currentTimeMillis();
        long end2 = System.nanoTime();
        System.out.println("Elapsed Time in nano-seconds: "+ (end2-start2));
    }

    public static void resetAlgo() {
        coloration = new LinkedHashMap<>();
        uncolored = new ArrayList<>();
    }

    // initialize the algo with starting values
    public static void initAlgo() {
        entropy = new LinkedList[G.V()];
        int origin=0; int degree;
        for (int i=0; i<G.V(); i++) {
            degree = G.degree(i);
            if (degree > M) {                   // init the starting domain of colors
                M = degree;
                origin = i;                     // select the first vertex
            }
            uncolored.add(i);
            entropy[i] = new LinkedList<>();
        }
        colorize(origin, collapse(origin));     // colorize the first vertex
        propagate(origin);
    }

    // algo main loop
    public static int solve() {
        while(chromaticNBR == -1) {
            initAlgo();
            int newOrigin;
            while (!uncolored.isEmpty()) {
                newOrigin = observe();      // select next vertex to colorize
                if (newOrigin == -1) {      // restart algo with one more color
                    chromaticNBR = -1;
                    resetAlgo();
                    M += 1;
                    System.out.println("Restart with M = "+M);
                    break;
                }
                colorize(newOrigin, collapse(newOrigin));
                propagate(newOrigin);
                if (uncolored.size() % 10000 == 0) { System.out.println(uncolored.size()+" left. color used : "+chromaticNBR+1); }
            }
        }
        System.out.println(coloration);
        System.out.println(chromaticNBR+1);
        resetAlgo();
        return chromaticNBR+1;
    }

    // mark a vertex as colored
    public static void colorize(int v, int color) {
        coloration.put(v, color);
        uncolored.remove(Integer.valueOf(v));
        chromaticNBR = Math.max(chromaticNBR, color);
    }

    // finds next vertex to color
    public static int observe() {
        int lowestEntropy = -1; int lowestVertex = -1; int curr_entropy;
        for (int vertex: uncolored) {
            curr_entropy = entropy(vertex);
            if (curr_entropy > lowestEntropy) {         // find vertex with less color possibilities
                lowestEntropy = curr_entropy;
                lowestVertex = vertex;
            }
        }
        if (lowestEntropy == M) { lowestVertex = -1; }  // impossible situation
        return lowestVertex;
    }

    // propagate color info to neighbour vertices
    public static void propagate(int v) {
        Stack<Integer> notPropagated = new Stack<>();
        notPropagated.push(v);

        int propagationOrigin; int colorOrigin;
        while (!notPropagated.isEmpty()) {
            propagationOrigin = notPropagated.pop();
            colorOrigin = color(propagationOrigin);
            for (int neigh: G.adj(propagationOrigin)) {     // reduce color possibilities of neighbours
                if (!coloration.containsKey(neigh)) {
                    if (entropy(neigh) < M-1) { entropy[neigh].add(colorOrigin); }
                    if (entropy(neigh) == M-1) {            // only 1 possibility -> can be colored
                        colorize(neigh, collapse(neigh));
                        notPropagated.push(neigh);          // propagate new colorization
                    }
                }
        }   }
    }

    public static int entropy(int v) { return entropy[v].size(); }

    public static int color(int v) { return coloration.get(v); }

    public static int collapse(int v) {
        for (int i=0; i<M; i++) { if (!entropy[v].contains(i)) { return i; } }
        return 0;
    }
}
