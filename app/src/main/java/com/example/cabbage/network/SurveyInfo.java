package com.example.cabbage.network;

/**
 * Author: xiemugan
 * Date: 2020/6/24
 * Description:
 **/
public class SurveyInfo extends NormalInfo {
    public Data data;

    public static class Data {
        // 基本信息
        public String observationId;
        public String materialType;
        public String materialNumber;
        public String plantNumber;
        public String location;
        public String investigatingTime;
        public String investigator;
        public int userId;

        //额外属性和备注
        public String spare1;
        public String spare2;

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
        public String plantType;
        public String plantHeight;
        public String developmentDegree;
        public String numberOfLeaves;
        public String thicknessOfSoftLeaf;
        public String bladeLength;
        public String bladeWidth;
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

        //结球期
        //前三个与莲座期相同
//        public String plantType;
//        public String plantHeight;
//        public String developmentDegree;

        public String isBall;
        public String bud;
        public String lateralBud;
        public String outerLeafLength;
        public String outerLeafWidth;
        public String outerLeafShape;
        public String colorOfMiddleRib;
        public String thicknessOfMiddleRib;
        public String lengthOfMiddleRib;
        public String widthOfMiddleRib;
        public String middleRibShape;
        public String numberOfOuterLeaves;

        //成熟期
        public String ballTopClosedType;
        public String ballTopHoldType;
        public String ballTopShape;
        public String upperLeafBulbColor;
        public String greenDegreeOfUpperLeafBall;
        public String leafBallShape;
        public String leafBallHeight;
        public String leafBallWidth;
        public String leafBallMiddleWidth;
        public String leafBulbEndWidth;
        public String leafBallCompactness;
        public String innerColorOfLeafBall;
        public String numberOfBulbs;
        public String softLeafRate;
        public String leafBallWeight;
        public String netVegetableRate;
        public String centerColumnShape;
        public String centerColumnLength;
        public String maturity;
        public String extendedHarvestPeriodEarly;
        public String extendedHarvestPeriodMidLate;

        //储藏期
        public String lossRate;

        //现蕾开花期
        public String halfPlantBudding;
        public String timeRequiredForTheFirstFlower;
        public String budState;
        public String budShape;
        public String budSize;
        public String petalShape;
        public String petalSize;
        public String petalColor;
        public String petalNumber;

        //public String plantHeight;//与前时期相同
        public String branchingAbility;
        public String sterilityOfSingleFlower;
        public String sterilityPerPlant;
        public String populationSterility;
        public String populationSterilePlantRate;
        public String maleSterile;

        //结荚与种子收获期
        public String seedPodLength;
        public String longBeakOfSeedPod;
        public String seedSize;
        public String seedShape;
        public String seedColor;
        public Float poddingRate;
        public String numberOfSeeds;
        public Float seedSettingRate;
        public String selfCompatibility;
    }
}
