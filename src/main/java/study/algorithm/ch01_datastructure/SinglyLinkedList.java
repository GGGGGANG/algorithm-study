package study.algorithm.ch01_datastructure;

import java.util.NoSuchElementException;

/**
 * 단일 연결 리스트.
 *
 * <p>각 노드가 다음 노드의 참조만 갖는 가장 단순한 형태의 리스트.
 * 임의 접근은 O(n) 이지만, 머리에서의 추가/삭제는 O(1) 이다.
 *
 * <ul>
 *   <li>addFirst, removeFirst : O(1)</li>
 *   <li>get(index), remove(index) : O(n)</li>
 *   <li>size : O(1) (size 필드를 별도로 유지)</li>
 * </ul>
 */
public class SinglyLinkedList<E> {

    /** 내부 노드. 외부로 노출하지 않는다. */
    private static final class Node<E> {
        final E value;
        Node<E> next;
        Node(E value) { this.value = value; }
    }

    private Node<E> head;
    private int size;

    /** 머리에 새 값을 끼워 넣는다. */
    public void addFirst(E value) {
        Node<E> node = new Node<>(value);
        node.next = head;
        head = node;
        size++;
    }

    /** 꼬리에 새 값을 추가한다. 꼬리 포인터가 없으므로 O(n). */
    public void addLast(E value) {
        Node<E> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            Node<E> cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node;
        }
        size++;
    }

    /** index 번째 값을 반환한다. 범위를 벗어나면 예외. */
    public E get(int index) {
        checkIndex(index);
        Node<E> cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.value;
    }

    /** 머리 노드를 제거하고 그 값을 반환한다. */
    public E removeFirst() {
        if (head == null) throw new NoSuchElementException("리스트가 비어 있다");
        E value = head.value;
        head = head.next;
        size--;
        return value;
    }

    /** index 번째 노드를 제거한다. */
    public E remove(int index) {
        checkIndex(index);
        if (index == 0) return removeFirst();
        Node<E> prev = head;
        for (int i = 0; i < index - 1; i++) prev = prev.next;
        E value = prev.next.value;
        prev.next = prev.next.next;
        size--;
        return value;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }
}
