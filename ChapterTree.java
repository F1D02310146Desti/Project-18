import java.util.Scanner;

// ------------------------------- FEATURE JAKI START ----------------------------------
public class ChapterTree {
    // Node class buat nyimpen data user, kayak username, password, dan referensi ke node selanjutnya
    static class Node {
        String username;
        String password;
        Node next;

        Node(String username, String password) {
            this.username = username;
            this.password = password;
            this.next = null;
        }
    }

    // 
    static Node head = null;

    // Display header yang rapi dan ditengahin gitu
    public static void displayHeader(String title) {
        int totalWidth = 40; // Lebar total buat display
        int padding = (totalWidth - title.length()) / 2; // Cek berapa banyak spasi supaya title ditengah
        String formattedTitle = " ".repeat(padding) + title;

        if (formattedTitle.length() < totalWidth) {
            formattedTitle += " "; // Make sure it’s full width
        }

        // Nampilin header dengan garis-garis biar keliatan rapi
        System.out.println("+------------------------------------------+");
        System.out.printf("| %-" + totalWidth + "s |\n", formattedTitle);
        System.out.println("+------------------------------------------+");
    }

    // Overload displayHeader buat dynamic message, title di tengah, message tengah juga
    public static void displayHeader(String title, String message) {
        int totalWidth = 40;
        int padding = (totalWidth - title.length()) / 2;
        String formattedTitle = " ".repeat(padding) + title;

        int messagePadding = (totalWidth - message.length()) / 2;
        String formattedMessage = " ".repeat(messagePadding) + message;

        if (formattedMessage.length() < totalWidth) {
            formattedMessage += " ";
        }

        // Tampilkan header dan pesan
        System.out.println();
        System.out.println("+------------------------------------------+");
        System.out.printf("| %-" + totalWidth + "s |\n", formattedTitle);
        System.out.printf("| %-" + totalWidth + "s |\n", formattedMessage);
        System.out.println("+------------------------------------------+");
    }

    // ------------------------------- FULL FUNGSI ADMIN DISINI ----------------------------------
    // Node class untuk menyimpan tahun sebagai key utama dan linked list buku untuk setiap tahun
    static class YearNode {
        int year;
        Book head; // Head dari linked list buku
        YearNode left;
        YearNode right;

        YearNode(int year) {
            this.year = year;
            this.head = null;
            this.left = null;
            this.right = null;
        }
    }

