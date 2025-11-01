package graph.io;
import graph.model.DirectedGraph;
import java.io.*;
public class DatasetLoader {

    public static class Loaded {
        public DirectedGraph graph;
        public int n;
        public int edgesCount;
        public int source;
        public String weightModel;
        public String filename;
    }

    public static Loaded load(String path) throws IOException {
        String raw = readAll(path);
        Loaded ld = new Loaded();
        ld.filename = new File(path).getName();
        ld.n = parseIntField(raw, "\"n\"", -1);
        ld.source = parseIntField(raw, "\"source\"", 0);
        ld.weightModel = parseStringField(raw, "\"weight_model\"", "edge");

        if (ld.n <= 0) throw new IOException("Invalid n in " + path);

        DirectedGraph g = new DirectedGraph(ld.n);
        int edgeCount = 0;

        int idx = 0;
        while (true) {
            idx = raw.indexOf("{", idx);
            if (idx == -1) break;
            int end = raw.indexOf("}", idx);
            if (end == -1) break;
            String obj = raw.substring(idx, end + 1);
            if (obj.contains("\"u\"") && obj.contains("\"v\"")) {
                int u = parseIntField(obj, "\"u\"", -1);
                int v = parseIntField(obj, "\"v\"", -1);
                int w = parseIntField(obj, "\"w\"", 1);
                if (u >= 0 && v >= 0 && u < ld.n && v < ld.n) {
                    g.addEdge(u, v, (double) w);
                    edgeCount++;
                }
            }
            idx = end + 1;
        }

        ld.graph = g;
        ld.edgesCount = edgeCount;
        return ld;
    }

    private static String readAll(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private static int parseIntField(String s, String key, int def) {
        int i = s.indexOf(key);
        if (i == -1) return def;
        int colon = s.indexOf(":", i);
        if (colon == -1) return def;
        int j = colon + 1;
        while (j < s.length() && !Character.isDigit(s.charAt(j)) && s.charAt(j) != '-') j++;
        if (j >= s.length()) return def;
        int k = j + (s.charAt(j) == '-' ? 1 : 0);
        while (k < s.length() && Character.isDigit(s.charAt(k))) k++;
        try {
            return Integer.parseInt(s.substring(j, k));
        } catch (Exception e) { return def; }
    }

    private static String parseStringField(String s, String key, String def) {
        int i = s.indexOf(key);
        if (i == -1) return def;
        int colon = s.indexOf(":", i);
        if (colon == -1) return def;
        int q1 = s.indexOf("\"", colon);
        if (q1 == -1) return def;
        int q2 = s.indexOf("\"", q1 + 1);
        if (q2 == -1) return def;
        return s.substring(q1 + 1, q2);
    }
}

