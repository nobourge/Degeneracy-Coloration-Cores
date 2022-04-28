import edu.princeton.cs.algs4.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphGenerator {
    //given a txt file containing edges, adds them to the graph
    public static void addEdges(Graph g, String filename, String delimiter) {
        try {
            Scanner in = new Scanner(new File(filename));
            //for (int i = 0; i < 2; i++) {
            for (int i = 0; i < 4; i++) {
                String line = in.nextLine();
                String[] tokens = line.split(delimiter);
                if (strToInt(tokens[0]) == -1) {
                    g.setV(strToInt(tokens[1]));
                }
                else if (strToInt(tokens[0]) == -2) {
                    int E = strToInt(tokens[1]);
                }
                //if token[1] is "Nodes:"
                else if (tokens[1].equals("Nodes:")) {
                    g.setV(strToInt(tokens[2]));
                }
                else if (tokens[0].equals("#")) {
                    continue;
                }
                else {
                    g.addEdge(strToInt(tokens[1]), strToInt(tokens[0]));
                }
            }
            while (in.hasNext()) {

                String line = in.nextLine();
                String[] tokens = line.split(delimiter);
                g.addEdge(strToInt(tokens[1]), strToInt(tokens[0]));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    //gets the number of vertices in the file
    public static int getV(String filename, String delimiter) {
        try {
            int V=0;
            Scanner in = new Scanner(new File(filename));
            while (in.hasNext()) {

                String line = in.nextLine();
                String[] tokens = line.split(delimiter);
                if (strToInt(tokens[0]) == -1) {
                    continue;
                }
                else if (strToInt(tokens[0]) == -2) {
                    continue;
                }
                else if (V < strToInt(tokens[0])) {
                    V = strToInt(tokens[0]);
                    //System.out.println("Graph Vertices quantity : " + V);

                }
                else if (V < strToInt(tokens[1])) {
                    V = strToInt(tokens[1]);
                    //System.out.println("Graph Vertices quantity : " + V);

                }
            }

            //System.out.println("Graph Vertices quantity : " + V);

            return V+1;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return 0;
    }

    private static int strToInt(String token) {
        return Integer.parseInt(token);
    }

    //generates graph from file
    public static Graph generateGraph(String filename, String delimiter) {
        System.out.println("File name: " + filename);
        System.out.println("Graph Vertices quantity : " + GraphGenerator.getV(filename, delimiter));
        Graph g = new Graph(0);
        addEdges(g, filename, delimiter);
        System.out.println("Graph created");

        return g;
    }

    //given a txt file containing edges, returns the graph
    public static Graph main(String[] args) {
        String filename = args[1];
        String delimiter = args[2];
        return generateGraph(filename, delimiter);
    }
}
