package study.algorithm.ch02_sort;

/**
 * 힙 정렬.
 *
 * <p>먼저 입력 배열을 최대 힙으로 만든 뒤, 루트(가장 큰 값) 와 마지막 자리를 바꾸고
 * 힙의 길이를 하나 줄여 다시 heapify 하는 일을 반복한다.
 * 결과적으로 배열이 끝에서부터 채워지며 정렬이 완성된다.
 *
 * <ul>
 *   <li>시간 복잡도 : 항상 O(n log n) — 입력 분포에 둔감</li>
 *   <li>공간 복잡도 : O(1) (제자리 정렬)</li>
 *   <li>안정 정렬 아님 : siftDown 과정에서 같은 값의 상대 순서가 깨질 수 있다</li>
 * </ul>
 *
 * <p>처음엔 빈 힙에 0 부터 insert 를 n 번 하면 되겠다고 생각했는데 그건 O(n log n) 이다.
 * O(n) 으로 끝내려면 인덱스 n/2 - 1 부터 0 까지 거꾸로 siftDown 하면 된다.
 * 잎 노드는 자기 혼자 이미 힙 성질을 만족하니 부모 쪽만 손보면 되는 게 핵심.
 */
public final class HeapSort {

    private HeapSort() {}

    public static void sort(int[] array) {
        int n = array.length;

        // 최대 힙 구성: 부모 노드들을 거꾸로 훑으며 siftDown.
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, i, n);
        }

        // 루트와 끝을 swap 하고 힙 크기를 줄이며 정렬을 완성.
        for (int end = n - 1; end > 0; end--) {
            int tmp = array[0];
            array[0] = array[end];
            array[end] = tmp;
            siftDown(array, 0, end);
        }
    }

    /** 인덱스 root 의 노드를 [0, end) 범위 내에서 max-heap 성질이 회복될 때까지 내린다. */
    private static void siftDown(int[] a, int root, int end) {
        while (true) {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            int largest = root;
            if (left < end && a[left] > a[largest]) largest = left;
            if (right < end && a[right] > a[largest]) largest = right;
            if (largest == root) break;
            int tmp = a[root];
            a[root] = a[largest];
            a[largest] = tmp;
            root = largest;
        }
    }
}
