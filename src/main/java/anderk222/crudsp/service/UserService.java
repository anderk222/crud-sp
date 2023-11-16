package anderk222.crudsp.service;

import anderk222.crudsp.domain.User;
import anderk222.crudsp.exception.ResourceNotFoundException;
import anderk222.crudsp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;


    public List<User> findAll(){

        return repository.findAll();

    }

    public User find(long id){

        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id, "id", "user"));

    }

    public User save(User user){

        return repository.save(user);

    }

    public User update(User user){

        return repository.update(user)
                .orElseThrow(()-> new ResourceNotFoundException(user.getId(), "id", "user"));

    }

    public User delete(long id){

        return repository.deleteById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id, "id", "user"));
    }
}