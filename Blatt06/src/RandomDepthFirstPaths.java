import java.util.*;

public class RandomDepthFirstPaths {
    private boolean[] marked; // marked[v] = is there an s-v path?
    private int[] edgeTo; // edgeTo[v] = last edge on s-v path
    private final int s; // source vertex
    private Queue<Integer> preorder; // vertices in preorder
    private Queue<Integer> postorder; // vertices in postorder
    private int[] distTo; // distTo[v] = number of edges s-v path


    /**
     * Computes a path between {@code s} and every other vertex in graph {@code G}.
     *
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public RandomDepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
    }

    public void randomDFS(Graph G) {
        randomDFS(G, s);
    }

    // depth first search from v
    private void randomDFS(Graph G, int v) {
        // TODO
        marked[v] = true;
        preorder.add(v);

        List<Integer> neighbors = new ArrayList<>(G.adj(v));
        Collections.shuffle(neighbors); // zufällige Reihenfolge!

        for (int w : neighbors) {
            if (!marked[w]) {
                edgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
                randomDFS(G, w);
            }
        }

        postorder.add(v);
    }


    public void randomNonrecursiveDFS(Graph G) {
        // TODO
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        preorder = new LinkedList<>();
        postorder = new LinkedList<>();

        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++) {
            List<Integer> shuffled = new ArrayList<>(G.adj(v));
            Collections.shuffle(shuffled);
            adj[v] = shuffled.iterator();
        }

        Stack<Integer> stack = new Stack<>();
        marked[s] = true;
        distTo[s] = 0;
        preorder.add(s);
        stack.push(s);

        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    preorder.add(w);
                    stack.push(w);
                }
            } else {
                postorder.add(v);
                stack.pop();
            }
        }
    }
    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * 
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a path between the vertex {@code v} and the source vertex {@code s},
     * or
     * {@code null} if no such path.
     * 
     * @param v the vertex
     * @return the sequence of vertices on a path between the vertex
     *         {@code v} and the source vertex {@code s}, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * 
     */
    public List<Integer> pathTo(int v) {
        // TODO
        // Prüft, ob der Knoten v überhaupt gültig ist (Index im erlaubten Bereich)
        validateVertex(v);
        // Wenn v nicht vom Startknoten s aus erreicht wurde, gibt es keinen Pfad → return null
        if (!marked[v]) return null;
        // Erstellt eine neue leere Liste, um den Pfad aufzubauen
        LinkedList<Integer> path = new LinkedList<>();
        // Dabei wird jeder Knoten am Ende der Liste eingefügt → ergibt falsche Reihenfolge!
        for (int x = v; x != s; x = edgeTo[x]) {
            path.addLast(x);  // fügt x hinten an – Pfad ist dann rückwärts
        }
        // Startknoten s auch noch hinten einfügen
        path.addLast(s);
        // Gibt die Pfadliste zurück (aber in umgekehrter Reihenfolge!)
        return path;
    }

    public int[] edge() {
        return edgeTo;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}
