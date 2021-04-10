package com.example.pfe.repository;

import com.example.pfe.entites.Projet;
import com.example.pfe.entites.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint,Long> {


    public Sprint findSprintByNomSprint(String nomSprint);
    public Sprint findSprintByIdSprint(long idSprint);
}
