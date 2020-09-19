package com.example.cabbage.network;

/**
 * Author: xiemugan
 * Date: 2020/6/24
 * Description:
 **/
public class MaterialNumberInfo extends NormalInfo {
    public Data data;

    public class Data {
        public String materialNumber;
        public String materialType;
        public String materialName;
        public String year;
        public String season;
        public String origin;
        public String feature;
        public String oddLeeds;
        public String experiment;
        public String paternal;
        public String maternal;
        public int userId;
    }
}
