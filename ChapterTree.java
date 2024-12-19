import java.util.Scanner;
public class ChapterTree {
    
    static class Node {
        String username;
        String password;
        Node next;
        int borrowedBooks;
        Book borrowedBookList;

        Node(String username, String password) {
            this.username = username;
            this.password = password;
            this.next = null;
            this.borrowedBooks = 0;
            this.borrowedBookList = null;
        }
    }

    static class BorrowQueue {
        String username;
        String bookTitle;
        BorrowQueue next;

        BorrowQueue(String username, String bookTitle) {
            this.username = username;
            this.bookTitle = bookTitle;
            this.next = null;
        }
    }

    static BorrowQueue borrowQueueFront = null;
    static BorrowQueue borrowQueueRear = null;

    static class BorrowHistory {
        String username;
        String bookTitle;
        BorrowHistory next;

        BorrowHistory(String username, String bookTitle) {
            this.username = username;
            this.bookTitle = bookTitle;
            this.next = null;
        }
    }

    static class ReturnHistory {
        String username;
        String bookTitle;
        ReturnHistory next;

        ReturnHistory(String username, String bookTitle) {
            this.username = username;
            this.bookTitle = bookTitle;
            this.next = null;
        }
    }

    static BorrowHistory borrowHistoryTop = null;
    static ReturnHistory historyTop = null;

    public static void pushReturnHistory(String username, String bookTitle) {
        ReturnHistory newHistory = new ReturnHistory(username, bookTitle);
        newHistory.next = historyTop;
        historyTop = newHistory;
    }

