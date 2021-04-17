package com.example.pfe.resource;

import com.example.pfe.entites.Projet;
import com.example.pfe.entites.Sprint;
import com.example.pfe.entites.Tache;
import com.example.pfe.entites.User;
import com.example.pfe.service.TacheService;
import com.example.pfe.service.UserService;
import com.example.pfe.service.implementation.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class TacheResource {
    private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

private UserService userService;
   private TacheService tacheService;
@Autowired
    public TacheResource(UserService userService, TacheService tacheService) {
        this.userService = userService;
        this.tacheService = tacheService;
    }




   /* @PostMapping("/tasks")
    public ResponseEntity<Tache> saveTache(@RequestBody Tache tache){
        //  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String currentPrincipalName = authentication.getName();
        //LOGGER.info(currentPrincipalName);
        User u=tache.getMember();
        User user= userService.findUserByIdentifiant(u.getIdentifiant());
        LOGGER.info(user.getNomUser()); //cree

        tache.setMember(user);
        tache.setDateAffectation(new Date());
        tache.setDateModification(new Date());
        Tache addedtache=tacheService.save(tache);

        return new ResponseEntity<>(addedtache,OK);

    }*/


    @PostMapping("/tasks")

    public ResponseEntity<Tache> addNewTache(@RequestBody Tache newTache) {
        User u = newTache.getMember();
        User user = userService.findUserByIdentifiant(u.getIdentifiant());
        newTache.setMember(user);
        Tache addedTache = tacheService.save(newTache);
        return new ResponseEntity<>(addedTache, OK);
    }/*
   @PostMapping("/tasks")
    public Tache saveTache(@RequestBody Tache tache) {

        tache.setDateAffectation(new Date());
        tache.setDateModification(new Date());

        return tacheService.save(tache);

    }*/

    @PutMapping("/tasks/{id}")
    public Tache updateTache(@PathVariable Long id, @RequestBody Tache task) {
        task.setId(id);
        task.setDateModification(new Date());
        System.out.println("update Tache --- ");
        System.out.println(task.getEtatTache());
        return tacheService.save(task);
    }

    @GetMapping("/tasks")
    public List<Tache> findAllTache() {
        return tacheService.findTacheByArchiveFalse();

    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTache(@PathVariable("id") Long id) {
        tacheService.deleteById(id);
    }

    @GetMapping("/tasks/encours")
    public int getTotalTacheENcours() {
        return tacheService.getTotalTacheENcours();
    }

    @GetMapping("/tasks/termine")
    public int getTotalTacheTermine() {
        return tacheService.getTotalTacheTermine();
    }

    @GetMapping("/tasks/nonCommence")
    public int getTotalTacheNonCommence() {
        return tacheService.getTotalTacheNonCommence();
    }

    @GetMapping("/tasks/annule")
    public int getTotalTacheAnnule() {
        return tacheService.getTotalTacheAnnule();
    }

    @GetMapping("/tasks/{id}")
    public Tache findTacheById(@PathVariable("id") Long id) {
        return tacheService.findTacheById(id);
    }

   /* @PostMapping("/tasks/comment")
    public Commentaire saveComment(@RequestBody Commentaire commentaire) {
        //String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        commentaire.setUtilisateur(utilisateurService.findUserByUsername(currentPrincipalName));
        commentaire.setDateComment(new Date());
        return commentaireService.saveComment(commentaire);
    }*/

    @GetMapping("/tasks/total")
    public int totalTache() {
        return tacheService.totalTache();
    }

    @GetMapping("/tasks/totale/archive")
    public int totalTacheArchived() {
        return tacheService.totalTacheArchived();
    }

   /* @GetMapping("/tasks/Auth/{username}")
    public List<Tache> getTacheByUser(@PathVariable("username") String username) {

        return tacheService.findTacheByUserId(userService.findUserByNomUser(username));
    }*/

  /*  @GetMapping("/tasks/comment/{id}")
    public List<Commentaire> getCommentByTache(@PathVariable("id") Long id) {

        return commentaireService.findCommentaireByTacheId(id);
    }
*/
 /*   @GetMapping("/tasks/total/{username}")
    public int totalTacheByUsername(@PathVariable("username") String username) {
        return tacheService.findTotalByUserId(userService.findUserByNomUser(username));
    }*/

    @GetMapping("/tasks/archives")
    public List<Tache> getArchivedTache() {
        return tacheService.findTacheByArchiveTrue();
    }

    @PutMapping("/tasks/archive/{id}")
    public void archiveTask(@PathVariable Long id, @RequestBody Tache task) {
        task.setId(id);
        task.setArchive(true);
        System.out.println("task archived !!!");
        tacheService.save(task);
    }

    @PutMapping("/tasks/restore/{id}")
    public void restoreTask(@PathVariable Long id, @RequestBody Tache task) {
        task.setId(id);
        task.setArchive(false);
        System.out.println("task restaured !!!");
        tacheService.save(task);
    }



}

