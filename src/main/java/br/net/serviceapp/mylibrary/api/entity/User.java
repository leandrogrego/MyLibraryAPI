package br.net.serviceapp.mylibrary.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String urlAvatar;
    private String token;
    private Date lastAccess;
    private List<Booke> books;

    public List<Booke> getBooks() {
        return books;
    }
    public void setBooks(List<Booke> books) {
        this.books = books;
    }
    public Date getLastAccess() {
        return lastAccess;
    }
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUrlAvatar() {
        return urlAvatar;
    }
    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
