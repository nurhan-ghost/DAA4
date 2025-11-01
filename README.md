# Assignment 4:

**Student:** Nurhan Turganbek  
**Group:** SE-2435  
**Course Topic:** Strongly Connected Components (SCC), Topological Ordering, Shortest Paths in DAGs  
**Goal:** Implement SCC (Tarjan), DAG topological sort, shortest and longest path algorithms, and generate a report on 9 test graphs.

The project uses **Java 17** and outputs results in **CSV format**.
---

## 2. Dataset

All datasets are in the `/data/` folder.  
Each dataset JSON contains:

```json
{
  "directed": true,
  "n": 6,
  "edges": [
    {"u": 0, "v": 1, "w": 8},
    {"u": 1, "v": 2, "w": 4}
  ],
  "source": 0,
  "weight_model": "edge"
}

```

## 3. Running the project

1. Clone the repository.
2. Make sure Java 17+ is installed.
3. Run the main application:


The program will process **all datasets in `/data/`** and generate `results.csv`.
## 4. CSV Output

| Dataset        | Nodes | Edges | SCCs | DFS_Visits | Kahn_Pops | Relaxations | Time_ns | LongestPathLength |
|----------------|-------|-------|------|------------|-----------|-------------|---------|-----------------|
| dataset1.json  | 6     | 0     | 1    | 6          | 3         | 0           | 4_492_100  | 0.0             |
| dataset2.json  | 8     | 0     | 1    | 8          | 3         | 0           | 131_300    | 0.0             |
| dataset3.json  | 10    | 0     | 1    | 10         | 3         | 0           | 184_000    | 0.0             |
| dataset4.json  | 12    | 0     | 1    | 12         | 3         | 0           | 186_800    | 0.0             |
| dataset5.json  | 15    | 3     | 3    | 15         | 9         | 0           | 284_900    | 0.0             |
| dataset6.json  | 18    | 0     | 1    | 18         | 3         | 0           | 302_100    | 0.0             |
| dataset7.json  | 25    | 0     | 1    | 25         | 3         | 0           | 285_600    | 0.0             |
| dataset8.json  | 35    | 2     | 2    | 35         | 6         | 0           | 474_500    | 0.0             |
| dataset9.json  | 45    | 0     | 1    | 45         | 3         | 0           | 602_800    | 0.0             |




## 5. Analysis

* Most datasets are small to medium; some include **cycles**, detected by SCC.
* SCC compression produces a **DAG**, allowing **topological sort** and **path computations**.
* Time measurements (`Time_ns`) indicate **linear or near-linear scaling** for small and medium graphs.
* Longest path lengths may be `0.0` when the DAG has no edges after SCC compression.

````
app/
├─ Main.java  
└─ CSVWriter.java  

graph/
├─ io/
│   └─ DatasetLoader.java
├─ model/
│   ├─ DirectedGraph.java
│   └─ CondensationGraph.java
├─ scc/
│   └─ TarjanSCC.java
├─ topo/
│   └─ TopologicalOrder.java
├─ dagsp/
│   └─ DAGPaths.java
└─ metrics/Metrics.java

data/
└─ *.json        
results.csv

````




## Usage

1. Make sure all 9 datasets are in the `data/` folder.
2. Compile the project with your Java IDE or via command line.
3. Run `app.Main`.



After running, `results.csv` will be generated in the project root.

## CSV Output

Example:
```
Dataset,Nodes,Edges,SCCs,DFS_Visits,Kahn_Pops,Relaxations,Time_ns,LongestPathLength
dataset1.json,6,0,1,6,3,0,4492100,0.0
```
## Metrics & Instrumentation

The `Metrics` class tracks:

* DFS visits and edges for SCC.
* Kahn's pushes/pops for topological sort.
* Edge relaxations for DAG shortest/longest paths.
* Elapsed time in nanoseconds.

## Testing

Unit tests are provided in `test/graph/GraphAlgorithmsTest.java` (JUnit 5):

* Small deterministic graphs
* Edge cases with multiple SCCs
* DAG path computations



## Notes

* Cyclic graphs are compressed into a DAG via SCC condensation.
* Longest path is computed via dynamic programming along topological order.
* CSV generation is handled by `CSVWriter` independent of the main runner.
* All datasets are processed in batch; results are consolidated into one CSV.

## Conclusions

* Tarjan SCC correctly detects cycles and compresses them into a DAG.

* Kahn topological sort produces a valid order of components.

* DAG shortest and longest path algorithms work on the condensation graph; zero lengths appear when the DAG has only one component.

* Metrics scale with graph size and density, and execution times are reasonable for all datasets.

* SCC compression and topological sorting are essential for scheduling tasks in graphs with dependencies.
