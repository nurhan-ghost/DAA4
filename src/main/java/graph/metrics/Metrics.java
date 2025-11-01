
package graph.metrics;

public class Metrics {
    public long dfsVisits = 0;
    public long dfsEdges = 0;
    public long kahnPops = 0;
    public long kahnPushes = 0;
    public long relaxations = 0;
    public long nanoTime = 0;

    public String toString(){
        double nano = nanoTime;
        return String.format(
                "dfsVisits=%d, dfsEdges=%d, kahnPops=%d, kahnPushes=%d, relaxations=%d, time_ms=%.3f",
                dfsVisits, dfsEdges, kahnPops, kahnPushes, relaxations, nano
        );
    }

}

