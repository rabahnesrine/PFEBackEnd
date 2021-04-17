package com.example.pfe.entites;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
public class Sprint implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long idSprint;
    private String nomSprint;
   @Temporal(TemporalType.DATE)
    private Date dateCreation ;
    @Temporal(TemporalType.DATE)
    private Date  dateModification;
   @Temporal(TemporalType.DATE)
    private Date dateFin;
    private String description;
    private String etatSprint;
    private boolean archive;
    //@ManyToOne
   // @JoinColumn(name="idUser")
   // private User sprintCreePar;//recupere user a partir instance projet
    @ManyToOne
    @JoinColumn(name="idProjet")
    private Projet projet ;
    @ManyToOne
   @JoinColumn(name="idUser")
   private  User chefEquipe;

  @OneToMany(cascade=CascadeType.ALL,mappedBy = "sprint")
  private List<Tache> taches;

    public  Sprint(){};


    public Sprint(long idSprint, String nomSprint, Date dateCreation, Date dateModification, Date dateFin, String description, String etatSprint, boolean archive,Projet projet, User chefEquipe, List<Tache> taches) {
        this.idSprint = idSprint;
        this.nomSprint = nomSprint;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.dateFin = dateFin;
        this.description = description;
        this.etatSprint = etatSprint;
        this.archive = archive;
        this.projet = projet;
        this.chefEquipe = chefEquipe;
        this.taches = taches;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtatSprint() {
        return etatSprint;
    }

    public void setEtatSprint(String etatSprint) {
        this.etatSprint = etatSprint;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }


    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public long getIdSprint() {
        return idSprint;
    }

    public void setIdSprint(long idSprint) {
        this.idSprint = idSprint;
    }
/*
    public User getSprintCreePar() {
        sprintCreePar= projet.getCreePar();
        return sprintCreePar;
    }

    public void setSprintCreePar(User creePar) {
        this.sprintCreePar = creePar;
    }*/

    public String getNomSprint() {
        return nomSprint;
    }

    public void setNomSprint(String nomSprint) {
        this.nomSprint = nomSprint;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }



    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public User getChefEquipe() {
        return chefEquipe;
    }

    public void setChefEquipe(User chefEquipe) {
        this.chefEquipe = chefEquipe;
    }
}
