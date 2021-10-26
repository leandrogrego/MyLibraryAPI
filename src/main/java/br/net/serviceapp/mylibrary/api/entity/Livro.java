package br.net.serviceapp.mylibrary.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date publishDate;
    private String urlCover;
    
    private String[] autors;

    @ManyToOne
    private User user;

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
    public String[] getAutors() {
        return autors;
    }
    public void setAutors(String[] autors) {
        this.autors = autors;
    }
    public String getUrlCover() {
        return urlCover;
    }
    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
