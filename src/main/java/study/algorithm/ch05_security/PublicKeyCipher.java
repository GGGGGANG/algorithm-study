package study.algorithm.ch05_security;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 학습용 RSA 공개키 암호.
 *
 * <p>두 개의 큰 소수 p, q 를 골라 n = p * q 와 phi = (p-1)(q-1) 을 만든 뒤,
 * gcd(e, phi) = 1 인 e 를 정하고 e * d ≡ 1 (mod phi) 인 d 를 구한다.
 * 공개키는 (e, n), 비밀키는 (d, n).
 *
 * <p>암호화 : c = m^e mod n, 복호화 : m = c^d mod n.
 *
 * <ul>
 *   <li>이 구현의 키 길이는 학습용으로 작게(기본 512 비트) 잡혀 있어 실제 보안 용도로 쓸 수 없다.</li>
 *   <li>실무에서는 java.security.KeyPairGenerator + javax.crypto.Cipher 와
 *       2048 비트 이상의 키, OAEP 같은 패딩을 사용해야 한다.</li>
 * </ul>
 *
 * <p>왜 e * d ≡ 1 (mod phi) 이면 m^(ed) ≡ m (mod n) 이 되는지 책에서는 한 줄로 넘어가는데,
 * p=11, q=13 같은 작은 수로 직접 계산해 보면 페르마-오일러 정리에서 나오는
 * m^(k*phi+1) ≡ m (mod n) 이라는 식이 알고리즘 전체를 떠받치는 핵심이라는 게 보인다.
 */
public final class PublicKeyCipher {

    private PublicKeyCipher() {}

    public record PublicKey(BigInteger e, BigInteger n) {}

    public record PrivateKey(BigInteger d, BigInteger n) {}

    public record KeyPair(PublicKey publicKey, PrivateKey privateKey) {}

    public static KeyPair generate(int bitLength, SecureRandom random) {
        if (bitLength < 32) throw new IllegalArgumentException("bitLength 가 너무 작다");
        BigInteger p = BigInteger.probablePrime(bitLength / 2, random);
        BigInteger q;
        do {
            q = BigInteger.probablePrime(bitLength / 2, random);
        } while (q.equals(p));

        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = BigInteger.valueOf(65537);   // 흔히 쓰이는 공개 지수
        if (!phi.gcd(e).equals(BigInteger.ONE)) {
            // phi 가 65537 의 배수인 드문 경우 — 다른 e 를 찾는다.
            e = BigInteger.valueOf(3);
            while (!phi.gcd(e).equals(BigInteger.ONE)) {
                e = e.add(BigInteger.TWO);
            }
        }
        BigInteger d = e.modInverse(phi);
        return new KeyPair(new PublicKey(e, n), new PrivateKey(d, n));
    }

    public static BigInteger encrypt(BigInteger message, PublicKey pub) {
        if (message.signum() < 0 || message.compareTo(pub.n) >= 0) {
            throw new IllegalArgumentException("메시지는 [0, n) 범위여야 한다");
        }
        return message.modPow(pub.e, pub.n);
    }

    public static BigInteger decrypt(BigInteger cipher, PrivateKey priv) {
        return cipher.modPow(priv.d, priv.n);
    }
}
