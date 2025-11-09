package zakhrey.sql.jdbc.repository.app.model;

import java.time.LocalDateTime;

public class Book {

    private String id;
    private String name;
    private final LocalDateTime releaseDate;

    public Book(String name) {
        this.name = name;
        this.releaseDate = LocalDateTime.now();
    }

    public Book(String name, LocalDateTime releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public Book(String id, String name, LocalDateTime releaseDate) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
