package com.mmia.inscope.exceptions;

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String message)
    {
        super("Error from In Scope PMS Application " + message);
    }
}
