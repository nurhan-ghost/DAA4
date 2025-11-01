package graph.topo;

import graph.model.DirectedGraph;
import graph.metrics.Metrics;

import java.util.*;
public class TopologicalOrder {
    private final DirectedGraph g;
    private final Metrics m;

    public TopologicalOrder(DirectedGraph g, Metrics m) {
        this.g = g; this.m = m;
    }

    public List<Integer> kahn() {
        int n = g.size();
        int[] indeg = new int[n];
        for (int u=0; u<n; u++){
            for (DirectedGraph.Edge e : g.adj().get(u)) indeg[e.to]++;
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int i=0;i<n;i++) if (indeg[i]==0) { q.add(i); m.kahnPushes++; }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            m.kahnPops++;
            order.add(u);
            for (DirectedGraph.Edge e : g.adj().get(u)) {
                indeg[e.to]--;
                if (indeg[e.to]==0) { q.add(e.to); m.kahnPushes++; }
            }
        }
        if (order.size() != n) {
            return Collections.emptyList();
        }
        return order;
    }
}
