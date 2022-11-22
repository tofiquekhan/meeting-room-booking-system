package com.webkorps.service;

import com.webkorps.entity.Meet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;


public interface MeetService {


    String addMeet(Meet meet);

    Set<String> vacantRoom(Meet meet);

    List<String> booking(InputStreamReader reader) throws IOException;

    String cancelMeet(Meet meet);

}
