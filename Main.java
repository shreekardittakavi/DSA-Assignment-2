import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static void warmup() {
        AVL<Integer> avlTree = new AVL<>();
        try {
            File f = new File("iter3_insert_keys.txt");
            List<Integer> data;
            try (Scanner scan = new Scanner(f)) {
                data = new ArrayList<>();
                while (scan.hasNextInt()) {
                    int key = scan.nextInt();
                    data.add(key);
                }
            }

            // insert warm up
            for (int i = 0; i < 10; i++) {
                for (int key : data)
                    avlTree.insert(key);
                avlTree.clear();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred reading the file");
        }
    }

    private static void AVLInsertSearchMetrics(String insertFilename, String searchFilename) {
        AVL<Integer> avlTree = new AVL<>();
        try {
            File f = new File(insertFilename);
            List<Integer> data;
            try (Scanner scan = new Scanner(f)) {
                data = new ArrayList<>();
                while (scan.hasNextInt()) {
                    int key = scan.nextInt();
                    data.add(key);
                }
            }

            // insert timed
            long insertStart = System.nanoTime();
            for (int key : data) {
                avlTree.insert(key);
            }
            long insertEnd = System.nanoTime();

            System.out.print("AVL insert time (ms): ");
            System.out.println((insertEnd - insertStart) * Math.pow(10, -6));

        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred reading the file");
        }

        try {
            File f = new File(searchFilename);
            List<Integer> data;
            try (Scanner scan = new Scanner(f)) {
                data = new ArrayList<>();
                while (scan.hasNextInt()) {
                    int key = scan.nextInt();
                    data.add(key);
                }
            }

            // search timed
            long searchStart = System.nanoTime();
            for (int key : data)
                avlTree.search(key);
            long searchEnd = System.nanoTime();

            System.out.print("AVL search time (ms): ");
            System.out.println((searchEnd - searchStart) * Math.pow(10, -6));

        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred reading the file");
        }
    }

    private static void SplayInsertSearchMetrics(String insertFilename, String searchFilename) {
        Splay<Integer> splayTree = new Splay<>();
        try {
            File f = new File(insertFilename);
            List<Integer> data;
            try (Scanner scan = new Scanner(f)) {
                data = new ArrayList<>();
                while (scan.hasNextInt()) {
                    int key = scan.nextInt();
                    data.add(key);
                }
            }

            // insert timed
            long insertStart = System.nanoTime();
            for (int key : data) {
                splayTree.insert(key);
            }
            long insertEnd = System.nanoTime();

            System.out.print("Splay tree insert time (ms): ");
            System.out.println((insertEnd - insertStart) * Math.pow(10, -6));

        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred reading the file");
        }

        try {
            File f = new File(searchFilename);
            List<Integer> data;
            try (Scanner scan = new Scanner(f)) {
                data = new ArrayList<>();
                while (scan.hasNextInt()) {
                    int key = scan.nextInt();
                    data.add(key);
                }
            }

            // search timed
            long searchStart = System.nanoTime();
            for (int key : data)
                splayTree.search(key);
            long searchEnd = System.nanoTime();

            System.out.print("Splay Tree search time (ms): ");
            System.out.println((searchEnd - searchStart) * Math.pow(10, -6));

        } catch (FileNotFoundException e) {
            System.out.println("An error has occurred reading the file");
        }
    }

    public static void main(String[] args) {
        warmup();
        AVLInsertSearchMetrics("iter1_insert_keys.txt", "iter1_search_keys.txt");
        AVLInsertSearchMetrics("iter2_insert_keys.txt", "iter2_search_keys.txt");
        AVLInsertSearchMetrics("iter3_insert_keys.txt", "iter3_search_keys.txt");

        SplayInsertSearchMetrics("iter1_insert_keys.txt", "iter1_search_keys.txt");
        SplayInsertSearchMetrics("iter2_insert_keys.txt", "iter2_search_keys.txt");
        SplayInsertSearchMetrics("iter3_insert_keys.txt", "iter3_search_keys.txt");
    }
}
