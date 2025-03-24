package study.algorithm.ch05_security;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class PublicKeyCipherTest {

    @Test
    void 암호화_복호화_왕복() {
        SecureRandom rand = new SecureRandom("seed-1".getBytes());
        PublicKeyCipher.KeyPair pair = PublicKeyCipher.generate(512, rand);

        BigInteger m = new BigInteger("123456789");
        BigInteger c = PublicKeyCipher.encrypt(m, pair.publicKey());
        BigInteger r = PublicKeyCipher.decrypt(c, pair.privateKey());

        assertEquals(m, r);
        assertNotEquals(m, c);
    }

    @Test
    void 다른_키쌍의_비밀키로는_복호화되지_않는다() {
        SecureRandom rand = new SecureRandom("seed-2".getBytes());
        PublicKeyCipher.KeyPair a = PublicKeyCipher.generate(512, rand);
        PublicKeyCipher.KeyPair b = PublicKeyCipher.generate(512, rand);

        BigInteger m = new BigInteger("987654321");
        BigInteger c = PublicKeyCipher.encrypt(m, a.publicKey());
        BigInteger r = PublicKeyCipher.decrypt(c, b.privateKey());
        assertNotEquals(m, r);
    }

    @Test
    void 메시지가_n보다_크면_예외() {
        SecureRandom rand = new SecureRandom("seed-3".getBytes());
        PublicKeyCipher.KeyPair pair = PublicKeyCipher.generate(64, rand);
        BigInteger tooBig = pair.publicKey().n().add(BigInteger.ONE);
        assertThrows(IllegalArgumentException.class,
                () -> PublicKeyCipher.encrypt(tooBig, pair.publicKey()));
    }
}
