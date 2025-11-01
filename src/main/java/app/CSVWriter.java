package app;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private FileWriter writer;

    public CSVWriter(String path) {
        try {
            writer = new FileWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeHeader() {
        try {
            writer.write("Dataset,Nodes,Edges,SCCs,DFS_Visits,Kahn_Pops,Relaxations,Time_ns,LongestPathLength\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void appendRow(String[] values) {
        try {
            writer.write(String.join(",", values) + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
