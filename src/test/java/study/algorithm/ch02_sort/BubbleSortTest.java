package study.algorithm.ch02_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BubbleSortTest {

    @Test
    void 무작위_입력을_오름차순으로_정렬한다() {
        int[] input = {5, 1, 9, 3, 7, 2, 8, 4, 6};
        int[] expected = input.clone();
        Arrays.sort(expected);
        BubbleSort.sort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void 빈_배열과_단일_원소도_안전하게_처리된다() {
        int[] empty = {};
        BubbleSort.sort(empty);
        assertArrayEquals(new int[]{}, empty);

        int[] single = {42};
        BubbleSort.sort(single);
        assertArrayEquals(new int[]{42}, single);
    }

    @Test
    void 큰_무작위_입력에서도_정확하게_정렬한다() {
        Random rand = new Random(123);
        int[] arr = rand.ints(2000, -1_000, 1_000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);
        BubbleSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}
