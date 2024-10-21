package study.algorithm.ch01_datastructure;

import java.util.NoSuchElementException;

/**
 * 배열 기반 스택. LIFO(Last In First Out) 자료구조.
 *
 * <p>가장 최근에 넣은 원소가 가장 먼저 나간다.
 *
 * <ul>
 *   <li>push, pop, peek : amortized O(1)</li>
 * </ul>
 */
public class Stack<E> {

    private static final int DEFAULT_CAPACITY = 8;

    @SuppressWarnings("unchecked")
    private E[] data = (E[]) new Object[DEFAULT_CAPACITY];
    private int size;

    /** 스택 위(top)에 새 원소를 올린다. */
    public void push(E value) {
        if (size == data.length) {
            grow();
        }
        data[size++] = value;
    }

    /** 스택 위 원소를 꺼낸다. 비어 있으면 예외. */
    public E pop() {
        if (size == 0) throw new NoSuchElementException("스택이 비어 있다");
        E value = data[--size];
        data[size] = null;        // GC 가 더 이상 들고 있지 않게 도와준다
        return value;
    }

    /** 꺼내지 않고 위 원소를 들여다본다. */
    public E peek() {
        if (size == 0) throw new NoSuchElementException("스택이 비어 있다");
        return data[size - 1];
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    @SuppressWarnings("unchecked")
    private void grow() {
        E[] expanded = (E[]) new Object[data.length * 2];
        System.arraycopy(data, 0, expanded, 0, size);
        data = expanded;
    }
}
