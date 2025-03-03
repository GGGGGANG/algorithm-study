package study.algorithm.ch04_graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 프림 알고리즘 (최소 신장 트리).
 *
 * <p>임의의 한 정점에서 시작해 트리를 한 정점씩 키워 나간다.
 * 매 단계에서 "현재 트리에 연결된 간선들 중 가장 가벼운 것" 을 골라
 * 트리에 새 정점을 끌어들인다.
 *
 * <ul>
 *   <li>시간 복잡도 : O((V + E) log V) — 우선순위 큐 사용</li>
 *   <li>공간 복잡도 : O(V + E)</li>
 * </ul>
 *
 * <p>처음엔 크루스칼이랑 다익스트라랑 뭐가 다른지 잘 안 잡혔다.
 * 코드 골격(PQ + 방문 표시) 은 다익스트라와 거의 같고, 차이는 PQ 의 비교 기준 하나다.
 * 다익스트라는 "시작점에서의 누적 거리", 프림은 "트리에 닿는 한 개 간선의 가중치" 를 쓴다.
 */
public final class Prim {

    private Prim() {}

    public record Edge(int to, int weight) {}

    public static final class Result {
        public final long totalWeight;
        public final int[] parent;        // parent[v] = 트리에서 v 의 부모 (시작점은 -1)
        Result(long totalWeight, int[] parent) {
            this.totalWeight = totalWeight;
            this.parent = parent;
        }
    }

    public static Result minimumSpanningTree(List<List<Edge>> adj, int start) {
        int n = adj.size();
        boolean[] inTree = new boolean[n];
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = -1;

        // (간선 가중치, from, to) — 트리에 닿는 간선들을 가벼운 순으로 본다.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        pq.add(new int[]{0, -1, start});
        long total = 0;

        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int w = top[0], from = top[1], to = top[2];
            if (inTree[to]) continue;       // 이미 트리에 있으면 사이클 — 무시.
            inTree[to] = true;
            total += w;
            parent[to] = from;
            for (Edge e : adj.get(to)) {
                if (!inTree[e.to]) {
                    pq.add(new int[]{e.weight, to, e.to});
                }
            }
        }
        return new Result(total, parent);
    }
}
