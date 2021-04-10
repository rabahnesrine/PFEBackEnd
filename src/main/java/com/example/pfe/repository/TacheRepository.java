package com.example.pfe.repository;

import com.example.pfe.entites.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache,Long> {


    @Query("SELECT COUNT(t.etatTache) FROM Tache t WHERE t.etatTache ='Non Commence'")
    int getTotalTacheNonCommence();

    @Query("SELECT COUNT(t.etatTache) FROM Tache t WHERE t.etatTache ='Annule'")
    int getTotalTacheAnnule();

    @Query("SELECT COUNT(t.etatTache) FROM Tache t WHERE t.etatTache ='Termine'")
    int getTotalTacheTermine();

    @Query("SELECT COUNT(t.etatTache) FROM Tache t WHERE t.etatTache ='encours'")
    int getTotalTacheENcours();

    @Query("SELECT COUNT(t) FROM Tache t WHERE t.archive=false ")
    int totalTache();

    @Query("SELECT COUNT(t) FROM Tache t WHERE t.archive=true ")
    int totalTacheArchived();

  //  List<Tache> findByUserIdAndArchiveFalse(Long id);

   // @Query("SELECT COUNT(t) FROM Tache t WHERE t.user.id=:id")
   // int findTotalByUserId(@Param("id") Long id);

    public List<Tache> findTacheByArchiveTrue();
   public  List<Tache> findTacheByArchiveFalse();
   public Tache findByNomTache(String nomTache);

}


