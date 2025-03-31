package study.algorithm.ch05_security;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class HybridCipherTest {

    @Test
    void 송신_수신_왕복으로_원문이_복원된다() {
        SecureRandom rand = new SecureRandom("hybrid-seed".getBytes());
        PublicKeyCipher.KeyPair receiver = PublicKeyCipher.generate(512, rand);

        byte[] message = "긴 메시지를 효율적으로 암호화하기 위한 하이브리드 방식의 시연.".getBytes(StandardCharsets.UTF_8);
        HybridCipher.EncryptedMessage envelope = HybridCipher.encrypt(message, receiver.publicKey(), rand);

        byte[] recovered = HybridCipher.decrypt(envelope, receiver.privateKey());
        assertArrayEquals(message, recovered);
    }

    @Test
    void 암호문은_원문과_다르다() {
        SecureRandom rand = new SecureRandom("hybrid-seed-2".getBytes());
        PublicKeyCipher.KeyPair receiver = PublicKeyCipher.generate(512, rand);

        byte[] message = "abc".getBytes(StandardCharsets.UTF_8);
        HybridCipher.EncryptedMessage envelope = HybridCipher.encrypt(message, receiver.publicKey(), rand);
        assertFalse(java.util.Arrays.equals(message, envelope.cipherText()));
    }
}
