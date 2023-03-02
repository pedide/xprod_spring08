package com.xprod.rest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {
	//Serializable :Je fais appel a une liste de données, des enumerations, des objets.
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(nullable = false, updatable = false)
		// Variables
		private Long uid; // id de la base de données DB
		
		private String userId; // id de l utilisateur
		

		@Column(name="userEmail")
		private String email;
		
		@Column(name="userPassword")
		private String password;

		@Column(name="username")
		private String username;

		@Column(name="mobileNumber")
		private String mobileNumber;

		@Column(name="securityQuestion")
		private String securityQuestion;
		
		@Column(name="answer")
		private String answer;

		@Column(name="address")
		private String address;

		@Column(name="cp")
		private String cp;

		@Column(name="city")
		private String city;

		@Column(name="state")
		private String state;
		
		@Column(name="country")
		private String country;

		@Column(name="authorization")
		private String authorization;

		@Column(name="firstname")
		private String firstname;
		
		@Column(name="lastname")
		private String lastname;

		@Column(name="company_name")
		private String companyName;
		
		@Column(name="profile_img")
		private String profileImageURL;
		
		private Date lastLoginDate;
		private Date lastLoginDateDisplay;
		private Date joinDate;
		private String role; // ROLE_USER(read,edit), ROLE_ADMIN(delete)
		private String[] authorities; // [] = tableau de String // Authorities = permissions (read, edit, delete)
		private boolean isActive;// Pour activer les rôles
		private boolean isNotLocked;

		public User() {
			super();
		}

		public User(Long id, String userId, String firstname, String lastname, String username, String email,
				String password, String mobileNumber, String securityQuestion, String answer, String address,
				String profileImageURL, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, String role,
				String[] authorities, boolean isActive, boolean isNotLocked) {
			super();
			this.uid = id;
			this.userId = userId;
			this.firstname = firstname;
			this.lastname = lastname;
			this.username = username;
			this.email = email;
			this.password = password;
			this.mobileNumber = mobileNumber;
			this.securityQuestion = securityQuestion;
			this.answer = answer;
			this.address = address;
			this.profileImageURL = profileImageURL;
			this.lastLoginDate = lastLoginDate;
			this.lastLoginDateDisplay = lastLoginDateDisplay;
			this.joinDate = joinDate;
			this.role = role;
			this.authorities = authorities;
			this.isActive = isActive;
			this.isNotLocked = isNotLocked;
		}

		public Long getUid() {
			return uid;
		}

		public void setId(Long uid) {
			this.uid = uid;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public String getSecurityQuestion() {
			return securityQuestion;
		}

		public void setSecurityQuestion(String securityQuestion) {
			this.securityQuestion = securityQuestion;
		}

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getProfileImageURL() {
			return profileImageURL;
		}

		public void setProfileImageURL(String profileImageURL) {
			this.profileImageURL = profileImageURL;
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

		public Date getJoinDate() {
			return joinDate;
		}

		public void setJoinDate(Date joinDate) {
			this.joinDate = joinDate;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String[] getAuthorities() {
			return authorities;
		}

		public void setAuthorities(String[] authorities) {
			this.authorities = authorities;
		}

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}

		public boolean isNotLocked() {
			return isNotLocked;
		}

		public void setNotLocked(boolean isNotLocked) {
			this.isNotLocked = isNotLocked;
		}

	
	

}
