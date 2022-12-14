package org.top.book_library.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

// Таблица книг
@Entity
@Table(name = "book_t")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    @NotBlank(message = "Name is required")
    private String title;                              // название книги

    @Column(name = "description", length = 2000)
    @NotBlank(message = "Description is required")
    private String description;                       // описание книги
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;                            // автор книги
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "genre_id", nullable = true)
    private Genre genre;                              // жанр книги

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Link link;                                // ссылка на скачивание книги

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Cover cover;                              // обложка книги

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Comment> comments;                    // список комментариев книги


    // конструктор по умолчанию
    public Book() {
    }

    // конструктор с 6-ю параметрами
    public Book(Long id, String title, String description, Author author,
                Genre genre, Link link, Cover cover) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.link = link;
        this.cover = cover;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", link=" + link +
                ", cover=" + cover +
                '}';
    }
}
