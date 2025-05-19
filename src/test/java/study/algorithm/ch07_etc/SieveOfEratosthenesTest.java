package study.algorithm.ch07_etc;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SieveOfEratosthenesTest {

    @Test
    void 작은_범위의_소수() {
        assertEquals(List.of(2, 3, 5, 7), SieveOfEratosthenes.primesUpTo(10));
        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17, 19), SieveOfEratosthenes.primesUpTo(20));
    }

    @Test
    void 경계_값() {
        assertEquals(List.of(), SieveOfEratosthenes.primesUpTo(1));
        assertEquals(List.of(2), SieveOfEratosthenes.primesUpTo(2));
    }

    @Test
    void 백_이하의_소수_개수는_25() {
        // 100 이하 소수는 25 개로 알려져 있다.
        assertEquals(25, SieveOfEratosthenes.primesUpTo(100).size());
    }
}
