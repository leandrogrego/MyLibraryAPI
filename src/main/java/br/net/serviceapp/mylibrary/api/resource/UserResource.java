package br.net.serviceapp.mylibrary.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.net.serviceapp.mylibrary.api.Service.UserService;
import br.net.serviceapp.mylibrary.api.entity.User;

@RestController
@RequestMapping("users")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @PutMapping
    public void update(@RequestBody User user){
        userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        userService.delete(id);
    }

    @GetMapping("one/{id}")
    public User getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.listAll();
    }

    @GetMapping
    public User getByToken(@RequestHeader(value="Authorization") String token){
        return userService.getByToken(token);
    }

}
