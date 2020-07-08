package com.example.cabbage.network;

/**
 * Author: xiemugan
 * Date: 2020/7/7
 * Description:
 **/
public class PhotoInfo extends NormalInfo {
    public data data;
    public class data {
        public int pictureId;
        public String bucket;
        public String name;
        public String size;
        public String type;
        public String url;
        public String suffix;
        public String updateTime;
        public String observationId;
        public String obsPeriod;
        public String specCharacter;
    }
}
