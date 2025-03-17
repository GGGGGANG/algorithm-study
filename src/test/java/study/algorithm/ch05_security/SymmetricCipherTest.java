package study.algorithm.ch05_security;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class SymmetricCipherTest {

    @Test
    void 같은_키로_두_번_적용하면_원문이_돌아온다() {
        byte[] key = "secret-key".getBytes(StandardCharsets.UTF_8);
        byte[] plain = "메시지를 비밀로 보낸다".getBytes(StandardCharsets.UTF_8);

        byte[] cipher = SymmetricCipher.xorCrypt(plain, key);
        byte[] recovered = SymmetricCipher.xorCrypt(cipher, key);

        assertArrayEquals(plain, recovered);
        assertFalse(java.util.Arrays.equals(plain, cipher));
    }

    @Test
    void 다른_키로_복호화하면_원문이_나오지_않는다() {
        byte[] key = "key-A".getBytes(StandardCharsets.UTF_8);
        byte[] wrong = "key-B".getBytes(StandardCharsets.UTF_8);
        byte[] plain = "hello".getBytes(StandardCharsets.UTF_8);

        byte[] cipher = SymmetricCipher.xorCrypt(plain, key);
        byte[] recovered = SymmetricCipher.xorCrypt(cipher, wrong);
        assertFalse(java.util.Arrays.equals(plain, recovered));
    }

    @Test
    void 빈_키는_예외() {
        assertThrows(IllegalArgumentException.class,
                () -> SymmetricCipher.xorCrypt(new byte[]{1}, new byte[]{}));
    }
}
