
import java.util.LinkedList;
import java.util.List;

public class SeparateChainingHashTable<E> {

    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    @SuppressWarnings("unchecked")
    public SeparateChainingHashTable(int size) {
        lists = new LinkedList[nextPrime(size)];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new LinkedList<>();
        }
    }

    public void insert(E x) {
        List<E> whichList = lists[myHash(x)];
        if (!whichList.contains(x)) {
            whichList.add(x);
            if (++currentSize > lists.length) {
                rehash();
            }
        }
    }

    public void remove(E x) {
        List<E> whichList = lists[myHash(x)];
        if (whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }

    public boolean contains(E x) {
        List<E> whichList = lists[myHash(x)];
        return whichList.contains(x);
    }

    public E search(E x) {
        List<E> whichList = lists[myHash(x)];

        for (E item : whichList) {
            if (item.equals(x)) {
                return item;
            }
        }

        return null;
    }

    public void clear() {
        for (List<E> list : lists) {
            list.clear();
        }
        currentSize = 0;
    }

    public void getChain(int index) {
        List<E> whichList = lists[index];
        for (E x : whichList) {
            System.out.println(x);
        }
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    private List<E>[] lists;
    private int currentSize;

    @SuppressWarnings("unchecked")
    private void rehash() {
        List<E>[] oldLists = lists;

        lists = new List[nextPrime(2 * lists.length)];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new LinkedList<>();
        }
        currentSize = 0;
        for (List<E> oldList : oldLists) {
            for (E item : oldList) {
                insert(item);
            }
        }
    }

    private int myHash(E x) {
        int hashVal = x.hashCode();
        hashVal %= lists.length;
        if (hashVal < 0) {
            hashVal += lists.length;
        }

        return hashVal;
    }

    private static int nextPrime(int n) {
        int candidate = n + 1;
        while (!isPrime(candidate)) {
            candidate++;
        }
        return candidate;
    }

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        int sqrt = (int) Math.sqrt(n);
        for (int i = 2; i < sqrt; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
