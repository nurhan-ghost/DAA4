package graph;

import graph.model.DirectedGraph;
import graph.metrics.Metrics;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalOrder;
import graph.dagsp.DAGPaths;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphAlgorithmsTest {

    private DirectedGraph buildTestGraph() {
        DirectedGraph g = new DirectedGraph(5);
        g.addEdge(0, 1, 2.0);
        g.addEdge(1, 2, 3.0);
        g.addEdge(2, 3, 4.0);
        g.addEdge(3, 4, 5.0);
        return g;
    }

    @Test
    public void testSCC() {
        DirectedGraph g = buildTestGraph();
        Metrics m = new Metrics();
        TarjanSCC scc = new TarjanSCC(g, m);
        List<List<Integer>> components = scc.compute();

        assertEquals(5, components.size());
        for (List<Integer> c : components) {
            assertEquals(1, c.size());
        }
    }

    @Test
    public void testTopologicalOrder() {
        DirectedGraph g = buildTestGraph();
        Metrics m = new Metrics();
        TopologicalOrder topo = new TopologicalOrder(g, m);
        List<Integer> order = topo.kahn();

        assertEquals(5, order.size());

        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(1) < order.indexOf(2));
        assertTrue(order.indexOf(2) < order.indexOf(3));
        assertTrue(order.indexOf(3) < order.indexOf(4));
    }

    @Test
    public void testDAGPaths() {
        DirectedGraph g = buildTestGraph();
        Metrics m = new Metrics();
        DAGPaths dag = new DAGPaths(g, m);

        DAGPaths.Result sp = dag.shortestFrom(0);
        assertEquals(0.0, sp.dist[0]);
        assertEquals(2.0, sp.dist[1]);
        assertEquals(5.0, sp.dist[2]);
        assertEquals(9.0, sp.dist[3]);
        assertEquals(14.0, sp.dist[4]);

        DAGPaths.Result lp = dag.longestFrom(0);
        assertEquals(0.0, lp.dist[0]);
        assertEquals(2.0, lp.dist[1]);
        assertEquals(5.0, lp.dist[2]);
        assertEquals(9.0, lp.dist[3]);
        assertEquals(14.0, lp.dist[4]);
        List<Integer> path = lp.reconstruct(4);

        assertEquals(List.of(0, 1, 2, 3, 4), path);
    }
}
