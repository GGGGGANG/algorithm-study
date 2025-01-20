package study.algorithm.ch04_graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * 너비 우선 탐색(BFS).
 *
 * <p>시작 정점에서 시작해 가까운 거리부터 한 겹씩 탐색해 나간다.
 * 가중치가 없는 그래프(또는 모든 간선의 가중치가 같은 그래프)에서의 최단 경로 길이를
 * 자연스럽게 알려주는 알고리즘.
 *
 * <ul>
 *   <li>시간 복잡도 : O(V + E)</li>
 *   <li>공간 복잡도 : O(V) — 방문 표시 배열과 큐</li>
 * </ul>
 *
 * <p>입력은 인접 리스트 형태(`adj.get(v)` 가 v 의 이웃 리스트)로 받는다.
 */
public final class BreadthFirstSearch {

    private BreadthFirstSearch() {}

    /** 시작 정점에서 각 정점까지의 최단 거리(간선 개수). 도달 불가는 -1. */
    public static int[] distances(List<List<Integer>> adj, int start) {
        int n = adj.size();
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[start] = 0;

        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (int u : adj.get(v)) {
                if (dist[u] == -1) {
                    dist[u] = dist[v] + 1;
                    queue.add(u);
                }
            }
        }
        return dist;
    }
}
