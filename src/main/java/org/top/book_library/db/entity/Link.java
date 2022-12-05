package org.top.book_library.db.entity;


import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "link_t")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_f")
    @NotBlank(message = "Name link is required")
    private String nameLink;

    @Column(name = "linkDownload")
    @NotBlank(message = "Link is required")
    @URL(protocol="https")
    private String linkDownload;

    public Link() {

    }

    public Link(String linkDownload, String nameLink) {
        this.linkDownload = linkDownload;
        this.nameLink = nameLink;
    }

    public Link(Long id, String linkDownload) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Link link)) return false;
        return Objects.equals(id, link.id) && Objects.equals(nameLink, link.nameLink) && Objects.equals(linkDownload, link.linkDownload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameLink, linkDownload);
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
