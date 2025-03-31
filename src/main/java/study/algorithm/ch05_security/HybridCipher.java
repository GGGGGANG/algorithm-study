package study.algorithm.ch05_security;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 학습용 하이브리드 암호.
 *
 * <p>대칭키 암호는 빠르지만 키를 어떻게 안전하게 공유할지 어려움이 있고,
 * 공개키 암호는 키 공유는 쉽지만 메시지가 길어지면 비싸다.
 * 이 둘을 합쳐 (1) 메시지는 빠른 대칭키로 암호화하고,
 * (2) 그때 사용한 대칭키는 짧은 한 덩어리이므로 공개키로 따로 안전하게 전달한다.
 *
 * <p>송신자: 임의의 대칭키 K 생성 → C1 = Sym(message, K), C2 = RSA(K, 수신자 공개키) → (C1, C2) 전달.
 * 수신자: K = RSA-decrypt(C2, 자기 비밀키) → message = Sym(C1, K).
 *
 * <p>이 구현은 학습용이므로 RSA 와 XOR 모두 본 저장소의 토이 구현을 사용한다.
 */
public final class HybridCipher {

    private HybridCipher() {}

    public record EncryptedMessage(byte[] cipherText, BigInteger encryptedKey) {}

    public static EncryptedMessage encrypt(
            byte[] message,
            PublicKeyCipher.PublicKey receiverPublic,
            SecureRandom random) {

        byte[] symmetricKey = new byte[16];
        random.nextBytes(symmetricKey);

        byte[] cipherText = SymmetricCipher.xorCrypt(message, symmetricKey);

        // 대칭키를 BigInteger 로 변환해 RSA 로 감싸 보낸다.
        BigInteger keyAsInt = new BigInteger(1, symmetricKey);
        BigInteger encryptedKey = PublicKeyCipher.encrypt(keyAsInt, receiverPublic);

        return new EncryptedMessage(cipherText, encryptedKey);
    }

    public static byte[] decrypt(
            EncryptedMessage encrypted,
            PublicKeyCipher.PrivateKey receiverPrivate) {

        BigInteger keyAsInt = PublicKeyCipher.decrypt(encrypted.encryptedKey, receiverPrivate);
        byte[] symmetricKey = unsignedBytes(keyAsInt, 16);
        return SymmetricCipher.xorCrypt(encrypted.cipherText, symmetricKey);
    }

    /** BigInteger 를 length 바이트로 left-pad 한 양의 표현으로 환원. */
    private static byte[] unsignedBytes(BigInteger value, int length) {
        byte[] raw = value.toByteArray();
        if (raw.length == length) return raw;
        byte[] result = new byte[length];
        if (raw.length > length) {
            // 부호 비트 때문에 한 바이트 더 길어진 경우(예: length+1) 의 head 0x00 을 잘라낸다.
            System.arraycopy(raw, raw.length - length, result, 0, length);
        } else {
            System.arraycopy(raw, 0, result, length - raw.length, raw.length);
        }
        return result;
    }
}
