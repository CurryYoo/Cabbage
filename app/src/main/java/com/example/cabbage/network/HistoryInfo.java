package com.example.cabbage.network;

import java.util.List;

/**
 * Author: xiemugan
 * Date: 2020/6/24
 * Description:
 **/
public class HistoryInfo extends NormalInfo {
    public List<data> data;
    public class data {
        public String observationId;
        public String materialType;
        public String materialNumber;
        public String plantNumber;
        public String location;
        public String investigatingTime;
        public String investigator;
        public String obsPeriod;
    }
}
