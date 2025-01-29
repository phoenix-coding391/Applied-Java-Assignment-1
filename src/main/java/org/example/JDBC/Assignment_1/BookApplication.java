package org.example.JDBC.Assignment_1;

import java.util.List;
import java.util.Scanner;

public class BookApplication {

    public static void main(String[] args) {
        BookDatabaseManager dbm = new BookDatabaseManager();

        System.out.println("Java Book Application. Enter Command: ");

        Scanner input = new Scanner(System.in);

        List<Book> books = dbm.getAllBooks();
        List<Author> authors = dbm.getAllAuthors();

        while (true) {
            System.out.println("Choose Action:\n1: Show All Books\n2: Show All Authors\n3: Update Book\n4: Add New Author\n5: Add New Book\n6: Exit\n");

            System.out.print("Enter Action: ");
            int command = input.nextInt();

            switch (command) {
                case 1:
                    for (Book book : books) {
                        System.out.println("ISBN: " + book.getIsbn());
                        System.out.println("Title: " + book.getTitle());
                        System.out.println("Edition Number: " + book.getEditionNumber());
                        System.out.println("Copyright: " + book.getCopyright());
                        System.out.println("Authors:");
                        for (Author author : book.getAuthorList()) {
                            System.out.println("  - " + author.getFirstName() + " " + author.getLastName());
                        }
                        System.out.println();
                    }
                    break;
                case 2:
                    for (Author author : authors) {
                        System.out.println();
                        System.out.println("Author ID: " + author.getAuthorID());
                        System.out.println("First Name: " + author.getFirstName());
                        System.out.println("Last Name: " + author.getLastName());
                        System.out.println("Author Books:");
                        for (Book book : author.getBookList()) {
                            System.out.println("  - " + book.getTitle());
                        }
                    }
                    System.out.println();
                    break;
                case 3:
                    Scanner userInput = new Scanner(System.in);
                    System.out.print("Enter Book ISBN: ");

                    String bookISBN = userInput.nextLine();

                    Book BookToUpdate = dbm.findBookByIsbn(bookISBN, books);

                    if (BookToUpdate != null) {

                        System.out.print("Enter New Book Title: ");
                        String updatedBookTitle = userInput.nextLine();

                        System.out.print("Enter New Book Copyright Year: ");
                        String updatedBookCopyrightYear = userInput.nextLine();

                        System.out.print("Enter New Book Edition Number: ");
                        int updatedBookEditionNumber = userInput.nextInt();

                        BookToUpdate.setTitle(updatedBookTitle);
                        BookToUpdate.setEditionNumber(updatedBookEditionNumber);
                        BookToUpdate.setCopyright(updatedBookCopyrightYear);

                        dbm.updateBook(BookToUpdate);
                        System.out.println("Book updated successfully!");

                        books = dbm.getAllBooks();
                        authors = dbm.getAllAuthors();

                    } else {
                        System.out.println("Book with ISBN " + bookISBN + " not found!");
                    }
                    break;
                case 4:
                        System.out.print("Enter Author First Name: ");
                        Scanner newAuthorFirstNameInput = new Scanner(System.in);
                        String newAuthorFirstName = newAuthorFirstNameInput.nextLine();

                        System.out.print("Enter Author Last Name: ");
                        Scanner newAuthorLastNameInput = new Scanner(System.in);
                        String newAuthorLastName = newAuthorLastNameInput.nextLine();

                        Author authorToAdd = new Author(0, newAuthorFirstName, newAuthorLastName);

                        dbm.addAuthor(authorToAdd);
                        System.out.println("Author added successfully!");

                        authors = dbm.getAllAuthors();
                        books = dbm.getAllBooks();
                        break;
                case 5:
                    System.out.print("Enter New Book ISBN: ");
                    Scanner newBookISBNInput = new Scanner(System.in);
                    String newBookISBN = newBookISBNInput.nextLine();

                    System.out.print("Enter New Book Title: ");
                    Scanner newBookTitleInput = new Scanner(System.in);
                    String newBookTitle = newBookTitleInput.nextLine();

                    System.out.print("Enter New Book Copyright Year: ");
                    Scanner newBookCopyrightYearInput = new Scanner(System.in);
                    String newBookCopyrightYear = newBookCopyrightYearInput.nextLine();

                    System.out.print("Enter New Book Edition Number: ");
                    Scanner newBookEditionNumberInput = new Scanner(System.in);
                    int newBookEditionNumber = newBookEditionNumberInput.nextInt();

                    Book newBook = new Book(newBookISBN, newBookTitle, newBookEditionNumber, newBookCopyrightYear);

                    // Display existing authors and allow the user to select or add new authors
                    System.out.println();
                    System.out.println("Existing Authors:");
                    for (int i = 0; i < authors.size(); i++) {
                        Author author = authors.get(i);
                        System.out.println(i + 1 + ": " + author.getFirstName() + " " + author.getLastName());
                    }

                    System.out.print("Enter number of authors to add to the book: ");
                    int numAuthors = newBookISBNInput.nextInt();
                    newBookISBNInput.nextLine(); // Consume newline

                    for (int i = 0; i < numAuthors; i++) {
                        System.out.print("Do you want to add an existing author? (yes/no): ");
                        String choice = newBookISBNInput.nextLine();

                        if (choice.equalsIgnoreCase("yes")) {
                            System.out.print("Enter author number from the list above: ");
                            int authorIndex = newBookISBNInput.nextInt() - 1;
                            newBookISBNInput.nextLine(); // Consume newline

                            if (authorIndex >= 0 && authorIndex < authors.size()) {
                                Author existingAuthor = authors.get(authorIndex);
                                newBook.addAuthor(existingAuthor);
                            } else {
                                System.out.println("Invalid author number.");
                            }
                        } else {
                            System.out.print("Enter Author First Name: ");
                            String firstName = newBookISBNInput.nextLine();
                            System.out.print("Enter Author Last Name: ");
                            String lastName = newBookISBNInput.nextLine();

                            int newAuthorID = authors.getLast().getAuthorID() + 1;

                            Author newAuthor = new Author(newAuthorID, firstName, lastName);
                            newBook.addAuthor(newAuthor);
                        }
                    }

                    dbm.addBook(newBook);

                    System.out.println("Book added successfully!");
                    books = dbm.getAllBooks();
                    authors = dbm.getAllAuthors();
                    break;
                case 6:
                    return;
            }
        }
    }
}
