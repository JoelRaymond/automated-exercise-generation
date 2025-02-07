package exercise_generation;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RadixSort {
    public int[] numbers;

    public int m; // Number of bits per number (6 or 8)

    public int n; // Length of array (8, 9, or 10)

    public int b; // Number of bits processed per iteration
    public boolean allowEmptyBuckets;
    public List<List<Integer>> steps; // Stores sorting steps

    public RadixSort(int m, int n, int b, boolean allowEmptyBuckets) {
        if (m != 6 && m != 8) {
            throw new IllegalArgumentException("m must be 6 or 8.");
        }
        if (n < 8 || n > 10) {
            throw new IllegalArgumentException("n must be between 8 and 10.");
        }
        if (m % b != 0) {
            throw new IllegalArgumentException("b must evenly divide m.");
        }

        this.m = m;
        this.n = n;
        this.b = b;
        this.allowEmptyBuckets = allowEmptyBuckets;
        this.steps = new ArrayList<>();
        this.numbers = generateNumbers();
    }

    private int[] generateNumbers() {
        int maxValue = (1 << m) - 1; // 2^m - 1
        Set<Integer> uniqueNumbers = new HashSet<>();
        while (uniqueNumbers.size() < n) {
            uniqueNumbers.add(ThreadLocalRandom.current().nextInt(0, maxValue + 1));
        }
        return uniqueNumbers.stream().mapToInt(Integer::intValue).toArray();
    }

    public void sort() {
        int mask = (1 << b) - 1; // Mask for extracting b bits
        int iterations = m / b;

        for (int i = 0; i < iterations; i++) {
            int shift = i * b;
            List<List<Integer>> buckets = new ArrayList<>(1 << b);
            for (int j = 0; j < (1 << b); j++) {
                buckets.add(new ArrayList<>());
            }

            for (int num : numbers) {
                int bucketIndex = (num >> shift) & mask;
                buckets.get(bucketIndex).add(num);
            }

            if (!allowEmptyBuckets) {
                buckets.removeIf(List::isEmpty);
            }

            List<Integer> sorted = new ArrayList<>();
            for (List<Integer> bucket : buckets) {
                sorted.addAll(bucket);
            }
            numbers = sorted.stream().mapToInt(Integer::intValue).toArray();
            steps.add(new ArrayList<>(sorted));
        }
    }

    public String toLatex() {
        StringBuilder latex = new StringBuilder();
        latex.append("\\\\question\\\\textbf{Radix Sort}")
                .append("\\\\newline Sorting the following numbers using radix sort (m=")
                .append(m).append(", b=").append(b).append("):\\\\newline ");

        latex.append("\\\\begin{center}\\n");
        for (int num : numbers) {
            latex.append(num).append(" ");
        }
        latex.append("\\\\end{center}\\n");

        latex.append("\\\\begin{solution}\\n");
        for (int i = 0; i < steps.size(); i++) {
            latex.append("\\\\textbf{Iteration ").append(i + 1).append("}: ");
            for (int num : steps.get(i)) {
                latex.append(num).append(" ");
            }
            latex.append("\\\\newline\\n");
        }
        latex.append("\\\\end{solution}\\n");

        return latex.toString();
    }
}