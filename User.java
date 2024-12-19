import java.util.Scanner;
public class User {
    
    static class Node {
        String username;
        String password;
        Node next;
        int borrowedBooks; 
    
        Node(String username, String password) {
            this.username = username;
            this.password = password;
            this.next = null;
            this.borrowedBooks = 0; // Defaultnya 0 buku
        }
    }

    static Node head = null;

    public static void displayHeader(String title) {
        int totalWidth = 40; 
        int padding = (totalWidth - title.length()) / 2;
        String formattedTitle = " ".repeat(padding) + title;

        if (formattedTitle.length() < totalWidth) {
            formattedTitle += " ";
        }
        System.out.println("+------------------------------------------+");
        System.out.printf("| %-" + totalWidth + "s |\n", formattedTitle);
        System.out.println("+------------------------------------------+");
    }

    static class YearNode {
        int year;
        Book head;
        YearNode left;
        YearNode right;

        YearNode(int year) {
            this.year = year;
            this.head = null;
            this.left = null;
            this.right = null;
        }
    }

    static class Book {
        String title;
        String genre;
        String author;
        Book next;

        Book(String title, String genre, String author) {
            this.title = title;
            this.genre = genre;
            this.author = author;
            this.next = null;
        }
    }

    static class Counter {
        int value = 1;
    }

    static YearNode root = null;

    public static void addBook(int year, String title, String genre, String author) {
        root = insertYearNode(root, year, title, genre, author);
    }

    private static YearNode insertYearNode(YearNode current, int year, String title, String genre, String author) {
        if (current == null) {
            YearNode newYearNode = new YearNode(year);
            newYearNode.head = new Book(title, genre, author);
            return newYearNode;
        }

        if (year < current.year) {
            current.left = insertYearNode(current.left, year, title, genre, author);
        } else if (year > current.year) {
            current.right = insertYearNode(current.right, year, title, genre, author);
        } else {
            Book newBook = new Book(title, genre, author);
            Book temp = current.head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newBook;
        }

        return current;
    }

    public static void displayBooks(YearNode node) {
        System.out.println();
        System.out.println("+-----+-------------------------------+----------+-------------------+------------+");
        System.out.println("| No  | Judul                         | Genre    | Author            | Tahun      |");
        System.out.println("+-----+-------------------------------+----------+-------------------+------------+");
        Counter counter = new Counter();
        displayBooksInOrder(node, counter);
        System.out.println("+-----+-------------------------------+----------+-------------------+------------+");
    }

    private static void displayBooksInOrder(YearNode node, Counter counter) {
        if (node != null) {
            displayBooksInOrder(node.left, counter);
            Book temp = node.head;
            while (temp != null) {
                System.out.printf("| %-3d | %-29s | %-8s | %-17s | %-10d |%n", counter.value, temp.title, temp.genre, temp.author, node.year);
                counter.value++;
                temp = temp.next;
            }
            displayBooksInOrder(node.right, counter);
        }
    }

    public static void borrowBook(Scanner scanner, String username) {
        displayBooks(root);
        System.out.println();
        System.out.print("Masukkan judul buku yang ingin dipinjam: ");
        String title = scanner.nextLine();

        if (linearSearchAndBorrow(root, title)) {
            Node current = head;
            while (current != null) {
                if (current.username.equals(username)) {
                    current.borrowedBooks++;
                    System.out.println("Buku berhasil dipinjam.");
                    return;
                }
                current = current.next;
            }
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }

    private static boolean linearSearchAndBorrow(YearNode node, String title) {
        if (node != null) {
            if (linearSearchAndBorrow(node.left, title)) return true;
            Book temp = node.head;
            while (temp != null) {
                if (temp.title.equalsIgnoreCase(title)) {
                    return true;
                }
                temp = temp.next;
            }
            if (linearSearchAndBorrow(node.right, title)) return true;
        }
        return false;
    }

    public static void searchBook(Scanner scanner) {
        System.out.print("Masukkan judul buku yang ingin dicari: ");
        String title = scanner.nextLine();

        if (linearSearch(root, title)) {
            System.out.println("Buku ditemukan.");
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }

    private static boolean linearSearch(YearNode node, String title) {
        if (node != null) {
            if (linearSearch(node.left, title)) return true;
            Book temp = node.head;
            while (temp != null) {
                if (temp.title.equalsIgnoreCase(title)) {
                    return true;
                }
                temp = temp.next;
            }
            if (linearSearch(node.right, title)) return true;
        }
        return false;
    }

    public static void userMenu(Scanner scanner, String username) {
        int userChoice;
        do {
            System.out.println("Silahkan memilih menu :");
            System.out.println("1. Pinjam buku");
            System.out.println("2. Cari buku");
            System.out.println("3. Display list buku");
            System.out.println("4. Logout");
            System.out.print("Pilih menu: ");
            userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    borrowBook(scanner, username);
                    break;
                case 2:
                    searchBook(scanner);
                    break;
                case 3:
                    if (root == null) {
                        System.out.println("Tidak ada buku yang tersedia.");
                    } else {
                        displayBooks(root);
                    }
                    break;
                case 4:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan gak valid. Coba lagi.");
            }
        } while (userChoice != 4);
    }
}