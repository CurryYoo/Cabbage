package com.example.cabbage.network;

import java.util.List;

/**
 * Author: xiemugan
 * Date: 2020/7/7
 * Description:
 **/
public class PhotoInfo extends NormalInfo {
    public data data;
    public class data {
        public int pageSize;
        public int total;
        public List<list> list;
        public int pageNum;
        public int totalPage;
        public class list {
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
}
