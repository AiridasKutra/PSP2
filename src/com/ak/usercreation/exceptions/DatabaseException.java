package com.ak.usercreation.exceptions;

public class DatabaseException extends Exception
{
	public DatabaseException() {}
	
	public DatabaseException(String message)
	{
		super(message);
	}
}