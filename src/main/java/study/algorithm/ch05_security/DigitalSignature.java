package study.algorithm.ch05_security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * 학습용 디지털 서명 (RSA 기반).
 *
 * <p>송신자가 자신의 비밀키로 "메시지 해시" 를 암호화한 결과를 서명으로 만든다.
 * 수신자는 송신자의 공개키로 서명을 복호화해 얻은 값이 메시지 해시와 같은지 비교해 검증한다.
 * 서명 위조에는 비밀키가 필요하므로, 서명이 일치한다면 그 메시지가 그 키 소유자에게서 왔고
 * 도중에 변조되지 않았다는 점을 함께 보장한다.
 *
 * <p>"공개키 암호를 반대로 쓴다" 한 문장으로 끝나는 설명을 코드로 옮길 때 두 가지가 더 필요했다.
 * (1) 메시지 자체가 아니라 해시를 서명해야 하고,
 * (2) 해시 값을 [0, n) 범위로 맞추기 위해 mod n 을 한 번 더 해야 한다.
 * 둘 다 임의 길이 메시지를 한 번의 modPow 에 넣기 위한 절차다.
 */
public final class DigitalSignature {

    private DigitalSignature() {}

    /** 비밀키로 메시지에 서명한다. */
    public static BigInteger sign(byte[] message, PublicKeyCipher.PrivateKey privateKey) {
        BigInteger digest = digestModN(message, privateKey.n());
        return digest.modPow(privateKey.d(), privateKey.n());
    }

    public static BigInteger sign(String message, PublicKeyCipher.PrivateKey privateKey) {
        return sign(message.getBytes(StandardCharsets.UTF_8), privateKey);
    }

    /** 공개키로 서명을 검증한다. */
    public static boolean verify(byte[] message, BigInteger signature, PublicKeyCipher.PublicKey publicKey) {
        BigInteger expected = digestModN(message, publicKey.n());
        BigInteger recovered = signature.modPow(publicKey.e(), publicKey.n());
        return expected.equals(recovered);
    }

    public static boolean verify(String message, BigInteger signature, PublicKeyCipher.PublicKey publicKey) {
        return verify(message.getBytes(StandardCharsets.UTF_8), signature, publicKey);
    }

    /** 토이 해시 결과를 [0, n) 으로 묶어 BigInteger 로 만든다. */
    private static BigInteger digestModN(byte[] message, BigInteger n) {
        long hash = HashFunction.hash(message);
        // hash 는 부호 있는 64비트지만 우리는 양의 표현이 필요하므로 toUnsignedString 사용.
        return new BigInteger(Long.toUnsignedString(hash)).mod(n);
    }
}
