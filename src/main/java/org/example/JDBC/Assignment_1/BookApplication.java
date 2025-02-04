package org.example.JDBC.Assignment_1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class BookApplication {

    private static final BookDatabaseManager dbm = new BookDatabaseManager();
    private static List<Book> books = dbm.getAllBooks();
    private static List<Author> authors = dbm.getAllAuthors();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Java Book Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            JPanel panel = new JPanel();
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel label = new JLabel("Java Book Application. Choose Action:");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);

            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            addButton(panel, "Show All Books", e -> showAllBooks());
            addButton(panel, "Show All Authors", e -> showAllAuthors());
            addButton(panel, "Update Book", e -> updateBook());
            addButton(panel, "Update Author", e -> updateAuthor());
            addButton(panel, "Add New Author", e -> addNewAuthor());
            addButton(panel, "Add New Book", e -> addNewBook());
            addButton(panel, "Delete Book By ISBN", e -> deleteBook());
            addButton(panel, "Delete Author By ID", e -> deleteAuthor());

            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            JButton exitButton = new JButton("Exit");
            exitButton.setFont(new Font("Arial", Font.BOLD, 14));
            exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            exitButton.addActionListener(e -> System.exit(0));
            panel.add(exitButton);

            frame.getContentPane().add(panel);
            frame.setVisible(true);
        });
    }

    private static void addButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);
        button.setMaximumSize(new Dimension(200, 30));
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private static void showAllBooks() {
        StringBuilder message = new StringBuilder("*** All Books ***\n");
        for (Book book : books) {
            message.append("----------------------------\n");
            message.append("ISBN: ").append(book.getIsbn()).append("\n");
            message.append("Title: ").append(book.getTitle()).append("\n");
            message.append("Edition Number: ").append(book.getEditionNumber()).append("\n");
            message.append("Copyright: ").append(book.getCopyright()).append("\n");
            message.append("Authors:\n");
            for (Author author : book.getAuthorList()) {
                message.append("  - ").append(author.getFirstName()).append(" ").append(author.getLastName()).append("\n");
            }
            message.append("----------------------------\n\n");
        }

        JTextArea textArea = new JTextArea(message.toString());
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Books", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showAllAuthors() {
        StringBuilder message = new StringBuilder("*** All Authors ***\n");
        for (Author author : authors) {
            message.append("----------------------------\n");
            message.append("Author ID: ").append(author.getAuthorID()).append("\n");
            message.append("First Name: ").append(author.getFirstName()).append("\n");
            message.append("Last Name: ").append(author.getLastName()).append("\n");
            message.append("Author Books:\n");
            for (Book book : author.getBookList()) {
                message.append("  - ").append(book.getTitle()).append("\n");
            }
            message.append("----------------------------\n\n");
        }

        JTextArea textArea = new JTextArea(message.toString());
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Authors", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void updateBook() {
        String bookISBN = JOptionPane.showInputDialog(null, "Enter Book ISBN:", "Update Book", JOptionPane.QUESTION_MESSAGE);
        Book bookToUpdate = dbm.findBookByIsbn(bookISBN, books);

        if (bookToUpdate != null) {
            String updatedBookTitle = JOptionPane.showInputDialog(null, "Enter New Book Title:", "Update Book", JOptionPane.QUESTION_MESSAGE);
            String updatedBookCopyrightYear = JOptionPane.showInputDialog(null, "Enter New Book Copyright Year:", "Update Book", JOptionPane.QUESTION_MESSAGE);
            String updatedBookEditionNumberStr = JOptionPane.showInputDialog(null, "Enter New Book Edition Number:", "Update Book", JOptionPane.QUESTION_MESSAGE);

            int updatedBookEditionNumber = Integer.parseInt(updatedBookEditionNumberStr);

            bookToUpdate.setTitle(updatedBookTitle);
            bookToUpdate.setEditionNumber(updatedBookEditionNumber);
            bookToUpdate.setCopyright(updatedBookCopyrightYear);

            dbm.updateBook(bookToUpdate);
            JOptionPane.showMessageDialog(null, "Book updated successfully!", "Update Book", JOptionPane.INFORMATION_MESSAGE);

            books = dbm.getAllBooks();
            authors = dbm.getAllAuthors();
        } else {
            JOptionPane.showMessageDialog(null, "Book with ISBN " + bookISBN + " not found!", "Update Book", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void updateAuthor() {
        String authorIDStr = JOptionPane.showInputDialog(null, "Enter Author ID:", "Update Author", JOptionPane.QUESTION_MESSAGE);
        int authorToEditID = Integer.parseInt(authorIDStr);
        Author authorToUpdate = dbm.findAuthorById(authorToEditID, authors);

        if (authorToUpdate != null) {
            String authorNewFirstName = JOptionPane.showInputDialog(null, "Enter New First Name:", "Update Author", JOptionPane.QUESTION_MESSAGE);
            String authorNewLastName = JOptionPane.showInputDialog(null, "Enter New Last Name:", "Update Author", JOptionPane.QUESTION_MESSAGE);

            authorToUpdate.setFirstName(authorNewFirstName);
            authorToUpdate.setLastName(authorNewLastName);

            dbm.updateAuthor(authorToUpdate);
            JOptionPane.showMessageDialog(null, "Author updated successfully!", "Update Author", JOptionPane.INFORMATION_MESSAGE);

            books = dbm.getAllBooks();
            authors = dbm.getAllAuthors();
        } else {
            JOptionPane.showMessageDialog(null, "Author with ID " + authorToEditID + " not found!", "Update Author", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void addNewAuthor() {
        String newAuthorFirstName = JOptionPane.showInputDialog(null, "Enter Author First Name:", "Add New Author", JOptionPane.QUESTION_MESSAGE);
        String newAuthorLastName = JOptionPane.showInputDialog(null, "Enter Author Last Name:", "Add New Author", JOptionPane.QUESTION_MESSAGE);

        Author authorToAdd = new Author(0, newAuthorFirstName, newAuthorLastName);

        dbm.addAuthor(authorToAdd);
        JOptionPane.showMessageDialog(null, "Author added successfully!", "Add New Author", JOptionPane.INFORMATION_MESSAGE);

        authors = dbm.getAllAuthors();
        books = dbm.getAllBooks();
    }

    private static void addNewBook() {
        String newBookISBN = JOptionPane.showInputDialog(null, "Enter New Book ISBN:", "Add New Book", JOptionPane.QUESTION_MESSAGE);
        String newBookTitle = JOptionPane.showInputDialog(null, "Enter New Book Title:", "Add New Book", JOptionPane.QUESTION_MESSAGE);
        String newBookCopyrightYear = JOptionPane.showInputDialog(null, "Enter New Book Copyright Year:", "Add New Book", JOptionPane.QUESTION_MESSAGE);
        String newBookEditionNumberStr = JOptionPane.showInputDialog(null, "Enter New Book Edition Number:", "Add New Book", JOptionPane.QUESTION_MESSAGE);

        int newBookEditionNumber = Integer.parseInt(newBookEditionNumberStr);

        Book newBook = new Book(newBookISBN, newBookTitle, newBookEditionNumber, newBookCopyrightYear);

        StringBuilder authorsList = new StringBuilder("Existing Authors:\n");
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            authorsList.append(i + 1).append(": ").append(author.getFirstName()).append(" ").append(author.getLastName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, authorsList.toString(), "Add New Book", JOptionPane.INFORMATION_MESSAGE);

        String numAuthorsStr = JOptionPane.showInputDialog(null, "Enter number of authors to add to the book:", "Add New Book", JOptionPane.QUESTION_MESSAGE);
        int numAuthors = Integer.parseInt(numAuthorsStr);

        for (int i = 0; i < numAuthors; i++) {
            String choice = JOptionPane.showInputDialog(null, "Do you want to add an existing author? (yes/no):", "Add New Book", JOptionPane.QUESTION_MESSAGE);

            if (choice.equalsIgnoreCase("yes")) {
                String authorIndexStr = JOptionPane.showInputDialog(null, "Enter author number from the list above:", "Add New Book", JOptionPane.QUESTION_MESSAGE);
                int authorIndex = Integer.parseInt(authorIndexStr) - 1;

                if (authorIndex >= 0 && authorIndex < authors.size()) {
                    Author existingAuthor = authors.get(authorIndex);
                    newBook.addAuthor(existingAuthor);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid author number.", "Add New Book", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                String firstName = JOptionPane.showInputDialog(null, "Enter Author First Name:", "Add New Book", JOptionPane.QUESTION_MESSAGE);
                String lastName = JOptionPane.showInputDialog(null, "Enter Author Last Name:", "Add New Book", JOptionPane.QUESTION_MESSAGE);

                int newAuthorID = authors.getLast().getAuthorID() + 1;

                Author newAuthor = new Author(newAuthorID, firstName, lastName);
                newBook.addAuthor(newAuthor);
            }
        }

        dbm.addBook(newBook);
        JOptionPane.showMessageDialog(null, "Book added successfully!", "Add New Book", JOptionPane.INFORMATION_MESSAGE);
        books = dbm.getAllBooks();
        authors = dbm.getAllAuthors();
    }

    private static void deleteBook() {
        String bookToDeleteISBN = JOptionPane.showInputDialog(null, "Enter Book ISBN:", "Delete Book", JOptionPane.QUESTION_MESSAGE);

        dbm.deleteBook(bookToDeleteISBN);
        JOptionPane.showMessageDialog(null, "Book deleted successfully!", "Delete Book", JOptionPane.INFORMATION_MESSAGE);

        books = dbm.getAllBooks();
        authors = dbm.getAllAuthors();
    }

    private static void deleteAuthor() {
        String authorIDStr = JOptionPane.showInputDialog(null, "Enter Author ID:", "Delete Author", JOptionPane.QUESTION_MESSAGE);
        int authorToDeleteID = Integer.parseInt(authorIDStr);

        dbm.deleteAuthor(authorToDeleteID);
        JOptionPane.showMessageDialog(null, "Author deleted successfully!", "Delete Author", JOptionPane.INFORMATION_MESSAGE);

        books = dbm.getAllBooks();
        authors = dbm.getAllAuthors();
    }
}