//display a graph given a list of vertices and edges
public class UI {

    public static <Graph> void main(String[] args) {

        Graph graph = new Graph();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        graph.addVertex("H");
        graph.addVertex("I");
        graph.addVertex("J");
        graph.addVertex("K");
        graph.addVertex("L");
        graph.addVertex("M");
        graph.addVertex("N");
        graph.addVertex("O");
        graph.addVertex("P");
        graph.addVertex("Q");
        graph.addVertex("R");
        graph.addVertex("S");
        graph.addVertex("T");
        graph.addVertex("U");
        graph.addVertex("V");
        graph.addVertex("W");
        graph.addVertex("X");
        graph.addVertex("Y");
        graph.addVertex("Z");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("A", "D");
        graph.addEdge("B", "E");
        graph.addEdge("B", "F");
        graph.addEdge("B", "G");
        graph.addEdge("C", "H");
        graph.addEdge("C", "I");
        graph.addEdge("C", "J");
        graph.addEdge("D", "K");
        graph.addEdge("D", "L");
        graph.addEdge("D", "M");
        graph.addEdge("E", "N");
        graph.addEdge("E", "O");
}

    public static void displayGraph(Graph graph) {
        System.out.println("Graph: ");
        for (Vertex v : graph.getVertices()) {
            System.out.println("Vertex: " + v.getLabel());
            for (Edge e : v.getEdges()) {
                System.out.println("Edge: " + e.getLabel());
            }
        }
    }
}   //end of class


