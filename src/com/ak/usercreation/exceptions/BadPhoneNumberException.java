package com.ak.usercreation.exceptions;

public class BadPhoneNumberException extends Exception
{
	public BadPhoneNumberException() {}
	
	public BadPhoneNumberException(String message)
	{
		super(message);
	}
}