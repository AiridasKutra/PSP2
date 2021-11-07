package com.ak.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.ak.usercreation.User;

public class UserTests
{
	@Test
	void testConstructorEmpty()
	{
		User user = new User();
		assertEquals(user.getId(), 0);
	}
	
	@Test
	void testEquals()
	{
		User user1 = new User(0, "a", "b", "c", "d", "e", "f");
		User user2 = new User(0, "a", "b", "c", "d", "e", "f");
		User user3 = new User(0, "x", "b", "c", "d", "e", "f");
		User user4 = new User(0, "a", "x", "c", "d", "e", "f");
		User user5 = new User(0, "a", "b", "x", "d", "e", "f");
		User user6 = new User(0, "a", "b", "c", "x", "e", "f");
		User user7 = new User(0, "a", "b", "c", "d", "x", "f");
		User user8 = new User(0, "a", "b", "c", "d", "e", "x");
		User user9 = new User(1, "a", "b", "c", "d", "e", "f");
		
		assertEquals(user1, user2);
		assertNotEquals(user1, user3);
		assertNotEquals(user1, user4);
		assertNotEquals(user1, user5);
		assertNotEquals(user1, user6);
		assertNotEquals(user1, user7);
		assertNotEquals(user1, user8);
		assertNotEquals(user1, user9);
	}
	
	@Test
	void testCopy()
	{
		User user1 = new User("a", "b", "c", "d", "e", "f");
		User user2 = new User();
		User user3 = new User(1, user2);
		user2.copy(user1);
		user3.copy(user1);
		
		assertEquals(user1, user2);
		assertNotEquals(user1, user3);
	}
}
