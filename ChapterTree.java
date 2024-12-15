import java.util.Scanner;
public class ChapterTree {
    
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

    // 
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
    public static void displayHeader(String title, String message) {
        int totalWidth = 40;
        int padding = (totalWidth - title.length()) / 2;
        String formattedTitle = " ".repeat(padding) + title;

        int messagePadding = (totalWidth - message.length()) / 2;
        String formattedMessage = " ".repeat(messagePadding) + message;

        if (formattedMessage.length() < totalWidth) {
            formattedMessage += " ";
        }
        System.out.println();
        System.out.println("+------------------------------------------+");
        System.out.printf("| %-" + totalWidth + "s |\n", formattedTitle);
        System.out.printf("| %-" + totalWidth + "s |\n", formattedMessage);
        System.out.println("+------------------------------------------+");
    }


    // ------------------------------- FULL FUNGSI ADMIN DISINI ----------------------------------
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
        int year;
        Book next;

        Book(String title, String genre, String author, int year) {
            this.title = title;
            this.genre = genre;
            this.author = author;
            this.year = year;
            this.next = null;
        }

        // Buat simpen copyan dari linked list book
        Book(Book book) {
            this.title = book.title;
            this.genre = book.genre;
            this.author = book.author;
            this.year = book.year;
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
            newYearNode.head = new Book(title, genre, author, year);
            return newYearNode;
        }

        if (year < current.year) {
            current.left = insertYearNode(current.left, year, title, genre, author);
        } else if (year > current.year) {
            current.right = insertYearNode(current.right, year, title, genre, author);
        } else {
            // Tahun sudah ada, tambahkan buku ke linked list
            Book newBook = new Book(title, genre, author, year);
            Book temp = current.head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newBook;
        }

