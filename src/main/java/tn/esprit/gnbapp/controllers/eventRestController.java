package tn.esprit.gnbapp.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.event;
import tn.esprit.gnbapp.services.IeventServices;
import tn.esprit.gnbapp.services.eventServicesImpl;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
@AllArgsConstructor
public class eventRestController {
    @Autowired
    IeventServices eventServices;

    @Autowired
    eventServicesImpl eventServiceImpl;


    @PostMapping("/add")
    event addevent(@RequestBody event event) {
        return eventServices.addOrUpdateevent(event);
    }

    @PutMapping("/update")
    event updateevent(@RequestBody event event) {
        return eventServices.addOrUpdateevent(event);
    }

    @GetMapping("/get/{id_event}")
    event getevent(@PathVariable("id_event") int id_event) {
        return eventServices.retrieveevent(id_event);
    }

    @GetMapping("/all")
    List<event> getAllevents() {
        return eventServiceImpl.retrieveAllevents();
    }

    @DeleteMapping("/delete/{id_event}")
    void deleteevent(@PathVariable("id_event") int id_event) {
        eventServices.removeevent(id_event);
    }


    @PostMapping("/{id_event}/assign/{id_user}")
    public ResponseEntity<event> assignEventToUser(@PathVariable("id_event") int id_event, @PathVariable("id_user") int id_user) {
        event event = eventServiceImpl.assignEventToUser(id_event, id_user);
        return ResponseEntity.ok().body(event);

    }

    @GetMapping("/likesbyevent/{eventId}")
    public ResponseEntity<event> getEventById(@PathVariable int eventId) {
        event event = eventServices.getEventById(eventId);
        return ResponseEntity.ok(event);
    }

    @PatchMapping("/likes/{eventId}")
    public ResponseEntity<event> updateEventLikes(
            @PathVariable int eventId,
            @RequestParam(value = "userId") int userId,
            @RequestParam(value = "action") String action
    ) {
        event updatedEvent = eventServices.updateEventLikes(eventId, userId, action);
        return ResponseEntity.ok(updatedEvent);
    }
}
