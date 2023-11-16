package anderk222.crudsp.service;

import anderk222.crudsp.domain.Account;
import anderk222.crudsp.exception.ResourceNotFoundException;
import anderk222.crudsp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository repository;

    public Account addMoney(BigDecimal cantidad,long id){

        return repository.addMoney(cantidad,id)
                .orElseThrow(()-> new ResourceNotFoundException(id, "id", "bank account"));
    }

    public List<Account> transaction(long sender, long receiver, BigDecimal cantidad){

        return repository.transaction(sender, receiver, cantidad);

    }

    public List<Account> findAll(){

        return repository.findAll();

    }

    public Account find(long id){

        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id, "id", "bank account"));

    }

    public Account save(long userId){

        return repository.save(userId);

    }

    public Account update(Account acc){

        return repository.update(acc)
                .orElseThrow(()-> new ResourceNotFoundException(acc.getId(), "id", "bank account"));
    }

    public Account delete(long id){

        return repository.deleteById(id)
                .orElseThrow(()-> new ResourceNotFoundException());

    }

}