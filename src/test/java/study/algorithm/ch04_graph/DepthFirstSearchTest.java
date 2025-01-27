package study.algorithm.ch04_graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DepthFirstSearchTest {

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
    void 모든_연결된_정점이_방문된다() {
        List<List<Integer>> g = undirected(5, new int[][]{{0,1},{0,2},{1,3},{2,4}});
        List<Integer> order = DepthFirstSearch.visitOrder(g, 0);

        assertEquals(5, order.size());
        for (int v = 0; v < 5; v++) assertTrue(order.contains(v));
    }

    @Test
    void 시작점이_먼저_방문된다() {
        List<List<Integer>> g = undirected(3, new int[][]{{0,1},{1,2}});
        List<Integer> order = DepthFirstSearch.visitOrder(g, 1);
        assertEquals(1, order.get(0));
        assertEquals(3, order.size());
    }

    @Test
    void 분리된_컴포넌트는_방문되지_않는다() {
        List<List<Integer>> g = undirected(4, new int[][]{{0,1},{2,3}});
        List<Integer> order = DepthFirstSearch.visitOrder(g, 0);
        assertEquals(2, order.size());
        assertTrue(order.contains(0));
        assertTrue(order.contains(1));
    }
}
