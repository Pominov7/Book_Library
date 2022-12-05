package org.top.book_library.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "cover_t")
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Name cover is required")
    private String nameCover;

    @Column(name = "urlCover", unique = true)
    @NotBlank(message = " A path to the cover is required")
    private String urlCover;

    public Cover() {

    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cover cover)) return false;
        return Objects.equals(id, cover.id) && Objects.equals(nameCover, cover.nameCover) && Objects.equals(urlCover, cover.urlCover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameCover, urlCover);
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
