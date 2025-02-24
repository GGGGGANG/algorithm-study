package study.algorithm.ch04_graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 크루스칼 알고리즘 (최소 신장 트리).
 *
 * <p>모든 간선을 가중치 오름차순으로 정렬해 두고, 가벼운 간선부터 차례로 보면서
 * "이 간선을 추가해도 사이클이 생기지 않으면" 트리에 포함시킨다.
 * 사이클 여부는 Union-Find(서로소 집합) 자료구조로 amortized 거의 상수 시간에 판별한다.
 *
 * <ul>
 *   <li>시간 복잡도 : O(E log E) — 간선 정렬이 지배적</li>
 *   <li>공간 복잡도 : O(V)</li>
 * </ul>
 *
 * <p>Union-Find 의 path compression 이 왜 amortized 거의 O(1) 인지 처음엔 잘 안 잡혔다.
 * find 가 호출될 때마다 그 경로 위 노드들이 모두 루트를 직접 가리키게 평탄화되니까,
 * 한 번 깊이 들어간 경로는 다음번 find 에서 한 단계로 끝난다.
 */
public final class Kruskal {

    private Kruskal() {}

    public record Edge(int u, int v, int weight) {}

    public static final class Result {
        public final List<Edge> mstEdges;
        public final long totalWeight;
        Result(List<Edge> mstEdges, long totalWeight) {
            this.mstEdges = mstEdges;
            this.totalWeight = totalWeight;
        }
    }

    public static Result minimumSpanningTree(int vertexCount, List<Edge> edges) {
        List<Edge> sorted = new ArrayList<>(edges);
        sorted.sort(Comparator.comparingInt(Edge::weight));

        UnionFind uf = new UnionFind(vertexCount);
        List<Edge> mst = new ArrayList<>();
        long total = 0;

        for (Edge e : sorted) {
            if (uf.union(e.u, e.v)) {     // 다른 집합이었다면 합쳐지고 true
                mst.add(e);
                total += e.weight;
                if (mst.size() == vertexCount - 1) break;
            }
        }
        return new Result(mst, total);
    }

    /** path compression + union by rank 가 적용된 서로소 집합. */
    static final class UnionFind {
        private final int[] parent;
        private final int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);     // 경로 압축
            }
            return parent[x];
        }

        /** 같은 집합이면 false, 합쳐졌다면 true. */
        boolean union(int a, int b) {
            int ra = find(a);
            int rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) {
                parent[ra] = rb;
            } else if (rank[ra] > rank[rb]) {
                parent[rb] = ra;
            } else {
                parent[rb] = ra;
                rank[ra]++;
            }
            return true;
        }
    }
}
