package tn.esprit.gnbapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.post;
import tn.esprit.gnbapp.repositories.IpostRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class PostServicesImpl implements IPostServices{
    private final IpostRepository postRepository;

    @Override
    public List<post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public post addOrUpdatePost(post p) {
        return postRepository.save(p);
    }

    @Override
    public post retrievePost(Integer id_post) {
        return postRepository.findById(id_post).orElse(null);
    }
    @Override
    public void removePost(Integer id_post) {
        postRepository.deleteById(id_post);

    }
}
