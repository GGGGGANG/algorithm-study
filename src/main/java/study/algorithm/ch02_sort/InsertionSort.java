package study.algorithm.ch02_sort;

/**
 * 삽입 정렬.
 *
 * <p>왼쪽 구간을 항상 정렬된 상태로 유지하면서, 다음 원소를 그 구간의 올바른 자리에 끼워 넣는다.
 * 거의 정렬된 입력에서는 비교가 거의 한두 번에 끝나 매우 빠르다.
 * 이런 특성 때문에 작은 구간을 다루는 다른 정렬(퀵, 병합)에서 base case 로도 자주 쓰인다.
 *
 * <ul>
 *   <li>시간 복잡도 : 평균 / 최악 O(n^2), 거의 정렬된 입력에서 O(n)</li>
 *   <li>공간 복잡도 : O(1)</li>
 *   <li>안정 정렬 : 같은 값을 만나면 거기에서 멈추므로 상대 순서가 유지된다</li>
 * </ul>
 */
public final class InsertionSort {

    private InsertionSort() {}

    public static void sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int target = array[i];
            int j = i - 1;
            // target 보다 큰 원소들을 한 칸씩 오른쪽으로 밀어내며 자리 만들기.
            while (j >= 0 && array[j] > target) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = target;
        }
    }
}
