package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;
import java.util.Random;

/**
 * CLI interface for benchmarking MaxHeap with different input sizes
 */
public class BenchmarkRunner {

    public static void main(String[] args) {
        System.out.println("MaxHeap Benchmark Runner");
        System.out.println("========================\n");

        PerformanceTracker tracker = new PerformanceTracker();
        int[] sizes = {100, 1000, 10000, 100000};

        for (int size : sizes) {
            System.out.println("Testing with n = " + size);
            benchmarkHeapOperations(size, tracker);
            System.out.println();
        }

        tracker.exportToCSV("performance-plots/maxheap_metrics.csv");
        System.out.println("Metrics exported to performance-plots/maxheap_metrics.csv");
    }

    private static void benchmarkHeapOperations(int n, PerformanceTracker tracker) {
        Random random = new Random(42);

        // Test 1: Build heap from random array
        tracker.reset();
        int[] randomArray = generateRandomArray(n, random);
        long startTime = System.nanoTime();

        MaxHeap heap = new MaxHeap(randomArray, tracker);
        long buildTime = System.nanoTime() - startTime;

        tracker.snapshot("build_heap", n);
        System.out.printf("  Build heap: %d ns, Valid: %b\n", buildTime, heap.isValidMaxHeap());

        // Test 2: Sequential insertions
        tracker.reset();
        startTime = System.nanoTime();

        MaxHeap heap2 = new MaxHeap(n, tracker);
        for (int value : randomArray) {
            heap2.insert(value);
        }
        long insertTime = System.nanoTime() - startTime;

        tracker.snapshot("sequential_insert", n);
        System.out.printf("  Sequential insert: %d ns, Valid: %b\n", insertTime, heap2.isValidMaxHeap());

        // Test 3: Extract all elements
        tracker.reset();
        startTime = System.nanoTime();

        while (!heap.isEmpty()) {
            heap.extractMax();
        }
        long extractTime = System.nanoTime() - startTime;

        tracker.snapshot("extract_all", n);
        System.out.printf("  Extract all: %d ns\n", extractTime);
    }

    private static int[] generateRandomArray(int n, Random random) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(10000);
        }
        return array;
    }
}