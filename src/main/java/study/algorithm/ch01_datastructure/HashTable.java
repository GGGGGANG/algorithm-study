package study.algorithm.ch01_datastructure;

/**
 * 체이닝 방식의 해시 테이블.
 *
 * <p>키의 해시값을 버킷 개수로 나눈 나머지를 인덱스로 사용하고,
 * 같은 인덱스에 떨어진 항목들은 단일 연결 리스트로 이어 둔다(체이닝).
 *
 * <ul>
 *   <li>put, get, remove : 평균 O(1), 최악 O(n) (모든 키가 한 버킷에 몰리는 경우)</li>
 * </ul>
 *
 * <p>load factor(원소수 / 버킷수) 가 임계값을 넘으면 버킷 개수를 두 배로 늘리고
 * 모든 항목을 다시 분배한다. 충돌이 잦으면 한 버킷에 긴 체인이 쌓여 O(n) 에 가까워지기 때문이다.
 */
public class HashTable<K, V> {

    private static final int DEFAULT_BUCKETS = 8;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private static final class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        Entry(K key, V value) { this.key = key; this.value = value; }
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V>[] buckets = (Entry<K, V>[]) new Entry[DEFAULT_BUCKETS];
    private int size;

    public void put(K key, V value) {
        int idx = bucketIndexOf(key, buckets.length);
        for (Entry<K, V> e = buckets[idx]; e != null; e = e.next) {
            if (equalsKey(e.key, key)) {
                e.value = value;     // 같은 키면 값만 갱신
                return;
            }
        }
        // 새 항목을 체인 머리에 끼워 넣는다.
        Entry<K, V> head = buckets[idx];
        Entry<K, V> entry = new Entry<>(key, value);
        entry.next = head;
        buckets[idx] = entry;
        size++;

        if ((double) size / buckets.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    public V get(K key) {
        int idx = bucketIndexOf(key, buckets.length);
        for (Entry<K, V> e = buckets[idx]; e != null; e = e.next) {
            if (equalsKey(e.key, key)) return e.value;
        }
        return null;
    }

    public V remove(K key) {
        int idx = bucketIndexOf(key, buckets.length);
        Entry<K, V> prev = null;
        for (Entry<K, V> e = buckets[idx]; e != null; e = e.next) {
            if (equalsKey(e.key, key)) {
                if (prev == null) buckets[idx] = e.next;
                else prev.next = e.next;
                size--;
                return e.value;
            }
            prev = e;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    private int bucketIndexOf(K key, int bucketCount) {
        int h = (key == null) ? 0 : key.hashCode();
        // 음수 해시값을 양수로 강제하기 위해 부호 비트를 마스킹한다.
        return (h & 0x7fffffff) % bucketCount;
    }

    private boolean equalsKey(K a, K b) {
        return a == null ? b == null : a.equals(b);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] expanded = (Entry<K, V>[]) new Entry[buckets.length * 2];
        for (Entry<K, V> head : buckets) {
            for (Entry<K, V> e = head; e != null; ) {
                Entry<K, V> next = e.next;
                int idx = bucketIndexOf(e.key, expanded.length);
                e.next = expanded[idx];
                expanded[idx] = e;
                e = next;
            }
        }
        buckets = expanded;
    }
}
