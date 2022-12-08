package org.top.book_library.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

// Таблица авторов
@Entity
@Table(name = "author_t")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotBlank(message = "Name is required")
    private String name;                                      // имя автора
    @Column(name = "lastName")
    @NotBlank(message = "Last Name is required")
    private String lastName;                                  // фамилия автора

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    private Set<Book> books;                                  // список книг автора


    // конструктор по умолчанию
    public Author() {
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("%-18s %-16s",
                lastName, name);
    }
}
