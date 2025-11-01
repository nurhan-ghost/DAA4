
package graph.scc;

import graph.model.DirectedGraph;
import graph.metrics.Metrics;

import java.util.*;

public class TarjanSCC {
    private final DirectedGraph g;
    private final Metrics metrics;

    public TarjanSCC(DirectedGraph g, Metrics m) { this.g = g; this.metrics = m; }

    public List<List<Integer>> run() {
        int n = g.size();
        int[] ids = new int[n];
        Arrays.fill(ids, -1);
        int[] low = new int[n];
        boolean[] onStack = new boolean[n];
        Deque<Integer> st = new ArrayDeque<>();
        List<List<Integer>> comps = new ArrayList<>();
        int id = 0;

        for (int i=0;i<n;i++){
            if (ids[i] == -1) dfs(i);
        }
        return result;

    }

    private int idCounter = 0;
    private int[] ids;
    private int[] low;
    private boolean[] onStack;
    private Deque<Integer> stack;
    private List<List<Integer>> result;
    private DirectedGraph graph;
    private Metrics m;

    public List<List<Integer>> compute() {
        this.graph = g;
        this.m = metrics;
        int n = graph.size();
        ids = new int[n]; Arrays.fill(ids, -1);
        low = new int[n];
        onStack = new boolean[n];
        stack = new ArrayDeque<>();
        result = new ArrayList<>();
        idCounter = 0;

        for (int i=0;i<n;i++){
            if (ids[i] == -1) {
                dfs(i);
            }
        }
        return result;
    }

    private void dfs(int at){
        ids[at] = idCounter;
        low[at] = idCounter;
        idCounter++;
        stack.push(at);
        onStack[at] = true;
        m.dfsVisits++;

        for (DirectedGraph.Edge e : graph.adj().get(at)) {
            m.dfsEdges++;
            int to = e.to;
            if (ids[to] == -1) {
                dfs(to);
                low[at] = Math.min(low[at], low[to]);
            } else if (onStack[to]) {
                low[at] = Math.min(low[at], ids[to]);
            }
        }

        if (low[at] == ids[at]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int node = stack.pop();
                onStack[node] = false;
                comp.add(node);
                if (node == at) break;
            }
            result.add(comp);
        }
    }
}