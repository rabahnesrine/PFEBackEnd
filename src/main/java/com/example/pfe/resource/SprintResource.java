package com.example.pfe.resource;

import com.example.pfe.entites.Projet;
import com.example.pfe.entites.Sprint;
import com.example.pfe.entites.User;
import com.example.pfe.service.ProjetService;
import com.example.pfe.service.SprintService;
import com.example.pfe.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/sprint"})
public class SprintResource {
  private UserService userService;
  private SprintService sprintService;
    private ProjetService projetService;

    public SprintResource(UserService userService, SprintService sprintService, ProjetService projetService) {
        this.userService = userService;
        this.sprintService = sprintService;
        this.projetService = projetService;
    }

    @PostMapping("/add")
    public ResponseEntity<Sprint> addNewSprint(@RequestBody Sprint NewSprint) {
        User u = NewSprint.getChefEquipe();
        User user = userService.findUserByIdentifiant(u.getIdentifiant());
       NewSprint.setChefEquipe(user);
      //  Projet p = NewSprint.getProjet();
      //  Projet projet = projetService.findProjetByIdProjet(p.getIdProjet());
        //NewSprint.setProjet(projet);
     //   NewSprint.setSprintCreePar(p.getCreePar());
        Sprint addedSprint = sprintService.addNewSprint(NewSprint);
        return new ResponseEntity<>(addedSprint, OK);
    }
}