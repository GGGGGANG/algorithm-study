package study.algorithm.ch05_security;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class MessageAuthenticationCodeTest {

    @Test
    void 같은_키와_메시지면_같은_태그() {
        byte[] key = "k".getBytes(StandardCharsets.UTF_8);
        long t1 = MessageAuthenticationCode.compute(key, "hello");
        long t2 = MessageAuthenticationCode.compute(key, "hello");
        assertEquals(t1, t2);
        assertTrue(MessageAuthenticationCode.verify(key, "hello".getBytes(StandardCharsets.UTF_8), t1));
    }

    @Test
    void 메시지가_변조되면_태그가_달라진다() {
        byte[] key = "k".getBytes(StandardCharsets.UTF_8);
        long t = MessageAuthenticationCode.compute(key, "hello");
        assertFalse(MessageAuthenticationCode.verify(key, "hellp".getBytes(StandardCharsets.UTF_8), t));
    }

    @Test
    void 다른_키로는_검증이_실패한다() {
        byte[] keyA = "kA".getBytes(StandardCharsets.UTF_8);
        byte[] keyB = "kB".getBytes(StandardCharsets.UTF_8);
        long t = MessageAuthenticationCode.compute(keyA, "msg");
        assertFalse(MessageAuthenticationCode.verify(keyB, "msg".getBytes(StandardCharsets.UTF_8), t));
    }
}
