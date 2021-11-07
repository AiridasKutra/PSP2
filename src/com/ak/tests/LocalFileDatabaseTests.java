package com.ak.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ak.usercreation.LocalFileDatabase;
import com.ak.usercreation.User;

public class LocalFileDatabaseTests
{
	private LocalFileDatabase database;
	
	@BeforeEach
	void initDatabase()
	{
		database = new LocalFileDatabase("test-db");
		database.clear();
	}
	
	@Test
	void testSaveCreate()
	{
		User user1 = new User("a", "b", "c", "d", "e", "f");
		User user2 = new User("b", "a", "c", "d", "e", "f");
		User savedUser1 = database.save(user1);
		User savedUser2 = database.save(user2);
		
		assertEquals(savedUser1.getName(), "a");
		assertEquals(savedUser2.getName(), "b");
		assertNotEquals(savedUser1.getId(), savedUser2.getId());
	}
	
	@Test
	void testSaveUpdate()
	{
		User user = new User("a", "b", "c", "d", "e", "f");
		User savedUser = database.save(user);
		savedUser.setName("x");
		User updatedUser = database.save(savedUser);

		assertEquals(updatedUser.getId(), savedUser.getId());
		assertEquals(updatedUser.getName(), "x");
	}
	
	@Test
	void testFind()
	{
		User user1 = new User("a", "b", "c", "d", "e", "f");
		User user2 = new User("b", "a", "c", "d", "e", "f");
		User savedUser1 = database.save(user1);
		User savedUser2 = database.save(user2);
		
		User foundUser1 = database.find(savedUser1.getId());
		User foundUser2 = database.find(savedUser2.getId());
		
		assertEquals(foundUser1.getName(), "a");
		assertEquals(foundUser2.getName(), "b");
	}
	
	@Test
	void testFindFail()
	{
		User user = new User("a", "b", "c", "d", "e", "f");
		User savedUser = database.save(user);
		
		User notFoundUser1 = database.find(savedUser.getId() + 1);
		User notFoundUser2 = database.find(0);
		
		assertNull(notFoundUser1);
		assertNull(notFoundUser2);
	}
	
	@Test
	void testFindAll()
	{
		User user1 = new User("a", "b", "c", "d", "e", "f");
		User user2 = new User("b", "a", "c", "d", "e", "f");
		database.save(user1);
		database.save(user2);
		
		List<User> foundUsers = database.findAll();
	
		assertEquals(foundUsers.size(), 2);
	}
	
	@Test
	void testDelete()
	{
		User user1 = new User("a", "b", "c", "d", "e", "f");
		User user2 = new User("b", "a", "c", "d", "e", "f");
		User savedUser1 = database.save(user1);
		User savedUser2 = database.save(user2);

		database.delete(savedUser2.getId());
		List<User> foundUsers = database.findAll();
		
		assertEquals(foundUsers.size(), 1);
	}
	
	@Test
	void testClear()
	{
		User user1 = new User("a", "b", "c", "d", "e", "f");
		User user2 = new User("b", "a", "c", "d", "e", "f");
		database.save(user1);
		database.save(user2);

		database.clear();
		List<User> foundUsers = database.findAll();
		
		assertEquals(foundUsers.size(), 0);
	}
}
