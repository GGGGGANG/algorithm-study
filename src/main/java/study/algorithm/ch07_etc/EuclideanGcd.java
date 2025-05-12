package study.algorithm.ch07_etc;

/**
 * 유클리드 호제법 (최대공약수).
 *
 * <p>"두 수의 최대공약수는 큰 수를 작은 수로 나눈 나머지와 작은 수의 최대공약수와 같다"
 * 라는 한 줄짜리 관찰을 끝까지 적용해 0 이 될 때까지 줄여 가면 답이 남는다.
 *
 * <ul>
 *   <li>시간 복잡도 : O(log(min(a, b))) — 매 단계 입력이 최소 1/2 이하로 줄어든다</li>
 *   <li>공간 복잡도 : O(1) (반복문 버전)</li>
 * </ul>
 */
public final class EuclideanGcd {

    private EuclideanGcd() {}

    public static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
}
