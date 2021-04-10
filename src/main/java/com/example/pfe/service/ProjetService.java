package com.example.pfe.service;

import com.example.pfe.entites.Projet;
import com.example.pfe.entites.User;
import com.example.pfe.exception.projetException.ProjetNameExistException;
import com.example.pfe.exception.projetException.ProjetNotFoundException;
import com.example.pfe.exception.projetException.UserProjetExistException;
import org.springframework.stereotype.Service;

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Service
public interface ProjetService {




     List<Projet> getProjets() ;
     void deleteProjet(long idProjet) ;
     //Projet addNewProjet(String nomProjet, Date dateEcheance,User creePar) throws ProjetNotFoundException, ProjetNameExistException, UserProjetExistException;
     Projet addNewProjet(Projet Newprojet) ;


  //  List<Projet> findProjetByUser(User user) ;
//Projet addNewMember(Projet projet,User user);

       //Projet findProjetBynomUser(String nomuser);
     Projet findProjetByNomProjet(String nomProjet);
     Projet findProjetByIdProjet(long idProjet);
     Projet findProjetByCreePar(User creePar);

     }
