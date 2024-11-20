package tn.esprit.gnbapp.services;

import tn.esprit.gnbapp.entities.post;
import java.util.List;

public interface IPostServices {
    List<post> retrieveAllPosts();

    post addOrUpdatePost(post p);

    post retrievePost (Integer id_post);

    void removePost (Integer id_post);
}
