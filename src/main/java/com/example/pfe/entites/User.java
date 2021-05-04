package com.example.pfe.entites;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long idUser;
    private String identifiant;
    private String nomUser;
    private String emailUser;
    private String  telephone;
    private Date dateInscrit;
    private  String professionUser;
    private  String profileImgUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private String roles ; //admin_role {read ,edit,delete},membre... /chef d'equipe /scrumMaster ....
    private  String[] authorities;
    private String password;
    private boolean isActive;
    private boolean isNotLocked;

    @OneToMany(mappedBy ="creePar" )
    private List<Projet> projets;

  //  @OneToMany(mappedBy = "sprintCreePar")
  //  private List<Sprint> sprints;

@OneToMany (mappedBy ="chefEquipe" )
private List<Sprint> sprintAffecter;

//@OneToMany(mappedBy ="chefEquipeTaches" )
//private List<Tache> taches;

@OneToMany(mappedBy = "member" )
private  List<Tache> memberTaches;

    public User(){}
    public User(Long idUser, String identifiant, String nomUser, String emailUser, String telephone, Date dateInscrit, String professionUser, String profileImgUrl, Date lastLoginDate, Date lastLoginDateDisplay, String roles, String[] authorities, String password, boolean isActive, boolean isNotLocked) {
        this.idUser = idUser;
        this.identifiant = identifiant;
        this.nomUser = nomUser;
        this.emailUser = emailUser;
        this.telephone = telephone;
        this.dateInscrit = dateInscrit;
        this.professionUser = professionUser;
        this.profileImgUrl = profileImgUrl;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateDisplay = lastLoginDateDisplay;
        this.roles = roles;
        this.authorities = authorities;
        this.password = password;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public List<Sprint> getSprintAffecter() {
        return sprintAffecter;
    }

    public void setSprintAffecter(List<Sprint> sprintAffecter) {
        this.sprintAffecter = sprintAffecter;
    }

    public List<Tache> getMemberTaches() {
        return memberTaches;
    }

    public void setMemberTaches(List<Tache> memberTaches) {
        this.memberTaches = memberTaches;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getDateInscrit() {
        return dateInscrit;
    }

    public void setDateInscrit(Date dateInscrit) {
        this.dateInscrit = dateInscrit;
    }

    public String getProfessionUser() {
        return professionUser;
    }

    public void setProfessionUser(String professionUser) {
        this.professionUser = professionUser;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginDateDisplay() {
        return lastLoginDateDisplay;
    }

    public void setLastLoginDateDisplay(Date lastLoginDateDisplay) {
        this.lastLoginDateDisplay = lastLoginDateDisplay;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }
}
