import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Bag;

//draws colored graph given a list of edges and a list of vertices


public class DrawGraph {
    /*

    public static void drawGraph(Graph G, List<Edge> edges, List<Vertex> vertices) {

        final int WIDTH = 800;
        final int HEIGHT = 800;
        final int N = 10;
        final int DELAY = 100;
        final int MARGIN = 20;
        final int RADIUS = 10;
        final int SIZE = WIDTH - 2 * MARGIN;
        final int SCALE = SIZE / N;
        final int X0 = MARGIN;
        final int Y0 = MARGIN;
        final int X1 = X0 + SIZE;
        final int Y1 = Y0 + SIZE;

        StdDraw.setCanvasSize(WIDTH, HEIGHT);
        StdDraw.setXscale(X0, X1);
        StdDraw.setYscale(Y0, Y1);
        StdDraw.setPenRadius(0.01);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        for (Edge e : edges) {
            Vertex v = e.either();
            Vertex w = e.other(v);
            double x0 = v.x();
            double y0 = v.y();
            double x1 = w.x();
            double y1 = w.y();
            StdDraw.line(x0, y0, x1, y1);


        }
        StdDraw.setPenColor(StdDraw.RED);
        for (Vertex v : vertices) {
            double x = v.x();
            double y = v.y();
            StdDraw.filledCircle(x, y, 0.05);

        }
        StdDraw.show();


    }


    //main method
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        List<Edge> edges = G.edges();
        List<Vertex> vertices = G.vertices();
        drawGraph(G, edges, vertices);
    }
    */


    //draws graph given a adjacency matrix
    public static void drawGraph(int[][] adj) {
        int N = adj.length;
        //int screenWidth =
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        StdDraw.setPenRadius(0.01);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        double radius = Math.min(1.0, Math.sqrt(N) / 2.0);
        double theta = 2 * Math.PI / N;

        for (int i = 0; i < N; i++) {
            double ix = radius * Math.cos(i * theta);
            double iy = radius * Math.sin(i * theta);
            for (int j = 0; j < N; j++) {
                double jx = radius * Math.cos(i * theta);
                double jy = radius * Math.sin(i * theta);
                if (adj[i][j] == 1) {
                    StdDraw.line(ix, iy, jx, jy);
                }
            }
        }
        StdDraw.show();
    }

    public static void main(String[] args) {
        int[][] adj = new int[][]{
                {0, 1, 0, 1, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 0, 1},
                {1, 1, 0, 0, 1},
                {0, 0, 1, 1, 0}
        };
        drawGraph(adj);

        /*
        In in = new In(args[0]);
        if (in.hasNextLine()) {
            int[][] adj = new int[][]{
                    {0, 1, 0, 1, 0},
                    {1, 0, 1, 1, 0},
                    {0, 1, 0, 0, 1},
                    {1, 1, 0, 0, 1},
                    {0, 0, 1, 1, 0}
            };
            drawGraph(adj);
        }
        */
        /*
        else {
            int N = in.readInt();
            int[][] adj = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    adj[i][j] = in.readInt();
                }
            }
            drawGraph(adj);
        }
        */
    }
}