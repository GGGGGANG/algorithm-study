package study.algorithm.ch04_graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AStarTest {

    @Test
    void 단순한_빈_격자에서_최단_경로_길이는_맨해튼_거리() {
        int[][] grid = new int[5][5];     // 모두 0 — 통과 가능
        List<int[]> path = AStar.shortestPath(grid, new int[]{0,0}, new int[]{4,4});
        // 4 + 4 = 8 칸 이동 → 경로 길이 9 (시작 포함)
        assertEquals(9, path.size());
        assertArrayEquals(new int[]{0,0}, path.get(0));
        assertArrayEquals(new int[]{4,4}, path.get(path.size()-1));
    }

    @Test
    void 벽을_피해_우회한다() {
        // 가운데 가로벽
        int[][] grid = {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
        };
        List<int[]> path = AStar.shortestPath(grid, new int[]{0,0}, new int[]{4,0});
        assertFalse(path.isEmpty());
        // 우회로 경로 길이는 9 칸 (시작 포함). (0,0)→(0,4)→(4,4)→(4,0) 의 (4+4+4)+1=13.
        // 더 짧게는 (0,0) → (1,0) → (1,4) → (3,4) → (3,0) → (4,0) : 1+4+2+4+1 = 12 칸 + 시작 1 = 13.
        assertEquals(13, path.size());
        // 벽을 통과하지 않았는지 확인
        for (int[] p : path) {
            assertEquals(0, grid[p[0]][p[1]], "벽 위에 있는 좌표가 경로에 들어갔다");
        }
    }

    @Test
    void 막혀있으면_빈_경로() {
        int[][] grid = {
                {0, 1, 0},
                {1, 1, 0},
                {0, 1, 0},
        };
        List<int[]> path = AStar.shortestPath(grid, new int[]{0,0}, new int[]{2,2});
        assertTrue(path.isEmpty());
    }
}
