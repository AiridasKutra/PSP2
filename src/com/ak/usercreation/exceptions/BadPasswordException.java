package com.ak.usercreation.exceptions;

public class BadPasswordException extends Exception
{
	public BadPasswordException() {}
	
	public BadPasswordException(String message)
	{
		super(message);
	}
}
