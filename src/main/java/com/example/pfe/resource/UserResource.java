package com.example.pfe.resource;

import com.example.pfe.constant.SecurityConstant;
import com.example.pfe.entites.HttpResponse;
import com.example.pfe.entites.Projet;
import com.example.pfe.entites.User;
import com.example.pfe.entites.UserPrincipal;
import com.example.pfe.exception.domain.*;
import com.example.pfe.service.UserService;
import com.example.pfe.service.implementation.UserServiceImpl;
import com.example.pfe.utility.JWTTokenProvider;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.example.pfe.constant.FileConstant.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@Component("myAuthenticationSuccessHandler")
@RequestMapping(path={"/","/user"})
public class UserResource   extends ExceptionHandling  {
    public static final String EMAIL_SENT = "An email with a new password was sent to :";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private UserService userService;
    private Logger LOGGER= LoggerFactory.getLogger(UserResource.class);

    private AuthenticationManager authenticationManager;
private JWTTokenProvider jwtTokenProvider;


@Autowired
    public UserResource(UserService userService, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }
/*  @GetMapping("/home")
    public String showUser()throws UserNotFoundException{
        throw new UserNotFoundException("user not found");
        //return "application Works";

    }*/


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
       authenticate(user.getNomUser(),user.getPassword());
       User loginUser =userService.findUserByNomUser(user.getNomUser());
        UserPrincipal userPrincipal =new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal) ;//pour retourner jwt header

        return new ResponseEntity<>(loginUser,jwtHeader, OK);

    }



    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
User newuser = userService.register(user.getNomUser(),user.getEmailUser(),user.getTelephone(),user.getProfessionUser());
return new ResponseEntity<>(newuser, OK);

}


  /*  @PostMapping("/add")
    public ResponseEntity<User> addNewUser(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        User newUser=userService.addNewUser(user.getNomUser(),user.getEmailUser(),user.getTelephone(),user.getProfessionUser(),user.getRoles(),user.isNotLocked(),user.isActive());
        return new ResponseEntity<>(newUser,OK);}
*/
  // origine
  @PostMapping("user/add")
    public ResponseEntity<User> addNewUser(@RequestParam("nomUser") String nomUser,
                                           @RequestParam("emailUser") String emailUser,
                                           @RequestParam("telephone")String telephone,
                                           @RequestParam("professionUser")String professionUser,
                                           @RequestParam("roles") String roles,
                                           @RequestParam("isNotLocked") String isNotLocked,
                                           @RequestParam("isActive")String isActive,
                                           @RequestParam( value = "profileImage",required = false)MultipartFile profileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        User newUser=userService.addNewUser(nomUser,emailUser,telephone,professionUser,roles,Boolean.parseBoolean(isNotLocked),Boolean.parseBoolean(isActive),profileImage);
        return new ResponseEntity<>(newUser,OK);
    }





  @PostMapping("/update")
  //@PreAuthorize("hasAnyAuthority('user:update')")
  public ResponseEntity<User> update(@RequestParam("currentNomUser") String currentNomUser,
                                           @RequestParam("nomUser") String nomUser,
                                           @RequestParam("emailUser") String emailUser,
                                           @RequestParam("telephone")String telephone,
                                           @RequestParam("professionUser")String professionUser,
                                           @RequestParam("roles") String roles,
                                           @RequestParam("isNotLocked") String isNotLocked,
                                           @RequestParam("isActive")String isActive,
                                           @RequestParam( value = "profileImage",required = false)MultipartFile profileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        User updatedUser=userService.updateUser(currentNomUser,nomUser,emailUser,telephone,professionUser,roles,Boolean.parseBoolean(isNotLocked),Boolean.parseBoolean(isActive),profileImage);
        return new ResponseEntity<>(updatedUser,OK);
    }

    @GetMapping("/find/{nomUser}")
    public ResponseEntity<User> getUser(@PathVariable("nomUser")String nomUser){
        User user=userService.findUserByNomUser(nomUser);
        return new ResponseEntity<>(user,OK);

    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUser(){
      List<User> users= userService.getUsers();
        return new ResponseEntity<>(users,OK);

    }
    @GetMapping("/resetPassword/{emailUser}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("emailUser")String emailUser) throws EmailNotFoundException, MessagingException {
        userService.resetPassword(emailUser);
        return response(OK,EMAIL_SENT + emailUser);
    }

    @DeleteMapping("/delete/{idUser}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("idUser") long  idUser){
        userService.deleteUser(idUser);
        return response(NO_CONTENT, USER_DELETED_SUCCESSFULLY);

    }
    @GetMapping("/findbyIdentifiant/{identifiant}")
    public ResponseEntity<User> findUserByIdentifiant(@PathVariable String identifiant){
        User user=userService.findUserByIdentifiant(identifiant);
        return new ResponseEntity<>(user,OK);}


    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestParam("nomUser") String nomUser,
                                       @RequestParam( value = "profileImage")MultipartFile profileImage ) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
        User user=userService.updateProfileImage(nomUser,profileImage);
        return new ResponseEntity<>(user,OK);
    }

@GetMapping(path="/image/{nomUser}/{fileName}",produces =IMAGE_JPEG_VALUE)
public byte[] getProfileImage(@PathVariable("nomUser") String nomUser,
        @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER+nomUser+FORWARD_SLASH+fileName));
         //user.home + /supportPortal/user/+ness+/+ness.jpg
}
//imageTempor
@GetMapping(path="/image/profile/{nomUser}",produces =IMAGE_JPEG_VALUE)
public byte[] getTempProfileImage(@PathVariable("nomUser") String nomUser ) throws IOException {
    URL url =new URL(TEMP_PROFILE_IMAGE_BASE_URL+ nomUser);
    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream(); //to store all the  data come from url
     try (InputStream inputStream=url.openStream())/*open url*/{
         int bytesRead;
         byte[] chunk = new byte[1024];
         while ((bytesRead=inputStream.read(chunk))>0){
             byteArrayOutputStream.write(chunk,0,bytesRead); //0 -1024 bytes ...
         }
     }
    return byteArrayOutputStream.toByteArray();
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String msg) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),
                msg.toUpperCase()),httpStatus);
    }


    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstant.JWT_TOKEN_HEADER,jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String nomUser, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nomUser,password));

    }


}
