package tn.esprit.gnbapp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.account;
import tn.esprit.gnbapp.entities.transaction;
import tn.esprit.gnbapp.services.ITransactionServices;
import java.util.List;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")

public class TransactionRestController {
    private final ITransactionServices transactionServices;
   // @PostMapping("/add")
   // transaction addTransaction(@RequestBody transaction transaction ){
    //    return transactionServices.addOrUpdateTransaction(transaction);
   // }
    @PutMapping("/update")
    transaction UpdateTransaction(@RequestBody transaction transaction ){
        return transactionServices.addOrUpdateTransaction(transaction);
    }
    @GetMapping("/get/{id}")
    transaction getTransaction(@PathVariable("id")Integer id_trans)
    {
        return transactionServices.retrieveTransaction(id_trans);
    }
    @GetMapping("/all")
    List<transaction>getAllTransaction() {
        return transactionServices.retrieveAllTransaction();
    }

        @DeleteMapping("/delete/{id}")
        void deleteTransaction(@PathVariable("id")Integer id_trans)
        {
            transactionServices.removeTransaction(id_trans);
        }

          @PutMapping("/assignAccountToTransaction/{id_trans}/{id_acc}")
    public account assignAccountToTransaction(@PathVariable("id_trans") Integer id_trans,
                                 @PathVariable("id_acc") Integer id_acc){
        return transactionServices.assignAccountToTransaction(id_trans,id_acc);
    }
    @GetMapping("/allEmisesByRib/{ribEmet}")
    public List<transaction> getAllTransactionsEmisesByRib( @PathVariable("ribEmet") long rib) {
        return transactionServices.AllTransactionsEmisesByRib( rib);
    }
    // http://localhost:8083/BKFIN/Transaction/add-Transaction/200
    @PostMapping("/add-Transaction")
    @ResponseBody
    public int addTransaction(@RequestBody transaction t ) throws MessagingException
    {
        int transaction = transactionServices.addTransaction(t) ;
        return transaction ;
    }
    @PostMapping("/app-Transaction")
    @ResponseBody
    public String approveTransaction(@RequestBody transaction t  ) throws MessagingException
    {
        String Transaction = transactionServices.approveTransaction(t) ;
        return Transaction ;
    }
    @PostMapping("/app-TransactionAng/{code}")
    @ResponseBody
    public String approveTransactionAng(@RequestBody transaction t ,@PathVariable("code") Long code ) throws MessagingException
    {
        String Transaction = transactionServices.approveTransactionAng(t,code);
        return Transaction ;
    }
}

