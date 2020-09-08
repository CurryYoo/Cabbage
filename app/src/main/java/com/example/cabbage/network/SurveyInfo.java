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
//        public String cotyledonSize2;
//        public String cotyledonSize3;
        public String cotyledonColor;
        public String cotyledonNumber;
        public String cotyledonShape;
        public String colorOfHeartLeaf;
        public String trueLeafColor;
        public String trueLeafLength;
//        public String trueLeafLength2;
//        public String trueLeafLength3;
        public String trueLeafWidth1;
//        public String trueLeafWidth2;
//        public String trueLeafWidth3;

        // 莲座期
        public String plantType;
        public String plantHeight;
//        public String plantHeight2;
//        public String plantHeight3;
        public String developmentDegree;
        public String numberOfLeaves;
        public String thicknessOfSoftLeaf;
//        public String thicknessOfSoftLeaf2;
//        public String thicknessOfSoftLeaf3;
        public String bladeLength;
//        public String bladeLength2;
//        public String bladeLength3;
        public String bladeWidth;
//        public String bladeWidth2;
//        public String bladeWidth3;
        public String leafShape;
        public String leafColor;
        public String leafLuster;
        public String leafFluff;
        public String leafMarginWavy;
        public String leafMarginSerrate;
        public String bladeSmooth;
        public String sizeOfVesicles;
        public String freshnessOfLeafVein;
        public String brightnessOfMiddleRib;
        public String leafCurl;
        public String leafCurlPart;
        public String leafTexture;

        public String spare1;
        public String spare2;
    }
}
