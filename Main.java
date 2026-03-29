import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Store results
    static double[] avlInsert = new double[3];
    static double[] splayInsert = new double[3];
    static double[] chainInsert = new double[3];
    static double[] quadInsert = new double[3];

    static double[] avlSearch = new double[3];
    static double[] splaySearch = new double[3];
    static double[] chainSearch = new double[3];
    static double[] quadSearch = new double[3];

    private static List<Integer> readData(String filename) throws FileNotFoundException {
        List<Integer> data = new ArrayList<>();
        File f = new File(filename);
        try (Scanner scan = new Scanner(f)) {
            while (scan.hasNextInt()) {
                data.add(scan.nextInt());
            }
        }
        return data;
    }

    private static void warmup() {
        AVL<Integer> avlTree = new AVL<>();
        try {
            List<Integer> data = readData("iter3_insert_keys.txt");

            for (int i = 0; i < 10; i++) {
                for (int key : data) {
                    avlTree.insert(key);
                }
                avlTree.clear();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Warmup file error");
        }
    }

    @SuppressWarnings("unchecked")
    private static double measureInsert(Object structure, List<Integer> data) {
        long start = System.nanoTime();

        for (int key : data) {
            if (structure instanceof AVL)
                ((AVL<Integer>) structure).insert(key);
            else if (structure instanceof Splay)
                ((Splay<Integer>) structure).insert(key);
            else if (structure instanceof SeparateChainingHashTable)
                ((SeparateChainingHashTable<Integer>) structure).insert(key);
            else if (structure instanceof QuadraticProbingHashTable)
                ((QuadraticProbingHashTable<Integer>) structure).insert(key);
        }

        long end = System.nanoTime();
        return (end - start) * Math.pow(10, -6);
    }

    @SuppressWarnings("unchecked")
    private static double measureSearch(Object structure, List<Integer> data) {
        long start = System.nanoTime();

        for (int key : data) {
            if (structure instanceof AVL)
                ((AVL<Integer>) structure).search(key);
            else if (structure instanceof Splay)
                ((Splay<Integer>) structure).search(key);
            else if (structure instanceof SeparateChainingHashTable)
                ((SeparateChainingHashTable<Integer>) structure).search(key);
            else if (structure instanceof QuadraticProbingHashTable)
                ((QuadraticProbingHashTable<Integer>) structure).search(key);
        }

        long end = System.nanoTime();
        return (end - start) / 1_000_000.0;
    }

    private static void printTable(String title, double[] avl, double[] splay,
                                   double[] chain, double[] quad) {

        System.out.println("\n" + title);
        System.out.printf("%-25s %-10s %-10s %-10s%n",
                "Data Structure", "1,000", "10,000", "100,000");

        System.out.printf("%-25s %-10.2f %-10.2f %-10.2f%n",
                "AVL Tree", avl[0], avl[1], avl[2]);

        System.out.printf("%-25s %-10.2f %-10.2f %-10.2f%n",
                "Splay Tree", splay[0], splay[1], splay[2]);

        System.out.printf("%-25s %-10.2f %-10.2f %-10.2f%n",
                "Hash Table (Chaining)", chain[0], chain[1], chain[2]);

        System.out.printf("%-25s %-10.2f %-10.2f %-10.2f%n",
                "Hash Table (Quadratic)", quad[0], quad[1], quad[2]);
    }

    public static void main(String[] args) {
        warmup();

        String[] insertFiles = {
                "iter1_insert_keys.txt",
                "iter2_insert_keys.txt",
                "iter3_insert_keys.txt"
        };

        String[] searchFiles = {
                "iter1_search_keys.txt",
                "iter2_search_keys.txt",
                "iter3_search_keys.txt"
        };

        for (int i = 0; i < 3; i++) {
            try {
                List<Integer> insertData = readData(insertFiles[i]);
                List<Integer> searchData = readData(searchFiles[i]);

                // AVL
                AVL<Integer> avl = new AVL<>();
                avlInsert[i] = measureInsert(avl, insertData);
                avlSearch[i] = measureSearch(avl, searchData);

                // Splay
                Splay<Integer> splay = new Splay<>();
                splayInsert[i] = measureInsert(splay, insertData);
                splaySearch[i] = measureSearch(splay, searchData);

                // Separate Chaining
                SeparateChainingHashTable<Integer> chain = new SeparateChainingHashTable<>();
                chainInsert[i] = measureInsert(chain, insertData);
                chainSearch[i] = measureSearch(chain, searchData);

                // Quadratic Probing
                QuadraticProbingHashTable<Integer> quad = new QuadraticProbingHashTable<>();
                quadInsert[i] = measureInsert(quad, insertData);
                quadSearch[i] = measureSearch(quad, searchData);

            } catch (FileNotFoundException e) {
                System.out.println("File error");
            }
        }

        // Print both tables
        printTable("Insertion Performance (Time in ms)",
                avlInsert, splayInsert, chainInsert, quadInsert);

        printTable("Search Performance (Time in ms)",
                avlSearch, splaySearch, chainSearch, quadSearch);
    }
}