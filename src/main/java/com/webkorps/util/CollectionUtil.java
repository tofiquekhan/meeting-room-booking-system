package com.webkorps.util;

import com.webkorps.entity.Meet;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtil {

    public static List<Meet> alreadyBooked(List<Meet> meets, LocalTime inTime,LocalTime outTime){

        List<Meet> already = null;

        already = meets.stream().filter(meet -> timeCheck(meet.getInTime(),meet.getOutTime(),inTime,outTime)).collect(Collectors.toList());
        return already;
    }

    public static boolean timeCheck(LocalTime alreadyBookedInTime,LocalTime alreadyBookedOutTime,LocalTime inTime,LocalTime outTime){

        boolean flag = false;
        flag = inTime.equals(alreadyBookedInTime);
        if(flag){
            return flag;
        }
        flag = inTime.isAfter((LocalTime) alreadyBookedInTime) && inTime.isBefore((LocalTime) alreadyBookedOutTime) || outTime.equals(alreadyBookedOutTime);
        return  flag;
    }



}
