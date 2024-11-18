package study.algorithm.ch01_datastructure;

/**
 * 정수 키 기반의 이진 탐색 트리(BST).
 *
 * <p>모든 노드 N 에 대해 왼쪽 서브트리의 모든 키 &lt; N.key &lt; 오른쪽 서브트리의 모든 키
 * 를 유지한다. 균형을 맞추는 회전은 들어 있지 않은 평범한 BST 라
 * 최악의 경우(정렬된 입력) 트리는 한쪽으로 쏠려 O(n) 까지 떨어진다.
 *
 * <ul>
 *   <li>insert, contains, remove : 평균 O(log n), 최악 O(n)</li>
 * </ul>
 *
 * <p>삭제가 제일 손이 많이 갔다. 자식이 두 개일 때 오른쪽 서브트리의 최솟값으로 키를 바꾸고
 * 그 최솟값을 다시 삭제하는 흐름이 코드로 옮길 때마다 헷갈렸다.
 * 재귀가 자식 포인터를 새 서브트리로 교체해 돌려준다는 관점으로 보면
 * 자식 0/1/2 모든 경우가 같은 골격이라 한 번에 정리된다.
 */
public class BinarySearchTree {

    private static final class Node {
        int key;
        Node left, right;
        Node(int key) { this.key = key; }
    }

    private Node root;
    private int size;

    public void insert(int key) {
        int before = size;
        root = insert(root, key);
        if (size != before) {
            // 새로 삽입된 경우만 size 가 증가한다(중복은 무시).
        }
    }

    private Node insert(Node node, int key) {
        if (node == null) {
            size++;
            return new Node(key);
        }
        if (key < node.key) node.left = insert(node.left, key);
        else if (key > node.key) node.right = insert(node.right, key);
        // 같은 키는 중복 삽입하지 않는다.
        return node;
    }

    public boolean contains(int key) {
        Node cur = root;
        while (cur != null) {
            if (key == cur.key) return true;
            cur = (key < cur.key) ? cur.left : cur.right;
        }
        return false;
    }

    public void remove(int key) {
        int before = size;
        root = remove(root, key);
        if (size == before) {
            // 존재하지 않는 키였던 경우.
        }
    }

    private Node remove(Node node, int key) {
        if (node == null) return null;
        if (key < node.key) {
            node.left = remove(node.left, key);
        } else if (key > node.key) {
            node.right = remove(node.right, key);
        } else {
            // 자식이 0 또는 1 개인 경우는 그 자식으로 교체.
            if (node.left == null) { size--; return node.right; }
            if (node.right == null) { size--; return node.left; }
            // 자식이 둘인 경우: 후계자(오른쪽 서브트리의 최솟값) 로 교체 후 그 후계자를 삭제.
            Node successor = min(node.right);
            node.key = successor.key;
            node.right = remove(node.right, successor.key);
        }
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }
}
