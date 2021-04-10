package com.example.pfe.resource;


import com.example.pfe.entites.HttpResponse;
import com.example.pfe.entites.Projet;
import com.example.pfe.entites.User;
import com.example.pfe.exception.domain.EmailExistException;
import com.example.pfe.exception.domain.UserNotFoundException;
import com.example.pfe.exception.domain.UsernameExistException;
import com.example.pfe.exception.projetException.ProjetNameExistException;
import com.example.pfe.exception.projetException.ProjetNotFoundException;
import com.example.pfe.exception.projetException.UserProjetExistException;
import com.example.pfe.repository.UserRepository;
import com.example.pfe.service.ProjetService;
import com.example.pfe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController

@RequestMapping(path={"/projet"})
public class ProjetResource {

    private ProjetService projetService ;

    private UserService userService;
    public static final String PROJECT_DELETED_SUCCESSFULLY = "Project deleted successfully";


    @Autowired
    public ProjetResource(ProjetService projetService,UserService userService) {
        this.projetService = projetService;
        this.userService=userService;
    }

   @PostMapping("/add")
    public ResponseEntity<Projet> addNewProjet(@RequestBody Projet Newprojet){
       User u=Newprojet.getCreePar();
       User user= userService.findUserByIdentifiant(u.getIdentifiant());
       Newprojet.setCreePar(user);
       Projet addedProjet=projetService.addNewProjet(Newprojet);
       return new ResponseEntity<>(addedProjet,OK);

    }
    /*        User u=Newprojet.getCreePar();
      User user= userRepository.findUserByIdentifiant(u.getIdentifiant());
        u.setIdUser(user.getIdUser());
        Newprojet.setCreePar(u);
        Projet addedProjet=projetService.addNewProjet(Newprojet);
        return new ResponseEntity<>(addedProjet,OK);
    }*/

    @PostMapping("/update/{idProjet}")
    public ResponseEntity<Projet> updateProjet(@PathVariable long idProjet,  @RequestBody Projet Newprojet){

            Newprojet.setIdProjet(idProjet);
            // projet.setDateModification(new Date());

            Projet updatedProjet=projetService.addNewProjet(Newprojet);
        return new ResponseEntity<>(updatedProjet,OK);
    }
  /*  @GetMapping("/Auth/{nomUser}")
    public List<Projet> ProjetByUser(@PathVariable("nomUser") String nomUser) {

        return projetService.findProjetByUser(userService.findUserByNomUser(nomUser));
    } */




    @GetMapping("/allProjets")
    //@PreAuthorize("hasAnyAuthority('projet:read')")
    public ResponseEntity<List<Projet>> getProjets(){
        List<Projet> projets= projetService.getProjets();
        return new ResponseEntity<>(projets,OK);

    }
    @DeleteMapping("/delete/{idProjet}")
   // @PreAuthorize("hasAnyAuthority('projet:delete')")
    public ResponseEntity<HttpResponse> deleteProjet(@PathVariable("idProjet") long  idProjet){
        projetService.deleteProjet(idProjet);
        return response(NO_CONTENT, PROJECT_DELETED_SUCCESSFULLY);

    }



        @GetMapping("/findbynom/{nomProjet}")
        public  ResponseEntity<Projet> findProjetByNomProjet(@PathVariable("nomProjet") String nomProjet) {
        Projet projet=projetService.findProjetByNomProjet(nomProjet);
            return new ResponseEntity<>(projet,OK);

        }
    @GetMapping("/findbyid/{idProjet}")
    public  ResponseEntity<Projet> findProjetByNomProjet(@PathVariable("idProjet") long idProjet) {
        Projet projet=projetService.findProjetByIdProjet(idProjet);
        return new ResponseEntity<>(projet,OK);

    }

    //erreur
  /*  @GetMapping("/findbycreateur/{creePar}")
    public ResponseEntity<Projet> findProjetByCreePar(@PathVariable long creePar){
        User user=userService.findUserByIdUser(creePar);
        Projet projet=projetService.findProjetByCreePar(user);
        return new ResponseEntity<>(projet,OK);
    }
*/


    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }

    }

