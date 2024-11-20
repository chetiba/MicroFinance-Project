package tn.esprit.gnbapp.services;

import tn.esprit.gnbapp.entities.event;
import tn.esprit.gnbapp.entities.event;

import java.util.List;

public interface IeventServices {
    List<event> retrieveAllevents() ;
    event addOrUpdateevent(event e);
    event retrieveevent (int id_event);
    void removeevent ( int id_event);

    event assignEventToUser(int id_event, int id_user);


    event getEventById(int eventId);


    event updateEventLikes(int eventId, int userId, String action);
}
