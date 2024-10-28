package study.algorithm.ch01_datastructure;

import java.util.NoSuchElementException;

/**
 * 원형 버퍼 기반 큐. FIFO(First In First Out) 자료구조.
 *
 * <p>먼저 넣은 원소가 먼저 나간다.
 * 단순히 배열의 head 를 한 칸씩 옮기면 dequeue 가 O(n) 이 되므로
 * head 와 tail 인덱스를 모듈로 연산으로 회전시키는 방식으로 두 동작을 모두 O(1) 로 만든다.
 *
 * <ul>
 *   <li>enqueue, dequeue, peek : amortized O(1)</li>
 * </ul>
 */
public class Queue<E> {

    private static final int DEFAULT_CAPACITY = 8;

    @SuppressWarnings("unchecked")
    private E[] data = (E[]) new Object[DEFAULT_CAPACITY];
    private int head;          // 다음에 꺼낼 자리
    private int tail;          // 다음에 넣을 자리
    private int size;

    /** 꼬리에 값을 추가한다. 가득 차면 capacity 를 두 배로 키우고 평탄화한다. */
    public void enqueue(E value) {
        if (size == data.length) {
            grow();
        }
        data[tail] = value;
        tail = (tail + 1) % data.length;
        size++;
    }

    /** 머리의 값을 꺼낸다. */
    public E dequeue() {
        if (size == 0) throw new NoSuchElementException("큐가 비어 있다");
        E value = data[head];
        data[head] = null;
        head = (head + 1) % data.length;
        size--;
        return value;
    }

    public E peek() {
        if (size == 0) throw new NoSuchElementException("큐가 비어 있다");
        return data[head];
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    @SuppressWarnings("unchecked")
    private void grow() {
        E[] expanded = (E[]) new Object[data.length * 2];
        // 원형 구조를 0 부터 시작하는 선형 배열로 다시 풀어 담는다.
        for (int i = 0; i < size; i++) {
            expanded[i] = data[(head + i) % data.length];
        }
        data = expanded;
        head = 0;
        tail = size;
    }
}
