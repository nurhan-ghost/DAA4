
package graph.model;

import java.util.*;

public class DirectedGraph {
    public final int n;
    final List<List<Edge>> adj;

    public DirectedGraph(int n, List<List<Edge>> adj) {
        this.n = n;
        this.adj = adj;
    }

    public static class Edge {
        public final int to;
        public final double weight;
        public Edge(int to, double weight) { this.to = to; this.weight = weight; }
    }

    public DirectedGraph(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i=0;i<n;i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) { addEdge(u, v, 1.0); }
    public void addEdge(int u, int v, double w) { adj.get(u).add(new Edge(v,w)); }

    public List<List<Edge>> adj() { return adj; }

    public int size(){ return n; }

    public static DirectedGraph fromEdgeList(int n, List<List<Object>> edges) {
        DirectedGraph g = new DirectedGraph(n);
        for (List<Object> e : edges) {
            int u = ((Number)e.get(0)).intValue();
            int v = ((Number)e.get(1)).intValue();
            double w = e.size() > 2 ? ((Number)e.get(2)).doubleValue() : 1.0;
            g.addEdge(u, v, w);
        }
        return g;
    }
}