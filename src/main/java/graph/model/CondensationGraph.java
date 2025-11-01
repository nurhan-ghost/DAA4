package graph.model;


import java.util.*;

public class CondensationGraph {

    public static DirectedGraph buildFrom(DirectedGraph graph, List<List<Integer>> sccs) {
        int n = sccs.size();
        DirectedGraph dag = new DirectedGraph(n);

        Map<Integer, Integer> vertexToScc = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++) {
            for (int v : sccs.get(i)) {
                vertexToScc.put(v, i);
            }
        }

        for (int u = 0; u < graph.n; u++) {
            for (DirectedGraph.Edge e : graph.adj.get(u)) {
                int fromComp = vertexToScc.get(u);
                int toComp = vertexToScc.get(e.to);
                if (fromComp != toComp) {
                    dag.addEdge(fromComp, toComp, e.weight);
                }
            }
        }

        return dag;
    }
}
