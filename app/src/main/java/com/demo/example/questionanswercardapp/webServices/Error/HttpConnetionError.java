package com.demo.example.questionanswercardapp.webServices.Error;

/**
 * Created by poonampatel on 25/02/18.
 */

public class HttpConnetionError extends Exception
{
    private static final long	serialVersionUID	= 4484004320855701414L;
    private int _statusCode;
    private String _message;

    public HttpConnetionError(int statusCode)
    {
        _statusCode = statusCode;
        _message = "Connection error. Unable to connect to web service";
    }

    public HttpConnetionError(String message)
    {
        super(message);
        _message = message;
    }


    public HttpConnetionError(int statusCode,String message)
    {
        super(message);
        _statusCode = statusCode;
        _message = message;
    }

    public int getStatusCode()
    {
        return _statusCode;
    }

    @Override
    public String getLocalizedMessage()
    {
        return _message;
    }

    @Override
    public String getMessage()
    {
        return getLocalizedMessage();
    }
}
