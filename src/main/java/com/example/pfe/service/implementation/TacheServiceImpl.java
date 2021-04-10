package com.example.pfe.service.implementation;

import com.example.pfe.entites.Tache;
import com.example.pfe.entites.User;
import com.example.pfe.repository.TacheRepository;
import com.example.pfe.service.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TacheServiceImpl implements TacheService {


    private TacheRepository tacheRepository;
@Autowired
    public TacheServiceImpl(TacheRepository tacheRepository) {
        this.tacheRepository = tacheRepository;
    }

//  private AttachementRepository attachementRepository;

  //  private CommentaireRepository commentaireRepository;


    @Override
    public Tache save(Tache tache) {
        if (tache.getEtatTache() == null)
            tache.setEtatTache("Non Commencer");
        if (!tache.isArchive())
            tache.setArchive(false);
        return tacheRepository.save(tache);
    }

    @Override
    public List<Tache> findAll() {

        return tacheRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        System.out.println("id tache" + id);
        //List<Commentaire> listeComment=  commentaireRepository.findByTacheId(id);
      //  List<Attachement> listeTache = attachementRepository.findByTacheId(id);
      //  listeTache.forEach(a ->attachementRepository.deleteById(a.getId()) );

       // for (Commentaire c:listeComment ) {
      //      commentaireRepository.deleteById(c.getId());
      //  }

        tacheRepository.deleteById(id);

    }

    @Override
    public int getTotalTacheENcours() {

        return tacheRepository.getTotalTacheENcours();
    }

    @Override
    public int getTotalTacheNonCommence() {

        return tacheRepository.getTotalTacheNonCommence();
    }

    @Override
    public int getTotalTacheAnnule() {

        return tacheRepository.getTotalTacheAnnule();
    }

    @Override
    public int getTotalTacheTermine() {

        return tacheRepository.getTotalTacheTermine();
    }

    @Override
    public Tache findTacheById(Long id) {

        return tacheRepository.getOne(id);

    }

    @Override
    public int totalTache() {
        return tacheRepository.totalTache();
    }

   /* @Override
    public List<Tache> findTacheByUserId(User user) {

        return tacheRepository.findByUserIdAndArchiveFalse(user.getIdUser());
    }*/

  /*  @Override
    public int findTotalByUserId(User user) {

        return tacheRepository.findTotalByUserId(user.getIdUser());
    }*/

    @Override
    public List<Tache> findTacheByArchiveTrue() {

        return tacheRepository.findTacheByArchiveTrue();
    }

    @Override
    public List<Tache> findTacheByArchiveFalse() {

        return tacheRepository.findTacheByArchiveFalse();
    }

    @Override
    public int totalTacheArchived() {

        return tacheRepository.totalTacheArchived();
    }

    @Override
    public Tache findTachebyNomTache(String nomTache) {

        return tacheRepository.findByNomTache(nomTache);
    }







}