        return current;
    }

    // Fungsi buat tampilkan buku
    public static void displayBooks(YearNode node) {
        System.out.println("Daftar Buku (Pre-order):");
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        System.out.println("| No  | Judul                         | Genre       | Author            | Tahun      |");
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        Counter counter = new Counter();
        displayBooksPreOrder(node, counter);
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
    }

    private static void displayBooksPreOrder(YearNode node, Counter counter) {
        if (node != null) {
            Book temp = node.head;
            while (temp != null) {
                System.out.printf("| %-3d | %-29s | %-11s | %-17s | %-10d |%n", counter.value, temp.title, temp.genre, temp.author, temp.year);
                counter.value++;
                temp = temp.next;
            }
            displayBooksPreOrder(node.left, counter);
            displayBooksPreOrder(node.right, counter);
        }
    }

    public static void sortAndDisplayBooks(Scanner scanner, boolean ascending) {
        System.out.println("Pilih kriteria pengurutan:");
        System.out.println("1. Judul");
        System.out.println("2. Genre");
        System.out.println("3. Author");
        System.out.println("4. Tahun");
        System.out.print("Pilih: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        Book tempList = createTempList(root);
        tempList = mergeSort(tempList, sortChoice, ascending);

        System.out.println("Daftar Buku (Setelah Pengurutan):");
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        System.out.println("| No  | Judul                         | Genre       | Author            | Tahun      |");
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");

        Counter counter = new Counter();
        Book temp = tempList;
        while (temp != null) {
            System.out.printf("| %-3d | %-29s | %-11s | %-17s | %-10d |%n", counter.value, temp.title, temp.genre, temp.author, temp.year);
            counter.value++;
            temp = temp.next;
        }

        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
    }

    private static Book createTempList(YearNode node) {
        Book tempListHead = null;
        Book tempListTail = null;

        if (node != null) {
            tempListHead = createTempList(node.left);
            tempListTail = tempListHead;
            while (tempListTail != null && tempListTail.next != null) {
                tempListTail = tempListTail.next;
            }

            Book currentBook = node.head;
            while (currentBook != null) {
                Book copiedBook = new Book(currentBook);
                if (tempListHead == null) {
                    tempListHead = copiedBook;
                    tempListTail = copiedBook;
                } else {
                    tempListTail.next = copiedBook;
                    tempListTail = copiedBook;
                }
                currentBook = currentBook.next;
            }

            Book rightList = createTempList(node.right);
            if (rightList != null) {
                if (tempListHead == null) {
                    tempListHead = rightList;
                } else {
                    tempListTail.next = rightList;
                }
            }
        }

        return tempListHead;
    }

    private static Book mergeSort(Book head, int sortChoice, boolean ascending) {
        if (head == null || head.next == null) {
            return head;
        }

        Book slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Book middle = slow;
        Book nextOfMiddle = middle.next;
        middle.next = null;

        Book left = mergeSort(head, sortChoice, ascending);
        Book right = mergeSort(nextOfMiddle, sortChoice, ascending);

        return sortedMerge(left, right, sortChoice, ascending);
    }

    private static Book sortedMerge(Book a, Book b, int sortChoice, boolean ascending) {
        if (a == null) return b;
        if (b == null) return a;

        if ((compareBooks(a, b, sortChoice) <= 0) == ascending) {
            a.next = sortedMerge(a.next, b, sortChoice, ascending);
            return a;
        } else {
            b.next = sortedMerge(a, b.next, sortChoice, ascending);
            return b;
        }
    }

    private static int compareBooks(Book book1, Book book2, int sortChoice) {
        switch (sortChoice) {
            case 1: 
                return book1.title.compareToIgnoreCase(book2.title);
            case 2: 
                return book1.genre.compareToIgnoreCase(book2.genre);
            case 3: 
                return book1.author.compareToIgnoreCase(book2.author);
            case 4: 
                return Integer.compare(book1.year, book2.year);
            default: 
                return 0;
        }
    }
    
    // Fitur Admin : 3. Display User Data
    public static void displayUserData() {
        if (head == null) {
            System.out.println();
            System.out.println("Tidak ada data user.");
            return;
        }
        
        System.out.println();
        System.out.println("+-----+-------------------------------+---------------------+");
        System.out.println("| No  | Username                      | Jumlah Buku         |");
        System.out.println("+-----+-------------------------------+---------------------+");

        Node current = head;
        int counter = 1;

        while (current != null) {
            System.out.printf("| %-3d | %-29s | %-19d |%n", counter, current.username, current.borrowedBooks);
            current = current.next;
            counter++;
        }

        System.out.println("+-----+-------------------------------+---------------------+");
    }
    
    public static void borrowBook(String username) {
        Node current = head;
        
        while (current != null) {
            if (current.username.equals(username)) {
                System.out.println();
                current.borrowedBooks++; // Tambah jumlah buku yang dipinjam
                System.out.println("Buku berhasil dipinjam.");
                return;
            }
            current = current.next;
        }
        System.out.println("User tidak ditemukan.");
    }

    // Fungsi buat hapus sama delete buku
    public static void searchAndEditOrDelete(Scanner scanner, String searchTerm, boolean isDelete) {
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        System.out.println("| No  | Judul                         | Genre       | Author            | Tahun      |");
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");

        Counter matchCounter = new Counter();
        searchBooks(root, searchTerm, matchCounter);

        if (matchCounter.value == 0) {
            System.out.println("Tidak ada buku yang sesuai dengan kata kunci: " + searchTerm);
            return;
        }

        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        System.out.print("Pilih nomor buku yang ingin " + (isDelete ? "dihapus" : "diedit") + " (0 untuk kembali): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            System.out.println("Kembali ke menu admin.");
            return;
        }

        if (choice < 1 || choice > matchCounter.value) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        executeEditOrDelete(root, searchTerm, choice, scanner, isDelete);
    }

    private static void searchBooks(YearNode node, String searchTerm, Counter matchCounter) {
        if (node != null) {
            searchBooks(node.left, searchTerm, matchCounter);
            Book temp = node.head;
            while (temp != null) {
                if (temp.title.toLowerCase().contains(searchTerm.toLowerCase())) {
                    System.out.printf("| %-3d | %-29s | %-11s | %-17s | %-10d |%n", matchCounter.value, temp.title, temp.genre, temp.author, node.year);
                    matchCounter.value++;
                }
                temp = temp.next;
            }
            searchBooks(node.right, searchTerm, matchCounter);
        }
    }

    private static void executeEditOrDelete(YearNode node, String searchTerm, int choice, Scanner scanner, boolean isDelete) {
        Counter currentCounter = new Counter();
        performActionOnBook(node, searchTerm, currentCounter, choice, scanner, isDelete);
    }

    private static boolean performActionOnBook(YearNode node, String searchTerm, Counter currentCounter, int choice, Scanner scanner, boolean isDelete) {
        if (node != null) {
            if (performActionOnBook(node.left, searchTerm, currentCounter, choice, scanner, isDelete)) {
                return true;
            }
            Book temp = node.head;
            Book prev = null; // Track previous book to correctly delete
            while (temp != null) {
                if (temp.title.toLowerCase().contains(searchTerm.toLowerCase())) {
                    if (currentCounter.value == choice) {
                        if (isDelete) {
                            deleteBook(temp, prev, node);
                            System.out.println("Buku berhasil dihapus.");
                        } else {
                            System.out.println("Pilih data yang ingin diubah:");
                            System.out.println("1. Ubah Judul Buku");
                            System.out.println("2. Ubah Genre Buku");
                            System.out.println("3. Ubah Author Buku");
                            System.out.println("4. Ubah Tahun Buku");
                            System.out.println("5. Ubah Semua Data");
                            System.out.print("Pilih: ");
                            int editChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (editChoice) {
                                case 1:
                                    System.out.print("Masukkan Judul Baru: ");
                                    temp.title = scanner.nextLine();
                                    System.out.println("Judul buku berhasil diubah.");
                                    break;
                                case 2:
                                    System.out.print("Masukkan Genre Baru: ");
                                    temp.genre = scanner.nextLine();
                                    System.out.println("Genre buku berhasil diubah.");
                                    break;
                                case 3:
                                    System.out.print("Masukkan Author Baru: ");
                                    temp.author = scanner.nextLine();
                                    System.out.println("Author buku berhasil diubah.");
                                    break;
                                case 4:
                                    System.out.print("Masukkan Tahun Baru: ");
                                    int newYear = scanner.nextInt();
                                    scanner.nextLine();
                                    addBook(newYear, temp.title, temp.genre, temp.author);
                                    deleteBook(temp, prev, node);
                                    System.out.println("Tahun buku berhasil diubah.");
                                    break;
                                case 5:
                                    System.out.print("Masukkan Judul Baru: ");
                                    temp.title = scanner.nextLine();
                                    System.out.print("Masukkan Genre Baru: ");
                                    temp.genre = scanner.nextLine();
                                    System.out.print("Masukkan Author Baru: ");
                                    temp.author = scanner.nextLine();
                                    System.out.print("Masukkan Tahun Baru: ");
                                    int allNewYear = scanner.nextInt();
                                    scanner.nextLine();
                                    addBook(allNewYear, temp.title, temp.genre, temp.author);
                                    deleteBook(temp, prev, node);
                                    System.out.println("Semua data buku berhasil diubah.");
                                    break;
                                default:
                                    System.out.println("Pilihan tidak valid.");
                            }
                        }
                        return true; // Stop further traversal
                    }
                    currentCounter.value++;
                }
                prev = temp;
                temp = temp.next;
            }
            if (performActionOnBook(node.right, searchTerm, currentCounter, choice, scanner, isDelete)) {
                return true;
            }
        }
        return false;
    }

    private static void deleteBook(Book book, Book prev, YearNode yearNode) {
        if (prev == null) {
            yearNode.head = book.next;
        } else {
            prev.next = book.next;
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
                    System.out.println();
                    System.out.println("Buku berhasil ditambahkan.");
                    break;
                case 2:
                    System.out.print("Masukkan kata kunci untuk mencari buku yang akan dihapus: ");
                    String deleteTerm = scanner.nextLine();
                    searchAndEditOrDelete(scanner, deleteTerm, true);
                    break;
                case 3:
                    System.out.print("Masukkan kata kunci untuk mencari buku yang akan diedit: ");
                    String editTerm = scanner.nextLine();
                    searchAndEditOrDelete(scanner, editTerm, false);
                    break;
                case 4:
                if (root == null) {
                    System.out.println("Tidak ada buku yang tersedia.");
                    } else {
                        displayBooks(root);
                        while (true) { 
                            System.out.println("Pilih opsi pengurutan:");
                            System.out.println("0. Kembali");
                            System.out.println("1. Urutkan Ascending");
                            System.out.println("2. Urutkan Descending");
                            System.out.print("Pilih: ");
                            int sortOption = scanner.nextInt();
                            scanner.nextLine();
                            if (sortOption == 1) {
                                sortAndDisplayBooks(scanner, true);
                            } else if (sortOption == 2) {
                                sortAndDisplayBooks(scanner, false);
                            } else if (sortOption == 0) {
                                break;
                            } else {
                                System.out.println("Pilihan tidak valid.");
                            }
                        }
                    }
                    break;
                case 5:
                    displayUserData();
                    break;
                case 6:
                    System.out.println();
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int mainChoice;
        
        // buat debugging aja ini, biar langsung ada data bukunya :v
        addBook(2020, "Percobaan Pertama", "Fantasy", "Coba");
        addBook(2021, "Buku Keren", "Science", "Buku Keren");
        addBook(2000, "Unram", "Science", "Chapter");
        addBook(2001, "Senja", "Non Fiksi", "Tree");

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