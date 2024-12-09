package study.algorithm.ch02_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class InsertionSortTest {

    @Test
    void 무작위_입력_정렬() {
        int[] input = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = input.clone();
        Arrays.sort(expected);
        InsertionSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void 중복_값이_많아도_정상() {
        int[] input = {3, 1, 3, 1, 2, 2, 3, 1};
        int[] expected = input.clone();
        Arrays.sort(expected);
        InsertionSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void 큰_무작위_입력_정렬() {
        Random rand = new Random(11);
        int[] arr = rand.ints(3000, -500, 500).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        InsertionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}
