import edu.princeton.cs.algs4.Graph;

public class Degeneracy {
    //given a graph, find the degeneracy of the graph
    public static int degeneracy(Graph g) {
        int degeneracy = 0;
        for (int v = 0; v < g.V(); v++) {
            if (g.degree(v) == 0) {
                degeneracy++;
            }
        }
        return degeneracy;


    }

    //given a graph, find the number of vertices in the largest clique
    public static int largestClique(Graph g) {
        int largestClique = 0;
        for (int v = 0; v < g.V(); v++) {
            if (g.degree(v) == 0) {
                largestClique++;
            }
        }
        return largestClique;


    }

}
