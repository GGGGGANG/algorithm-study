package study.algorithm.ch04_graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimTest {

    private static List<List<Prim.Edge>> graph(int n, int[][] edges) {
        List<List<Prim.Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(new Prim.Edge(e[1], e[2]));
            adj.get(e[1]).add(new Prim.Edge(e[0], e[2]));
        }
        return adj;
    }

    @Test
    void Kruskal_과_같은_총_가중치_를_돌려준다() {
        // 위 KruskalTest 의 입력과 같다.
        List<List<Prim.Edge>> g = graph(5, new int[][]{
                {0,1,1}, {0,2,4}, {1,2,2}, {1,3,5}, {2,3,3}, {3,4,2}, {2,4,6}
        });
        Prim.Result r = Prim.minimumSpanningTree(g, 0);
        assertEquals(8, r.totalWeight);
    }

    @Test
    void 시작점의_부모는_minus_1() {
        List<List<Prim.Edge>> g = graph(3, new int[][]{{0,1,1},{1,2,1}});
        Prim.Result r = Prim.minimumSpanningTree(g, 0);
        assertEquals(-1, r.parent[0]);
    }

    @Test
    void 단일_정점() {
        List<List<Prim.Edge>> g = graph(1, new int[][]{});
        Prim.Result r = Prim.minimumSpanningTree(g, 0);
        assertEquals(0, r.totalWeight);
    }
}
