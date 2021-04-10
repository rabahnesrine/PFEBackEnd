package com.example.pfe;

import com.example.pfe.repository.UserRepository;
import com.example.pfe.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
public class UserRestServices {
   /* @Autowired
    private UserRepository userRepository;

    @GetMapping(value="/listUsers")
    public List<User> listUsers(){
return userRepository.findAll();
    }


    @GetMapping(value="/listUsers/{id}")
    public User listUsers(@PathVariable(name="idUser") Long id){
        return userRepository.findById(id).get();}
*/

}
