package study.algorithm.ch07_etc;

import java.util.ArrayList;
import java.util.List;

/**
 * 에라토스테네스의 체 (소수 판별).
 *
 * <p>2 부터 N 까지 후보를 늘어놓고, 소수 p 를 만날 때마다
 * p^2, p^2 + p, p^2 + 2p, ... 를 합성수로 표시한다.
 * 한 번 합성수로 표시된 수는 다시 보지 않으므로 매우 빠르다.
 *
 * <ul>
 *   <li>시간 복잡도 : O(N log log N) — 표준 분석</li>
 *   <li>공간 복잡도 : O(N)</li>
 * </ul>
 */
public final class SieveOfEratosthenes {

    private SieveOfEratosthenes() {}

    /** 2 이상 N 이하의 모든 소수를 오름차순으로 돌려준다. */
    public static List<Integer> primesUpTo(int n) {
        if (n < 2) return List.of();
        boolean[] isComposite = new boolean[n + 1];

        for (int i = 2; (long) i * i <= n; i++) {
            if (!isComposite[i]) {
                // i 의 배수 중 i*i 부터 시작해서 표시한다 (그보다 작은 배수는 이미 더 작은 소수에 의해 표시됨).
                for (int j = i * i; j <= n; j += i) {
                    isComposite[j] = true;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) primes.add(i);
        }
        return primes;
    }
}
