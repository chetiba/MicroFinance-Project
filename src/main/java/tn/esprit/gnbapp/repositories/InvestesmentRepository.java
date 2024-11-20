package tn.esprit.gnbapp.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.gnbapp.entities.fund;
import tn.esprit.gnbapp.entities.investesment;
import java.util.List;

public interface InvestesmentRepository extends JpaRepository<investesment, Integer> {
    @Query(value = "SELECT  i  FROM investesment i WHERE i.fund.idFund= ?1")
    List<investesment> retrieveInvestesmentbyFund(Integer idFund);
}
