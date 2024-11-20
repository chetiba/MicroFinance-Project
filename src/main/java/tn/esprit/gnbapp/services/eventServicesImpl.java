package tn.esprit.gnbapp.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.Exceptions.BadRequestException;
import tn.esprit.gnbapp.Exceptions.ResourceNotFoundException;
import tn.esprit.gnbapp.entities.event;
import tn.esprit.gnbapp.entities.user;
import tn.esprit.gnbapp.repositories.userRepository;

import java.util.List;

@Service
@AllArgsConstructor

public class eventServicesImpl implements IeventServices {
   @Autowired
   userRepository userRepository;
    @Autowired
    private tn.esprit.gnbapp.repositories.eventRepository eventRepository;


    @Override
    public List<event> retrieveAllevents() {
        return eventRepository.findAll();
    }

    @Override
    public event addOrUpdateevent(event e) {
        return eventRepository.save(e);    }

    @Override
    public event retrieveevent(int id_event) {
        return eventRepository.findById(id_event).orElse(null);
    }

    @Override
    public void removeevent(int id_event) {
        eventRepository.deleteById(id_event);

    }

    @Override
    public event assignEventToUser(int id_event, int id_user){
        event event = eventRepository.findById(id_event).orElse(null);
        user user = userRepository.findById(id_user).orElse(null);
        assert event != null;
        event.setUser(user);
        return eventRepository.save(event);
    }

//update event based on user likes
    @Override
    public event updateEventLikes(int eventId, int userId, String action) {
        event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("event", "id", eventId));

        if ("like".equalsIgnoreCase(action)) {
            event.getLikesByUser().add(userId);
            event.setLikes(event.getLikes() + 1);
        } else if ("dislike".equalsIgnoreCase(action)) {
            event.getDislikesByUser().add(userId);
            event.setDislikes(event.getDislikes() + 1);
        } else {
            throw new BadRequestException("Invalid action parameter: " + action);
        }

        return eventRepository.save(event);
    }

    //get event likes by a specified user
    @Override
    public event getEventById(int eventId) {
        event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("event", "id", eventId));

        int totalLikes = event.getLikesByUser().size() + event.getLikes();
        int totalDislikes = event.getDislikesByUser().size() + event.getDislikes();

        event.setLikes(totalLikes);
        event.setDislikes(totalDislikes);

        return event;
    }


}