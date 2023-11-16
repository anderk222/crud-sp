package anderk222.crudsp.controller;

import anderk222.crudsp.domain.Account;
import anderk222.crudsp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/bank-acc")
public class AccountController {

    @Autowired
    AccountService service;

    @GetMapping
    public List<Account> findAll(){

        return service.findAll();

    }

    @GetMapping("/{id}")
    public Account find(@PathVariable long id){

        return service.find(id);

    }

    @PostMapping("/{userId}")
    public Account save(@PathVariable long userId){

        return service.save(userId);

    }

    @PostMapping("add-money/{cantidad}/{accId}")
    Account addMoney(@PathVariable BigDecimal cantidad,@PathVariable long accId){

        return service.addMoney(cantidad, accId);

    }

    //POST http://localhost:8080//api/v1/bank-account/transaction?sender=1&receiver=2&cantidad=10.00

    @PostMapping("/transaction")
    public List<Account> transaction(
            @RequestParam(name = "sender", required = true) long sender,
            @RequestParam(name= "receiver", required= true) long receiver,
            @RequestParam(name= "cantidad", required = true) BigDecimal cantidad
    ){

        return service.transaction(sender, receiver, cantidad);
    }

}