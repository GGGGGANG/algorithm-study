package study.algorithm.ch02_sort;

/**
 * 병합 정렬.
 *
 * <p>배열을 절반으로 계속 쪼개고, 길이가 1 이 된 조각들을 다시 정렬된 상태로
 * 합치며 올라온다. 매 합치기 단계가 O(n), 깊이가 log n 이라 총 O(n log n).
 *
 * <ul>
 *   <li>시간 복잡도 : 항상 O(n log n)</li>
 *   <li>공간 복잡도 : O(n) — 병합 버퍼가 필요</li>
 *   <li>안정 정렬 : 같은 값일 때 왼쪽 조각의 원소를 먼저 가져가게 하면 상대 순서가 보존된다</li>
 * </ul>
 *
 * <p>분할은 이름대로라 어렵지 않은데, merge 에서 한쪽 인덱스가 먼저 끝났을 때 처리가 헷갈렸다.
 * 두 정렬된 작은 배열을 종이에 적어 놓고 인덱스 두 개를 한 칸씩 옮겨 보면,
 * 한쪽이 끝나면 나머지를 그대로 복사하는 마무리 루프 두 개가 자연스럽게 따라온다.
 */
public final class MergeSort {

    private MergeSort() {}

    public static void sort(int[] array) {
        if (array.length < 2) return;
        int[] buffer = new int[array.length];
        sort(array, buffer, 0, array.length - 1);
    }

    private static void sort(int[] a, int[] buf, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (lo + hi) >>> 1;       // overflow 안전한 중점 계산
        sort(a, buf, lo, mid);
        sort(a, buf, mid + 1, hi);
        merge(a, buf, lo, mid, hi);
    }

    private static void merge(int[] a, int[] buf, int lo, int mid, int hi) {
        // 정렬된 두 부분 [lo..mid], [mid+1..hi] 를 buf 에 병합한 뒤 다시 a 로 옮긴다.
        for (int i = lo; i <= hi; i++) buf[i] = a[i];

        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            // 안정성을 위해 같은 값일 때 왼쪽 조각의 원소를 먼저 보낸다.
            if (buf[i] <= buf[j]) a[k++] = buf[i++];
            else                   a[k++] = buf[j++];
        }
        while (i <= mid) a[k++] = buf[i++];
        while (j <= hi)  a[k++] = buf[j++];
    }
}
