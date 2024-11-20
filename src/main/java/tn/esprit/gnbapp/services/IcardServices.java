package tn.esprit.gnbapp.services;

import tn.esprit.gnbapp.entities.account;
import tn.esprit.gnbapp.entities.card;

import java.util.List;

public interface IcardServices {
    List<card> retrieveAllcards() ;
    card addOrUpdatecard(card c);
    card retrievecard (int id_card);
    void removecard ( int id_card);
}
