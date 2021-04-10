package com.example.pfe.service.implementation;

import static com.example.pfe.constant.FileConstant.*;
import  static com.example.pfe.constant.UserImplConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.example.pfe.constant.FileConstant;
import com.example.pfe.entites.User;
import com.example.pfe.entites.UserPrincipal;
import com.example.pfe.enumeration.Role;
import com.example.pfe.exception.domain.EmailExistException;
import com.example.pfe.exception.domain.EmailNotFoundException;
import com.example.pfe.exception.domain.UserNotFoundException;
import com.example.pfe.exception.domain.UsernameExistException;
import com.example.pfe.repository.UserRepository;
import com.example.pfe.service.EmailService;
import com.example.pfe.service.LoginAttemptService;
import com.example.pfe.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);
   private UserRepository userRepository;
   private BCryptPasswordEncoder passwordEncoder;
   private LoginAttemptService loginAttemptService ;
   private EmailService emailService;
@Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,LoginAttemptService loginAttemptService
,EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService=loginAttemptService;
    this.emailService=emailService;
    }

    @Override
    public User register( String nomUser, String emailUser,String telephone,String professionUser) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        ValidateNewnomUserAndEmail(StringUtils.EMPTY,nomUser,emailUser);
        User user =new User();
        user.setIdentifiant(generateUserIdentifiant());
        String password = generatePassword();
        String encodedPassword= encodePassword(password);
        user.setNomUser(nomUser);
        user.setEmailUser(emailUser);
        user.setTelephone(telephone);
        user.setDateInscrit(new Date());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setRoles(Role.ROLE_MEMBRE.name());
        user.setAuthorities(Role.ROLE_MEMBRE.getAuthorities());
        user.setProfileImgUrl(getTemporaryProfileImageUrl(nomUser));
        user.setProfessionUser(professionUser);
        userRepository.save(user);
        emailService.sendNewPasswordEmail(nomUser,password,emailUser);
       // LOGGER.info("New user password:"+ password);
        return user;
    }
   /* @Override
    public User addNewUser(String nomUser, String emailUser,String telephone,String professionUser, String role, boolean isNonLocked, boolean isActive) throws UserNotFoundException, UsernameExistException, EmailExistException {
        ValidateNewnomUserAndEmail(StringUtils.EMPTY,nomUser,emailUser);
        User user =new User();
        String password = generatePassword();
        user.setIdentifiant(generateUserIdentifiant());
        user.setNomUser(nomUser);
        user.setEmailUser(emailUser);
        user.setTelephone(telephone);
        user.setProfessionUser(professionUser);
        user.setDateInscrit(new Date());
        user.setPassword(encodePassword(password));
        user.setActive(isActive);
        user.setNotLocked(isNonLocked);

        user.setRoles(getRoleEnumName(role).name());
        LOGGER.info("New user password:"+ password);
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        user.setProfileImgUrl(getTemporaryProfileImageUrl(nomUser));
        userRepository.save(user);




        return user;
    } */
 @Override
    public User addNewUser(String nomUser, String emailUser,String telephone,String professionUser, String roles, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        ValidateNewnomUserAndEmail(StringUtils.EMPTY,nomUser,emailUser);
        User user =new User();
        String password = generatePassword();
        user.setIdentifiant(generateUserIdentifiant());
        user.setNomUser(nomUser);
        user.setEmailUser(emailUser);
        user.setTelephone(telephone);
        user.setProfessionUser(professionUser);
        user.setDateInscrit(new Date());
        user.setPassword(encodePassword(password));
        user.setActive(isActive);
        user.setNotLocked(isNotLocked);

        user.setRoles(getRoleEnumName(roles).name());

        user.setAuthorities(getRoleEnumName(roles).getAuthorities());
        user.setProfileImgUrl(getTemporaryProfileImageUrl(nomUser));
        userRepository.save(user);
        saveProfileImage(user,profileImage);



        return user;
    }



   @Override
    public User updateUser(String currentNomUser, String newNomUser, String newEmailUser,String telephone,String professionUser, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
       User currentUser= ValidateNewnomUserAndEmail(currentNomUser,newNomUser,newEmailUser);
        User user =new User();
        currentUser.setNomUser(newNomUser);
        currentUser.setEmailUser(newEmailUser);
        currentUser.setTelephone(telephone);
        currentUser.setProfessionUser(professionUser);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNotLocked);

        currentUser.setRoles(getRoleEnumName(role).name());

        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepository.save(currentUser);
        saveProfileImage(currentUser,profileImage);



        return currentUser;
    }

    @Override
    //called whenever user going to log in
    public UserDetails loadUserByUsername(String nomUser) throws UsernameNotFoundException {
        User user =userRepository.findUserByNomUser(nomUser);
        if(user ==null){
            LOGGER.error(NO_USER_FOUND_BY_USERNAME+nomUser);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME+nomUser);
        }else {
           validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + nomUser);
            return userPrincipal;
        }
    }



    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByNomUser(String nomUser) {
        return userRepository.findUserByNomUser(nomUser);
    }
    @Override
   public User findUserByIdentifiant(String identifiant){return  userRepository.findUserByIdentifiant(identifiant);}


    @Override
    public User findUserByEmailUser(String emailUser) {
        return userRepository.findUserByEmailUser(emailUser);
    }
