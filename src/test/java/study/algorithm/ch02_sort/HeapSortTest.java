package study.algorithm.ch02_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapSortTest {

    @Test
    void 무작위_입력_정렬() {
        int[] input = {12, 11, 13, 5, 6, 7};
        int[] expected = input.clone();
        Arrays.sort(expected);
        HeapSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void 빈_배열과_단일_원소() {
        int[] empty = {};
        HeapSort.sort(empty);
        assertArrayEquals(new int[]{}, empty);

        int[] single = {-3};
        HeapSort.sort(single);
        assertArrayEquals(new int[]{-3}, single);
    }

    @Test
    void 큰_무작위_입력_정렬() {
        Random rand = new Random(99);
        int[] arr = rand.ints(5000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        HeapSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}
