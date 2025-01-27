package study.algorithm.ch04_graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 깊이 우선 탐색(DFS).
 *
 * <p>한 길을 끝까지 따라 들어갔다가 막히면 직전 분기점으로 돌아와 다른 길을 따라간다.
 * 재귀 호출 스택으로 자연스럽게 구현되며, 사이클 검출이나 위상 정렬, 강한 연결 요소 등의
 * 토대가 된다.
 *
 * <ul>
 *   <li>시간 복잡도 : O(V + E)</li>
 *   <li>공간 복잡도 : O(V) — 재귀 스택과 방문 표시</li>
 * </ul>
 */
public final class DepthFirstSearch {

    private DepthFirstSearch() {}

    /** 시작 정점에서부터 방문한 순서를 돌려준다. */
    public static List<Integer> visitOrder(List<List<Integer>> adj, int start) {
        boolean[] visited = new boolean[adj.size()];
        List<Integer> order = new ArrayList<>();
        dfs(adj, start, visited, order);
        return order;
    }

    private static void dfs(List<List<Integer>> adj, int v, boolean[] visited, List<Integer> order) {
        if (visited[v]) return;
        visited[v] = true;
        order.add(v);
        for (int u : adj.get(v)) {
            if (!visited[u]) dfs(adj, u, visited, order);
        }
    }
}
