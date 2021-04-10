package com.example.pfe.repository;

import com.example.pfe.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin("*")
//@RepositoryRestResource
public interface UserRepository extends JpaRepository<User,Long > {

    public User findUserByNomUser( String nomUser);
    public User findUserByEmailUser( String emailUser);
public User findUserByIdUser(long idUser);
    public User findUserByIdentifiant(String identifiant);





    /*@RestResource(path="/byNomUser")
  public List <User> findByNomUserContains(@Param("nn") String nom);

    @RestResource(path="/byemailUser")
    public List<User> findByemailUserContains(@Param("mc") String emailUser);
    @RestResource(path="/bypassword")
    public List<User> findBypasswordContains(@Param("mc") String password);


    @RestResource(path="/byNomUserPage")
   public Page<User> findByNomUserContains(@Param("nn") String nom, Pageable pageable);
*/

}
