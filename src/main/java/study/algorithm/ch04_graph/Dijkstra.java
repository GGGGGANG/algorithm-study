package study.algorithm.ch04_graph;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 다익스트라 알고리즘.
 *
 * <p>음의 가중치가 없는 그래프에서 단일 출발점 최단 경로를 구한다.
 * 우선순위 큐(최소 힙) 에 (현재까지의 최단 거리, 정점) 쌍을 넣고
 * 가장 가까운 정점을 꺼내 그 정점에서 갈 수 있는 이웃들을 완화한다.
 *
 * <ul>
 *   <li>시간 복잡도 : O((V + E) log V)</li>
 *   <li>공간 복잡도 : O(V)</li>
 *   <li>전제 조건 : 모든 간선의 가중치가 음이 아니어야 한다</li>
 * </ul>
 *
 * <p>처음엔 PQ 에 같은 정점이 여러 번 들어가도 되는지 헷갈렸는데, 그래도 된다.
 * 거리가 갱신될 때마다 새 항목을 그냥 넣고, 꺼낼 때 d &gt; dist[v] 면 stale 이라 무시하면 된다.
 * 이 패턴 덕분에 decrease-key 같은 연산을 따로 만들 필요가 없다.
 */
public final class Dijkstra {

    private Dijkstra() {}

    /** 가중치가 있는 인접 리스트의 한 항목. */
    public record Edge(int to, int weight) {}

    public static int[] shortestPaths(List<List<Edge>> adj, int source) {
        int n = adj.size();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // (현재까지의 거리, 정점) 을 거리 오름차순으로 관리한다.
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.add(new long[]{0L, source});

        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            long d = top[0];
            int v = (int) top[1];

            // PQ 에서 꺼낸 거리가 이미 알려진 최단보다 크면 stale 항목이다. 무시.
            if (d > dist[v]) continue;

            for (Edge e : adj.get(v)) {
                long nd = d + e.weight;
                if (nd < dist[e.to]) {
                    dist[e.to] = (int) nd;
                    pq.add(new long[]{nd, e.to});
                }
            }
        }
        return dist;
    }
}
