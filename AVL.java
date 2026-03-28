import java.util.LinkedList;

public class AVL<E extends Comparable<E>> {
    private static final int ALLOWED_IMBALANCE = 1;

    private static class Node<E> {
        public Node(E d, Node<E> l, Node<E> r) {
            data = d;
            left = l;
            right = r;
            height = 0;
        }

        public Node(E data) {
            this(data, null, null);
        }

        public E data;
        public Node<E> left;
        public Node<E> right;
        int height;
    }

    public Node<E> root;

    public AVL() {
        root = null;
    }

    public void insert(E value) {
        root = insert(value, root);
    }

    public void search(E value) {
        search(value, root);
    }

    public int getAVLKeyHeight(E key) {
        return height(search(key, root));
    }

    public void clear() {
        root = null;
    }

    public void print() {
        print(root);
    }

    private Node<E> insert(E value, Node<E> t) {
        if (t == null)
            return new Node<>(value);

        int compare = value.compareTo(t.data);

        if (compare < 0)
            t.left = insert(value, t.left);
        else if (compare > 0)
            t.right = insert(value, t.right);
        else
            System.out.println("Value already exists");

        return balance(t);
    }

    private Node<E> search(E value, Node<E> t) {
        if (t == null)
            return null;

        int compare = value.compareTo(t.data);

        if (compare < 0)
            return search(value, t.left);
        else if (compare > 0)
            return search(value, t.right);
        else
            return t;
    }

    private void print(Node<E> t) {
        if (t == null) {
            System.out.println("Tree is empty");
            return;
        }

        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.add(t);

        while (!queue.isEmpty()) {
            Node<E> current = queue.removeFirst();
            System.out.print(current.data + " ");

            if (current.left != null)
                queue.add(current.left);
            if (current.right != null)
                queue.add(current.right);
        }
        System.out.println();
    }

    private Node<E> rotateWithLeftChild(Node<E> k2) {
        Node<E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;

        return k1;
    }

    private Node<E> rotateWithRightChild(Node<E> k2) {
        Node<E> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;

        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;

        return k1;
    }

    private Node<E> doubleWithLeftChild(Node<E> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Node<E> doubleWithRightChild(Node<E> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private Node<E> balance(Node<E> t) {
        if (t == null)
            return null;

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right))
                t = rotateWithLeftChild(t);
            else
                t = doubleWithLeftChild(t);
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            if (height(t.right.right) >= height(t.right.left))
                t = rotateWithRightChild(t);
            else
                t = doubleWithRightChild(t);
        }

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private int height(Node<E> t) {
        return t == null ? -1 : t.height;
    }
}