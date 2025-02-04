package org.example.JDBC.Assignment_1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class BookDatabaseManager {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/books";
    private static final String USER = "root";
    private static final String PASS = "nbudmaria";

    private final List<Book> books;
    private final List<Author> authors;

    public BookDatabaseManager() {
        books = new ArrayList<>();
        authors = new ArrayList<>();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setInt(3, book.getEditionNumber());
            pstmt.setString(4, book.getCopyright());
            pstmt.executeUpdate();

            books.add(book);

            for (Author author : book.getAuthorList()) {
                if (!authorExists(author.getAuthorID())) {
                    addAuthor(author);
                }
                linkBookAuthor(book, author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean authorExists(int authorID) {
        String sql = "SELECT 1 FROM authors WHERE authorID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, authorID);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addAuthor(Author author) {
        String sql = "INSERT INTO authors (firstName, lastName) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author.getFirstName());
            pstmt.setString(2, author.getLastName());
            pstmt.executeUpdate();

            authors.add(author);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void linkBookAuthor(Book book, Author author) {
        String sql = "INSERT INTO authorISBN (authorID, isbn) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, author.getAuthorID());
            pstmt.setString(2, book.getIsbn());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM titles";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getInt("editionNumber"),
                        rs.getString("copyright")
                );
                bookList.add(book);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Author> authorList = new ArrayList<>();
        sql = "SELECT * FROM authors";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Author author = new Author(
                        rs.getInt("authorID"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );
                authorList.add(author);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadBookAuthors(bookList, authorList);
        loadAuthorBooks(authorList, bookList);
        return bookList;
    }

    public List<Author> getAllAuthors() {
        List<Author> authorList = new ArrayList<>();
        String sql = "SELECT * FROM authors";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Author author = new Author(
                        rs.getInt("authorID"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );
                authorList.add(author);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Book> bookList = new ArrayList<>();
        sql = "SELECT * FROM titles";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getInt("editionNumber"),
                        rs.getString("copyright")
                );
                bookList.add(book);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadBookAuthors(bookList, authorList);
        loadAuthorBooks(authorList, bookList);
        return authorList;
    }

    private void loadBookAuthors(List<Book> bookList, List<Author> authorList) {
        String sql = "SELECT * FROM authorISBN";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String isbn = rs.getString("isbn");

                Book book = findBookByIsbn(isbn, bookList);
                Author author = findAuthorById(authorID, authorList);

                if (book != null && author != null) {
                    book.addAuthor(author);
                    author.addBook(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAuthorBooks(List<Author> authorList, List<Book> bookList) {
        String sql = "SELECT * FROM authorISBN";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String isbn = rs.getString("isbn");

                Book book = findBookByIsbn(isbn, bookList);
                Author author = findAuthorById(authorID, authorList);

                if (book != null && author != null) {
                    author.addBook(book);
                    book.addAuthor(author);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        String sql = "UPDATE titles SET title = ?, editionNumber = ?, copyright = ? WHERE isbn = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setInt(2, book.getEditionNumber());
            pstmt.setString(3, book.getCopyright());
            pstmt.setString(4, book.getIsbn());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAuthor(Author author) {
        String sql = "UPDATE authors SET firstName = ?, lastName = ? WHERE authorID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, author.getFirstName());
            pstmt.setString(2, author.getLastName());
            pstmt.setInt(3, author.getAuthorID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String isbn) {
        String authorISBNSQL = "DELETE FROM authorISBN WHERE isbn = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(authorISBNSQL)) {
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "DELETE FROM titles WHERE isbn = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAuthor(int authorID) {
        String authorISBNSQL = "DELETE FROM authorISBN WHERE authorID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(authorISBNSQL)) {
            pstmt.setInt(1, authorID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "DELETE FROM authors WHERE authorID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, authorID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book findBookByIsbn(String isbn, List<Book> bookList) {
        for (Book book : bookList) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public Author findAuthorById(int authorID, List<Author> authorList) {
        for (Author author : authorList) {
            if (author.getAuthorID() == authorID) {
                return author;
            }
        }
        return null;
    }
}



