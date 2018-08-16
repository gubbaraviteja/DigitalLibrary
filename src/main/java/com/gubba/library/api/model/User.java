package com.gubba.library.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name = "LIBRARY_USER")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String emailId;
    private Date joinedDate;
    @Lob
    private byte[] wishlist;  //for the sake of POC, using byte array which can hold list of titles

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public byte[] getWishlist() {
        return wishlist;
    }

    public void setWishlist(byte[] wishlist) {
        this.wishlist = wishlist;
    }

    public User() {
        super();
    }

    public User(Long id, String username, String emailId, Date joinedDate, byte[] wishlist) {
        super();
        this.id = id;
        this.username = username;
        this.emailId = emailId;
        this.joinedDate = joinedDate;
        this.wishlist = wishlist;
    }
}
