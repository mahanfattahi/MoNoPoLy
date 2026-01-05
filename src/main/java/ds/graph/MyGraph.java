package ds.graph;

public class MyGraph {
    private int[][] adjacencyMatrix;
    private GraphNode[] nodes;
    private int size;
    private int capacity;

    public MyGraph(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.adjacencyMatrix = new int[capacity][capacity];
        this.nodes = new GraphNode[capacity];
    }

    public void addNode(int playerId, String name) {
        if (size < capacity) {
            nodes[size] = new GraphNode(playerId, name);
            size++;
        }
    }

    public void addTransaction(int fromIndex, int toIndex, int amount) {
        if (fromIndex >= 0 && fromIndex < size && toIndex >= 0 && toIndex < size) {
            adjacencyMatrix[fromIndex][toIndex] += amount;
        }
    }

    public int getTransactionAmount(int fromIndex, int toIndex) {
        return adjacencyMatrix[fromIndex][toIndex];
    }
}