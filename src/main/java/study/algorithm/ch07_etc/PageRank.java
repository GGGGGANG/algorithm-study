package study.algorithm.ch07_etc;

import java.util.List;

/**
 * 페이지랭크.
 *
 * <p>웹 페이지의 중요도를 "다른 중요한 페이지가 많이 링크해 줄수록 더 중요하다" 라는 재귀적 정의로 잡고,
 * 그 정의를 만족하는 점수를 반복 계산으로 찾는다.
 * 매 반복마다 각 페이지는 자기 점수를 자기가 가리키는 모든 페이지에게 동등하게 나눠 주고,
 * 받은 점수의 합이 다음 반복에서의 새 점수가 된다.
 * 무작위 서퍼가 들고 다니는 damping factor d 는 보통 0.85 로 두고,
 * 1 - d 의 확률로 임의의 페이지로 점프한다는 흐름을 더해 dead-end 와 spider trap 을 피한다.
 *
 * <p>"수렴한다" 는 게 처음엔 그냥 받아들여야 하는 사실이었다.
 * 정점 4 개짜리 작은 그래프로 1, 2, 3, ... 회 갱신해 보면 값들이 점점 안정되는 게 눈에 보이고,
 * 결국 행렬 거듭제곱이 정상상태로 가는 과정이라는 게 그렇게 한 번 돌려 보면 자연스럽다.
 */
public final class PageRank {

    private PageRank() {}

    /**
     * @param outLinks   adj 형태의 링크. outLinks[i] 는 i 가 가리키는 페이지들의 인덱스 목록.
     * @param damping    감쇠 계수 (보통 0.85)
     * @param iterations 반복 횟수
     * @return 페이지별 페이지랭크 점수. 합은 1 에 가까이 유지된다.
     */
    public static double[] compute(List<List<Integer>> outLinks, double damping, int iterations) {
        int n = outLinks.size();
        double[] rank = new double[n];
        for (int i = 0; i < n; i++) rank[i] = 1.0 / n;

        for (int iter = 0; iter < iterations; iter++) {
            double[] next = new double[n];

            // dangling: outlink 가 없는 노드의 점수는 모두에게 균등 분배한다.
            double danglingMass = 0;
            for (int i = 0; i < n; i++) {
                if (outLinks.get(i).isEmpty()) danglingMass += rank[i];
            }

            for (int i = 0; i < n; i++) {
                List<Integer> outs = outLinks.get(i);
                if (outs.isEmpty()) continue;
                double share = rank[i] / outs.size();
                for (int j : outs) {
                    next[j] += share;
                }
            }

            for (int i = 0; i < n; i++) {
                next[i] = (1 - damping) / n
                        + damping * (next[i] + danglingMass / n);
            }
            rank = next;
        }
        return rank;
    }
}
