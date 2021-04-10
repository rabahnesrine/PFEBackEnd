package com.example.pfe.entites;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class Tache implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomTache;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
   @Temporal(TemporalType.DATE)
    private Date dateEcheance;
    private String etatTache;

//@ManyToOne
//@JoinColumn(name="idUser")
    private User chefEquipeTaches;

@ManyToOne
@JoinColumn(name="idSprint")
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name="idUser")
    private User member;

    private Date dateAffectation;
    private Date dateModification;
    private boolean archive;

public  Tache(){};
    public Tache(Long id, String nomTache, String description, Date dateDebut, Date dateEcheance, String etatTache, User chefEquipeTaches, Sprint sprint, User member, Date dateAffectation, Date dateModification, boolean archive) {
        this.id = id;
        this.nomTache = nomTache;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.etatTache = etatTache;
        this.chefEquipeTaches = chefEquipeTaches;
        this.sprint = sprint;
        this.member = member;
        this.dateAffectation = dateAffectation;
        this.dateModification = dateModification;
        this.archive = archive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTache() {
        return nomTache;
    }

    public void setNomTache(String nomTache) {
        this.nomTache = nomTache;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getEtatTache() {
        return etatTache;
    }

    public void setEtatTache(String etatTache) {
        this.etatTache = etatTache;
    }

    public User getChefEquipeTaches() {
        chefEquipeTaches= sprint.getChefEquipe();
        return chefEquipeTaches;
    }

    public void setChefEquipeTaches(User chefEquipeTaches) {
        this.chefEquipeTaches = chefEquipeTaches;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        member = member;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
