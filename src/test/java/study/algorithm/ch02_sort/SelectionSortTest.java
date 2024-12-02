package study.algorithm.ch02_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SelectionSortTest {

    @Test
    void 무작위_입력을_오름차순으로_정렬한다() {
        int[] input = {3, -1, 7, 0, 2, 5, 4, 9, 8, 6, 1};
        int[] expected = input.clone();
        Arrays.sort(expected);
        SelectionSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void 이미_정렬된_입력은_그대로() {
        int[] sorted = {1, 2, 3, 4, 5};
        SelectionSort.sort(sorted);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    void 큰_무작위_입력_정렬() {
        Random rand = new Random(7);
        int[] arr = rand.ints(2000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        SelectionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}
