package com.revature.exceptions;

public class InvalidCredentialsException extends Exception
{
	public InvalidCredentialsException()
	{
        System.out.print("Credential combination invalid. ");
    }
}
