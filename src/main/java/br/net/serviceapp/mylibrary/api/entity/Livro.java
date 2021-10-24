package br.net.serviceapp.mylibrary.api.entity;

import java.util.List;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date publishDate;
    private List<String> autors;
    private String urlCover;

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    public List<String> getAutors() {
        return autors;
    }
    public void setAutors(List<String> autors) {
        this.autors = autors;
    }
    public String getUrlCover() {
        return urlCover;
    }
    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

}
