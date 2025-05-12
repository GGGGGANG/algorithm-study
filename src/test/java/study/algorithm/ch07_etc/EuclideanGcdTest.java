package study.algorithm.ch07_etc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EuclideanGcdTest {

    @Test
    void 표준_케이스() {
        assertEquals(6, EuclideanGcd.gcd(48, 18));
        assertEquals(1, EuclideanGcd.gcd(17, 13));
        assertEquals(12, EuclideanGcd.gcd(36, 60));
    }

    @Test
    void 영을_포함하는_입력() {
        assertEquals(7, EuclideanGcd.gcd(7, 0));
        assertEquals(7, EuclideanGcd.gcd(0, 7));
        assertEquals(0, EuclideanGcd.gcd(0, 0));
    }

    @Test
    void 음수도_절댓값_기준으로_계산된다() {
        assertEquals(6, EuclideanGcd.gcd(-48, 18));
        assertEquals(6, EuclideanGcd.gcd(48, -18));
        assertEquals(6, EuclideanGcd.gcd(-48, -18));
    }
}
