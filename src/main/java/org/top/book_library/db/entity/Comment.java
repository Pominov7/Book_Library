package org.top.book_library.db.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.top.book_library.db.entity.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

// таблица комментариев
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", length = 2000,nullable = false)
    @NotBlank(message = "Enter text, or click cancel")
    private String text;                                 // текст комментария

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date date = new Date();    // дата и время добавления комментария


    @ManyToOne
    @JoinColumn(name = "book_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;                                   // книга, к которой пишется комментарий
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;                                   // пользователь, который пишет комментарий

    // конструктор по умолчанию
    public Comment() {

    }

    // конструктор с 3-мя параметрами
    public Comment(String text, Book book, User user) {
        this.text = text;
        this.book = book;
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
