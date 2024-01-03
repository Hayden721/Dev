package com.dev.shop.reserve.controller;

import com.dev.shop.reserve.dao.ReserveDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest

class ReserveControllerTest {


    @Test
    public void getTimeList() {

        // start_time, end_time Map
        Map<String, ArrayList<Integer>> timeMap = new HashMap<String, ArrayList<Integer>>();


        ArrayList<Integer> startTimeValues = new ArrayList<>();
        ArrayList<Integer> endTimeValues = new ArrayList<>();

        for (int i=1; i<=24; i++) {
            startTimeValues.add(i);
            endTimeValues.add(i);
        }

        timeMap.put("start_time", startTimeValues);
        timeMap.put("end_time", endTimeValues);

        for (Map.Entry<String, ArrayList<Integer>> entry : timeMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        //---------------------------------------------------

        ArrayList<Integer> removeStartTimeValues = new ArrayList<>();

        for (int i = 1; i<=1; i++) {
            removeStartTimeValues.add(i);
        }

        ArrayList<Integer> removeEndTimeValues = new ArrayList<>();

        for (int i = 1; i<=2; i++) {
            removeEndTimeValues.add(i);
        }

        timeMap.get("start_time").removeAll(removeStartTimeValues );
        timeMap.get("end_time").removeAll(removeEndTimeValues);


        for (Map.Entry<String, ArrayList<Integer>> entry : timeMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }



    }





}