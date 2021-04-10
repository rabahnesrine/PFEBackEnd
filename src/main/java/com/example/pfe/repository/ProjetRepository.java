package com.example.pfe.repository;

import com.example.pfe.entites.Projet;
import com.example.pfe.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProjetRepository extends JpaRepository< Projet, Long> {

public Projet findProjetByNomProjet(String nomProjet);
public Projet findProjetByIdProjet(long idProjet);


    public Projet findProjetByCreePar(User creePar);


//public List<Projet> findByIdUser(Long id);
  // public List<Projet> findProjetByUser(Long id);
//public Projet findProjetBynomUser(String nomuser);





}
