package com.example.pfe.service;

import com.example.pfe.entites.User;
import com.example.pfe.exception.domain.EmailExistException;
import com.example.pfe.exception.domain.EmailNotFoundException;
import com.example.pfe.exception.domain.UserNotFoundException;
import com.example.pfe.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(String nomUser,String emailUser,String telephone,String professionUser) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;
    List<User> getUsers();
    User findUserByNomUser(String nomUser);
    User findUserByEmailUser(String emailUser);
    User findUserByIdUser(long idUser);
    User findUserByIdentifiant(String identifiant);
    //User addNewUser(String nomUser, String emailUser,String telephone,String professionUser, String role, boolean isNonLocked, boolean isActive) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

   User addNewUser(String nomUser, String emailUser,String telephone,String professionUser, String roles, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

    User  updateUser(String currentNomUser ,String newNomUser, String newEmailUser,String telephone,String professionUser, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
    void deleteUser(long idUser);
    void resetPassword(String email) throws EmailNotFoundException, MessagingException;
    User updateProfileImage(String nomUser, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;



}
