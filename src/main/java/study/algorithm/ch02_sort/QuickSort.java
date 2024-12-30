package study.algorithm.ch02_sort;

import java.util.Random;

/**
 * 퀵 정렬.
 *
 * <p>피벗을 하나 고르고, 피벗보다 작은 값은 왼쪽 / 큰 값은 오른쪽으로 나눈 뒤
 * 두 영역을 각각 재귀적으로 같은 방식으로 정렬한다.
 * 평균적으로 매우 빠르지만, 피벗 선택이 나쁘면 매번 한쪽이 비어 O(n^2) 까지 떨어진다.
 * 그래서 여기서는 무작위 피벗을 골라 최악 케이스를 입력 의존이 아니라 확률에 맡긴다.
 *
 * <ul>
 *   <li>시간 복잡도 : 평균 O(n log n), 최악 O(n^2)</li>
 *   <li>공간 복잡도 : O(log n) — 재귀 호출 스택 (제자리 분할)</li>
 *   <li>안정 정렬 아님</li>
 * </ul>
 *
 * <p>책에서는 "피벗 기준으로 가른다" 한 줄로 끝나지만, 코드로 옮길 때 한 번의 partition 이
 * 정확히 무엇을 끝내는지가 잘 안 보였다. 작은 배열을 손으로 따라가 보면
 * partition 한 번은 피벗 하나의 최종 위치만 확정하고 나머지는 다음 재귀에 그대로 넘긴다.
 * 이걸 알면 partition 마지막에 피벗을 store 자리로 swap 하는 줄이 자연스럽게 읽힌다.
 */
public final class QuickSort {

    private static final Random RNG = new Random(0xC0FFEEL);

    private QuickSort() {}

    public static void sort(int[] array) {
        if (array.length < 2) return;
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }

    /** Lomuto 분할: 피벗 하나의 최종 위치를 반환한다. */
    private static int partition(int[] a, int lo, int hi) {
        int pivotIndex = lo + RNG.nextInt(hi - lo + 1);
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, hi);    // 피벗을 끝으로 잠시 옮긴다

        int store = lo;
        for (int i = lo; i < hi; i++) {
            if (a[i] < pivot) {
                swap(a, i, store);
                store++;
            }
        }
        swap(a, store, hi);          // 피벗을 자기 자리로
        return store;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
}
