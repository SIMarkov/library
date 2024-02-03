package pu.project.app.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class BookDTO extends AbstractEntity {

    @Lob
    @Column(length = 1000000)
    private String title;
    private Long authorId;
    private Long publisherId;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getAuthorID() {
        return authorId;
    }
    public void setAuthor(Long authorId) {
        this.authorId = authorId;
    }
    public Long getPublisherID() {
        return publisherId;
    }
    public void setPublisher(Long publisherId) {
        this.publisherId = publisherId;
    }
}