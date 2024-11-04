package study.algorithm.ch01_datastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void put_과_get_의_왕복() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("apple", 1);
        table.put("banana", 2);
        table.put("cherry", 3);

        assertEquals(1, table.get("apple"));
        assertEquals(2, table.get("banana"));
        assertEquals(3, table.get("cherry"));
        assertNull(table.get("durian"));
    }

    @Test
    void 같은_키의_재삽입은_값을_덮어쓴다() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("k", 1);
        table.put("k", 2);
        assertEquals(2, table.get("k"));
        assertEquals(1, table.size());
    }

    @Test
    void remove_는_체인에서_정확한_노드만_제거한다() {
        HashTable<Integer, String> table = new HashTable<>();
        // 1000 개를 넣고 절반을 지운다 → 충돌이 자연스럽게 발생.
        for (int i = 0; i < 1000; i++) table.put(i, "v" + i);
        for (int i = 0; i < 500; i++) assertEquals("v" + i, table.remove(i));

        assertEquals(500, table.size());
        for (int i = 0; i < 500; i++) assertNull(table.get(i));
        for (int i = 500; i < 1000; i++) assertEquals("v" + i, table.get(i));
    }

    @Test
    void load_factor_초과시_resize_되어도_조회_가능() {
        HashTable<Integer, Integer> table = new HashTable<>();
        for (int i = 0; i < 1024; i++) table.put(i, i * 7);
        for (int i = 0; i < 1024; i++) {
            assertEquals(i * 7, table.get(i));
        }
    }
}
