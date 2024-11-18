package study.algorithm.ch01_datastructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    @Test
    void contains_는_삽입한_키만_true_를_돌려준다() {
        BinarySearchTree tree = new BinarySearchTree();
        int[] keys = {5, 3, 8, 1, 4, 7, 9};
        for (int k : keys) tree.insert(k);

        for (int k : keys) assertTrue(tree.contains(k));
        assertFalse(tree.contains(2));
        assertFalse(tree.contains(10));
    }

    @Test
    void 자식이_둘인_노드의_삭제는_후계자로_교체된다() {
        BinarySearchTree tree = new BinarySearchTree();
        int[] keys = {5, 3, 8, 1, 4, 7, 9};
        for (int k : keys) tree.insert(k);

        tree.remove(5);          // 후계자는 7
        assertFalse(tree.contains(5));
        for (int k : new int[]{3, 8, 1, 4, 7, 9}) assertTrue(tree.contains(k));
    }

    @Test
    void 잎_노드_삭제와_단일자식_노드_삭제() {
        BinarySearchTree tree = new BinarySearchTree();
        int[] keys = {5, 3, 8, 1, 9};
        for (int k : keys) tree.insert(k);

        tree.remove(1);          // 잎 노드
        assertFalse(tree.contains(1));
        tree.remove(8);          // 오른쪽 자식 9 만 가진 노드
        assertFalse(tree.contains(8));
        assertTrue(tree.contains(9));
    }

    @Test
    void 중복_삽입은_size_를_증가시키지_않는다() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(7);
        tree.insert(7);
        assertEquals(1, tree.size());
    }
}