    public static void pushBorrowHistory(String username, String bookTitle) {
        BorrowHistory newHistory = new BorrowHistory(username, bookTitle);
        newHistory.next = borrowHistoryTop;
        borrowHistoryTop = newHistory;
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
        private static void displayBooksInOrder(YearNode node, Counter counter) {
        if (node != null) {
            displayBooksInOrder(node.left, counter);
            Book temp = node.head;
            while (temp != null) {
                System.out.printf("| %-3d | %-29s | %-11s | %-17s | %-10d |%n", counter.value, temp.title, temp.genre, temp.author, node.year);
                counter.value++;
                temp = temp.next;
            }
            displayBooksInOrder(node.right, counter);
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
            Book prev = null;
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
                        return true;
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

    public static void displayReturnHistory() {
        if (historyTop == null) {
            System.out.println("Tidak ada history pengembalian buku dari user.");
            return;
        }

        System.out.println("+-----+-------------------------------+-------------------+");
        System.out.println("| No  | User                          | Judul Buku        |");
        System.out.println("+-----+-------------------------------+-------------------+");

        ReturnHistory temp = historyTop;
        int counter = 1;
        while (temp != null) {
            System.out.printf("| %-3d | %-29s | %-17s |%n", counter, temp.username, temp.bookTitle);
            counter++;
            temp = temp.next;
        }

        System.out.println("+-----+-------------------------------+-------------------+");
    }

    public static void displayBorrowHistory() {
        if (borrowHistoryTop == null) {
            System.out.println("Tidak ada history peminjaman buku.");
            return;
        }

        System.out.println("+-----+-------------------------------+-------------------+");
        System.out.println("| No  | User                          | Judul Buku        |");
        System.out.println("+-----+-------------------------------+-------------------+");

        BorrowHistory temp = borrowHistoryTop;
        int counter = 1;
        while (temp != null) {
            System.out.printf("| %-3d | %-29s | %-17s |%n", counter, temp.username, temp.bookTitle);
            counter++;
            temp = temp.next;
        }

        System.out.println("+-----+-------------------------------+-------------------+");
    }

    public static void processBorrowQueue() {
        if (borrowQueueFront == null) {
            System.out.println("Tidak ada permintaan peminjaman di antrian.");
            return;
        }

        System.out.println("Daftar Antrian Peminjaman Buku:");
        System.out.println("+-----+-------------------------------+-------------------+");
        System.out.println("| No  | Username                      | Judul Buku        |");
        System.out.println("+-----+-------------------------------+-------------------+");

        BorrowQueue temp = borrowQueueFront;
        int counter = 1;
        while (temp != null) {
            System.out.printf("| %-3d | %-29s | %-17s |%n", counter, temp.username, temp.bookTitle);
            counter++;
            temp = temp.next;
        }

        System.out.println("+-----+-------------------------------+-------------------+");
        System.out.print("Masukkan nomor antrian yang ingin dikonfirmasi (0 untuk kembali): ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 0) {
            System.out.println("Kembali ke menu admin.");
            return;
        }

        BorrowQueue previous = null;
        temp = borrowQueueFront;
        counter = 1;

        while (temp != null) {
            if (counter == choice) {
                System.out.println("Memproses permintaan peminjaman untuk pengguna: " + temp.username);
                Node user = findUser(temp.username);
                if (user != null) {
                    confirmBorrow(user, temp.bookTitle);
                } else {
                    System.out.println("Pengguna tidak ditemukan.");
                }

                if (previous == null) {
                    borrowQueueFront = temp.next;
                } else {
                    previous.next = temp.next;
                }

                if (temp == borrowQueueRear) {
                    borrowQueueRear = previous;
                }
                System.out.println("Permintaan berhasil dikonfirmasi.");
                return;
            }
            previous = temp;
            temp = temp.next;
            counter++;
        }

        System.out.println("Nomor antrian tidak valid.");
    }

    public static void confirmBorrow(Node user, String bookTitle) {
        Book book = findBookByTitle(root, bookTitle);
        if (book == null) {
            System.out.println("Buku tidak ditemukan.");
            return;
        }

        Book borrowedBook = new Book(book);
        if (user.borrowedBookList == null) {
            user.borrowedBookList = borrowedBook;
        } else {
            Book temp = user.borrowedBookList;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = borrowedBook;
        }
        user.borrowedBooks++;
        System.out.println("Buku \"" + bookTitle + "\" berhasil dipinjam oleh pengguna: " + user.username);
        pushBorrowHistory(user.username, bookTitle);
    }
    // ------------------------------- AKHIR FUNGSI ADMIN DISINI ----------------------------------
    

    // Yang Admin-Admin Aja (YAAAA), pilih-pilih fitur kayak tambah, hapus, edit buku
    public static void adminMenu(Scanner scanner) {
        int adminChoice;
        do {
            displayHeader("Silahkan Pilih Menu Admin :");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Hapus Buku");
            System.out.println("3. Edit Buku");
            System.out.println("4. Konfirmasi Peminjaman");
            System.out.println("5. Tampilkan List Buku");
            System.out.println("6. Tampilkan Data User");
            System.out.println("7. Tampilkan Riwayat Peminjaman Buku");
            System.out.println("8. Tampilkan Riwayat Pengembalian Buku");
            System.out.println("9. Logout");
            System.out.print("Pilih menu: ");
            adminChoice = scanner.nextInt();
            scanner.nextLine();

            switch (adminChoice) {
                case 1:
                    System.out.print("Masukkan Tahun Buku: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
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
                    processBorrowQueue();
                    break;
                case 5:
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
                case 6:
                    displayUserData();
                    break;
                case 7:
                    displayBorrowHistory();
                    break;
                case 8:
                    displayReturnHistory();
                    break;
                case 9:
                    System.out.println();
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan gak valid. Coba lagi.");
            }
        } while (adminChoice != 9); // Exit klo pilih logout
    }

    // ------------------------------- AWAL FUNGSI USER DISINI ----------------------------------
    public static void displayAllBooks() {
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        System.out.println("| No  | Judul                         | Genre       | Author            | Tahun      |");
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        Counter counter = new Counter();
        displayBooksInOrder(root, counter);
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
    }

    public static void borrowBook(Node user, String bookTitle) {
        enqueueBorrowRequest(user.username, bookTitle); // Tambahkan ke antrian
    }

    public static void searchBook(Scanner scanner) {
        System.out.print("Masukkan judul buku yang ingin dicari: ");
        String title = scanner.nextLine();
    
        Book foundBook = linearSearch(root, title);
        if (foundBook != null) {
            System.out.println("Buku ditemukan.");
            System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
            System.out.printf("| %-3s | %-29s | %-11s | %-17s | %-10s |\n", 
                              "No", "Judul", "Genre", "Author", "Tahun");
            System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
            System.out.printf("| %-3d | %-29s | %-11s | %-17s | %-10d |\n", 
                              1, foundBook.title, foundBook.genre, foundBook.author, foundBook.year);
            System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }
    
    private static Book linearSearch(YearNode node, String title) {
        if (node != null) {
            Book foundInLeft = linearSearch(node.left, title);
            if (foundInLeft != null) return foundInLeft;
    
            Book temp = node.head;
            while (temp != null) {
                if (temp.title.equalsIgnoreCase(title)) {
                    return temp;
                }
                temp = temp.next;
            }
    
            Book foundInRight = linearSearch(node.right, title);
            if (foundInRight != null) return foundInRight;
        }
        return null;
    }

    private static Node findUser(String username) {
        Node current = head;
        while (current != null) {
            if (current.username.equals(username)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private static Book findBookByTitle(YearNode node, String title) {
        if (node == null) return null;

        Book temp = node.head;
        while (temp != null) {
            if (temp.title.equalsIgnoreCase(title)) {
                return temp;
            }
            temp = temp.next;
        }

        Book leftResult = findBookByTitle(node.left, title);
        if (leftResult != null) return leftResult;

        return findBookByTitle(node.right, title);
    }

    public static void displayBorrowedBooks(Node user) {
        if (user.borrowedBookList == null) {
            System.out.println("Tidak ada buku yang dipinjam.");
            return;
        }

        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
        System.out.println("| No  | Judul                         | Genre       | Author            | Tahun      |");
        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");

        Book temp = user.borrowedBookList;
        Counter counter = new Counter();
        while (temp != null) {
            System.out.printf("| %-3d | %-29s | %-11s | %-17s | %-10d |%n", counter.value, temp.title, temp.genre, temp.author, temp.year);
            counter.value++;
            temp = temp.next;
        }

        System.out.println("+-----+-------------------------------+-------------+-------------------+------------+");
    }

    public static void returnBook(Node user, int bookNumber) {
        if (user.borrowedBookList == null) {
            System.out.println("Tidak ada buku yang dipinjam.");
            return;
        }

        Book temp = user.borrowedBookList;
        Book prev = null;
        Counter counter = new Counter();

        while (temp != null) {
            if (counter.value == bookNumber) {
                if (prev == null) {
                    user.borrowedBookList = temp.next;
                } else {
                    prev.next = temp.next;
                }
                user.borrowedBooks--;
                System.out.println("Buku berhasil dikembalikan.");
                pushReturnHistory(user.username, temp.title);
                return;
            }
            counter.value++;
            prev = temp;
            temp = temp.next;
        }

        System.out.println("Nomor buku tidak valid.");
    }

    public static void enqueueBorrowRequest(String username, String bookTitle) {
        BorrowQueue newRequest = new BorrowQueue(username, bookTitle);
        if (borrowQueueRear == null) {
            borrowQueueFront = borrowQueueRear = newRequest;
        } else {
            borrowQueueRear.next = newRequest;
            borrowQueueRear = newRequest;
        }
        System.out.println("Permintaan peminjaman ditambahkan ke antrian.");
    }
    // ------------------------------- AKHIR FUNGSI USER DISINI ----------------------------------

    // Menu buat user biasa (rakyat jelata), bisa pinjam buku atau cari buku
    public static void userMenu(Scanner scanner, String username) {
        Node user = findUser(username);
        int userChoice;
        do {
            System.out.println("Silahkan memilih menu :");
            System.out.println("1. Pinjam buku");
            System.out.println("2. Cari buku");
            System.out.println("3. Tampilkan buku yang dipinjam");
            System.out.println("4. Kembalikan Buku");
            System.out.println("5. Logout");
            System.out.println();
            System.out.print("Pilih menu: ");
            userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    displayAllBooks();
                    System.out.print("Masukkan judul buku yang ingin dipinjam: ");
                    String bookTitle = scanner.nextLine();
                    borrowBook(user, bookTitle);
                    break;
                case 2:
                    searchBook(scanner);
                    break;
                case 3:
                    displayBorrowedBooks(user);
                    break;
                case 4:
                    if (user.borrowedBookList == null) {
                        System.out.println("Tidak ada buku yang dipinjam.");
                    } else {
                        displayBorrowedBooks(user);
                        System.out.print("Masukkan nomor buku yang ingin dikembalikan: ");
                        int bookNumber = scanner.nextInt();
                        scanner.nextLine();
                        returnBook(user, bookNumber);
                    }
                    break;
                case 5:
                    System.out.println("Logout berhasil.");
                    break;
                default:
                    System.out.println("Pilihan gak valid. Coba lagi.");
            }
        } while (userChoice != 5); // Keluar kalau logout
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
                    System.out.println("Terima kasih telah menggunakan ChapterTree!");
                    break;
                default:
                    System.out.println("Pilihan gak valid.");
            }
        } while (mainChoice != 3); // Exit kalo pilih 3
    }
}