package study.algorithm.ch02_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {

    @Test
    void 무작위_입력_정렬() {
        int[] input = {38, 27, 43, 3, 9, 82, 10};
        int[] expected = input.clone();
        Arrays.sort(expected);
        MergeSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void 역순_입력_정렬() {
        int[] reversed = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        MergeSort.sort(reversed);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, reversed);
    }

    @Test
    void 큰_무작위_입력_정렬() {
        Random rand = new Random(202);
        int[] arr = rand.ints(10_000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        MergeSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}
