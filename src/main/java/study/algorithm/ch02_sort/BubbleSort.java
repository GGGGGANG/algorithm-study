package study.algorithm.ch02_sort;

/**
 * 버블 정렬.
 *
 * <p>이웃한 두 원소를 비교해 잘못된 순서면 자리를 바꾸는 일을 끝까지 반복한다.
 * 한 번의 패스가 끝나면 가장 큰 값이 거품처럼 끝으로 떠오른다.
 *
 * <ul>
 *   <li>시간 복잡도 : 평균 / 최악 O(n^2), 이미 정렬된 입력에서 조기 종료가 들어가면 O(n)</li>
 *   <li>공간 복잡도 : O(1) (제자리 정렬)</li>
 *   <li>안정 정렬 : 같은 값의 상대 순서가 유지된다</li>
 * </ul>
 */
public final class BubbleSort {

    private BubbleSort() {}

    public static void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            // 한 번의 패스 동안 한 번도 swap 이 없었다면 이미 정렬 완료.
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
