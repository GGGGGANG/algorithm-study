package study.algorithm.ch05_security;

/**
 * 학습용 대칭키 암호.
 *
 * <p>송신자와 수신자가 같은 키를 공유한다는 개념을 보이기 위한 XOR 스트림 암호.
 * 짧은 키를 데이터 길이에 맞춰 반복하며 XOR 한다.
 * XOR 의 성질 덕분에 같은 함수가 암호화와 복호화 모두를 처리한다.
 *
 * <p>실무에서는 AES (java.crypto.Cipher) 와 안전한 키/IV 관리를 사용해야 한다.
 */
public final class SymmetricCipher {

    private SymmetricCipher() {}

    public static byte[] xorCrypt(byte[] data, byte[] key) {
        if (key.length == 0) throw new IllegalArgumentException("key 가 비어 있다");
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return result;
    }
}
