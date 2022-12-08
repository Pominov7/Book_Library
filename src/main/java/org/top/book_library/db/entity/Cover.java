package org.top.book_library.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

// таблица обложек
@Entity
@Table(name = "cover_t")
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name cover is required")
    private String nameCover;                                // название обложки

    @Column(name = "urlCover", unique = true)
    @NotBlank(message = " A path to the cover is required")
    private String urlCover;                                 // путь к обложке

    // конструктор по умолчанию
    public Cover() {

    }

    // конструктор с 3-мя параметрами
    public Cover(Long id, String nameCover, String urlCover) {
        this.id = id;
        this.nameCover = nameCover;
        this.urlCover = urlCover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCover() {
        return nameCover;
    }

    public void setNameCover(String nameCover) {
        this.nameCover = nameCover;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "id=" + id +
                ", nameCover='" + nameCover + '\'' +
                ", urlCover='" + urlCover + '\'' +
                '}';
    }
}
