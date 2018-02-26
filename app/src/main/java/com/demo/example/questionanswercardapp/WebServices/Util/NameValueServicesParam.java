package com.demo.example.questionanswercardapp.WebServices.Util;

import org.apache.http.NameValuePair;

/**
 * Created by poonampatel on 25/02/18.
 */

public class NameValueServicesParam implements NameValuePair
{
    private String _name;
    private String _value;

    public NameValueServicesParam(String name, String value)
    {
        _name = name;
        _value = value;
    }

    @Override
    public String getName()
    {
        return _name;
    }

    @Override
    public String getValue()
    {
        return _value;
    }
}

