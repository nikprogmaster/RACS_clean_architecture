package com.example.racs;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

public class ServerDate {
    private String serverStr;

    public Date getServerDate() {
        return serverDate;
    }

    private Date serverDate;
    private Date curDate = new Date();
    public ServerDate(String str){
        serverStr = str;
    }
    public void parseDate(){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            if (serverStr!=null) {
                serverDate = ft.parse(serverStr);
            }
            else
                serverDate = curDate;
        }
        catch (ParseException e){
            System.out.println("Cтрока не распаршена с помощью "+ft);
        }
    }

    public String getDiff() {
        int days = 0;
        int hours = 0;
        int mins = 0;
        String result="";
        long dif = Math.abs(serverDate.getTime() - curDate.getTime());
        days = (int)dif/(1000*60*60*24);
        hours = (int)dif/(1000*60*60)- days*24;
        mins = (int)dif/(1000*60) - days*24*60 - hours*60;
        result = days + "д " + hours + "ч " + mins + "мин";
        return result;
    }

}
