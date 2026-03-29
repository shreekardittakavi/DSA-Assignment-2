
public class QuadraticProbingHashTable<E> {

    public QuadraticProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public QuadraticProbingHashTable(int size) {
        allocateArray(size);
        clear();
    }

    private void clear() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    public boolean contains(E x) {
        int currentPos = findPos(x);
        return isActive(currentPos);
    }

    public E search(E x) {
        int currentPos = findPos(x);

        if (isActive(currentPos)) {
            return array[currentPos].element;
        }

        return null;
    }

    public void insert(E x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            return;
        }
        array[currentPos] = new HashEntry<>(x);
        if (++currentSize > array.length / 2) {
            rehash();
        }
    }

    public void remove(E x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            array[currentPos].isActive = false;
        }
    }

    public void getQuadraticIndex(E key) {
        int index = findPos(key);
        if (isActive(index)) {
            System.out.println(index);
        } else {
            System.out.println("Element not in table.");
        }
    }

    private static class HashEntry<E> {

        public E element;
        public boolean isActive;

        public HashEntry(E x) {
            this(x, true);
        }

        public HashEntry(E x, boolean i) {
            element = x;
            isActive = i;
        }
    }

    private static final int DEFAULT_TABLE_SIZE = 11;

    private HashEntry<E>[] array;
    private int currentSize;

    @SuppressWarnings("unchecked")
    private void allocateArray(int arraySize) {
        
        array = new HashEntry[nextPrime(arraySize)];
    }

    private boolean isActive(int currentPos) {
        return array[currentPos] != null && array[currentPos].isActive;
    }

    private int findPos(E x) {
        int offset = 1;
        int currentPos = myHash(x);

        while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
            currentPos += offset;
            offset += 2;
            if (currentPos >= array.length) {
                currentPos -= array.length;
            }
        }
        return currentPos;
    }

    private void rehash() {
        HashEntry<E>[] oldArray = array;
        allocateArray(nextPrime(2 * oldArray.length));
        currentSize = 0;
        for (HashEntry<E> oldArray1 : oldArray) {
            if (oldArray1 != null && oldArray1.isActive) {
                insert(oldArray1.element);
            }
        }
    }

    private int myHash(E x) {
        int hashVal = x.hashCode();
        hashVal %= array.length;
        if (hashVal < 0) {
            hashVal += array.length;
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
