package com.ak.usercreation;

import java.util.List;

public interface IDatabase
{
	public User save(User user);
	public User find(int id);
	public List<User> findAll();
	public boolean delete(int id);
	public boolean clear();
}
