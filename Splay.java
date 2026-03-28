public class Splay<E extends Comparable<E>> {
    private static class Node<E> {
        public E data;
        public Node<E> left, right, parent;

        public Node(E data, Node<E> parent) {
            this.data = data;
            this.parent = parent;
        }
    }

    public Node<E> root;

    public Splay() {
        root = null;
    }

    public void insert(E value) {
        root = insert(value, root, null);
    }

    public boolean search(E value) {
        Node<E> searched = search(value, root);
        if (searched != null) {
            splayToRoot(searched);
            return true;
        }
        return false;
    }

    public void clear() {
        root = null;
    }

    public void print() {
        print(root);
    }

    private Node<E> insert(E value, Node<E> t, Node<E> parent) {
        if (t == null)
            return new Node<>(value, parent);

        int compare = value.compareTo(t.data);

        if (compare < 0)
            t.left = insert(value, t.left, t);
        else if (compare > 0)
            t.right = insert(value, t.right, t);
        else
            System.out.println("Value already exists");

        return t;
    }

    private Node<E> search(E value, Node<E> t) {
        if (t == null)
            return null;

        int compare = value.compareTo(t.data);

        if (compare < 0)
            return search(value, t.left);
        else if (compare > 0)
            return search(value, t.right);
        return t;
    }

    private void print(Node<E> t) {
        if (t == null) {
            System.out.println("Tree is empty");
            return;
        }

        print(t.left);
        System.out.println(t.data);
        print(t.right);
    }

    private Node<E> splayToRoot(Node<E> t) {
        if (t == null || t.parent == null)
            return t;

        while (t.parent != null) {
            Node<E> p = t.parent;
            Node<E> g = p.parent;

            // zig
            if (g == null) {
                if (t == p.left) rotateRight(p);
                else rotateLeft(p);
            }

            // zig-zig
            else if (t == p.left && p == g.left) {
                rotateRight(g);
                rotateRight(p);
            }
            // zag-zag
            else if (t == p.right && p == g.right) {
                rotateLeft(g);
                rotateLeft(p);
            }
            // zig-zag
            else if (t == p.left && p == g.right) {
                rotateRight(p);
                rotateLeft(g);
            }
            // zag-zig
            else {
                rotateLeft(p);
                rotateRight(g);
            }
        }

        root = t;
        return t;
    }

    private void rotateLeft(Node<E> x) {
        Node<E> y = x.right;
        if (y == null)
            return;

        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node<E> x) {
        Node<E> y = x.left;
        if (y == null)
            return;

        x.left = y.right;
        if (y.right != null)
            y.right.parent = x;

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.right = x;
        x.parent = y;
    }
}