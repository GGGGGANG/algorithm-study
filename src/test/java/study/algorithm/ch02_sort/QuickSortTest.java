package study.algorithm.ch02_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class QuickSortTest {

    @Test
    void 무작위_입력_정렬() {
        int[] input = {3, 6, 1, 8, 2, 9, 4, 7, 5};
        int[] expected = input.clone();
        Arrays.sort(expected);
        QuickSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void 모두_같은_값에서도_안전() {
        int[] same = {5, 5, 5, 5, 5, 5};
        QuickSort.sort(same);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5, 5}, same);
    }

    @Test
    void 정렬된_입력_역순_입력_혼합_입력_모두() {
        Random rand = new Random(7);
        int n = 10_000;

        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) sorted[i] = i;
        QuickSort.sort(sorted);
        assertArrayEquals(java.util.stream.IntStream.range(0, n).toArray(), sorted);

        int[] reversed = new int[n];
        for (int i = 0; i < n; i++) reversed[i] = n - i;
        int[] expected = reversed.clone();
        Arrays.sort(expected);
        QuickSort.sort(reversed);
        assertArrayEquals(expected, reversed);

        int[] mixed = rand.ints(n).toArray();
        int[] mixedExpected = mixed.clone();
        Arrays.sort(mixedExpected);
        QuickSort.sort(mixed);
        assertArrayEquals(mixedExpected, mixed);
    }
}
