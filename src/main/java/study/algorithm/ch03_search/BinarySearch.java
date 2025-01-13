package study.algorithm.ch03_search;

/**
 * 이진 탐색.
 *
 * <p>정렬된 배열에서만 동작한다. 후보 구간의 중앙 값을 한 번 비교할 때마다
 * 후보가 절반으로 줄기 때문에 큰 배열에서도 매우 빠르다.
 *
 * <ul>
 *   <li>시간 복잡도 : 평균 / 최악 O(log n)</li>
 *   <li>공간 복잡도 : O(1) (반복문 버전)</li>
 *   <li>전제 조건 : 입력 배열이 오름차순 정렬되어 있어야 한다</li>
 * </ul>
 */
public final class BinarySearch {

    private BinarySearch() {}

    /** 정렬된 배열에서 target 의 인덱스를 찾고, 없으면 -1 을 돌려준다. */
    public static int indexOf(int[] sorted, int target) {
        int lo = 0;
        int hi = sorted.length - 1;

        while (lo <= hi) {
            // (lo + hi) / 2 는 큰 배열에서 오버플로우 위험. 다음 식이 안전하다.
            int mid = lo + (hi - lo) / 2;
            int v = sorted[mid];
            if (v == target) return mid;
            else if (v < target) lo = mid + 1;
            else                 hi = mid - 1;
        }
        return -1;
    }
}