@Override
    public User findUserByIdUser(long idUser){return  userRepository.findUserByIdUser(idUser);}


    @Override
    public void deleteUser(long idUser) {
    userRepository.deleteById(idUser);

    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
    User user =userRepository.findUserByEmailUser(email);
    if(user==null){
        throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL+email);
    }
    String password=generatePassword();
    user.setPassword(encodePassword(password));
    userRepository.save(user);
   emailService.sendNewPasswordEmail(user.getNomUser(),password,user.getEmailUser());
    LOGGER.info("New user password:"+ password);

    }

    @Override
    public User updateProfileImage(String nomUser, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        User user= ValidateNewnomUserAndEmail(nomUser,null,null);
        saveProfileImage(user,profileImage);
        return user;
    }

    private void validateLoginAttempt(User user)  {
    //check si user compte verrouillé  isNOTlocked=false
    if(user.isNotLocked()){
          if(loginAttemptService.hasExceededMaxAttempts(user.getNomUser())){
              user.setNotLocked(false);;//bloquée
            }else{
                  user.setNotLocked(true); //ouvert

              }

    }else{
         loginAttemptService.evitUserFromLoginAttemptCache(user.getNomUser()); //true :ouvert
    }
}

    private String getTemporaryProfileImageUrl(String nomUser) {
    return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH+ nomUser).toUriString() ;
    }

    private String encodePassword(String password) {
    return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserIdentifiant() {
        return RandomStringUtils.randomNumeric(10);
    }




    private User ValidateNewnomUserAndEmail(String currentNomUser, String newNomUser, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        User userByNewNomUser=findUserByNomUser(newNomUser);
        User userByNewEmailUser=findUserByEmailUser(newEmail);

        if(StringUtils.isNotBlank(currentNomUser)){
            User currentUser=findUserByNomUser(currentNomUser);
            if(currentUser==null){
                throw  new UserNotFoundException(NO_USER_FOUND_BY_USERNAME +currentNomUser);
            }
            if(userByNewNomUser!=null && ! currentUser.getIdUser().equals(userByNewNomUser.getIdUser())) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS );
            }
            if(userByNewEmailUser!=null && !currentUser.getIdUser().equals(userByNewEmailUser.getIdUser())){
            throw  new EmailExistException(USER_EMAIL_ALREADY_EXISTS );
            }
            return currentUser;
        }else {
            if(userByNewNomUser!=null ) {
                throw new UsernameExistException(USERNAME_ALREADY_EXISTS );
            }
            if(userByNewEmailUser!=null ){
                throw  new EmailExistException(USER_EMAIL_ALREADY_EXISTS );
            } return null ;
        }

    }



    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
    if(profileImage!=null){
        Path userFolder = Paths.get(USER_FOLDER+user.getNomUser()).toAbsolutePath().normalize(); //user/home/supportportal/user/ness
        if(!Files.exists(userFolder)){
            Files.createDirectories(userFolder);
            LOGGER.info(DIRECTORY_CREATED + userFolder);
        }
        Files.deleteIfExists(Paths.get(userFolder+user.getNomUser()+DOT+ JPG_EXTENSION));//supp img s'il existe
        Files.copy(profileImage.getInputStream(),userFolder.resolve(user.getNomUser()+DOT+JPG_EXTENSION),REPLACE_EXISTING); //replace img existe qu a la meme nom et folder
        user.setProfileImgUrl(setProfileImageUrl(user.getNomUser()));
        userRepository.save(user);
        LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM+ profileImage.getOriginalFilename());
    }}
//return actuel location of img
    private String setProfileImageUrl(String nomUser) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH+ nomUser + FORWARD_SLASH + nomUser+ DOT + JPG_EXTENSION).toUriString() ;


    }


    private Role getRoleEnumName(String role) {
    return Role.valueOf(role.toUpperCase());
    }

}
