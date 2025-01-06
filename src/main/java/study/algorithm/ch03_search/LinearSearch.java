package study.algorithm.ch03_search;

/**
 * 선형 탐색.
 *
 * <p>배열의 처음부터 끝까지 한 칸씩 비교한다. 정렬되지 않은 배열에 그대로 적용 가능하다는
 * 장점이 있고, 데이터가 작거나 한 번만 찾으면 끝나는 상황에서 가장 단순한 정답이다.
 *
 * <ul>
 *   <li>시간 복잡도 : 평균 / 최악 O(n)</li>
 *   <li>공간 복잡도 : O(1)</li>
 *   <li>전제 조건 : 없음</li>
 * </ul>
 */
public final class LinearSearch {

    private LinearSearch() {}

    /** 찾으면 인덱스, 없으면 -1 을 돌려준다. */
    public static int indexOf(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) return i;
        }
        return -1;
    }
}
