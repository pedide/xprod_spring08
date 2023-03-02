package com.xprod.rest.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;

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

import com.xprod.rest.entity.User;
import com.xprod.rest.entity.UserPrincipal;
import com.xprod.rest.enumeration.Role;
import com.xprod.rest.exception.domain.EmailExistException;
import com.xprod.rest.exception.domain.EmailNotFoundException;
import com.xprod.rest.exception.domain.NotAnImageFileException;
import com.xprod.rest.exception.domain.UserNotFoundException;
import com.xprod.rest.exception.domain.UsernameExistException;
import com.xprod.rest.repository.UserRepository;
import com.xprod.rest.service.EmailService;
import com.xprod.rest.service.LoginAttemptService;
import com.xprod.rest.service.UserService;

import static com.xprod.rest.constant.UserImplConstant.NEW_USER_PASSWORD;
import static com.xprod.rest.constant.FileConstant.*;
import static com.xprod.rest.constant.UserImplConstant.*;
import static com.xprod.rest.enumeration.Role.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.*;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	
	@Autowired // permet d'activer l'injection automatique de dépendance
	private UserRepository iUserRepository;
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private BCryptPasswordEncoder passwordEncoder;
	private LoginAttemptService loginAttemptService;
	
	/*A SUPPRIMER APRES TEST*/
	private EmailService emailService;
	/**/
	

	@Autowired
	public UserServiceImpl(UserRepository iUserRepository, BCryptPasswordEncoder passwordEncoder,
			LoginAttemptService loginAttemptService) {
		super();
		this.iUserRepository = iUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;

	}
	
	// Ajoute un objet utilisateur dans la base de données, réserver au back office elle est destinée à l'ajout d'un
	// utilisateur exécuté par un administrateur de l'application
	@Override
	public User addNewUser(String firstname, String lastname, String username, String email, String role,
			boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws NotAnImageFileException, UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		
			validateNewUsernameAndEmail(EMPTY, username, email);
			User user = new User();

			String password = generatePassword();
			String encodedPassword = encodePassword(password);
			user.setUserId(generateUserId());
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setUsername(username);
			user.setEmail(email);
			user.setJoinDate(new Date());
			user.setPassword(encodedPassword);
			user.setActive(isActive);
			user.setNotLocked(isNonLocked);
			user.setRole(getRoleEnumName(role).name());
			user.setAuthorities(getRoleEnumName(role).getAuthorities());
			user.setProfileImageURL(setProfileImageUrl(username));
	
			iUserRepository.save(user);
			LOGGER.info(NEW_USER_PASSWORD + password);
			saveProfileImage(user, profileImage);

			return user;
		
	}
	
	// Ajoute également un objet utilisateur dans la base de données, réserver au front office elle est destinée 
	// à l'ajout d'un utilisateur lorsqu'un utilisateur créé un compte dans l'application
	@Override
	public User register(String firstname, String lastname, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException {
		
			validateNewUsernameAndEmail(StringUtils.EMPTY, username, email);
			User user = new User();
			String password = generatePassword();
			String encodedPassword = encodePassword(password);
			user.setUserId(generateUserId());			
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setUsername(username);
			user.setEmail(email);
			user.setJoinDate(new Date());
			user.setPassword(encodedPassword);
			user.setActive(true);
			user.setNotLocked(true);
			user.setRole(ROLE_USER.name());
			user.setAuthorities(ROLE_USER.getAuthorities());
			//user.setProfileImageURL(setProfileImageUrl(username));
			user.setProfileImageURL(setProfileImageUrl("avatar-Paul1.png"));
			iUserRepository.save(user);
			LOGGER.info(NEW_USER_PASSWORD + password);
			return user;

		
	}

	// saveProfileImage() est appelée par addNewUser() elle permet la création d'une image de profile
	private void saveProfileImage(User user, MultipartFile profileImage) throws IOException,NotAnImageFileException {
			
		
		if (profileImage != null) {
			if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
				throw new NotAnImageFileException(profileImage.getOriginalFilename()+ NOT_AN_IMAGE_FILE);
			}
			
			File file=new File("src/main/resources/static/images/profile/");
			final Path userFolder=Paths.get(file.getAbsolutePath());
			
			Files.deleteIfExists(Paths.get(userFolder+user.getUsername()+ DOT +JPG_EXTENSION));
			
			
			Files.copy(profileImage.getInputStream(),userFolder.resolve(profileImage.getName()+DOT+JPG_EXTENSION),StandardCopyOption.REPLACE_EXISTING);
//			Files.copy(profileImage.getInputStream(),userFolder.resolve(user.getUsername()+DOT+JPG_EXTENSION),REPLACE_EXISTING);
			
			
			user.setProfileImageURL(setProfileImageUrl(profileImage.getOriginalFilename()));			
			iUserRepository.save(user);
			LOGGER.info(FILE_SAVED_IN_SYSTEM +profileImage.getOriginalFilename());
	
		}


	}

	// setProfileImageUrl() est appelée par register() elle permet la création d'un avatar pour l'image de profile de 
	//l'utilisateur
	private String setProfileImageUrl(String username) {
		return FORWARD_SLASH + username;
		//return TEMP_PROFILE_IMAGE_BASE_URL + FORWARD_SLASH + username;
  }

	// Enumération d'une liste de rôles pour déterminer le(s) droit(s) d'un l'utilisateur dans l'application 
	// Chaque rôle peut contenir un ou plusieurs droit(s)(create, read, update, delete)
	private Role getRoleEnumName(String role) {
		return Role.valueOf(role);
	}

	// loadUserByUsername() cette méthode cherche un utilisateur en faisant et exécute des actions en foction du résultat 
	//retourné
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = iUserRepository.findUserByUsername(username);

		if (user == null) {
			LOGGER.error(USER_NOT_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(USER_NOT_FOUND_BY_USERNAME + username);
		} else {
			try {
				validateLoginAttempt(user);
				user.setLastLoginDateDisplay(user.getLastLoginDate());
				user.setLastLoginDate(new Date());
				iUserRepository.save(user);
				UserPrincipal userPrincipal = new UserPrincipal(user);
				return userPrincipal;
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	// getUsers() renvoie dans une liste tous les objets User utilisateurs enregistrés dans la base de données
	@Override
	public List<User> getUsers() {
		return iUserRepository.findAll();
	}

	// findUserByEmail() cette méthode trouve un utilisateur en le recherchant par son email
	@Override
	public User findUserByEmail(String email) {
		return iUserRepository.findUserByEmail(email);
	}
	
	// findUserByUsername() cette méthode trouve un utilisateur en le recherchant par : username
	@Override
	public User findUserByUsername(String username) {
		return iUserRepository.findUserByUsername(username);
	}

	// updateUser met à jour les données d'un utilisateur
	@Override
	public User updateUser(String currentUsername, String newFirstname, String newLastname, String newUsername,
			String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) 
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
		
			User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);

			currentUser.setFirstname(newFirstname);
			currentUser.setLastname(newLastname);
			currentUser.setUsername(newUsername);
			currentUser.setEmail(newEmail);
			currentUser.setActive(isActive);
			currentUser.setNotLocked(isNonLocked);
			currentUser.setRole(getRoleEnumName(role).name());
			currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
			currentUser.setProfileImageURL(profileImage.getName());
			iUserRepository.save(currentUser);
			//saveProfileImage(currentUser, profileImage);
			
			System.out.println("Lett username :"+newUsername );

			return currentUser;
		
	}

	// deleteUser() supprime un objet User en le ciblant via son ID
	@Override
	public void deleteUser(long id) {

		iUserRepository.deleteById(id);
	}
	
	// Cette méthode permet de réinitialiser le mot de passe de l'utilisateur
	@Override
	public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
		User user = iUserRepository.findUserByEmail(email);
		if (user == null) {
			throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL);
		}
		String password = generatePassword();
		user.setPassword(encodePassword(password));
		iUserRepository.save(user);
		LOGGER.info("New user password "+password); // J'affiche le password dans les LOGGERS
		//emailService.sendNewPasswordEmail(user.getFirstname(), password, user.getEmail());
	}

	// updateProfileImage() met à jour l'image de profile
	@Override
	public User updateProfileImage(String username, MultipartFile profileImage) throws NotAnImageFileException, UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		
			User user = validateNewUsernameAndEmail(username, null, null);
			saveProfileImage(user, profileImage);
			return user;
		
	}

	// encodePassword() encode le mot de passe de l'utilisateur
	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	// generatePassword() génère un mot de passe à l'utilisateur
	private String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	// generateUserId() génère un uid 
	private String generateUserId() {
		return RandomStringUtils.randomNumeric(10);
	}

	// validateNewUsernameAndEmail() est appelé par validateNewUsernameAndEmail() et register()
	// elle vérifie si les valeurs Username et Email appartiennent déjà à un utlisateur 
	private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
			throws UserNotFoundException, UsernameExistException, EmailExistException {

		User userByNewUsername = findUserByUsername(newUsername);
		User userByNewEmail = findUserByEmail(newEmail);

		if (StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findUserByUsername(currentUsername);

			if (currentUser == null) {
				throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			}

			if (userByNewUsername != null && !currentUser.getUid().equals(userByNewUsername.getUid())) {

				throw new UsernameExistException(USERNAME_ALREADY_EXIST);
			}

			if (userByNewEmail != null && !currentUser.getUid().equals(userByNewEmail.getUid())) {
				throw new EmailExistException(EMAIL_ALREADY_EXIST);
			}
			return currentUser;
		} else {

			if (userByNewUsername != null) {
				throw new UsernameExistException(USERNAME_ALREADY_EXIST/* + userByNewUsername*/);
			}

			if (userByNewEmail != null) {
				throw new EmailExistException(EMAIL_ALREADY_EXIST/* + currentUsername + userByNewEmail*/);
			}
			return null;
		}
	}

	// validateLoginAttempt() block un utilisateur si celui-ci à exécuté trop de tentatives de connexion 
	//avec un mauvais mot de passe
	private void validateLoginAttempt(User user) throws ExecutionException {
		if (user.isNotLocked()) {
			if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
		}
	}

	



}