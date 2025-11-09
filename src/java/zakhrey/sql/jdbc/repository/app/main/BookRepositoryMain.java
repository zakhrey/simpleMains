package zakhrey.sql.jdbc.repository.app.main;

import zakhrey.sql.jdbc.repository.app.model.Book;
import zakhrey.sql.jdbc.repository.app.repository.BookRepository;
import zakhrey.sql.jdbc.repository.app.repository.BookRepositoryJdbcImpl;

import java.sql.*;

public class BookRepositoryMain {

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            BookRepository repository = new BookRepositoryJdbcImpl(connection);

            Book book = new Book("My second book");
            repository.save(book);
            System.out.println(repository.findById(book.getId()));

            book.setName("New name for my book");
            repository.update(book);
            System.out.println(repository.findById(book.getId()));

            repository.deleteById(book.getId());
            System.out.println(repository.findById(book.getId()));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
