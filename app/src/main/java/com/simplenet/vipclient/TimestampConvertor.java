package com.simplenet.vipclient;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConvertor {
    public  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    @TypeConverter
    public  Date fromTimestamp (String value)
    {
        if (value != null)
        {
            try{
                return dateFormat.parse(value);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            return null;
        }
        else
            return null;
    }
    @TypeConverter
    public String toTimestamp (Date value)
    {
        if (value != null)
        {
            try{
                return dateFormat.format(value);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        else
            return null;
    }
}
