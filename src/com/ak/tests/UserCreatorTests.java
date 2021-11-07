package com.ak.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ak.usercreation.UserCreator;
import com.ak.usercreation.exceptions.BadEmailException;
import com.ak.usercreation.exceptions.BadPasswordException;
import com.ak.usercreation.exceptions.BadPhoneNumberException;
import com.ak.usercreation.exceptions.DatabaseException;
import com.ak.usercreation.User;

public class UserCreatorTests
{
	private UserCreator creator;
	
	@BeforeEach
	void initCreator()
	{
		creator = new UserCreator("test-db");
		creator.deleteAllUsers();
		creator.setCountryCode("LT");
	}
	
	@Test
	void testCreateUser() throws BadEmailException, BadPasswordException, BadPhoneNumberException, DatabaseException
	{
		User user = new User("airidas", "kutra", "Awooga!5", "812345679", "airidas.kutra@gmail.com", "didlaukio g. 59");
		creator.createUser(user);
	}
	
	@Test
	void testCreateUser_badEmail()
	{
		User user = new User("airidas", "kutra", "Awooga!5", "812345679", "airidas.kutragmail.com", "didlaukio g. 59");
		assertThrows(BadEmailException.class, () -> { creator.createUser(user); });
	}
	
	@Test
	void testCreateUser_badPassword()
	{
		User user = new User("airidas", "kutra", "Awooga5", "812345679", "airidas.kutra@gmail.com", "didlaukio g. 59");
		assertThrows(BadPasswordException.class, () -> { creator.createUser(user); });
	}
	
	@Test
	void testCreateUser_badPhoneNumber()
	{
		User user = new User("airidas", "kutra", "Awooga!5", "8125679", "airidas.kutra@gmail.com", "didlaukio g. 59");
		assertThrows(BadPhoneNumberException.class, () -> { creator.createUser(user); });
	}
	
	@Test
	void testUpdateUser() throws BadEmailException, BadPasswordException, BadPhoneNumberException, DatabaseException
	{
		User user = new User("airidas", "kutra", "Awooga!5", "812345679", "airidas.kutra@gmail.com", "didlaukio g. 59");
		User createdUser = creator.createUser(user);
		createdUser.setName("airydas");
		
		assertNotNull(creator.updateUser(createdUser));
	}
}
