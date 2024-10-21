package study.algorithm.ch01_datastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    void push_pop_은_LIFO_순서를_지킨다() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void peek_은_꺼내지_않는다() {
        Stack<String> stack = new Stack<>();
        stack.push("hello");
        stack.push("world");

        assertEquals("world", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void 빈_스택의_pop_은_예외() {
        Stack<Integer> stack = new Stack<>();
        assertThrows(java.util.NoSuchElementException.class, stack::pop);
    }

    @Test
    void capacity_초과시에도_LIFO_순서_유지() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= 50; i++) stack.push(i);
        for (int i = 50; i >= 1; i--) {
            assertEquals(i, stack.pop());
        }
    }
}
