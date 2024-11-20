package tn.esprit.gnbapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.card;

import tn.esprit.gnbapp.repositories.cardRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class cardServicesImpl implements IcardServices {
    private final cardRepository cardRepository ;
    @Override
    public List<card> retrieveAllcards() {
        return cardRepository.findAll();
    }

    @Override
    public card addOrUpdatecard(card c) {
        return cardRepository.save(c);
    }

    @Override
    public card retrievecard(int id_card) {
        return cardRepository.findById(id_card).orElse(null);
    }

    @Override
    public void removecard(int id_card) {
        cardRepository.deleteById(id_card);

    }
}
