package graph.dagsp;

import graph.model.DirectedGraph;
import graph.metrics.Metrics;
import graph.topo.TopologicalOrder;

import java.util.*;

public class DAGPaths {
    private final DirectedGraph g;
    private final Metrics m;
    public DAGPaths(DirectedGraph g, Metrics m) { this.g = g; this.m = m; }

    public Result shortestFrom(int src) {
        TopologicalOrder ts = new TopologicalOrder(g, m);
        List<Integer> topo = ts.kahn();
        if (topo.isEmpty()) throw new IllegalStateException("Graph is not a DAG (has cycle)");

        int n = g.size();
        double[] dist = new double[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(parent, -1);
        dist[src] = 0.0;

        boolean started = false;
        for (int u : topo) {
            if (dist[u] < Double.POSITIVE_INFINITY) {
                started = true;
                for (DirectedGraph.Edge e : g.adj().get(u)) {
                    m.relaxations++;
                    if (dist[u] + e.weight < dist[e.to]) {
                        dist[e.to] = dist[u] + e.weight;
                        parent[e.to] = u;
                    }
                }
            }
        }
        return new Result(dist, parent);
    }

    public Result longestFrom(int src) {
        TopologicalOrder ts = new TopologicalOrder(g, m);
        List<Integer> topo = ts.kahn();
        if (topo.isEmpty()) throw new IllegalStateException("Graph is not a DAG (has cycle)");

        int n = g.size();
        double[] dist = new double[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Double.NEGATIVE_INFINITY);
        Arrays.fill(parent, -1);
        dist[src] = 0.0;

        for (int u : topo) {
            if (dist[u] > Double.NEGATIVE_INFINITY) {
                for (DirectedGraph.Edge e : g.adj().get(u)) {
                    m.relaxations++;
                    if (dist[u] + e.weight > dist[e.to]) {
                        dist[e.to] = dist[u] + e.weight;
                        parent[e.to] = u;
                    }
                }
            }
        }
        return new Result(dist, parent);
    }

    public static class Result {
        public final double[] dist;
        public final int[] parent;

        public Result(double[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }

        public List<Integer> reconstruct(int to) {
            if (dist[to] == Double.POSITIVE_INFINITY || dist[to] == Double.NEGATIVE_INFINITY)
                return Collections.emptyList();

            List<Integer> path = new ArrayList<>();
            int cur = to;
            while (cur != -1) {
                path.add(cur);
                cur = parent[cur];
            }
            Collections.reverse(path);
            return path;
        }
    }
}
