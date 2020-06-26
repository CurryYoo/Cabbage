package com.example.cabbage.network;

import java.util.List;

/**
 * Author: xiemugan
 * Date: 2020/6/24
 * Description:
 **/
public class MaterialInfo extends NormalInfo {
    public Data data;
    public class Data {
        public int pageSize;
        public int total;
        public List<list> list;
        public int pageNum;
        public int totalPage;
        public class list {
            public String materialNumber;
            public String materialType;
            public String year;
            public String season;
            public String origin;
            public String feature;
            public String oddLeeds;
            public String experiment;
            public String paternal;
            public String maternal;
        }
    }
}
