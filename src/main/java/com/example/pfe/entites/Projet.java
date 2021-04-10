package com.example.pfe.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Projet implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private  long idProjet;
    private String nomProjet ;

    @Temporal(TemporalType.DATE)
    private Date dateCreation ;

    @Temporal(TemporalType.DATE)
    private  Date dateEcheance;
     private String etatProjet;

     @OneToMany(cascade=CascadeType.ALL, mappedBy = "projet")
     private List<Sprint> sprints;
//@manyToOne
    @ManyToOne(fetch = FetchType.EAGER)
    private User creePar;
    private boolean archiveProjet ;


//@ManyToOne(fetch = FetchType.EAGER)
   // private User []usersInclus ;


public  Projet(){};

    public Projet(long idProjet, String nomProjet, Date dateCreation, Date dateEcheance, String etatProjet, List<Sprint> sprints, User creePar, boolean archiveProjet) {
        this.idProjet = idProjet;
        this.nomProjet = nomProjet;
        this.dateCreation = dateCreation;
        this.dateEcheance = dateEcheance;
        this.etatProjet = etatProjet;
        this.sprints = sprints;
        this.creePar =creePar;
        this.archiveProjet = archiveProjet;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }



    public long getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(long idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getEtatProjet() {
        return etatProjet;
    }

    public void setEtatProjet(String etatProjet) {
        this.etatProjet = etatProjet;
    }

    public User getCreePar() {
        return creePar;
    }

    public void setCreePar(User creePar) {
        this.creePar = creePar;
    }

    public boolean isArchiveProjet() {
        return archiveProjet;
    }

    public void setArchiveProjet(boolean archiveProjet) {
        this.archiveProjet = archiveProjet;
    }
}
