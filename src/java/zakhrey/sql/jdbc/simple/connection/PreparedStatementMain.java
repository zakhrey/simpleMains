package zakhrey.sql.jdbc.simple.connection;

import java.sql.*;

public class PreparedStatementMain {

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";


    public static void main(String[] args) {

        String preparedQuery = "select * from simple_main.book where release_date > ?::timestamp;";
        String query = "select * from pg_prepared_statements;";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(preparedQuery);
            Statement statement = connection.createStatement()) {
            preparedStatement.setString(1, "1997-11-15");

            preparedStatement.executeQuery();
            preparedStatement.executeQuery();
            preparedStatement.executeQuery();
            preparedStatement.executeQuery();
            preparedStatement.executeQuery();


            try(ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    System.out.println("name: " + resultSet.getString("name") + " ".repeat(20 - resultSet.getString("name").length()) + "statement: " + resultSet.getString("statement"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
