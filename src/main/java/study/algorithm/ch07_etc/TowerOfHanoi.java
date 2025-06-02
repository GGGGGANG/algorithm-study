package study.algorithm.ch07_etc;

import java.util.ArrayList;
import java.util.List;

/**
 * 하노이의 탑.
 *
 * <p>세 개의 기둥이 있고, 처음에는 한 기둥에 크기가 다른 n 개의 원반이 큰 것이 아래에
 * 오도록 쌓여 있다. 한 번에 하나의 원반만 옮길 수 있고, 작은 원반 위에 큰 원반을 올릴 수 없다.
 * 모든 원반을 다른 기둥으로 옮기는 데 필요한 최소 이동 횟수는 2^n - 1.
 *
 * <ul>
 *   <li>시간 복잡도 : O(2^n) — 정의에 충실한 상한이자 하한</li>
 *   <li>공간 복잡도 : O(n) — 재귀 깊이</li>
 * </ul>
 *
 * <p>"위쪽 n-1 개 옮기고, 큰 원반 1 개 옮기고, 다시 n-1 개 옮긴다" 가 책에서는 한 줄인데
 * 책 덮고 코드로 옮기려니 잘 안 잡혔다. n=3 정도로 호출 스택을 종이에 그려 보면
 * 결국 코드는 그 정의를 그대로 받아 적은 것뿐이라는 게 보인다.
 */
public final class TowerOfHanoi {

    private TowerOfHanoi() {}

    public record Move(int disk, char from, char to) {}

    /** n 개의 원반을 from 기둥에서 to 기둥으로 옮기는 이동 순서를 돌려준다. */
    public static List<Move> solve(int n) {
        List<Move> moves = new ArrayList<>();
        if (n > 0) move(n, 'A', 'C', 'B', moves);
        return moves;
    }

    private static void move(int n, char from, char to, char via, List<Move> moves) {
        if (n == 1) {
            moves.add(new Move(1, from, to));
            return;
        }
        move(n - 1, from, via, to, moves);     // 위 n-1 개를 보조 기둥으로
        moves.add(new Move(n, from, to));      // 가장 큰 한 개를 목적지로
        move(n - 1, via, to, from, moves);     // 보조 기둥의 n-1 개를 목적지로
    }
}
