import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
/**
 * Class that represents a maze with N*N junctions.
 * 
 * @author Vera Röhr
 */
public class Maze {
    private final int N;
    private Graph M;    //Maze
    public int startnode;

    public Maze(int N, int startnode) {

        if (N < 0) throw new IllegalArgumentException("Number of vertices in a row must be nonnegative");
        this.N = N;
        this.M = new Graph(N * N);
        this.startnode = startnode;
        buildMaze();
    }

    public Maze(In in) {
        this.M = new Graph(in);
        this.N = (int) Math.sqrt(M.V());
        this.startnode = 0;
    }


    /**
     * Adds the undirected edge v-w to the graph M.
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        // TODO
        if (v == w) return; // keine reflexiven Kanten
        if (!hasEdge(v, w)) {
            M.addEdge(v, w); // undirected → M ist symmetrisch
        }
    }

    
    /**
     * Returns true if there is an edge between 'v' and 'w'
     * @param v one vertex
     * @param w another vertex
     * @return true or false
     */
    public boolean hasEdge( int v, int w){
		// TODO
        if (v == w) return true; // reflexive Kante gilt als vorhanden
        return M.adj(v).contains(w);
    }


    
    /**
     * Builds a grid as a graph.
     * @return Graph G -- Basic grid on which the Maze is built
     */
    public Graph mazegrid() {
        // TODO
        Graph g = new Graph(M.V()); // Anzahl der Knoten
        for (int i = 0; i < M.V(); i++) {
            // Für jeden Knoten:
            // Kante nach rechts (außer letzte Spalte)
            if (i % N != N - 1) {
                g.addEdge(i, i + 1);
            }
            // Kante nach unten (außer letzte Zeile)
            if (i + N < M.V()) {
                g.addEdge(i, i + N);
            }
        }
        return g;
    }
    
    /**
     * Builds a random maze as a graph.
     * The maze is build with a randomized DFS as the Graph M.
     */
    private void buildMaze() {
		// TODO
    }

    /**
     * Find a path from node v to w
     * @param v start node
     * @param w end node
     * @return List<Integer> -- a list of nodes on the path from v to w (both included) in the right order.
     */
    public List<Integer> findWay(int v, int w){
		// TODO
        DepthFirstPaths dfs = new DepthFirstPaths(M, v); // DFS im Maze
        return dfs.pathTo(w); // gibt null zurück, falls kein Pfad existiert
    }
    
    /**
     * @return Graph M
     */
    public Graph M() {
    	return M;
    }

    public static void main(String[] args) {
		// FOR TESTING
    }


}

