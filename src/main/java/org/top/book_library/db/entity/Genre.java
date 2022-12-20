package org.top.book_library.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

// таблица жанров
@Entity
@Table(name = "genre_t")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = true)
    @NotBlank(message = "Name genre is required")
    private String name;                                             // название жанра

    @OneToMany(mappedBy = "genre", cascade = CascadeType.PERSIST)
    private Set<Book> books;                                         // список книг жанра

    // конструктор по умолчанию
    public Genre() {

    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(Long id) {
        this.id = id;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Genre(String nameGenre) {
        this.name = nameGenre;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameGenre) {
        this.name = nameGenre;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", nameGenre='" + name + '\'' +
                '}';
    }
}
