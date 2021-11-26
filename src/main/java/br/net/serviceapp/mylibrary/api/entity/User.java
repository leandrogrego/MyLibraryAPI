package br.net.serviceapp.mylibrary.api.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String socialId;
    private String provider;
    private String name;
    private boolean enabled = true;
    private String type = "user";
    private String avatar_url;
    private String phone;
    private boolean numberValidated;
    private String email;
    private boolean emailValidated;
    private Date lastAccess;
    private Date created = new Date();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Book> books;

    @JsonIgnore
    private String password = null;
    
    @JsonIgnore
	private String code;
    
    @JsonIgnore
    private String token;

    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
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
    public void setToken(String token) {
        this.token = token;
    }
    public boolean checkToken(String token) {
        return token.equals(this.token);
    }
    public String getSocialId() {
        return socialId;
    }
    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }
    public String getProvider() {
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAvatar_url() {
        return avatar_url;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
    public boolean isNumberValidated() {
        return numberValidated;
    }
    public void setNumberValidated(boolean numberValidated) {
        this.numberValidated = numberValidated;
    }
    public boolean isEmailValidated() {
        return emailValidated;
    }
    public void setEmailValidated(boolean emailValidated) {
        this.emailValidated = emailValidated;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public boolean checkCode(String code) {
        return code.equals(this.code);
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void addBook(Book book) {
        this.books.add(book);
    }

}
