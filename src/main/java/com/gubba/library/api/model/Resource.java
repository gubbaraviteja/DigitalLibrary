package com.gubba.library.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "RESOURCE")
public class Resource {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private Date publishedDate;
    private ResourceType resourceType;    //This can hold values like book, movie, music
    private Integer waitingList;

    public Resource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getWaitingList() {
        return waitingList;
    }

    public void setWaitingList(Integer waitingList) {
        this.waitingList = waitingList;
    }

    public Resource(Long id, String title, String author, Date publishedDate, ResourceType resourceType, Integer waitingList) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.resourceType = resourceType;
        this.waitingList = waitingList;
    }
}
