package study.algorithm.ch04_graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KruskalTest {

    @Test
    void 표준_예제의_MST_가중치() {
        // 정점 0..4
        // 0-1(1), 0-2(4), 1-2(2), 1-3(5), 2-3(3), 3-4(2), 2-4(6)
        List<Kruskal.Edge> edges = List.of(
                new Kruskal.Edge(0,1,1),
                new Kruskal.Edge(0,2,4),
                new Kruskal.Edge(1,2,2),
                new Kruskal.Edge(1,3,5),
                new Kruskal.Edge(2,3,3),
                new Kruskal.Edge(3,4,2),
                new Kruskal.Edge(2,4,6)
        );
        Kruskal.Result r = Kruskal.minimumSpanningTree(5, edges);
        assertEquals(8, r.totalWeight);    // 1 + 2 + 3 + 2
        assertEquals(4, r.mstEdges.size());
    }

    @Test
    void 단일_정점은_빈_MST() {
        Kruskal.Result r = Kruskal.minimumSpanningTree(1, List.of());
        assertEquals(0, r.totalWeight);
        assertEquals(0, r.mstEdges.size());
    }

    @Test
    void 분리된_그래프는_연결된_컴포넌트만_묶는다() {
        // 정점 4 개, {0,1} 과 {2,3} 만 연결
        List<Kruskal.Edge> edges = List.of(
                new Kruskal.Edge(0,1,5),
                new Kruskal.Edge(2,3,2)
        );
        Kruskal.Result r = Kruskal.minimumSpanningTree(4, edges);
        assertEquals(7, r.totalWeight);
        assertEquals(2, r.mstEdges.size());
    }
}
