package study.algorithm.ch03_search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BinarySearchTest {

    @Test
    void 정렬된_배열에서_원소를_찾는다() {
        int[] sorted = {1, 3, 5, 7, 9, 11, 13};
        assertEquals(0, BinarySearch.indexOf(sorted, 1));
        assertEquals(3, BinarySearch.indexOf(sorted, 7));
        assertEquals(6, BinarySearch.indexOf(sorted, 13));
    }

    @Test
    void 없는_값은_minus_1() {
        int[] sorted = {1, 3, 5, 7, 9};
        assertEquals(-1, BinarySearch.indexOf(sorted, 4));
        assertEquals(-1, BinarySearch.indexOf(sorted, 100));
        assertEquals(-1, BinarySearch.indexOf(sorted, -1));
    }

    @Test
    void 큰_배열에서도_정확하게_찾는다() {
        int[] sorted = new int[1_000_000];
        for (int i = 0; i < sorted.length; i++) sorted[i] = i * 2;

        assertEquals(0, BinarySearch.indexOf(sorted, 0));
        assertEquals(500_000, BinarySearch.indexOf(sorted, 1_000_000));
        assertEquals(999_999, BinarySearch.indexOf(sorted, 1_999_998));
        assertNotEquals(-1, BinarySearch.indexOf(sorted, 12_346 * 2));
    }

    @Test
    void 빈_배열() {
        assertEquals(-1, BinarySearch.indexOf(new int[]{}, 7));
    }
}
