package com.example.pfe.resource;

import com.example.pfe.entites.*;
import com.example.pfe.service.ProjetService;
import com.example.pfe.service.SprintService;
import com.example.pfe.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/sprint"})
public class SprintResource {
  private UserService userService;
  private SprintService sprintService;

    public static final String SPRINT_DELETED_SUCCESSFULLY = "Sprint deleted successfully";

    public SprintResource(UserService userService, SprintService sprintService) {
        this.userService = userService;
        this.sprintService = sprintService;
    }

    @PostMapping("/add")
    public ResponseEntity<Sprint> addNewSprint(@RequestBody Sprint newSprint) {
        User u = newSprint.getChefEquipe();
        User user = userService.findUserByIdentifiant(u.getIdentifiant());
        newSprint.setChefEquipe(user);
        Sprint addedSprint = sprintService.addNewSprint(newSprint);
        return new ResponseEntity<>(addedSprint, OK);
    }

    @PutMapping("/update/{idSprint}")
    public ResponseEntity<Sprint> updateSprint(@PathVariable long idSprint, @RequestBody Sprint newSprint){
        User u = newSprint.getChefEquipe();
        User user = userService.findUserByIdentifiant(u.getIdentifiant());
        newSprint.setChefEquipe(user);
        newSprint.setIdSprint(idSprint);
        newSprint.setDateModification(new Date());

        Sprint updatedSprint=sprintService.addNewSprint(newSprint);
        return new ResponseEntity<>(updatedSprint,OK);
    }


    @GetMapping("/allSprints")
    //@PreAuthorize("hasAnyAuthority('sprint:read')")
    public ResponseEntity<List<Sprint>> getSprints(){
        List<Sprint> sprints= sprintService.getSprints();
        return new ResponseEntity<>(sprints,OK);

    }

    @GetMapping("/findTache/{nomSprint}")
    public ResponseEntity<List<Tache>> getSprintTache(@PathVariable("nomSprint")String nomSprint){
        Sprint sprint=sprintService.findSprintByNomSprint(nomSprint);
        List<Tache> taches =sprint.getTaches();
        return new ResponseEntity<>(taches,OK);
    }

    @DeleteMapping("/delete/{idSprint}")
    // @PreAuthorize("hasAnyAuthority('sprint:delete')")
    public ResponseEntity<HttpResponse> deleteProjet(@PathVariable("idSprint") long  idSprint){
        sprintService.deleteSprint(idSprint);
        return  response(NO_CONTENT, SPRINT_DELETED_SUCCESSFULLY);

    }

    @GetMapping("/findbynom/{nomSprint}")
    public  ResponseEntity<Sprint> findtSprintByNomSprint(@PathVariable("nomSprint") String nomSprint) {
        Sprint sprint=sprintService.findSprintByNomSprint(nomSprint);
        return new ResponseEntity<>(sprint,OK);

    }
    @GetMapping("/findbyid/{idSprint}")
    public  ResponseEntity<Sprint> findSprintByIdSprint(@PathVariable("idSprint") long idSprint) {
        Sprint sprint=sprintService.findSprintByIdSprint(idSprint);
        return new ResponseEntity<>(sprint,OK);

    }



    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }

}


