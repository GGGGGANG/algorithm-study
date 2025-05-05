package study.algorithm.ch06_clustering;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class KMeansTest {

    @Test
    void 분명히_분리된_두_무리는_서로_다른_군집으로_묶인다() {
        double[][] points = {
                {0, 0}, {0.1, -0.1}, {-0.1, 0.05}, {0.05, 0.1},
                {10, 10}, {10.1, 9.9}, {9.95, 10.05}, {10.05, 10.1}
        };
        KMeans.Result r = KMeans.cluster(points, 2, 50, new Random(0));

        // 같은 무리 내 점들은 같은 군집에 속해야 한다.
        int firstGroup = r.assignments[0];
        for (int i = 1; i < 4; i++) assertEquals(firstGroup, r.assignments[i]);
        int secondGroup = r.assignments[4];
        for (int i = 5; i < 8; i++) assertEquals(secondGroup, r.assignments[i]);
        assertNotEquals(firstGroup, secondGroup);
    }

    @Test
    void 군집_중심들은_서로_멀리_떨어져_위치한다() {
        // 초기 중심이 운 나쁘게 한쪽 무리에서 둘 다 뽑히는 경우를 피하기 위해
        // 여러 시드로 실행해 보고, 그중 한 번이라도 두 중심이 잘 분리되면 합격으로 본다.
        // 이는 "초기값에 따라 결과가 달라진다" 는 k-means 의 알려진 한계 자체를 인정하는 검증이다.
        double[][] points = {
                {0, 0}, {0.5, 0}, {0, 0.5}, {0.5, 0.5},
                {100, 100}, {100.5, 100}, {100, 100.5}, {100.5, 100.5}
        };
        boolean separated = false;
        for (long seed = 0; seed < 20 && !separated; seed++) {
            KMeans.Result r = KMeans.cluster(points, 2, 50, new Random(seed));
            double[] a = r.centers[0];
            double[] b = r.centers[1];
            double dist = Math.hypot(a[0] - b[0], a[1] - b[1]);
            if (dist > 100) separated = true;     // 두 무리 간 진짜 거리는 약 141
        }
        assertTrue(separated, "어떤 시드에서도 두 군집이 분리되지 않았다");
    }

    @Test
    void 점의_수가_k_보다_작으면_예외() {
        double[][] points = {{0, 0}};
        assertThrows(IllegalArgumentException.class,
                () -> KMeans.cluster(points, 3, 10, new Random()));
    }
}
