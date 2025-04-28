package study.algorithm.ch05_security;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 학습용 디지털 인증서.
 *
 * <p>"내가 가진 이 공개키는 정말 그 이름을 가진 주체의 것인가" 를 신뢰할 수 있는 제3자(CA) 의 서명으로 보증한다.
 * 인증서는 (subject 이름, subject 공개키) 와 그 두 정보에 대한 CA 의 서명을 함께 담는다.
 * 검증자는 CA 의 공개키를 미리 알고 있다는 가정 하에 그 서명을 풀어 본문과 일치하는지 확인한다.
 *
 * <p>"체인" 이라는 단어가 책으로는 잘 안 잡혔는데 직접 만들어 보니 단순했다.
 * 한 인증서의 서명을 검증하려면 발급한 CA 의 공개키가 필요하고, 그 CA 도 또 다른 인증서로
 * 자기 공개키를 보증한다. 끝까지 올라가다 보면 결국 self-signed 한 루트 CA 에서 멈춘다.
 *
 * <p>본 구현은 두 단계 구조(루트 CA → 종단 인증서)만 다룬다.
 */
public final class DigitalCertificate {

    /** 인증서의 본문(서명 대상)과 서명을 함께 들고 다닌다. */
    public record Certificate(
            String subjectName,
            PublicKeyCipher.PublicKey subjectPublicKey,
            String issuerName,
            BigInteger issuerSignature) {}

    private DigitalCertificate() {}

    /** CA 가 (subjectName, subjectPublicKey) 에 대해 인증서를 발급한다. */
    public static Certificate issue(
            String subjectName,
            PublicKeyCipher.PublicKey subjectPublicKey,
            String issuerName,
            PublicKeyCipher.PrivateKey issuerPrivateKey) {
        byte[] body = body(subjectName, subjectPublicKey);
        BigInteger signature = DigitalSignature.sign(body, issuerPrivateKey);
        return new Certificate(subjectName, subjectPublicKey, issuerName, signature);
    }

    /** 발급자(CA) 의 공개키로 인증서의 서명을 검증한다. */
    public static boolean verify(Certificate cert, PublicKeyCipher.PublicKey issuerPublicKey) {
        byte[] body = body(cert.subjectName, cert.subjectPublicKey);
        return DigitalSignature.verify(body, cert.issuerSignature, issuerPublicKey);
    }

    /** 서명 대상을 한 덩어리의 바이트로 직렬화한다. */
    private static byte[] body(String subjectName, PublicKeyCipher.PublicKey publicKey) {
        byte[] name = subjectName.getBytes(StandardCharsets.UTF_8);
        byte[] e = publicKey.e().toByteArray();
        byte[] n = publicKey.n().toByteArray();
        return ByteBuffer.allocate(name.length + e.length + n.length)
                .put(name).put(e).put(n)
                .array();
    }
}
