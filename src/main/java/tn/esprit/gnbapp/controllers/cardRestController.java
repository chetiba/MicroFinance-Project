package tn.esprit.gnbapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.card;
import tn.esprit.gnbapp.services.IcardServices;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class cardRestController {

    private final IcardServices cardServices;
    @PostMapping("/add")
    card addcard (@RequestBody card card) { return cardServices.addOrUpdatecard (card);
    }
    @PutMapping("/update")
    card updatecard (@RequestBody card card) {
        return cardServices.addOrUpdatecard (card);
    }
    @GetMapping("/get/{id}")
    card getcard (@PathVariable("id") int id_card) {
        return cardServices.retrievecard(id_card);
    }
    @GetMapping("/all")
    List<card> getAllcards() { return cardServices.retrieveAllcards(); }
    @DeleteMapping("/delete/{id}")
    void deletecard (@PathVariable("id") int id_card) {
        cardServices.removecard (id_card);
    }
}
