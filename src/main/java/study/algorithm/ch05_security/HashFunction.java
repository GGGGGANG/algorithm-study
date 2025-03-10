package study.algorithm.ch05_security;

/**
 * 학습용 해시 함수.
 *
 * <p>실제 보안에 쓰일 수 있는 안전한 해시 함수가 아니다.
 * 책에서 강조하는 해시 함수의 성질
 * (1) 같은 입력은 항상 같은 출력 (2) 출력 길이가 고정 (3) 작은 변경이 큰 변경을 만든다 (눈사태)
 * 가 코드에서 어떻게 드러나는지를 따라가기 위한 다항식 기반의 단순 구현.
 *
 * <p>실제 시스템에서는 SHA-256 등 표준 해시 함수를
 * `java.security.MessageDigest` 를 통해 사용해야 한다.
 */
public final class HashFunction {

    private HashFunction() {}

    /** 다항식 해시: h = (h * 31 + byte) mod 2^64 의 정신을 담은 64비트 정수 해시. */
    public static long hash(byte[] input) {
        long h = 0xcbf29ce484222325L;        // FNV 의 초기값을 빌렸다(임의의 큰 수).
        for (byte b : input) {
            h = h * 1099511628211L;          // FNV prime — 충돌을 줄이기 위해 큰 소수 사용
            h ^= (b & 0xffL);
        }
        return h;
    }

    public static long hash(String input) {
        return hash(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
