package com.webkorps.service.impl;

import com.webkorps.entity.Meet;
import com.webkorps.exception.MeetException;
import com.webkorps.exception.MeetTimeException;
import com.webkorps.service.MeetService;
import com.webkorps.util.CollectionUtil;
import com.webkorps.util.TimeUtil;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


public class MeetServiceImpl implements MeetService {

    static Long genId;
    static Map<String,Integer> rooms;

    static List<Meet> bookedMeets;

    static{
        rooms = new HashMap<>();
        rooms.put("C-Cave",3);
        rooms.put("D-Tower",7);
        rooms.put("G-Mansion",20);
        bookedMeets = new ArrayList<>();
        genId = 0L;
    }


    public List<String> booking(InputStreamReader reader)throws IOException{

        List<String> msgs = null;
        Reader in;
        BufferedReader bufferedReader;

            bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            msgs = new ArrayList<>();
             while (line!=null){
                String[] strings = line.split(" ");
                String command = strings[0];
                 LocalTime inTime = null;
                 LocalTime outTime = null;
                String  roomToCancel = null;
                 Meet meet = new Meet();
                if(command.equalsIgnoreCase("VACANCY") ||command.equalsIgnoreCase("BOOK")) {
                   inTime  = LocalTime.parse(strings[1]);
                   outTime  = LocalTime.parse(strings[2]);
                }else  if(command.equalsIgnoreCase("CANCEL_BOOKING")) {
                    roomToCancel = strings[1];
                    inTime  = LocalTime.parse(strings[2]);
                    outTime  = LocalTime.parse(strings[3]);
                    meet.setRoom(roomToCancel);
                }

                try {
                    meet.setInTime(inTime);
                    meet.setOutTime(outTime);

                if(command.equalsIgnoreCase("VACANCY")){
                     msgs.add(getAvaiableRoomNames(vacantRoom(meet)));
                }else if(command.equalsIgnoreCase("BOOK")){
                    meet.setMembers(Integer.parseInt(strings[3]));
                    msgs.add(addMeet(meet));
                }else if(command.equalsIgnoreCase("CANCEL_BOOKING")){
                    msgs.add(cancelMeet(meet));
                }
                 }catch (Exception e){
                     msgs.add(e.getMessage());
                 }
                line = bufferedReader.readLine();
            }

        return msgs;
    }

    @Override
    public String cancelMeet(Meet meet) {
        String roomToCancel = meet.getRoom();
        List<Meet> alreadyBooked = new ArrayList<>(bookedMeets);
        alreadyBooked.stream().filter(alreadyBookedmeet -> roomToCancel.equalsIgnoreCase(alreadyBookedmeet.getRoom())).collect(Collectors.toList());
        alreadyBooked = CollectionUtil.alreadyBooked(alreadyBooked,meet.getInTime(),meet.getOutTime());
        if(alreadyBooked.size()==0){
            throw new MeetException("NO_BOOKING");
        }
        Meet toRemove = alreadyBooked.get(0);

        bookedMeets.remove(toRemove);

        return "SUCCESS";
    }

    @Override
    public String addMeet(Meet meet) {
        Meet confirmMeet = null;
        Set<String> roomsSet = vacantRoom(meet);
        for(String room:roomsSet){
            if(meet.getMembers()<=rooms.get(room)){
                meet.setRoom(room);
                break;
            }
        }
       if(meet.getRoom()==null){
               throw new MeetException("NO_VACANT_ROOM");
       }
        meet.setId(genId);
         bookedMeets.add(meet);
       genId++;
        return meet.getRoom();
    }


    @Override
    public Set<String> vacantRoom(Meet meet) {
        StringBuffer str = null;
        LocalTime inTime = meet.getInTime();
        LocalTime outTime = meet.getOutTime();
        if(TimeUtil.isOutTimeGreater(inTime,outTime)){
            throw new MeetTimeException("INCORRECT_INPUT");
        }
        if(TimeUtil.bufferTimeCheck(inTime,outTime)){
            throw new MeetTimeException("NO_VACANT_ROOM");
        }
        List<Meet> meets = new ArrayList<>(bookedMeets);
        List<Meet> allReadyMeet = CollectionUtil.alreadyBooked(meets,meet.getInTime(),meet.getOutTime());
        Set<String> roomsSet = new TreeSet<>(rooms.keySet());

        for(Meet listMeet :allReadyMeet){

            if(rooms.containsKey(listMeet.getRoom())){

                roomsSet.remove(listMeet.getRoom());
            }
        }
        if(roomsSet.size()==0){
            throw new MeetException("NO_VACANT_ROOM");
        }

        return roomsSet;
    }

    public static String getAvaiableRoomNames(Set<String> roomSet){
        StringBuffer stringBuffer = new StringBuffer();
        for(String roomName : roomSet){
            stringBuffer.append(roomName+" ");
        }
        return stringBuffer.toString();
    }

}
