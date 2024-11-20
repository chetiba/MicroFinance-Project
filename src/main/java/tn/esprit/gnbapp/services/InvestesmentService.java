package tn.esprit.gnbapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.fund;
import tn.esprit.gnbapp.entities.investesment;
import tn.esprit.gnbapp.repositories.FundRepository;
import tn.esprit.gnbapp.repositories.InvestesmentRepository;


import java.util.List;
@Service
@RequiredArgsConstructor

public class InvestesmentService implements IInvestesmentService{
    @Autowired
    InvestesmentRepository investesmentRepository;

    @Autowired
    FundRepository fundRepository;

    //Afficher tous les inves
    @Override
    public List<investesment> retrieveAllInvestesments() {
        return (List<investesment>) investesmentRepository.findAll();
    }

    /*l'ajout d'un inves
    le taux sera générer automatiquement
    le fund sera automatiquement mis à jour (montant + taux)
    */
    @Override
    public investesment addInvestesment(investesment i, Integer idFund) {
        float Amount = i.getAmoutInvestesment();
		/*formule de taux economique (invalide)
		i.setTauxInves((i.getAmoutInvestesment())/(pib*100));
		Le taux d'inves est variable selon le montant choisit
		Plus le montant aug plus le taux aug
		Minimum du montant investit = 7000
		*/
        if (Amount >= 7000) {
            fund f = fundRepository.findById(idFund).orElse(null);
            //Si le montant atteint 200000 le tau reste fixe à 12%
            double Rate = 0.12*(1- Math.exp(-(Amount)/10000));
            i.setTauxInves((float) Rate);
            i.setFund(f);
            //incrémentation du fund pour chaque investissement
            f.setAmountFund(f.getAmountFund()+i.getAmoutInvestesment());
            //incrémentation du taux pour chaque investissement
            List<investesment> listInves = (List<investesment>) investesmentRepository.findAll();
            float s = 0;
            for (investesment  inv : listInves) {
                s=s+(inv.getAmoutInvestesment());
            }
            s=s+i.getAmoutInvestesment();
            float pourc_inv = (i.getAmoutInvestesment())/s;
            f.setTauxFund(pourc_inv*(i.getTauxInves())+(1-pourc_inv)*f.getTauxFund());
            investesmentRepository.save(i);
        }
        return i;
    }

    //mise à jour de l'inves
    @Override
    public investesment updateInvestesment(investesment i) {

        float Amount = i.getAmoutInvestesment();
        double Rate = 0.12*(1- Math.exp(-(Amount)/10000));

        i.setNameInvestesment(i.getNameInvestesment());
        i.setSecondnnameInvestesment(i.getSecondnnameInvestesment());
        i.setAmoutInvestesment(Amount);
        i.setTauxInves((float) Rate);
        i.setCinInvestesment(i.getCinInvestesment());
        i.setMailInvestesment(i.getMailInvestesment());
        i.setProfessionInvestesment(i.getProfessionInvestesment());
        investesment inv =  investesmentRepository.save(i);
        return inv;
    }


    //Afficher un seul inves par son id
    @Override
    public investesment retrieveInvestesment(Integer idInvestesment) {
        investesment investesments =  investesmentRepository.findById(idInvestesment).orElse(null);
        return investesments;
    }



    //Afficher une liste d inves par l id du Fund
    public List<investesment> retrieveInvestesmentbyFund(Integer idFund) {
        return (List<investesment>) investesmentRepository.retrieveInvestesmentbyFund(idFund);
    }


    //Calcul annuel
//	@Scheduled(cron = "0 0 0 31 12 *" )

    //Calcul du Montant recu par l'investisseur apres avoir investit (Montant initial + gain )
    float finalA;
    @Override
    public void CalculateAmoutOfInves(Integer idInvestissement) {
        investesment inves =  investesmentRepository.findById(idInvestissement).orElse(null);
        inves.setFinalAmount(inves.getAmoutInvestesment()+(inves.getAmoutInvestesment()*inves.getTauxInves()));
        investesmentRepository.save(inves);
    }
    //test
    //@Scheduled(cron = "10 * * * * *" )
    @Scheduled(cron = "0 0 0 31 12 *" )
    @Override
    public void finalAmount() {
        List<investesment> listInves = (List<investesment>) investesmentRepository.findAll();
        for (investesment  inv : listInves)
        {
            inv.setFinalAmount((inv.getAmoutInvestesment()*(1+inv.getTauxInves())));
            investesmentRepository.save(inv);
        }
        //	System.out.println("scheduled okay");
    }
    //Calcul rate d'investissement
    float Rate;
    @Override
    public float CalculateRateOfInves(Integer idInvestissement) {
        investesment inves =  investesmentRepository.findById(idInvestissement).orElse(null);
        //Fund f = fundRepository.findById(idFund).orElse(null);
        float Amount = inves.getAmoutInvestesment();
        double Rate = 0.12*(1- Math.exp(-(Amount)/10000));
        inves.setTauxInves((float) Rate);
        return (float) Rate;
    }

    public double Rate(float AmountInvestestesment) {
        double Rate = 0.12*(1- Math.exp(-(AmountInvestestesment)/10000));
        return  Rate;
    }
/*Calcul rate économique (invalide)
float finalrate;
	float pib=(float) 39.24;
	@Override
	public float CalculateRateOfInves(Long idInvestissement,Long idFund) {
		Investesment inves =  investesmentRepository.findById(idInvestissement).orElse(null);
		Fund f = fundRepository.findById(idFund).orElse(null);
		finalrate=(f.getAmountFund())/(pib*100);
		return finalrate;
	}
*/

}
