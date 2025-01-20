package study.algorithm.ch04_graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BreadthFirstSearchTest {

    private static List<List<Integer>> undirected(int n, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        return adj;
    }

    @Test
    void 가까운_것부터_거리가_매겨진다() {
        List<List<Integer>> g = undirected(5, new int[][]{{0,1},{0,2},{1,3},{2,3},{3,4}});
        int[] dist = BreadthFirstSearch.distances(g, 0);
        assertArrayEquals(new int[]{0, 1, 1, 2, 3}, dist);
    }

    @Test
    void 도달_불가능한_정점은_minus_1() {
        List<List<Integer>> g = undirected(4, new int[][]{{0,1},{2,3}});
        int[] dist = BreadthFirstSearch.distances(g, 0);
        assertArrayEquals(new int[]{0, 1, -1, -1}, dist);
    }

    @Test
    void 단일_정점_그래프() {
        List<List<Integer>> g = undirected(1, new int[][]{});
        int[] dist = BreadthFirstSearch.distances(g, 0);
        assertEquals(1, dist.length);
        assertEquals(0, dist[0]);
    }
}
