package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

class MaxHeapTest {
    private PerformanceTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new PerformanceTracker();
    }

    @Test
    void testEmptyHeap() {
        MaxHeap heap = new MaxHeap(10, tracker);
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        assertThrows(NoSuchElementException.class, heap::extractMax);
    }

    @Test
    void testSingleElement() {
        MaxHeap heap = new MaxHeap(10, tracker);
        heap.insert(42);

        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        assertEquals(42, heap.extractMax());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testBuildHeapFromArray() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        MaxHeap heap = new MaxHeap(array, tracker);

        assertTrue(heap.isValidMaxHeap());
        assertEquals(8, heap.size());

        int[] extracted = new int[8];
        for (int i = 0; i < 8; i++) {
            extracted[i] = heap.extractMax();
        }

        int[] expected = {9, 6, 5, 4, 3, 2, 1, 1};
        assertArrayEquals(expected, extracted);
    }

    @Test
    void testIncreaseKey() {
        int[] array = {1, 2, 3, 4, 5};
        MaxHeap heap = new MaxHeap(array, tracker);

        heap.increaseKey(4, 10);
        assertEquals(10, heap.extractMax());
        assertTrue(heap.isValidMaxHeap());
    }

    @Test
    void testDuplicateValues() {
        int[] array = {5, 5, 5, 5, 5};
        MaxHeap heap = new MaxHeap(array, tracker);

        assertTrue(heap.isValidMaxHeap());
        for (int i = 0; i < 5; i++) {
            assertEquals(5, heap.extractMax());
        }
    }

    @Test
    void testInvalidIncreaseKey() {
        int[] array = {10, 8, 5};
        MaxHeap heap = new MaxHeap(array, tracker);

        assertThrows(IllegalArgumentException.class,
                () -> heap.increaseKey(0, 5));
    }

    @Test
    void testLargeHeap() {
        int n = 1000;
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }

        MaxHeap heap = new MaxHeap(array, tracker);
        assertTrue(heap.isValidMaxHeap());

        for (int i = n - 1; i >= 0; i--) {
            assertEquals(i, heap.extractMax());
        }
    }
}