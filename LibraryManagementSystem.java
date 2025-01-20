
    import java.util.ArrayList;
import java.util.List;

class Book {
    String title, author, isbn;
    boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }
}

class Member {
    String name, id;
    List<Book> borrowedBooks;

    public Member(String name, String id) {
        this.name = name;
        this.id = id;
        this.borrowedBooks = new ArrayList<>();
    }

    public boolean canBorrowMoreBooks() {
        return borrowedBooks.size() < 5;
    }
}

class Library {
    List<Book> books;
    List<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.title);
    }

    public void removeBook(String isbn) {
        books.removeIf(book -> book.isbn.equals(isbn));
        System.out.println("Book removed with ISBN: " + isbn);
    }

    public void searchBook(String title) {
        books.stream().filter(book -> book.title.contains(title)).forEach(book -> {
            System.out.println("Found: " + book.title + " by " + book.author + " | Available: " + book.isAvailable);
        });
    }

    public void borrowBook(String memberId, String isbn) {
        Member member = members.stream().filter(m -> m.id.equals(memberId)).findFirst().orElse(null);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        if (!member.canBorrowMoreBooks()) {
            System.out.println("Cannot borrow more books. Limit reached.");
            return;
        }

        Book book = books.stream().filter(b -> b.isbn.equals(isbn) && b.isAvailable).findFirst().orElse(null);
        if (book == null) {
            System.out.println("Book not available.");
            return;
        }

        book.isAvailable = false;
        member.borrowedBooks.add(book);
        System.out.println("Book borrowed: " + book.title);
    }

    public void returnBook(String memberId, String isbn) {
        Member member = members.stream().filter(m -> m.id.equals(memberId)).findFirst().orElse(null);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        Book book = member.borrowedBooks.stream().filter(b -> b.isbn.equals(isbn)).findFirst().orElse(null);
        if (book == null) {
            System.out.println("Book not borrowed by this member.");
            return;
        }

        book.isAvailable = true;
        member.borrowedBooks.remove(book);
        System.out.println("Book returned: " + book.title);
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Add books
        library.addBook(new Book("Java Programming", "Author A", "ISBN001"));
        library.addBook(new Book("Python Basics", "Author B", "ISBN002"));

        // Add members
        Member member = new Member("John Doe", "MEM001");
        library.members.add(member);

        // Search books
        library.searchBook("Java");

        // Borrow and return books
        library.borrowBook("MEM001", "ISBN001");
        library.returnBook("MEM001", "ISBN001");
    }
}

    

