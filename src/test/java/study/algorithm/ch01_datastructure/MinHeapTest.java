package study.algorithm.ch01_datastructure;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void extractMin_은_항상_가장_작은_값을_돌려준다() {
        MinHeap heap = new MinHeap();
        int[] input = {5, 1, 9, 3, 7, 2, 8, 4, 6};
        for (int v : input) heap.insert(v);

        int prev = Integer.MIN_VALUE;
        while (!heap.isEmpty()) {
            int next = heap.extractMin();
            assertTrue(next >= prev, "비단조 감소가 발생했다: prev=" + prev + ", next=" + next);
            prev = next;
        }
    }

    @Test
    void 무작위_입력에_대해_정렬_순서로_방출된다() {
        Random rand = new Random(42);
        MinHeap heap = new MinHeap();
        int n = 1000;
        for (int i = 0; i < n; i++) heap.insert(rand.nextInt());

        long prev = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int v = heap.extractMin();
            assertTrue(v >= prev);
            prev = v;
        }
        assertTrue(heap.isEmpty());
    }

    @Test
    void 빈_힙의_extractMin_은_예외() {
        MinHeap heap = new MinHeap();
        assertThrows(java.util.NoSuchElementException.class, heap::extractMin);
    }
}
