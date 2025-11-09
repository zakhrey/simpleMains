package zakhrey.sql.jdbc.repository.app.repository;

import zakhrey.sql.jdbc.repository.app.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BookRepositoryJdbcImpl implements BookRepository {

    //language=SQL
    private static final String SQL_INSERT = "insert into simple_main.book(name, release_date) VALUES (?, ?);";
    //language=SQL
    private static final String SQL_UPDATE = "update simple_main.book set name = ?, release_date = ? where book.id = ?::uuid;";
    //language=SQL
    private static final String SQL_DELETE = "delete from simple_main.book where book.id = ?::uuid;";
    //language=SQL
    private static final String SQL_FIND_BY_ID = "select * from simple_main.book where id = ?::uuid";
    //language=SQL
    private static final String SQL_FIND_ALL = "select * from simple_main.book order by id limit ? offset ?;";
    private final Connection connection;

    public BookRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    private static final Function<ResultSet, Book> mapBook = resultSet -> {
        try {
            return new Book(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getTimestamp("release_date").toLocalDateTime()
            );
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public void save(Book book) {

        try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getName());
            statement.setTimestamp(2, Timestamp.valueOf(book.getReleaseDate()));

            int res = statement.executeUpdate();

            if(res != 1) {
                throw new SQLException("Error when insert data");
            }
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                book.setId(rs.getString("id"));
            } else {
                throw new SQLException("There is no id in db");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Book book) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setString(1, book.getName());
            statement.setTimestamp(2, Timestamp.valueOf(book.getReleaseDate()));
            statement.setString(3, book.getId());

            int res = statement.executeUpdate();

            if(res != 1) {
                throw new SQLException("Error when update data");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void deleteById(String id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Optional<Book> findById(String id) {

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapBook.apply(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Book> findAll(Integer page, Integer limit) {

        List<Book> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {

            statement.setInt(1, limit);
            statement.setInt(2, limit * page);
            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    result.add(mapBook.apply(rs));
                }
                return result;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
