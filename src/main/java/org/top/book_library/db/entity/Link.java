package org.top.book_library.db.entity;


import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

// таблица ссылок на скачивание книги
@Entity
@Table(name = "link_t")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_f")
    @NotBlank(message = "Name link is required")
    private String nameLink;                               // название ссылки

    @Column(name = "linkDownload")
    @NotBlank(message = "Link is required")
    @URL(protocol = "https")
    private String linkDownload;                            // url адрес ссылки

    // конструктор по умолчанию
    public Link() {

    }

    // конструктор с 2-мя параметрами
    public Link(String linkDownload, String nameLink) {
        this.linkDownload = linkDownload;
        this.nameLink = nameLink;
    }

    // конструктор с 3-мя параметрами
    public Link(Long id, String nameLink, String linkDownload) {
        this.id = id;
        this.nameLink = nameLink;
        this.linkDownload = linkDownload;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameLink() {
        return nameLink;
    }

    public void setNameLink(String nameLink) {
        this.nameLink = nameLink;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", nameLink='" + nameLink + '\'' +
                ", linkDownload='" + linkDownload + '\'' +
                '}';
    }
}
