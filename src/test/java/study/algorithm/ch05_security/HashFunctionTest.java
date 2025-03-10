package study.algorithm.ch05_security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashFunctionTest {

    @Test
    void 같은_입력은_항상_같은_출력() {
        long a = HashFunction.hash("hello world");
        long b = HashFunction.hash("hello world");
        assertEquals(a, b);
    }

    @Test
    void 다른_입력은_다른_출력일_가능성이_매우_높다() {
        assertNotEquals(HashFunction.hash("hello"), HashFunction.hash("world"));
    }

    @Test
    void 한_바이트만_바뀌어도_큰_변경() {
        long a = HashFunction.hash("kitten");
        long b = HashFunction.hash("kitten ");
        long c = HashFunction.hash("Kitten");
        assertNotEquals(a, b);
        assertNotEquals(a, c);
    }

    @Test
    void 빈_입력도_안전하게_처리() {
        long h = HashFunction.hash(new byte[0]);
        // 단순히 예외 없이 어떤 값을 돌려주면 된다.
        assertNotEquals(0, h);
    }
}
