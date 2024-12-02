package study.algorithm.ch02_sort;

/**
 * 선택 정렬.
 *
 * <p>매 단계마다 "아직 정렬되지 않은 구간에서 가장 작은 값"을 찾아 맨 앞에 둔다.
 * 비교 횟수는 입력에 상관 없이 거의 같지만, swap 이 한 단계당 한 번뿐이라
 * 메모리 쓰기 비용이 큰 환경에서 의미가 있다.
 *
 * <ul>
 *   <li>시간 복잡도 : 항상 O(n^2)</li>
 *   <li>공간 복잡도 : O(1)</li>
 *   <li>안정 정렬 아님 : 멀리 있는 원소를 통째로 swap 하기 때문에 같은 값의 상대 순서가 깨질 수 있다</li>
 * </ul>
 */
public final class SelectionSort {

    private SelectionSort() {}

    public static void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) minIndex = j;
            }
            if (minIndex != i) {
                int tmp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = tmp;
            }
        }
    }
}
