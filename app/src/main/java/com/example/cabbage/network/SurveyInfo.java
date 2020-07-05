package com.example.cabbage.network;

/**
 * Author: xiemugan
 * Date: 2020/6/24
 * Description:
 **/
public class SurveyInfo extends NormalInfo {
    public data data;
    public class data {
        // 基本信息
        public String observationId;
        public String materialType;
        public String materialNumber;
        public String plantNumber;
        public String location;
        public String investigatingTime;
        public String investigator;
        public int userId;

        // 发芽期
        public String germinationRate;

        // 幼苗期
        public String cotyledonSize;
        public String cotyledonColor;
        public String cotyledonNumber;
        public String cotyledonShape;
        public String colorOfHeartLeaf;
        public String trueLeafColor;
        public String trueLeafLength;
        public String trueLeafWidth;

        // 莲座期

    }
}
