package study.algorithm.ch05_security;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DiffieHellmanTest {

    @Test
    void 두_사람이_같은_공유_비밀에_도달한다() {
        SecureRandom rand = new SecureRandom("dh-seed".getBytes());
        DiffieHellman.Parameters params = DiffieHellman.parameters(256, rand);

        DiffieHellman.Party alice = DiffieHellman.generate(params, rand);
        DiffieHellman.Party bob = DiffieHellman.generate(params, rand);

        BigInteger aliceShared = DiffieHellman.sharedSecret(alice, bob.publicValue(), params);
        BigInteger bobShared = DiffieHellman.sharedSecret(bob, alice.publicValue(), params);

        assertEquals(aliceShared, bobShared);
    }

    @Test
    void 다른_세션은_다른_비밀로_이어진다() {
        SecureRandom rand = new SecureRandom("dh-seed-2".getBytes());
        DiffieHellman.Parameters params = DiffieHellman.parameters(256, rand);

        DiffieHellman.Party alice1 = DiffieHellman.generate(params, rand);
        DiffieHellman.Party bob1 = DiffieHellman.generate(params, rand);
        DiffieHellman.Party alice2 = DiffieHellman.generate(params, rand);
        DiffieHellman.Party bob2 = DiffieHellman.generate(params, rand);

        BigInteger s1 = DiffieHellman.sharedSecret(alice1, bob1.publicValue(), params);
        BigInteger s2 = DiffieHellman.sharedSecret(alice2, bob2.publicValue(), params);
        assertNotEquals(s1, s2);
    }
}
