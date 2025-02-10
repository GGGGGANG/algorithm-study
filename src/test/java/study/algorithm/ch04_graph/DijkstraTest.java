package study.algorithm.ch04_graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DijkstraTest {

    private static List<List<Dijkstra.Edge>> graph(int n, int[][] edges) {
        List<List<Dijkstra.Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(new Dijkstra.Edge(e[1], e[2]));
            adj.get(e[1]).add(new Dijkstra.Edge(e[0], e[2]));
        }
        return adj;
    }

    @Test
    void 표준적인_무방향_가중_그래프의_최단경로() {
        // 책에 자주 나오는 5 정점 그래프.
        // 0-1(7), 0-2(9), 0-5(14), 1-2(10), 1-3(15),
        // 2-3(11), 2-5(2), 3-4(6), 4-5(9)
        List<List<Dijkstra.Edge>> g = graph(6, new int[][]{
                {0,1,7}, {0,2,9}, {0,5,14}, {1,2,10}, {1,3,15},
                {2,3,11}, {2,5,2}, {3,4,6}, {4,5,9}
        });
        int[] dist = Dijkstra.shortestPaths(g, 0);
        assertArrayEquals(new int[]{0, 7, 9, 20, 20, 11}, dist);
    }

    @Test
    void 도달_불가능한_정점은_MAX_VALUE() {
        List<List<Dijkstra.Edge>> g = graph(3, new int[][]{{0,1,5}});
        int[] dist = Dijkstra.shortestPaths(g, 0);
        assertEquals(0, dist[0]);
        assertEquals(5, dist[1]);
        assertEquals(Integer.MAX_VALUE, dist[2]);
    }

    @Test
    void 단일_정점_그래프() {
        List<List<Dijkstra.Edge>> g = graph(1, new int[][]{});
        int[] dist = Dijkstra.shortestPaths(g, 0);
        assertArrayEquals(new int[]{0}, dist);
    }
}
