package com.example.pfe.service.implementation;

import com.example.pfe.entites.Projet;
import com.example.pfe.entites.User;
import com.example.pfe.entites.UserPrincipal;
import com.example.pfe.enumeration.Role;
import com.example.pfe.exception.projetException.ProjetNameExistException;
import com.example.pfe.exception.projetException.ProjetNotFoundException;
import com.example.pfe.exception.projetException.UserProjetExistException;

import com.example.pfe.repository.ProjetRepository;
import com.example.pfe.resource.UserResource;
import com.example.pfe.service.ProjetService;
import com.example.pfe.service.UserService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.pfe.constant.ProjetImplConstant.*;

@Service
@Transactional
public class ProjetServiceImpl implements ProjetService {
    private  ProjetRepository projetRepository;
    private UserResource userResource;

    @Autowired
    public ProjetServiceImpl(ProjetRepository projetRepository,UserResource userResource) {
        this.projetRepository = projetRepository;
        this.userResource=userResource;
    }

    @Override
    public List<Projet> getProjets()
    {
            return projetRepository.findAll();
        }
    @Override
    public void deleteProjet(long idProjet) {
       projetRepository.deleteById(idProjet);

    }


/* @Override
public Projet addNewMember(Projet projet ,User user){
       if (user.getRoles().equals(Role.ROLE_MEMBRE) ||user.getRoles().equals(Role.ROLE_CHEF)){
    projet.setUsersInclus(user);
        return projetRepository.save(projet);
    }
}*/



    @Override
    public Projet addNewProjet(Projet Newprojet) {
     //   ValidateNewnomProjet(StringUtils.EMPTY, nomProjet, creePar);
        if(Newprojet.getEtatProjet()==null)
        {Newprojet.setEtatProjet("Non Commencer");}
        if(!Newprojet.isArchiveProjet())
        {Newprojet.setArchiveProjet(false);}
        Newprojet.setDateCreation (new Date());





        return   projetRepository.save(Newprojet);

    }


/*
    private Projet ValidateNewnomProjet(String currentNomProjet, String nomProjet , User creePar) throws ProjetNotFoundException, ProjetNameExistException, UserProjetExistException {
Projet projetByNewNomProjet=findProjetByNomProjet(nomProjet);
        Projet projetByNewUser=findProjetBynomUser(creePar.getNomUser());

        if(StringUtils.isNotBlank(currentNomProjet)){
            Projet currentProjet=findProjetByNomProjet(currentNomProjet);
            if(currentProjet==null){
                throw  new ProjetNotFoundException(NO_PROJECT_FOUND_BY_PROJECTNAME+currentNomProjet);
            }
            if(projetByNewNomProjet!=null && !Objects.equals(currentProjet.getIdProjet(), projetByNewNomProjet.getIdProjet())) {
                throw new ProjetNameExistException(PROJECTNAME_ALREADY_EXISTS ); //test nom et id (retoure)
            }
            if(projetByNewUser!=null && !Objects.equals(currentProjet.getIdProjet(), projetByNewUser.getIdProjet())){
                throw  new UserProjetExistException(USER_PROJECT_ALREADY_EXISTS);
            } return currentProjet;
        }else {
            if(projetByNewNomProjet!=null ) {
                throw new ProjetNameExistException(PROJECTNAME_ALREADY_EXISTS ); //test nom et id (a voir)
            }
            if(projetByNewUser!=null ){
                throw  new UserProjetExistException(USER_PROJECT_ALREADY_EXISTS );
            } return null ;
        }
    }
*/
       /*   @Override
    public Projet findProjetBynomUser(String nomuser) {
        return projetRepository.findProjetBynomUser(nomuser); }*/

    @Override
    public Projet findProjetByNomProjet(String nomProjet) {
        return projetRepository.findProjetByNomProjet(nomProjet);    }

    @Override
    public Projet findProjetByIdProjet(long idProjet) {
        return projetRepository.findProjetByIdProjet(idProjet);    }

        @Override
        public Projet findProjetByCreePar(User creePar){
        return  projetRepository.findProjetByCreePar(creePar);}
        }





