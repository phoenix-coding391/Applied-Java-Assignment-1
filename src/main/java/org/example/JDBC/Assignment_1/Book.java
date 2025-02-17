package org.example.JDBC.Assignment_1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book with an ISBN, title, edition number, copyright, and a list of authors.
 */
public class Book {
    private String isbn;
    private String title;
    private int editionNumber;
    private String copyright;
    private List<Author> authorList;

    /**
     * Constructs a new Book with the specified ISBN, title, edition number, and copyright.
     *
     * @param isbn The ISBN of the book.
     * @param title The title of the book.
     * @param editionNumber The edition number of the book.
     * @param copyright The copyright information of the book.
     */
    public Book(String isbn, String title, int editionNumber, String copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionNumber = editionNumber;
        this.copyright = copyright;
        this.authorList = new ArrayList<>();
    }

    /**
     * Returns the ISBN of the book.
     *
     * @return The ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title The new title of the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the edition number of the book.
     *
     * @return The edition number of the book.
     */
    public int getEditionNumber() {
        return editionNumber;
    }

    /**
     * Sets the edition number of the book.
     *
     * @param editionNumber The new edition number of the book.
     */
    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    /**
     * Returns the copyright information of the book.
     *
     * @return The copyright information of the book.
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Sets the copyright information of the book.
     *
     * @param copyright The new copyright information of the book.
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * Returns the list of authors associated with the book.
     *
     * @return The list of authors.
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * Adds an author to the book's author list if the author is not already present.
     *
     * @param author The author to add.
     */
    public void addAuthor(Author author) {
        if (!authorList.contains(author)) {
            authorList.add(author);
            author.addBook(this);
        }
    }
}
