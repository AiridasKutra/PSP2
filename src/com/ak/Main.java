package com.ak;

import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ak.usercreation.User;
import com.ak.usercreation.UserCreator;
import com.ak.usercreation.exceptions.BadEmailException;
import com.ak.usercreation.exceptions.BadPasswordException;
import com.ak.usercreation.exceptions.BadPhoneNumberException;
import com.ak.usercreation.exceptions.DatabaseException;

public class Main
{
	public static UserCreator creator;
	
	public static void main(String[] args)
	{
		System.out.println("Welcome!");
		System.out.println("########");
		System.out.println();
		System.out.println("Type help/h to see available commands");
		mainLoop();
	}
	
	public static void printHelp()
	{
		// create,airidas,kutra,Awooga!5,812345679,airidas.kutra@gmail.com,didlaukio g. 59
		System.out.println("Available commands:");
		System.out.println(" - create/c,{name},{surname},{password},{phone number},{email},{address}: Create a new user");
		System.out.println(" - update/u,{id},{name},{surname},{password},{phone number},{email},{address}: Update an existing user");
		System.out.println(" - find/f {id}: See details of a user");
		System.out.println(" - findall/fa: See details of all users");
		System.out.println(" - delete/d {id}: Delete a user");
		System.out.println(" - clear/cl {id}: Delete all users");
		System.out.println(" - help/h: Show this command list");
	}
	
	public static void mainLoop()
	{
		creator = new UserCreator("db");
		creator.setCountryCode("LT");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
		{
			while (true)
			{
				String input = new String();
				while( input.length() < 1 )
				{
					System.out.print( "> " );
					input = reader.readLine();
				}
	
				switch (input.split(",")[0])
				{
					case "create": create(input); break;
					case "c": create(input); break;
					case "update": update(input); break;
					case "u": update(input); break;
					case "find": find(input); break;
					case "f": find(input); break;
					case "findall": findAll(); break;
					case "fa": findAll(); break;
					case "delete": delete(input); break;
					case "d": delete(input); break;
					case "clear": deleteAll(); break;
					case "cl": deleteAll(); break;
					case "help": printHelp(); break;
					case "h": printHelp(); break;
					case "exit": return;
					case "e": return;
					default: System.out.println("Unknown command, type help / h to see the list of all commands");
				}
			}
		}
		catch (Exception e) {}
	}
	
	public static void create(String input)
	{
		String[] args = input.split(",");
		if (args.length != 7)
		{
			System.out.println("Incorrect argument count.");
			return;
		}
		
		User user = new User(args[1], args[2], args[3], args[4], args[5], args[6]);
		try
		{
			User createdUser = creator.createUser(user);
			System.out.println("User successfully created with id [" + createdUser.getId() + "]!");
		}
		catch (BadEmailException e)
		{
			System.out.println("Invalid email.");
		}
		catch (BadPasswordException e)
		{
			System.out.println("Invalid password.");
		}
		catch (BadPhoneNumberException e)
		{
			System.out.println("Invalid phone number.");
		}
		catch (DatabaseException e)
		{
			System.out.println("There was a problem saving the user.");
		}
	}
	
	public static void update(String input)
	{
		String[] args = input.split(",");
		if (args.length != 8)
		{
			System.out.println("Incorrect argument count.");
			return;
		}
		
		int id;
		try
		{
			id = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Invalid id argument.");
			return;
		}
		
		User user = new User(id, args[2], args[3], args[4], args[5], args[6], args[7]);
		try
		{
			User updatedUser = creator.updateUser(user);
			if (updatedUser == null)
			{
				System.out.println("User with the given id not found.");
			}
			else
			{
				System.out.println("User successfully updated!");
			}
		}
		catch (BadEmailException e)
		{
			System.out.println("Invalid email.");
		}
		catch (BadPasswordException e)
		{
			System.out.println("Invalid password.");
		}
		catch (BadPhoneNumberException e)
		{
			System.out.println("Invalid phone number.");
		}
		catch (DatabaseException e)
		{
			System.out.println("There was a problem saving the user.");
		}
	}
	
	public static void find(String input)
	{
		String[] args = input.split(",");
		if (args.length != 2)
		{
			System.out.println("Incorrect argument count.");
			return;
		}
		
		int id;
		try
		{
			id = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Invalid id argument.");
			return;
		}
		
		User user = creator.findUser(id);
		if (user == null)
		{
			System.out.println("User with the given id not found.");
		}
		else
		{
			user.print();
		}
	}
	
	public static void findAll()
	{
		List<User> users = creator.findAllUsers();
		if (users.size() == 0)
		{
			System.out.println("No users currently exist.");
		}
		else
		{
			for (User user : users)
			{
				user.print();
			}
		}
	}
	
	public static void delete(String input)
	{
		String[] args = input.split(",");
		if (args.length != 2)
		{
			System.out.println("Incorrect argument count.");
			return;
		}
		
		int id;
		try
		{
			id = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Invalid id argument.");
			return;
		}
		
		boolean success = creator.deleteUser(id);
		if (!success)
		{
			System.out.println("User with the given id not found.");
		}
		else
		{
			System.out.println("User deleted successfully!");
		}
	}
	
	public static void deleteAll()
	{
		if (!creator.deleteAllUsers())
		{
			System.out.println("Could not delete the users.");
		}
	}
}
