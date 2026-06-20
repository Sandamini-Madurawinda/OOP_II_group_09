package g9.pulse.pulse.service;

import g9.pulse.pulse.model.Post;
import g9.pulse.pulse.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> searchPostsByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return postRepository.findByContentContainingIgnoreCase(keyword);
    }

    public long getPostCountByUser(String firstName, String lastName) {
        return postRepository.countByAuthorFirstNameAndAuthorLastName(firstName, lastName);
    }
}