package study.algorithm.ch07_etc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TowerOfHanoiTest {

    @Test
    void 이동_횟수는_2의_n승_빼기_1() {
        for (int n = 0; n <= 8; n++) {
            int expected = (n == 0) ? 0 : (1 << n) - 1;
            assertEquals(expected, TowerOfHanoi.solve(n).size(), "n=" + n);
        }
    }

    @Test
    void 모든_이동이_규칙을_지키는지_시뮬레이션으로_확인() {
        int n = 6;
        java.util.Deque<Integer>[] stacks = new java.util.Deque[3];
        for (int i = 0; i < 3; i++) stacks[i] = new java.util.ArrayDeque<>();
        for (int i = n; i >= 1; i--) stacks[0].push(i);   // A 에 큰 것이 아래로 쌓임

        List<TowerOfHanoi.Move> moves = TowerOfHanoi.solve(n);
        for (TowerOfHanoi.Move m : moves) {
            int from = m.from() - 'A';
            int to = m.to() - 'A';
            assertFalse(stacks[from].isEmpty(), "빈 기둥에서 옮기려 했다");
            int disk = stacks[from].pop();
            if (!stacks[to].isEmpty()) {
                assertTrue(disk < stacks[to].peek(), "큰 원반을 작은 원반 위에 올렸다");
            }
            stacks[to].push(disk);
        }

        // 마지막에는 모든 원반이 C 에 큰 것이 아래로 쌓여 있어야 한다.
        assertEquals(n, stacks[2].size());
        int prev = 0;
        while (!stacks[2].isEmpty()) {
            int d = stacks[2].pop();
            assertTrue(d > prev);
            prev = d;
        }
    }
}
