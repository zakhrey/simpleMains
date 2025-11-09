package zakhrey.sql.jdbc.repository.app.repository;

import zakhrey.sql.jdbc.repository.app.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void save(Book book);
    void update(Book book);
    void deleteById(String id);
    Optional<Book> findById(String id);

    List<Book> findAll(Integer page, Integer limit);
}
