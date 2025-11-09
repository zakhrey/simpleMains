package zakhrey.sql.jdbc.simple.connection;

import java.sql.*;

public class SimpleConnectionMain {

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                Statement statement = connection.createStatement()) {

            String sql = "select * from simple_main.book where release_date > '1997-11-15';";
            try(ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    System.out.println("name: " + resultSet.getString("name") + " ".repeat(31 - resultSet.getString("name").length()) + "release date: " + resultSet.getString("release_date"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
