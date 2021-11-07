package com.ak.usercreation;

import java.util.List;

import com.ak.usercreation.exceptions.BadEmailException;
import com.ak.usercreation.exceptions.BadPasswordException;
import com.ak.usercreation.exceptions.BadPhoneNumberException;
import com.ak.usercreation.exceptions.DatabaseException;
import com.alemal.gintare.validation.EmailValidator;
import com.alemal.gintare.validation.PasswordChecker;
import com.alemal.gintare.validation.PhoneValidator;

public class UserCreator
{
	private LocalFileDatabase database;
	private String countryCode;
	
	public UserCreator(String saveDir)
	{
		database = new LocalFileDatabase(saveDir);
		countryCode = "";
	}
	
	public User createUser(User user) throws BadEmailException, BadPasswordException, BadPhoneNumberException, DatabaseException
	{
		return saveUser(user, true);
	}
	
	public User updateUser(User user) throws BadEmailException, BadPasswordException, BadPhoneNumberException, DatabaseException
	{
		if (user.getId() == 0) return null;
		return saveUser(user, false);
	}
	
	private User saveUser(User user, boolean forceCreation) throws BadEmailException, BadPasswordException, BadPhoneNumberException, DatabaseException
	{
		validateEmail(user);
		validatePassword(user);
		validatePhoneNumber(user);
		
		User userToSave = forceCreation ? new User(0, user) : user;
		User savedUser = database.save(userToSave);
		if (savedUser == null) throw new DatabaseException();
		
		return savedUser;
	}
	
	public User findUser(int id)
	{
		return database.find(id);
	}
	
	public List<User> findAllUsers()
	{
		return database.findAll();
	}
	
	public boolean deleteUser(int id)
	{
		return database.delete(id);
	}
	
	public boolean deleteAllUsers()
	{
		return database.clear();
	}
	
	public void setCountryCode(String code)
	{
		countryCode = code;
	}
	
	private void validateEmail(User user) throws BadEmailException
	{
		if (!EmailValidator.hasInput(user.getEmail())) throw new BadEmailException();
		if (!EmailValidator.containsAtSign(user.getEmail())) throw new BadEmailException();
		if (!EmailValidator.containsNoInvalidChars(user.getEmail())) throw new BadEmailException();
		if (!EmailValidator.isDomainCorrect(user.getEmail())) throw new BadEmailException();
		if (!EmailValidator.isTLDCorrect(user.getEmail())) throw new BadEmailException();
	}
	
	private void validatePassword(User user) throws BadPasswordException
	{
		if (!PasswordChecker.hasInput(user.getPassword())) throw new BadPasswordException();
		if (!PasswordChecker.isLengthValid(user.getPassword())) throw new BadPasswordException();
		if (!PasswordChecker.containsUppercaseLetters(user.getPassword())) throw new BadPasswordException();
		if (!PasswordChecker.containsSpecialCharacter(user.getPassword())) throw new BadPasswordException();
	}
	
	private void validatePhoneNumber(User user) throws BadPhoneNumberException
	{
		if (!PhoneValidator.hasInput(user.getPhoneNumber())) throw new BadPhoneNumberException();
		// Fix for bad implementation
		String phoneNumber = user.getPhoneNumber();
		if (countryCode.equals("LT") && phoneNumber.substring(0, 4).equals("+370"))
		{
			phoneNumber = "8" + phoneNumber.substring(4);
		}
		if (!PhoneValidator.containsNumbersOnly(phoneNumber)) throw new BadPhoneNumberException();
		if (!PhoneValidator.isCorrectLength(countryCode, phoneNumber)) throw new BadPhoneNumberException();
		user.setPhoneNumber(PhoneValidator.checkPrefix(countryCode, user.getPhoneNumber()));
	}
}
