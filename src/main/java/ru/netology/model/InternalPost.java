package ru.netology.model;

import java.util.Optional;

public class InternalPost {
    private long id;
    private String content;
    private boolean deleted;

    public InternalPost(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.deleted = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean notDeleted() {
        return !deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Post toPost() {
        return new Post(id, content);
    }

    public Optional<Post> toPostIfNotDeleted() {
        return deleted ? Optional.empty() : Optional.of(toPost());
    }
}
