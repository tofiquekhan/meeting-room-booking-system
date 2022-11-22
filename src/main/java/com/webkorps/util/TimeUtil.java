package com.webkorps.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeUtil {

    static List<LocalTime> bufferInTimes = null;
    static List<LocalTime> bufferOutTimes = null;

    static {
        bufferInTimes = new ArrayList<LocalTime>();
        bufferOutTimes = new ArrayList<LocalTime>();
        bufferInTimes.add (LocalTime.of(9,00));
        bufferInTimes.add(LocalTime.of(13,15));
        bufferInTimes.add(LocalTime.of(18,45));
        bufferInTimes.add(LocalTime.of(23,45));
        bufferOutTimes.add(LocalTime.of(9,15));
        bufferOutTimes.add(LocalTime.of(13,45));
        bufferOutTimes.add(LocalTime.of(19,00));
        bufferOutTimes.add(LocalTime.of(00,00));

    }

    public static boolean bufferTimeCheck(LocalTime inTime,LocalTime outTime){
        boolean flag = false;
       for(int i =0;i<bufferInTimes.size();i++){
           flag = CollectionUtil.timeCheck(bufferInTimes.get(i),bufferOutTimes.get(i),inTime,outTime);
           if(flag==true){
               break;
           }
       }

       return flag;
    }

    public static boolean isValidTime(LocalTime localTime){
        boolean flag = false;
        if(localTime.getMinute()%15==0){
            flag = true;
        }
        return flag;
    }

    public static boolean isOutTimeGreater(LocalTime inTime,LocalTime outTime){
        boolean flag = false;
        if(outTime.isBefore(inTime) || outTime.equals(inTime)){
            flag = true;
        }
        return flag;
    }

}