    // Node class untuk linked list buku
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
            // Tahun sudah ada, tambahkan buku ke linked list
            Book newBook = new Book(title, genre, author);
            Book temp = current.head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newBook;
        }

        return current;
    }

    // Display Buku gabungan dari semua tahun
    public static void displayBooks(YearNode node) {
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
            Book temp = node.head; // Use a temporary pointer
            while (temp != null) {
                System.out.printf("| %-3d | %-29s | %-8s | %-17s | %-10d |%n", counter.value, temp.title, temp.genre, temp.author, node.year);
                counter.value++;
                temp = temp.next;
            }
            displayBooksInOrder(node.right, counter);
        }
    }
    // ------------------------------- FULL FUNGSI ADMIN DISINI ----------------------------------
    

    // Yang Admin-Admin Aja (YAAAA), pilih-pilih fitur kayak tambah, hapus, edit buku
    public static void adminMenu(Scanner scanner) {
        int adminChoice;
        do {
            displayHeader("Silahkan Pilih Menu Admin :");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Edit Buku");
            System.out.println("4. Display List Buku");
            System.out.println("5. Display Data User");
            System.out.println("6. Logout");
            System.out.print("Pilih menu: ");
            adminChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (adminChoice) {
                case 1:
                    System.out.print("Masukkan Tahun Buku: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer
                    System.out.print("Masukkan Judul Buku: ");
                    String title = scanner.nextLine();
                    System.out.print("Masukkan Genre Buku: ");
                    String genre = scanner.nextLine();
                    System.out.print("Masukkan Author Buku: ");
                    String author = scanner.nextLine();
                    addBook(year, title, genre, author);
                    System.out.println("Buku berhasil ditambahkan.");
                    break;
                case 2:
                    System.out.println("{FITUR COMING SOON}");
                    break;
                case 3:
                    System.out.println("{FITUR COMING SOON}");
                    break;
                case 4:
                if (root == null) {
                    System.out.println("Tidak ada buku yang tersedia.");
                    } else {
                        displayBooks(root);
                    }
                    break;
                case 5:
                    System.out.println("{FITUR COMING SOON}");
                    break;
                case 6:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan gak valid. Coba lagi.");
            }
        } while (adminChoice != 6); // Exit klo pilih logout
    }

    // Menu buat user biasa (rakyat jelata), bisa pinjam buku atau cari buku
    public static void userMenu(Scanner scanner, String username) {
        int userChoice;
        do {
            System.out.println("Silahkan memilih menu :");
            System.out.println("1. Pinjam buku");
            System.out.println("2. Cari buku");
            System.out.println("3. Display list buku yang dipinjam");
            System.out.println("4. Logout");
            System.out.print("Pilih menu: ");
            userChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (userChoice) {
                case 1:
                    System.out.println("{FITUR COMING SOON}");
                    break;
                case 2:
                    System.out.println("{FITUR COMING SOON}");
                    break;
                case 3:
                    System.out.println("{FITUR COMING SOON}");
                    break;
                case 4:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan gak valid. Coba lagi.");
            }
        } while (userChoice != 4); // Keluar kalau logout
    }

    // Fungsi buat registrasi user baru
    public static void signUp(Scanner scanner) {
        System.out.println();
        displayHeader("Daftar Member ChapterTree");
        System.out.print("Silahkan masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Silahkan masukkan password: ");
        String password = scanner.nextLine();

        System.out.println(); // Kasih spasi biar gak terlalu padat
        if (isUsernameTaken(username)) { // Cek apakah username udah ada
            System.out.println("Username sudah terdaftar. Silakan gunakan username lain.");
        } else {
            addUser(username, password); // Simpen username dan password
            displayHeader("Registrasi Berhasil!");
        }
    }

    // Fungsi buat login
    public static void signIn(Scanner scanner) {
        System.out.println();
        displayHeader("Login Member ChapterTree");
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("1234")) { // Cek login admin
            System.out.println();
            displayHeader("Login Berhasil!", "Selamat datang, akun admin.");
            adminMenu(scanner); // Kalo admin login, masuk ke menu admin
        } else {
            // Cek apakah username/password bener
            if (authenticate(username, password)) {
                displayHeader("Login Berhasil!", "Selamat datang, " + username + ".");
                userMenu(scanner, username); // Kalo bukan admin, masuk ke menu user
            } else {
                System.out.println();
                displayHeader("Username/password salah. Coba lagi");
            }
        }
    }

    // Cek apakah username udah ada di Linked List
    public static boolean isUsernameTaken(String username) {
        Node current = head;
        while (current != null) {
            if (current.username.equals(username)) {
                return true; // Username udah ada, ga bisa daftar
            }
            current = current.next;
        }
        return false;
    }

    // Tambahin user baru ke Linked List
    public static void addUser(String username, String password) {
        Node newNode = new Node(username, password);
        if (head == null) {
            head = newNode; // Kalo Linked List kosong, jadi head
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next; // Cari akhir dari Linked List
            }
            current.next = newNode; // Tambah node baru di akhir
        }
    }

    // Verifikasi username dan password
    public static boolean authenticate(String username, String password) {
        Node current = head;
        while (current != null) {
            if (current.username.equals(username) && current.password.equals(password)) {
                return true; // Cek kalo password sama
            }
            current = current.next;
        }
        return false;
    }
// ------------------------------- FEATURE JAKI END ----------------------------------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int mainChoice;

        do {
            displayHeader("Selamat datang di ChapterTree!");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Pilih menu: ");
            mainChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (mainChoice) {
                case 1:
                    signUp(scanner); // Panggil fungsi sign up
                    break;
                case 2:
                    signIn(scanner); // Panggil fungsi sign in
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan ChapterTree!"); // Exit message
                    break;
                default:
                    System.out.println("Pilihan gak valid.");
            }
        } while (mainChoice != 3); // Exit kalo pilih 3
    }
}