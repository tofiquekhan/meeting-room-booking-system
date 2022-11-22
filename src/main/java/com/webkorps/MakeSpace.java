package com.webkorps;

import com.webkorps.service.MeetService;
import com.webkorps.service.impl.MeetServiceImpl;


import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;


public class MakeSpace {
    public static void main(String[] args) throws IOException {
        for(String s : args){
            InputStreamReader reader = new InputStreamReader(new FileInputStream(s));
            MeetService meetService = new MeetServiceImpl();
            List<String> al =  meetService.booking(reader);
            for(String val : al){
                System.out.println(val);
            }
        }
    }
}
