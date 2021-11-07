package com.ak.usercreation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileDatabase implements IDatabase
{
	private String filepath;
	private boolean usable;
	
	private int lastId;
	private ArrayList<User> users = new ArrayList<User>();
	
	public LocalFileDatabase(String filepath)
	{
		this.filepath = filepath;
		this.usable = loadFileData();
		this.lastId = findLastId();
	}

	@Override
	public User save(User user)
	{
		if (!usable) return null;
		
		if (user.getId() == 0)
		{
			lastId++;
			User newUser = new User(lastId, user);
			users.add(newUser);
			saveFileData();
			return new User(newUser);
		}
		else
		{
			for (User u : users)
			{
				if (u.getId() == user.getId())
				{
					u.copy(user);
					saveFileData();
					return new User(u);
				}
			}
			return null;
		}
	}

	@Override
	public User find(int id)
	{
		if (!usable) return null;
		
		for (User u : users)
		{
			if (u.getId() == id)
			{
				return new User(u);
			}
		}
		return null;
	}
	
	@Override
	public List<User> findAll()
	{
		return users;
	}

	@Override
	public boolean delete(int id)
	{
		if (!usable) return false;
		
		for (int i = 0; i < users.size(); i++)
		{
			if (users.get(i).getId() == id)
			{
				users.remove(i);
				saveFileData();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean clear()
	{
		if (!usable) return false;
		
		users.clear();
		return saveFileData();
	}
	
	public boolean usable()
	{
		return usable;
	}
	
	private boolean loadFileData()
	{
		File file = openFile();
		if (file == null) return false;
		
		// Open file for reading
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(file));
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
		
		try
		{
			String line = reader.readLine();
			while (line != null)
			{
				User user = lineToUser(line);
				if (user != null)
				{
					users.add(user);
				}
				
				line = reader.readLine(); 
			}
		}
		catch (IOException e)
		{
			try { reader.close(); } catch (IOException ex) {}
			return false;
		}

		try { reader.close(); } catch (IOException ex) {}
		return true;
	}
	
	private boolean saveFileData()
	{
		File file = openFile();
		if (file == null) return false;
		
		// Open file for writing
		BufferedWriter writer;
		try
		{
			writer = new BufferedWriter(new FileWriter(file));
		}
		catch (IOException e)
		{
			return false;
		}
		
		try
		{
			for (User user : users)
			{
				String line = ""
					+ user.getId() + ","
					+ user.getName() + ","
					+ user.getSurname() + ","
					+ user.getPassword() + ","
					+ user.getPhoneNumber() + ","
					+ user.getEmail() + ","
					+ user.getAddress();
				writer.write(line);
				writer.newLine();
			}
		}
		catch (IOException e)
		{
			try { writer.close(); } catch (IOException ex) {}
			return false;
		}

		try { writer.close(); } catch (IOException ex) {}
		return true;
	}
	
	private File openFile()
	{
		File file = new File(filepath);
		if (file.exists())
		{
			return file;
		}
		else
		{
			try
			{
				file.createNewFile();
				return file;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
	}
	
	private User lineToUser(String line)
	{
		String[] parts = line.split(",");
		if (parts.length != 7) return null;
		
		int id = Integer.parseInt(parts[0]);
		String name = parts[1];
		String surname = parts[2];
		String password = parts[3];
		String phoneNumber = parts[4];
		String email = parts[5];
		String address = parts[6];
		
		return new User(id, name, surname, password, phoneNumber, email, address);
	}
	
	private int findLastId()
	{
		if (users.size() == 0)
		{
			return 0;
		}
		else
		{
			return users.get(users.size() - 1).getId();
		}
	}
}
