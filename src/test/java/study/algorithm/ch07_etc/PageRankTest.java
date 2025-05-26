package study.algorithm.ch07_etc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageRankTest {

    @Test
    void 합은_거의_1_에_가깝게_유지된다() {
        // 4 개 정점, 0->1, 1->2, 2->0, 2->3, 3->2
        List<List<Integer>> graph = build(4, new int[][]{{0,1},{1,2},{2,0},{2,3},{3,2}});
        double[] r = PageRank.compute(graph, 0.85, 100);

        double sum = 0;
        for (double v : r) sum += v;
        assertEquals(1.0, sum, 1e-6);
    }

    @Test
    void 더_많이_가리켜지는_노드의_랭크가_높다() {
        // 0->1, 0->2, 0->3, 1->3, 2->3 — 노드 3 이 가장 많이 가리켜진다.
        List<List<Integer>> graph = build(4, new int[][]{{0,1},{0,2},{0,3},{1,3},{2,3}});
        double[] r = PageRank.compute(graph, 0.85, 200);

        // 3 이 가장 큰 점수를 가져야 한다.
        int best = 0;
        for (int i = 1; i < r.length; i++) if (r[i] > r[best]) best = i;
        assertEquals(3, best);
        assertTrue(r[3] > r[1] && r[3] > r[2]);
    }

    @Test
    void 모든_노드가_dangling_이어도_안전() {
        // 어떤 노드도 outlink 가 없는 그래프.
        List<List<Integer>> graph = build(3, new int[][]{});
        double[] r = PageRank.compute(graph, 0.85, 50);
        // 균등하게 1/3 씩 나뉘는 게 자연스럽다.
        assertEquals(1.0 / 3, r[0], 1e-9);
        assertEquals(1.0 / 3, r[1], 1e-9);
        assertEquals(1.0 / 3, r[2], 1e-9);
    }

    private static List<List<Integer>> build(int n, int[][] edges) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<>());
        for (int[] e : edges) g.get(e[0]).add(e[1]);
        return g;
    }
}
