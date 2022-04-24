

import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.Graph;

public class WFC {

    static int chromaticNBR = 0;
    static int M=0;
    static LinkedList<Integer> L;
    static Map<Integer, Integer> coloration = new LinkedHashMap<>();
    static List<Integer>[] domain = null;
    static List<Integer> uncolored = new ArrayList<>();

    public static void main(String[] args) {

    }

    public static void resetAlgo() {
        M = 0;
        L.clear();
        coloration = new LinkedHashMap<>();
        domain = null;
        uncolored = new ArrayList<>();
    }

    public static void initAlgo(Graph G) {
        for (int i=0; i<M; i++) { L.add(i); }

        int origin=0;
        for (int i=0; i<G.V(); i++) {
            int degree = G.degree(i);
            if (degree > M) {
                M = degree;
                origin = i;
            }
            domain[i] = L;
            uncolored.add(i);
        }
        colorize(origin, M);
        propagate(G, origin);
    }

    public static int solve(Graph G) {
        while(chromaticNBR != -1) {
            initAlgo(G);
            while (uncolored.size() > 0) {
                int newOrigin = observe();
                if (newOrigin == -1) {
                    chromaticNBR = -1;
                    resetAlgo();
                    break;
                }
                colorize(newOrigin, collapse(newOrigin));
                propagate(G, newOrigin);
            }
        }
        System.out.println(coloration);
        System.out.println(chromaticNBR);
        return chromaticNBR;
    }

    public static void colorize(int v, int color) {
        coloration.put(v, color);
        uncolored.remove(Integer.valueOf(v));
        chromaticNBR = Math.max(chromaticNBR, color);
    }

    public static int observe() {
        int lowestEntropy = M;
        int lowestVertex = 0;
        int curr_entropy;
        for (int vertex: uncolored) {
            curr_entropy = entropy(vertex);
            if (curr_entropy < lowestEntropy) {
                lowestEntropy = curr_entropy;
                lowestVertex = vertex;
            }
        }
        if (lowestEntropy == 0) {
            M += 1;
            lowestVertex = -1;
        }
        return lowestVertex;
    }

    public static void propagate(Graph G, int v) {
        Stack<Integer> notPropagated = new Stack<>();
        notPropagated.add(v);
        int propagationOrigin;
        while (!notPropagated.isEmpty()) {
            propagationOrigin = notPropagated.pop();
            for (int neigh: G.adj(propagationOrigin)) {
                if (entropy(neigh) > 1) { domain[neigh].remove(Integer.valueOf(color(propagationOrigin))); }
                if (entropy(neigh) == 1) {
                    colorize(neigh, collapse(neigh));
                    notPropagated.add(neigh);
                }
        }   }
    }

    public static int entropy(int v) { return domain[v].size(); }

    public static int color(int v) { return coloration.get(v); }

    public static int collapse(int v) { return domain[v].get(0); }
}
