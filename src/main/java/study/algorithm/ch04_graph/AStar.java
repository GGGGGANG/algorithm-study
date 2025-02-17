package study.algorithm.ch04_graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A* 알고리즘.
 *
 * <p>다익스트라가 "지금까지 온 거리(g)" 가 작은 정점부터 펼쳐 본다면,
 * A* 는 "지금까지 온 거리(g) + 목표까지의 추정 거리(h)" 가 작은 정점부터 펼쳐 본다.
 * h(휴리스틱) 가 admissible(목표까지의 실제 최단보다 결코 크지 않다)하다면 결과가 최적이다.
 *
 * <p>이 구현은 격자(grid) 위에서 동작하며 휴리스틱은 맨해튼 거리를 쓴다.
 * 격자의 각 셀은 0 또는 1 이며 1 은 통과 불가. 가중치는 모두 1.
 *
 * <ul>
 *   <li>시간 복잡도 : 휴리스틱 품질에 크게 의존. 최악 O(b^d)</li>
 *   <li>공간 복잡도 : O(셀 수)</li>
 * </ul>
 *
 * <p>휴리스틱이 admissible 해야 한다는 조건이 왜 중요한지 처음엔 잘 몰랐다.
 * h 가 실제 거리보다 크면 더 짧은 경로가 PQ 에서 한참 뒤로 밀리면서 결국 최적해를 놓치게 된다.
 * 작은 격자에 일부러 큰 h 를 넣어 돌려 보면 바로 보인다.
 */
public final class AStar {

    private AStar() {}

    /** grid: 0 통과 가능, 1 벽. start, goal 은 (row, col). 경로 없으면 빈 리스트. */
    public static List<int[]> shortestPath(int[][] grid, int[] start, int[] goal) {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] gScore = new int[rows][cols];
        for (int[] row : gScore) Arrays.fill(row, Integer.MAX_VALUE);
        int[][] cameFromR = new int[rows][cols];
        int[][] cameFromC = new int[rows][cols];
        for (int[] row : cameFromR) Arrays.fill(row, -1);

        gScore[start[0]][start[1]] = 0;
        // (f-score, r, c). f = g + h
        PriorityQueue<int[]> open = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        open.add(new int[]{heuristic(start, goal), start[0], start[1]});

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!open.isEmpty()) {
            int[] cur = open.poll();
            int r = cur[1], c = cur[2];

            if (r == goal[0] && c == goal[1]) {
                return reconstruct(cameFromR, cameFromC, goal);
            }

            for (int[] d : dirs) {
                int nr = r + d[0];
                int nc = c + d[1];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                if (grid[nr][nc] == 1) continue;

                int tentative = gScore[r][c] + 1;
                if (tentative < gScore[nr][nc]) {
                    gScore[nr][nc] = tentative;
                    cameFromR[nr][nc] = r;
                    cameFromC[nr][nc] = c;
                    int f = tentative + heuristic(new int[]{nr, nc}, goal);
                    open.add(new int[]{f, nr, nc});
                }
            }
        }
        return Collections.emptyList();
    }

    private static int heuristic(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }

    private static List<int[]> reconstruct(int[][] fromR, int[][] fromC, int[] goal) {
        List<int[]> path = new ArrayList<>();
        int r = goal[0], c = goal[1];
        while (r != -1) {
            path.add(new int[]{r, c});
            int pr = fromR[r][c];
            int pc = fromC[r][c];
            r = pr;
            c = pc;
        }
        Collections.reverse(path);
        return path;
    }
}
