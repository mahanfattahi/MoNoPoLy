package ds.heap;

public class MyMinMaxHeap {
    private int[] heap;
    private Object[] data;
    private int size;
    private int capacity;

    public MyMinMaxHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
        this.data = new Object[capacity];
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return (2 * i) + 1; }
    private int rightChild(int i) { return (2 * i) + 2; }

    public void insert(int key, Object value) {
        if (size == capacity) return;

        size++;
        int i = size - 1;
        heap[i] = key;
        data[i] = value;

        while (i != 0 && heap[parent(i)] < heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    public Object extractMax() {
        if (size <= 0) return null;
        if (size == 1) {
            size--;
            return data[0];
        }

        Object rootData = data[0];
        heap[0] = heap[size - 1];
        data[0] = data[size - 1];
        size--;
        maxHeapify(0);

        return rootData;
    }

    private void maxHeapify(int i) {
        int l = leftChild(i);
        int r = rightChild(i);
        int largest = i;

        if (l < size && heap[l] > heap[largest]) largest = l;
        if (r < size && heap[r] > heap[largest]) largest = r;

        if (largest != i) {
            swap(i, largest);
            maxHeapify(largest);
        }
    }

    private void swap(int i, int j) {
        int tempKey = heap[i];
        heap[i] = heap[j];
        heap[j] = tempKey;

        Object tempData = data[i];
        data[i] = data[j];
        data[j] = tempData;
    }
}