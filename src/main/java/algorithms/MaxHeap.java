package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Max-Heap implementation with increase-key and extract-max operations
 * In-place array-based binary heap (0-based indexing)
 */
public class MaxHeap {
    private int[] heap;
    private int size;
    private final PerformanceTracker tracker;

    public MaxHeap(int capacity, PerformanceTracker tracker) {
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = tracker;
        tracker.recordMemoryAllocation(capacity * 4);
    }

    public MaxHeap(int[] array, PerformanceTracker tracker) {
        this.heap = Arrays.copyOf(array, array.length);
        this.size = array.length;
        this.tracker = tracker;
        tracker.recordMemoryAllocation(array.length * 4);
        buildHeap();
    }

    private void buildHeap() {
        for (int i = (size - 2) / 2; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    public void insert(int value) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = value;
        tracker.recordArrayAccess(1);
        size++;
        heapifyUp(size - 1);
    }

    public int extractMax() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }

        int max = heap[0];
        tracker.recordArrayAccess(1);
        heap[0] = heap[size - 1];
        tracker.recordArrayAccess(2);
        size--;

        if (size > 0) {
            heapifyDown(0);
        }

        tracker.recordSwap(1);
        return max;
    }

    public void increaseKey(int index, int newValue) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        tracker.recordArrayAccess(1);
        if (newValue < heap[index]) {
            throw new IllegalArgumentException("New value must be greater than current value");
        }

        heap[index] = newValue;
        tracker.recordArrayAccess(1);
        heapifyUp(index);
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            tracker.recordArrayAccess(2);
            tracker.recordComparison(1);
            if (heap[parent] >= heap[index]) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            tracker.recordArrayAccess(1);
            if (left < size) {
                tracker.recordArrayAccess(1);
                tracker.recordComparison(1);
                if (heap[left] > heap[largest]) {
                    largest = left;
                }
            }

            if (right < size) {
                tracker.recordArrayAccess(1);
                tracker.recordComparison(1);
                if (heap[right] > heap[largest]) {
                    largest = right;
                }
            }

            if (largest == index) {
                break;
            }

            swap(index, largest);
            index = largest;
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        tracker.recordArrayAccess(4);
        tracker.recordSwap(1);
    }

    private void resize() {
        int newCapacity = heap.length * 2;
        heap = Arrays.copyOf(heap, newCapacity);
        tracker.recordMemoryAllocation(newCapacity * 4);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return heap.length;
    }

    public boolean isValidMaxHeap() {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && heap[i] < heap[left]) return false;
            if (right < size && heap[i] < heap[right]) return false;
        }
        return true;
    }
}