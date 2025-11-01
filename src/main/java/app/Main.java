package app;

import graph.io.DatasetLoader;
import graph.model.*;
import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;
import graph.metrics.Metrics;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        File folder = new File("data/");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            System.out.println("No datasets found in /data/");
            return;
        }

        CSVWriter csv = new CSVWriter("results.csv");
        csv.writeHeader();

        for (File f : files) {
            try {
                DatasetLoader.Loaded data = DatasetLoader.load(f.getPath());
                DirectedGraph g = data.graph;
                Metrics m = new Metrics();

                long startTime = System.nanoTime();

                TarjanSCC scc = new TarjanSCC(g, m);
                List<List<Integer>> comps = scc.compute();

                DirectedGraph dag = CondensationGraph.buildFrom(g, comps);

                TopologicalOrder topo = new TopologicalOrder(dag, m);
                List<Integer> order = topo.kahn();

                DAGPaths sp = new DAGPaths(dag, m);
                DAGPaths.Result shortest = sp.shortestFrom(0);
                DAGPaths.Result longest = sp.longestFrom(0);

                long endTime = System.nanoTime();
                m.nanoTime = (endTime - startTime);

                double longestLength = Arrays.stream(longest.dist).max().orElse(Double.NaN);

                int edgeCount = 0;
                for (var list : dag.adj()) edgeCount += list.size();

                csv.appendRow(new String[]{
                        f.getName(),
                        String.valueOf(g.size()),
                        String.valueOf(edgeCount),
                        String.valueOf(comps.size()),
                        String.valueOf(m.dfsVisits),
                        String.valueOf(m.kahnPops),
                        String.valueOf(m.relaxations),
                        String.valueOf(m.nanoTime),
                        String.valueOf(longestLength)
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        csv.close();

        System.out.println("All 9 datasets done, imported to CSV");
    }
}
