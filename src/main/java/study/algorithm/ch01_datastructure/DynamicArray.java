package study.algorithm.ch01_datastructure;

/**
 * 동적 배열.
 *
 * <p>고정 크기 배열을 안에 두고 원소가 가득 차면 두 배 크기로 새 배열을 만들어 옮긴다.
 * 평균적으로 add 가 O(1) 이 되는 이유는, n 번의 add 중 doubling 비용이
 * 1 + 2 + 4 + ... + n/2 = O(n) 이라 전체를 평균 내면 add 한 번당 O(1) 로 떨어지기 때문이다.
 *
 * <ul>
 *   <li>add (꼬리), get, set : amortized O(1)</li>
 *   <li>remove (임의 위치) : O(n)</li>
 *   <li>contains : O(n)</li>
 * </ul>
 */
public class DynamicArray {

    private static final int DEFAULT_CAPACITY = 8;

    private int[] data;
    private int size;

    public DynamicArray() {
        this.data = new int[DEFAULT_CAPACITY];
    }

    /** 꼬리에 값을 추가한다. 가득 차면 capacity 를 두 배로 확장한다. */
    public void add(int value) {
        if (size == data.length) {
            grow();
        }
        data[size++] = value;
    }

    public int get(int index) {
        checkIndex(index);
        return data[index];
    }

    public void set(int index, int value) {
        checkIndex(index);
        data[index] = value;
    }

    /** 주어진 위치의 원소를 제거하고, 뒤에 있는 원소들을 한 칸씩 앞으로 당긴다. */
    public int remove(int index) {
        checkIndex(index);
        int removed = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        return removed;
    }

    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) return true;
        }
        return false;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    private void grow() {
        int[] expanded = new int[data.length * 2];
        System.arraycopy(data, 0, expanded, 0, size);
        data = expanded;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }
}
