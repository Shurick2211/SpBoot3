package com.sn.org.spboot3.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    private long postId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Person author;
    private String text;

    private LocalDateTime dateTime;

    public Post() {
    }

    public Post(long postId, Person authorId, String text, LocalDateTime dateTime) {
        this.postId = postId;
        this.author = authorId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
