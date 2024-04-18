import java.util.*;

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

        return graph.getOrDefault(u, new HashMap<>());
    }

    public Integer getWeight(int u, int v) {

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

        System.out.println("punkty sąsiadujące z punktem 1");
        System.out.println(g.getNeighbors(1));

        System.out.println("Waga krawędzi między wierzchołkami 2 i 3:");
        System.out.println(g.getWeight(2, 3));

        // Find minimum spanning tree using Kruskal's algorithm
        Map<Integer, Integer> mst = g.kruskalMST();
        System.out.println("Minimalne drzewo rozpinające:");
        System.out.println(mst);
    }

    public <T> Map<Integer, T> kruskalMST() {

        class Edge { // Define Edge class
            public int u;
            public int v;
            public int weight;

            public Edge(int u, int v, int weight) {
                this.u = u;
                this.v = v;
                this.weight = weight;
            }
        }

        PriorityQueue<Edge> edges = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);


        for (Map.Entry<Integer, Map<Integer, Integer>> entry1 : graph.entrySet()) {
            for (Map.Entry<Integer, Integer> entry2 : entry1.getValue().entrySet()) {
                int u = entry1.getKey();
                int v = entry2.getKey();
                int weight = entry2.getValue();
                edges.add(new Edge(u, v, weight));
            }
        }


        Set<Edge> mst = new HashSet<>();


        Map<Integer, Integer> parent = new HashMap<>();
        for (int v : graph.keySet()) {
            parent.put(v, v);
        }


        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            int u = edge.u;
            int v = edge.v;


            if (findRoot(u, parent) != findRoot(v, parent)) {
                mst.add(edge);
                parent.put(v, u);
            }
        }


        Map<Integer, Integer> mstMap = new HashMap<>(); // Use generic type for mstMap
        for (Edge edge : mst) {
            mstMap.computeIfAbsent(edge.u, k -> new HashMap<>()).put(edge.v, edge.weight);
            mstMap.computeIfAbsent(edge.v, k -> new HashMap<>()).put(edge.u, edge.weight);
        }



        return (Map<Integer, T>) mstMap;
    }

    private int findRoot(int u, Map<Integer, Integer> parent) {
        if (parent.get(u) != u) {
            parent.put(u, findRoot(parent.get(u), parent)); // Path compression
        }
        return parent.get(u);
    }
}

