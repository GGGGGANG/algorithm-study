package study.algorithm.ch04_graph;

import java.util.Arrays;
import java.util.List;

/**
 * 벨만-포드 알고리즘.
 *
 * <p>음의 가중치를 가진 간선까지 다룰 수 있는 단일 출발점 최단 경로 알고리즘.
 * 모든 간선을 (V-1) 번 완화(relax) 하는 단순한 반복으로 동작한다.
 * V 번째 반복에서 또 완화가 일어난다면 그래프에 음의 사이클이 존재한다는 뜻이다.
 *
 * <ul>
 *   <li>시간 복잡도 : O(V * E)</li>
 *   <li>공간 복잡도 : O(V)</li>
 * </ul>
 *
 * <p>처음엔 왜 V-1 번이면 충분한지 헷갈렸다. 한 번 모든 간선을 완화하면
 * 길이 1 짜리 최단경로가 확정되고, k 번 반복하면 길이 k 짜리가 확정된다.
 * 최단경로의 길이는 길어야 V-1 이라 그만큼만 돌리면 끝나는 거였다.
 */
public final class BellmanFord {

    private BellmanFord() {}

    /** 한 개의 가중치 간선. */
    public record Edge(int from, int to, int weight) {}

    public static final class Result {
        /** 음의 사이클이 검출되었는지. */
        public final boolean hasNegativeCycle;
        /** 도달 불가 정점은 Integer.MAX_VALUE. */
        public final int[] dist;
        Result(boolean hasNegativeCycle, int[] dist) {
            this.hasNegativeCycle = hasNegativeCycle;
            this.dist = dist;
        }
    }

    public static Result shortestPaths(int vertexCount, List<Edge> edges, int source) {
        int[] dist = new int[vertexCount];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // V-1 번 모든 간선을 완화한다.
        for (int i = 0; i < vertexCount - 1; i++) {
            boolean updated = false;
            for (Edge e : edges) {
                if (dist[e.from] == Integer.MAX_VALUE) continue;
                long candidate = (long) dist[e.from] + e.weight;
                if (candidate < dist[e.to]) {
                    dist[e.to] = (int) candidate;
                    updated = true;
                }
            }
            if (!updated) break;     // 더 이상 갱신이 없으면 조기 종료
        }

        // 한 번 더 완화가 가능하면 음의 사이클 존재.
        for (Edge e : edges) {
            if (dist[e.from] == Integer.MAX_VALUE) continue;
            if ((long) dist[e.from] + e.weight < dist[e.to]) {
                return new Result(true, dist);
            }
        }
        return new Result(false, dist);
    }
}
