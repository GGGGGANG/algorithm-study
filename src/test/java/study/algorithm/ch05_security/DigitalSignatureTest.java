package study.algorithm.ch05_security;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class DigitalSignatureTest {

    @Test
    void 서명_검증_왕복() {
        SecureRandom rand = new SecureRandom("sig-seed".getBytes());
        PublicKeyCipher.KeyPair pair = PublicKeyCipher.generate(512, rand);

        String message = "이 문장이 변조되지 않았음을 증명한다";
        BigInteger signature = DigitalSignature.sign(message, pair.privateKey());
        assertTrue(DigitalSignature.verify(message, signature, pair.publicKey()));
    }

    @Test
    void 메시지가_변조되면_검증_실패() {
        SecureRandom rand = new SecureRandom("sig-seed-2".getBytes());
        PublicKeyCipher.KeyPair pair = PublicKeyCipher.generate(512, rand);

        String message = "원본";
        BigInteger signature = DigitalSignature.sign(message, pair.privateKey());
        assertFalse(DigitalSignature.verify("변조본", signature, pair.publicKey()));
    }

    @Test
    void 다른_공개키로는_검증_실패() {
        SecureRandom rand = new SecureRandom("sig-seed-3".getBytes());
        PublicKeyCipher.KeyPair signer = PublicKeyCipher.generate(512, rand);
        PublicKeyCipher.KeyPair other = PublicKeyCipher.generate(512, rand);

        BigInteger sig = DigitalSignature.sign("hi", signer.privateKey());
        assertFalse(DigitalSignature.verify("hi", sig, other.publicKey()));
    }
}
