package study.algorithm.ch06_clustering;

import java.util.Arrays;
import java.util.Random;

/**
 * k-means 클러스터링 (Lloyd 알고리즘).
 *
 * <p>주어진 2차원 점들을 k 개의 군집으로 나눈다.
 * (1) 무작위로 k 개의 중심을 정한다,
 * (2) 각 점을 가장 가까운 중심에 배정한다,
 * (3) 같은 군집에 속한 점들의 평균으로 중심을 갱신한다,
 * (4) 중심이 더 이상 움직이지 않거나 정해진 반복 횟수를 채울 때까지 (2)–(3) 을 반복한다.
 *
 * <ul>
 *   <li>시간 복잡도 : O(n * k * iterations)</li>
 *   <li>지역 최적해에 빠질 수 있다 — 초기 중심에 따라 결과가 달라진다</li>
 * </ul>
 */
public final class KMeans {

    private KMeans() {}

    public static final class Result {
        /** 각 점이 속한 군집 인덱스. */
        public final int[] assignments;
        /** 최종 중심 좌표. */
        public final double[][] centers;
        Result(int[] assignments, double[][] centers) {
            this.assignments = assignments;
            this.centers = centers;
        }
    }

    public static Result cluster(double[][] points, int k, int maxIterations, Random random) {
        if (points.length < k) throw new IllegalArgumentException("점의 수가 k 보다 작다");
        int n = points.length;

        double[][] centers = pickInitialCenters(points, k, random);
        int[] assignments = new int[n];
        Arrays.fill(assignments, -1);

        for (int iter = 0; iter < maxIterations; iter++) {
            boolean changed = false;
            for (int i = 0; i < n; i++) {
                int nearest = nearestCenter(points[i], centers);
                if (nearest != assignments[i]) {
                    assignments[i] = nearest;
                    changed = true;
                }
            }
            if (!changed) break;
            centers = recomputeCenters(points, assignments, k, centers);
        }
        return new Result(assignments, centers);
    }

    /** 입력 점들 중에서 무작위로 k 개를 골라 초기 중심으로 삼는다. */
    private static double[][] pickInitialCenters(double[][] points, int k, Random random) {
        // Fisher–Yates 의 앞 k 개만 결정.
        int[] indices = new int[points.length];
        for (int i = 0; i < indices.length; i++) indices[i] = i;
        for (int i = 0; i < k; i++) {
            int j = i + random.nextInt(indices.length - i);
            int tmp = indices[i]; indices[i] = indices[j]; indices[j] = tmp;
        }
        double[][] centers = new double[k][];
        for (int i = 0; i < k; i++) {
            centers[i] = points[indices[i]].clone();
        }
        return centers;
    }

    private static int nearestCenter(double[] point, double[][] centers) {
        int best = 0;
        double bestDist = squaredDistance(point, centers[0]);
        for (int i = 1; i < centers.length; i++) {
            double d = squaredDistance(point, centers[i]);
            if (d < bestDist) {
                bestDist = d;
                best = i;
            }
        }
        return best;
    }

    private static double squaredDistance(double[] a, double[] b) {
        double dx = a[0] - b[0];
        double dy = a[1] - b[1];
        return dx * dx + dy * dy;
    }

    private static double[][] recomputeCenters(double[][] points, int[] assignments, int k, double[][] previous) {
        double[][] sums = new double[k][2];
        int[] counts = new int[k];
        for (int i = 0; i < points.length; i++) {
            int c = assignments[i];
            sums[c][0] += points[i][0];
            sums[c][1] += points[i][1];
            counts[c]++;
        }
        double[][] centers = new double[k][2];
        for (int c = 0; c < k; c++) {
            if (counts[c] == 0) {
                // 비어 있는 군집은 이전 중심을 유지한다(빈 클러스터 보호).
                centers[c] = previous[c].clone();
            } else {
                centers[c][0] = sums[c][0] / counts[c];
                centers[c][1] = sums[c][1] / counts[c];
            }
        }
        return centers;
    }
}
