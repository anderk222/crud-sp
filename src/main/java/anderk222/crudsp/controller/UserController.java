package anderk222.crudsp.controller;

import anderk222.crudsp.domain.User;
import anderk222.crudsp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping    //http://localhost:8080/api/v1/user
    public List<User> findAll(){

        return service.findAll();
    }

    @GetMapping("/{id}") //http://localhost:8080/api/v1/user/1
    public User find(@PathVariable long id){

        return service.find(id);
    }

    @PostMapping()
    public User save(@RequestBody User user){

        return service.save(user);

    }

    @PutMapping()
    public User update(@RequestBody User user){
        return service.update(user);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable long id){

        return service.delete(id);

    }


}