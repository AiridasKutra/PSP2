package com.ak.usercreation;

public class User
{
	private int id;
	private String name;
	private String surname;
	private String password;
	private String phoneNumber;
	private String email;
	private String address;

	public User()
	{
		id = 0;
	}
	
	public User(String name, String surname, String password, String phoneNumber, String email, String address)
	{
		this();
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
	}
	
	public User(int id, String name, String surname, String password, String phoneNumber, String email, String address)
	{
		this(name, surname, password, phoneNumber, email, address);
		this.id = id;
	}
	
	public User(User user)
	{
		this.id = user.id;
		copy(user);
	}
	
	public User(int id, User user)
	{
		this.id = id;
		copy(user);
	}
	
	public void copy(User user)
	{
		this.name = user.name;
		this.surname = user.surname;
		this.password = user.password;
		this.phoneNumber = user.phoneNumber;
		this.email = user.email;
		this.address = user.address;
	}
	
	public void print()
	{
		System.out.println("[" + id + "] " + name + " " + surname + ", " + email + ", " + phoneNumber + ", " + address + " (" + password + ")");
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getSurname()
	{
		return surname;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	//public void setId(int id)
	//{
	//	this.id = id;
	//}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setSurname(String surname)
	{
		this.surname = surname;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		User other = (User)obj;
		if (id != other.id) return false;
		if (name != other.name) return false;
		if (surname != other.surname) return false;
		if (password != other.password) return false;
		if (phoneNumber != other.phoneNumber) return false;
		if (email != other.email) return false;
		if (address != other.address) return false;
		return true;
	}
}
