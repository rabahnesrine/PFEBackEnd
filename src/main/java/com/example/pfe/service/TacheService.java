package com.example.pfe.service;

import com.example.pfe.entites.Tache;
import com.example.pfe.entites.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TacheService {



    Tache save(Tache tache);
    List<Tache> findAll();
    void deleteById(Long id);
    int getTotalTacheENcours();
    int getTotalTacheNonCommence();
    int getTotalTacheAnnule();
    int getTotalTacheTermine();
    Tache findTacheById(Long id);
    int totalTache();
    int totalTacheArchived();
   // List<Tache> findTacheByUserId(User user);
 //   int findTotalByUserId(User user);
    Tache findTachebyNomTache(String nomTache);
     List<Tache> findTacheByArchiveTrue();
     List<Tache> findTacheByArchiveFalse();


}
