package org.example.JDBC.Assignment_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an author with an ID, first name, last name, and a list of books written by the author.
 */
public class Author {
    private final int authorID;
    private String firstName;
    private String lastName;
    private final List<Book> bookList;

    /**
     * Constructs a new Author with the specified ID, first name, and last name.
     *
     * @param authorID The ID of the author.
     * @param firstName The first name of the author.
     * @param lastName The last name of the author.
     */
    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookList = new ArrayList<>();
    }

    /**
     * Returns the ID of the author.
     *
     * @return The ID of the author.
     */
    public int getAuthorID() {
        return authorID;
    }

    /**
     * Returns the first name of the author.
     *
     * @return The first name of the author.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the author.
     *
     * @param firstName The new first name of the author.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the author.
     *
     * @return The last name of the author.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the author.
     *
     * @param lastName The new last name of the author.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the list of books written by the author.
     *
     * @return The list of books.
     */
    public List<Book> getBookList() {
        return bookList;
    }

    /**
     * Adds a book to the author's book list if the book is not already present.
     *
     * @param book The book to add.
     */
    public void addBook(Book book) {
        if (!bookList.contains(book)) {
            bookList.add(book);
            book.addAuthor(this);
        }
    }
}
