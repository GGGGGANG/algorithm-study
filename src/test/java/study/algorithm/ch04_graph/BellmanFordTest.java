package study.algorithm.ch04_graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BellmanFordTest {

    @Test
    void 음의_가중치를_포함한_그래프의_최단경로() {
        // 0->1(6), 0->2(7), 1->2(8), 1->3(5), 1->4(-4),
        // 2->3(-3), 2->4(9), 3->1(-2), 4->0(2), 4->3(7)
        List<BellmanFord.Edge> edges = List.of(
                new BellmanFord.Edge(0, 1, 6),
                new BellmanFord.Edge(0, 2, 7),
                new BellmanFord.Edge(1, 2, 8),
                new BellmanFord.Edge(1, 3, 5),
                new BellmanFord.Edge(1, 4, -4),
                new BellmanFord.Edge(2, 3, -3),
                new BellmanFord.Edge(2, 4, 9),
                new BellmanFord.Edge(3, 1, -2),
                new BellmanFord.Edge(4, 0, 2),
                new BellmanFord.Edge(4, 3, 7)
        );

        BellmanFord.Result r = BellmanFord.shortestPaths(5, edges, 0);
        assertFalse(r.hasNegativeCycle);
        assertArrayEquals(new int[]{0, 2, 7, 4, -2}, r.dist);
    }

    @Test
    void 음의_사이클이_검출된다() {
        // 0 -> 1 -> 2 -> 0 의 합이 음수
        List<BellmanFord.Edge> edges = List.of(
                new BellmanFord.Edge(0, 1, 1),
                new BellmanFord.Edge(1, 2, -3),
                new BellmanFord.Edge(2, 0, 1)
        );
        BellmanFord.Result r = BellmanFord.shortestPaths(3, edges, 0);
        assertTrue(r.hasNegativeCycle);
    }

    @Test
    void 도달_불가능한_정점은_MAX_VALUE() {
        List<BellmanFord.Edge> edges = List.of(
                new BellmanFord.Edge(0, 1, 5)
        );
        BellmanFord.Result r = BellmanFord.shortestPaths(3, edges, 0);
        assertEquals(0, r.dist[0]);
        assertEquals(5, r.dist[1]);
        assertEquals(Integer.MAX_VALUE, r.dist[2]);
    }
}
