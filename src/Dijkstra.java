/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: Dijkstra Class
 * Class Description: This is a class that is copied from our algorithms textbook with a few changes in order to implement a Dijkstra searching algorithm.
 * This is given an edge weighted graph and a starting value for the starting city. This class allows us to find the shortest path in the graph of cities
 * we give the object.
 *
 */

import java.util.Stack;
public class Dijkstra {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPriorityQueue<Double> pq;    // priority queue of vertices
    /**
     * Computes a shortest-paths tree from the source vertex <tt>s</tt> to every
     * other vertex in the edge-weighted graph <tt>G</tt>.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless 0 &le; <tt>s</tt> &le; <tt>V</tt> - 1
     */
    public Dijkstra(EdgeWeightedGraph G, int s) {
        for (Edge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        // relax vertices in order of distance from s
        pq = new IndexMinPriorityQueue<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v))
                relax(e, v);
        }
        // check optimality conditions
        assert check(G, s);
    }
    // relax edge e and update pq if changed
    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }
    /**
     * Returns the length of a shortest path between the source vertex <tt>s</tt> and
     * vertex <tt>v</tt>.
     *
     * @param  v the destination vertex
     * @return the length of a shortest path between the source vertex <tt>s</tt> and
     *         the vertex <tt>v</tt>; <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public double distTo(int v) {
        return distTo[v];
    }
    /**
     * Returns true if there is a path between the source vertex <tt>s</tt> and
     * vertex <tt>v</tt>.
     *
     * @param  v the destination vertex
     * @return <tt>true</tt> if there is a path between the source vertex
     *         <tt>s</tt> to vertex <tt>v</tt>; <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
    /**
     * Returns a shortest path between the source vertex <tt>s</tt> and vertex <tt>v</tt>.
     *
     * @param  v the destination vertex
     * @return a shortest path between the source vertex <tt>s</tt> and vertex <tt>v</tt>;
     *         <tt>null</tt> if no such path
     */
    public Iterable<Edge> pathTo(int v) {
        
    	if (!hasPathTo(v)){
        	return null;
        }
        
    	
    	Stack<Edge> path = new Stack<Edge>();
        
    	int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            //System.out.println(e.either());
            x = e.other(x);
        }
        
       
        return path;
    }
    // check optimality conditions:
    // (i) for all edges e = v-w:            distTo[w] <= distTo[v] + e.weight()
    // (ii) for all edge e = v-w on the SPT: distTo[w] == distTo[v] + e.weight()
    private boolean check(EdgeWeightedGraph G, int s) {
        // check that edge weights are nonnegative
        for (Edge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }
        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }
        // check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }
        // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            if (w != e.either() && w != e.other(e.either())) return false;
            int v = e.other(w);
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

}
