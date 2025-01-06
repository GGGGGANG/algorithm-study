package study.algorithm.ch03_search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinearSearchTest {

    @Test
    void 존재하는_원소는_첫_위치를_돌려준다() {
        int[] arr = {3, 7, 1, 9, 4, 7};
        assertEquals(0, LinearSearch.indexOf(arr, 3));
        assertEquals(1, LinearSearch.indexOf(arr, 7));
        assertEquals(4, LinearSearch.indexOf(arr, 4));
    }

    @Test
    void 없는_원소는_minus_1() {
        int[] arr = {3, 7, 1, 9, 4};
        assertEquals(-1, LinearSearch.indexOf(arr, 2));
    }

    @Test
    void 빈_배열() {
        assertEquals(-1, LinearSearch.indexOf(new int[]{}, 5));
    }
}
