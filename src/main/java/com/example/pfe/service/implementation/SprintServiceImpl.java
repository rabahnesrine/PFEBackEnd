package com.example.pfe.service.implementation;

import com.example.pfe.entites.Projet;
import com.example.pfe.entites.Sprint;
import com.example.pfe.repository.SprintRepository;
import com.example.pfe.service.SprintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SprintServiceImpl implements SprintService {

private SprintRepository sprintRepository;

    public SprintServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }





    @Override
    public Sprint findSprintByNomSprint(String nomSprint) {
        return sprintRepository.findSprintByNomSprint(nomSprint);    }

    @Override
    public Sprint findSprintByIdSprint(long idSprint) {
        return sprintRepository.findSprintByIdSprint(idSprint);    }



    @Override
    public List<Sprint> getSprints()
    {
        return sprintRepository.findAll();
    }

    @Override
    public void deleteSprint(long idSprint) {
        sprintRepository.deleteById(idSprint);

    }

    @Override
   public Sprint addNewSprint(Sprint Newsprint) {
//Validation !!!
        if(Newsprint.getEtatSprint()==null)
        {Newsprint.setEtatSprint("Non Commencer");}
        if(!Newsprint.isArchive())
        {Newsprint.setArchive(false);}
        Newsprint.setDateCreation (new Date());
        return   sprintRepository.save(Newsprint);

    }

}
