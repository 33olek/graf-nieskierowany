import java.util.HashMap;
import java.util.Map;

class Graph {
    private Map<Integer, Map<Integer, Integer>> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addEdge(int u, int v, int weight) {

        graph.computeIfAbsent(u, k -> new HashMap<>()).put(v, weight);
        graph.computeIfAbsent(v, k -> new HashMap<>()).put(u, weight);
    }

    public Map<Integer, Integer> getNeighbors(int u) {
        // Zwracamy sąsiadów wierzchołka u wraz z wagami
        return graph.getOrDefault(u, new HashMap<>());
    }

    public Integer getWeight(int u, int v) {
        // Zwracamy wagę krawędzi między wierzchołkami u i v
        if (graph.containsKey(u) && graph.get(u).containsKey(v)) {
            return graph.get(u).get(v);
        }
        return null;
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addEdge(1, 2, 65);
        g.addEdge(2, 3, 6767);
        g.addEdge(3, 4, 998);
        g.addEdge(1, 4, 786);

        // Sprawdzamy sąsiadów wierzchołka 1 wraz z wagami
        System.out.println("punkty sąsiadujące z punktem 1");
        System.out.println(g.getNeighbors(1));

        // Sprawdzamy wagę krawędzi między wierzchołkami 1 i 4
        System.out.println("Waga krawędzi między wierzchołkami 2 i 3:");
        System.out.println(g.getWeight(2, 3));
    }
}