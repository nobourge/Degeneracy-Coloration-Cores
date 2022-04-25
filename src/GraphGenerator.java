import edu.princeton.cs.algs4.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphGenerator {
    //given a txt file containing edges, adds them to the graph
    public static void addEdges(Graph g, String filename) {
        try {
            Scanner in = new Scanner(new File(filename));
            while (in.hasNext()) {
                String line = in.nextLine();
                //String[] tokens = line.split(" ");
                String[] tokens = line.split("\t");
                //g.addEdge(tokens[0], tokens[1]);
                if (strToInt(tokens[0]) == -1) {
                    g.setV(strToInt(tokens[1]));
                }

                else if (strToInt(tokens[0]) == -2) {
                    int E = strToInt(tokens[1]);
                }

                else {
                    g.addEdge(strToInt(tokens[1]), strToInt(tokens[0]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    private static int strToInt(String token) {
        return Integer.parseInt(token);
    }

    //generates graph from file
    public static Graph generateGraph(String filename) {
        Graph g = new Graph(0);
        //addEdges(g, "src/GraphGenerator.txt");
        //addEdges(g, "ressources/graph/SNAP/facebook/facebook_combined.txt/facebook_combined.txt");
        //addEdges(g, "ressources/graph/SNAP/roadNet-PA.txt/roadNet-PA.txt");
        //addEdges(g, "graphtest");
        //System.out.println(g);

        //WFC.solve(g);

        addEdges(g, filename);
        return g;
    }

    //given a txt file containing edges, returns the graph
    public static Graph main(String[] args) {
        String filename = args[1];
        return generateGraph(filename);
    }
}
