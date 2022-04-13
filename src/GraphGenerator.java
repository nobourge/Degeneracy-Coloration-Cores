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
                String[] tokens = line.split(" ");
                //g.addEdge(tokens[0], tokens[1]);
                g.addEdge(strToInt(tokens[1]), strToInt(tokens[0]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    private static int strToInt(String token) {
        return Integer.parseInt(token);
    }
    void main(String[] args) {
        Graph g = new Graph(10);
        //addEdges(g, "src/GraphGenerator.txt");
        addEdges(g, "ressources/graph/SNAP/facebook/facebook_combined.txt/facebook_combined.txt");
        System.out.println(g);
    }
}
