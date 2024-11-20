package tn.esprit.gnbapp.services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import tn.esprit.gnbapp.entities.consultation;
import tn.esprit.gnbapp.entities.credit;
import tn.esprit.gnbapp.repositories.IclaimRepository;
import tn.esprit.gnbapp.entities.claim;
import tn.esprit.gnbapp.repositories.IcreditRepository;
import tn.esprit.gnbapp.repositories.IuserRepository;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimServicesImpl implements IClaimServices{
    private final IclaimRepository iclaimRepository;
    private final IcreditRepository icreditRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    IuserRepository userRepository;

    @Override
    public claim  assignClaimToCredit(Integer id_claim, Integer id_credit) {
        claim c = iclaimRepository.findById(id_claim).orElse(null);
        credit cr = icreditRepository.findById(id_credit).orElse(null);
        c.setCredit(cr);
        return iclaimRepository.save(c);
    }
    @Override
    public List<claim> getallclaims(){
        return iclaimRepository.findAll();

    }
    @Override
    public claim addOrUpdateClaim(claim c) {
        return iclaimRepository.save(c);
    }

    @Override
    public void removeClaim(Integer id_claim) {
        iclaimRepository.deleteById(id_claim);

    }
    @Override
    public claim Addclaim(claim claim) throws BadWordException {
        // Vérification de bad words dans la description
        String description = claim.getDescription_claim().toLowerCase();
        List<String> badWords = getBadWords(); // récupération de la liste de bad words

        for (String word : badWords) {
            if (description.contains(word)) {
                throw new BadWordException("La description contient un mot interdit : " + word);
            }
        }

        // Si la description ne contient pas de bad words, enregistrer la réclamation
        return iclaimRepository.save(claim);
    }

    private List<String> getBadWords() {
        List<String> badWords = new ArrayList<>();

        try {
            File file = ResourceUtils.getFile("classpath:badwords.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                badWords.add(line.trim().toLowerCase());
            }

            reader.close();
        } catch (IOException e) {
            // en cas d'erreur de lecture de fichier
            System.out.println("Erreur lors de la récupération des bad words : " + e.getMessage());
        }

        return badWords;
    }


}
