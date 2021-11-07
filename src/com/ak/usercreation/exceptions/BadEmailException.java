package com.ak.usercreation.exceptions;

public class BadEmailException extends Exception
{
	public BadEmailException() {}
	
	public BadEmailException(String message)
	{
		super(message);
	}
}
