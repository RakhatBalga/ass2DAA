package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks performance metrics for algorithm analysis
 */
public class PerformanceTracker {
    private int comparisons;
    private int swaps;
    private int arrayAccesses;
    private int memoryAllocations;

    private final List<MetricSnapshot> history;

    public PerformanceTracker() {
        this.history = new ArrayList<>();
        reset();
    }

    public void recordComparison(int count) {
        comparisons += count;
    }

    public void recordSwap(int count) {
        swaps += count;
    }

    public void recordArrayAccess(int count) {
        arrayAccesses += count;
    }

    public void recordMemoryAllocation(int bytes) {
        memoryAllocations += bytes;
    }

    public void snapshot(String operation, int inputSize) {
        history.add(new MetricSnapshot(operation, inputSize,
                comparisons, swaps, arrayAccesses, memoryAllocations));
    }

    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        memoryAllocations = 0;
    }

    public void exportToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("operation,input_size,comparisons,swaps,array_accesses,memory_allocations\n");
            for (MetricSnapshot snapshot : history) {
                writer.write(snapshot.toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error exporting metrics: " + e.getMessage());
        }
    }

    public int getComparisons() { return comparisons; }
    public int getSwaps() { return swaps; }
    public int getArrayAccesses() { return arrayAccesses; }
    public int getMemoryAllocations() { return memoryAllocations; }

    private static class MetricSnapshot {
        final String operation;
        final int inputSize;
        final int comparisons;
        final int swaps;
        final int arrayAccesses;
        final int memoryAllocations;

        MetricSnapshot(String operation, int inputSize, int comparisons,
                       int swaps, int arrayAccesses, int memoryAllocations) {
            this.operation = operation;
            this.inputSize = inputSize;
            this.comparisons = comparisons;
            this.swaps = swaps;
            this.arrayAccesses = arrayAccesses;
            this.memoryAllocations = memoryAllocations;
        }

        String toCSV() {
            return String.format("%s,%d,%d,%d,%d,%d",
                    operation, inputSize, comparisons, swaps, arrayAccesses, memoryAllocations);
        }
    }
}