package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.InternalPost;
import ru.netology.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepository {

    private final Map<Long, InternalPost> posts = new ConcurrentHashMap<>();

    public List<Post> all(boolean includeDeleted) {
        return posts.values().stream()
                .filter((p) -> p.notDeleted() || includeDeleted)
                .map(InternalPost::toPost)
                .toList();
    }

    public List<Post> all() {
        return all(false);
    }

    public Optional<Post> getById(long id, boolean includeDeleted) {
        if (!posts.containsKey(id) ) {
            return Optional.empty();
        }
        if (includeDeleted) {
            return Optional.of(posts.get(id).toPost());
        }
        return posts.get(id).toPostIfNotDeleted();
    }

    public Optional<Post> getById(long id) {
        return getById(id, false);
    }

    public Post save(Post post) {
        posts.put(post.getId(), new InternalPost(post));
        return post;
    }

    public void removeById(long id, boolean permanent) {
        if (posts.containsKey(id)) {
            if (permanent) {
                posts.remove(id);
            } else {
                posts.get(id).setDeleted(true);
            }
        }
    }

    public void removeById(long id) {
        removeById(id, false);
    }
}
