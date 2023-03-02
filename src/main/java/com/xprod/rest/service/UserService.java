package com.xprod.rest.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.xprod.rest.entity.User;
import com.xprod.rest.exception.domain.EmailExistException;
import com.xprod.rest.exception.domain.EmailNotFoundException;
import com.xprod.rest.exception.domain.NotAnImageFileException;
import com.xprod.rest.exception.domain.UserNotFoundException;
import com.xprod.rest.exception.domain.UsernameExistException;


public interface UserService {

	User register(String firstname, String lastname, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException;

	User findUserByEmail(String email);

	User findUserByUsername(String username);
	
	User updateProfileImage(String username, MultipartFile profileImage) throws NotAnImageFileException, UserNotFoundException, UsernameExistException, EmailExistException, IOException;

	User addNewUser(String firstname, String lastname, String username, String email, String role, boolean isActive, 
			boolean isNonLocked, MultipartFile profileImage) throws NotAnImageFileException, IOException, UserNotFoundException, UsernameExistException, EmailExistException;
	
	User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
			String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) 
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

	List<User> getUsers();

	void deleteUser(long id);

	void resetPassword(String email) throws EmailNotFoundException, MessagingException;

	
}
