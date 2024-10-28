package study.algorithm.ch01_datastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueTest {

    @Test
    void enqueue_dequeue_는_FIFO_순서를_지킨다() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void capacity_초과시_원형구조가_평탄화되어도_순서_유지() {
        Queue<Integer> queue = new Queue<>();
        // head 와 tail 이 다양한 위치에 있도록 enqueue/dequeue 를 섞는다.
        for (int i = 0; i < 5; i++) queue.enqueue(i);
        for (int i = 0; i < 3; i++) queue.dequeue();
        for (int i = 100; i < 200; i++) queue.enqueue(i);

        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        for (int i = 100; i < 200; i++) {
            assertEquals(i, queue.dequeue());
        }
    }

    @Test
    void 빈_큐의_dequeue_는_예외() {
        Queue<Integer> queue = new Queue<>();
        assertThrows(java.util.NoSuchElementException.class, queue::dequeue);
    }
}
