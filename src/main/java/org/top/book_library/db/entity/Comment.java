package org.top.book_library.db.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.top.book_library.db.entity.security.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text" , length = 2000)
    private String text;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date = LocalDateTime.now();


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String text, LocalDateTime date, Book book, User user) {
        this.text = text;
        this.date = date;
        this.book = book;
        this.user = user;

    }

    public Comment(String text, Book book, User user) {
        this.text = text;
        this.book = book;
        this.user = user;
        LocalDateTime.now();
    }
    public Comment() {

    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", book=" + book +
                ", user=" + user +
                '}';
    }
}
