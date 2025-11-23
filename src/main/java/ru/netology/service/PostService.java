package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PostService {
    private final PostRepository repository;
    private final AtomicLong counter = new AtomicLong();

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        post.setId(counter.incrementAndGet());
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(repository.getById(id).orElseThrow(NotFoundException::new).getId());
    }

    public Post updateById(long id, Post post) {
        post.setId(id);
        repository.getById(id).orElseThrow(NotFoundException::new);
        return repository.save(post);
    }
}

