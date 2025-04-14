package study.algorithm.ch05_security;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 학습용 메시지 인증 코드(MAC).
 *
 * <p>"이 메시지는 비밀 키 K 를 알고 있는 사람이 보낸 것이고 도중에 변조되지 않았다" 는 사실을
 * 동시에 검증하기 위한 짧은 태그.
 * 가장 단순한 형태로 hash(key || message) 를 사용한다.
 *
 * <p>실무에서는 표준 HMAC 구성과 안전한 해시(예: SHA-256)를 사용해야 한다.
 * 단순 hash(key||message) 는 length-extension 공격에 취약하다.
 */
public final class MessageAuthenticationCode {

    private MessageAuthenticationCode() {}

    /** 키와 메시지를 이어 붙인 뒤 본 저장소의 토이 해시로 태그를 만든다. */
    public static long compute(byte[] key, byte[] message) {
        byte[] joined = ByteBuffer.allocate(key.length + message.length)
                .put(key)
                .put(message)
                .array();
        return HashFunction.hash(joined);
    }

    public static long compute(byte[] key, String message) {
        return compute(key, message.getBytes(StandardCharsets.UTF_8));
    }

    /** 받은 메시지와 태그가 같은 키로부터 만들어졌는지 검증한다. */
    public static boolean verify(byte[] key, byte[] message, long tag) {
        return compute(key, message) == tag;
    }
}
