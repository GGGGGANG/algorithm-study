package study.algorithm.ch01_datastructure;

import java.util.NoSuchElementException;

/**
 * 배열 기반 최소 이진 힙.
 *
 * <p>완전 이진 트리를 배열에 그대로 펴서 담는다.
 * 인덱스 i 의 부모는 (i-1)/2, 왼쪽 자식은 2i+1, 오른쪽 자식은 2i+2 이다.
 * 이 인덱스 식 덕분에 트리 노드 객체와 포인터 없이도 트리 동작이 가능하다.
 *
 * <ul>
 *   <li>insert : O(log n) (siftUp)</li>
 *   <li>extractMin : O(log n) (siftDown)</li>
 *   <li>peek : O(1)</li>
 * </ul>
 */
public class MinHeap {

    private static final int DEFAULT_CAPACITY = 8;

    private int[] data = new int[DEFAULT_CAPACITY];
    private int size;

    /** 새 값을 마지막에 붙이고 부모와 비교하며 위로 올린다. */
    public void insert(int value) {
        if (size == data.length) {
            grow();
        }
        data[size] = value;
        siftUp(size);
        size++;
    }

    /** 가장 작은 값(루트)을 꺼내고, 마지막 값을 루트로 옮긴 뒤 아래로 내린다. */
    public int extractMin() {
        if (size == 0) throw new NoSuchElementException("힙이 비어 있다");
        int min = data[0];
        size--;
        if (size > 0) {
            data[0] = data[size];
            siftDown(0);
        }
        return min;
    }

    public int peek() {
        if (size == 0) throw new NoSuchElementException("힙이 비어 있다");
        return data[0];
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data[index] < data[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;
            if (left < size && data[left] < data[smallest]) smallest = left;
            if (right < size && data[right] < data[smallest]) smallest = right;
            if (smallest == index) break;
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private void grow() {
        int[] expanded = new int[data.length * 2];
        System.arraycopy(data, 0, expanded, 0, size);
        data = expanded;
    }
}
