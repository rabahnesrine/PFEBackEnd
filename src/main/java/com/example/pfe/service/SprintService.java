package com.example.pfe.service;

import com.example.pfe.entites.Projet;
import com.example.pfe.entites.Sprint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SprintService {



   Sprint findSprintByNomSprint(String nomSprint) ;
    Sprint findSprintByIdSprint(long idSprint) ;

    List<Sprint> getSprints();

     void deleteSprint(long idSprint);
    Sprint addNewSprint(Sprint Newsprint) ;


    /* @Override
public Projet addNewMember(Projet projet ,User user){
       if (user.getRoles().equals(Role.ROLE_MEMBRE) ||user.getRoles().equals(Role.ROLE_CHEF)){
    projet.setUsersInclus(user);
        return projetRepository.save(projet);
    }
}*/


}
