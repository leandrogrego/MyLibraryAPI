package br.net.serviceapp.mylibrary.api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.serviceapp.mylibrary.api.entity.User;
import br.net.serviceapp.mylibrary.api.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
    }

    public void update(User user){
        if(user.getId() > 0)
            userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public User getByToken(String token){
        return userRepository.findByToken(token);
    }

    public User getById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
        return user.get();
        else return null;
    }

}