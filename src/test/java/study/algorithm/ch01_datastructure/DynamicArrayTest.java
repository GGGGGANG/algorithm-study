package study.algorithm.ch01_datastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void add_시_입력_순서대로_저장된다() {
        DynamicArray array = new DynamicArray();
        for (int i = 0; i < 100; i++) array.add(i);

        assertEquals(100, array.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(i, array.get(i));
        }
    }

    @Test
    void capacity_초과시_doubling_되어도_데이터_보존된다() {
        DynamicArray array = new DynamicArray();
        // 기본 capacity 8 을 넘기는 입력
        for (int i = 1; i <= 1000; i++) array.add(i * 2);

        assertEquals(1000, array.size());
        assertEquals(2, array.get(0));
        assertEquals(2000, array.get(999));
    }

    @Test
    void remove_시_뒤_원소들이_앞으로_당겨진다() {
        DynamicArray array = new DynamicArray();
        array.add(10); array.add(20); array.add(30); array.add(40);

        assertEquals(20, array.remove(1));
        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
        assertEquals(40, array.get(2));
    }

    @Test
    void contains_와_set_동작() {
        DynamicArray array = new DynamicArray();
        array.add(7);
        assertTrue(array.contains(7));
        array.set(0, 9);
        assertFalse(array.contains(7));
        assertTrue(array.contains(9));
    }
}
