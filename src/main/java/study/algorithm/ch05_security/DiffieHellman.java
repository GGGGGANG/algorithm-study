package study.algorithm.ch05_security;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 학습용 디피-헬만 키 교환.
 *
 * <p>공개된 큰 소수 p 와 g (g 는 Z_p 의 원시근에 해당) 가 모두에게 알려져 있다.
 * Alice 가 비밀 a 를 골라 A = g^a mod p 를 공개하고, Bob 이 비밀 b 를 골라 B = g^b mod p 를 공개한다.
 * 두 사람은 각각 B^a mod p 와 A^b mod p 를 계산하는데 둘 다 g^(ab) mod p 로 같다.
 * 도청자는 A, B, g, p 만 보고 a 또는 b 를 끌어내야 하는데, 그것이 이산 로그 문제라 어렵다는 게 안전성의 근거.
 *
 * <p>두 사람이 같은 비밀에 도달한다는 결과는 알고 있었지만, 핵심은 그냥 g^(ab) = g^(ba) 한 줄이다.
 * Alice 와 Bob 의 코드를 나란히 놓고 보면 양쪽 다 modPow 한 번이라는 게 분명히 보인다.
 */
public final class DiffieHellman {

    private DiffieHellman() {}

    public record Parameters(BigInteger p, BigInteger g) {}

    public record Party(BigInteger privateValue, BigInteger publicValue) {}

    /** 학습용 파라미터를 만든다. 큰 소수 p 와 작은 g(=2 또는 5) 를 쓴다. */
    public static Parameters parameters(int bitLength, SecureRandom random) {
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        // g 는 학습용으로 2 를 사용. 실제로는 안전한 prime 과 generator 를 함께 사용해야 한다.
        return new Parameters(p, BigInteger.TWO);
    }

    public static Party generate(Parameters params, SecureRandom random) {
        // private value 는 [2, p-2] 범위에서 무작위로 고른다.
        BigInteger pMinusTwo = params.p.subtract(BigInteger.TWO);
        BigInteger priv;
        do {
            priv = new BigInteger(params.p.bitLength(), random);
        } while (priv.compareTo(BigInteger.TWO) < 0 || priv.compareTo(pMinusTwo) > 0);

        BigInteger pub = params.g.modPow(priv, params.p);
        return new Party(priv, pub);
    }

    /** 자기 비밀과 상대의 공개값으로부터 공유 비밀을 계산한다. */
    public static BigInteger sharedSecret(Party self, BigInteger otherPublic, Parameters params) {
        return otherPublic.modPow(self.privateValue, params.p);
    }
}
