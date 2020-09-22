package com.example.cabbage.network;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Author: xiemugan
 * Date: 2020/6/24
 * Description:
 **/
public class SurveyInfo extends NormalInfo {
    public Data data;

    public SurveyInfo(Data data) {
        this.data = data;
    }

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
        public String poddingRate;
        public String numberOfSeeds;
        public String seedSettingRate;
        public String selfCompatibility;

        public void setObservationId(String observationId) {
            this.observationId = observationId;
        }

        public void setMaterialType(String materialType) {
            this.materialType = materialType;
        }

        public void setMaterialNumber(String materialNumber) {
            this.materialNumber = materialNumber;
        }

        public void setPlantNumber(String plantNumber) {
            this.plantNumber = plantNumber;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setInvestigatingTime(String investigatingTime) {
            this.investigatingTime = investigatingTime;
        }

        public void setInvestigator(String investigator) {
            this.investigator = investigator;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setSpare1(String spare1) {
            this.spare1 = spare1;
        }

        public void setSpare2(String spare2) {
            this.spare2 = spare2;
        }

        public void setGerminationRate(String germinationRate) {
            this.germinationRate = germinationRate;
        }

        public void setCotyledonSize(String cotyledonSize) {
            this.cotyledonSize = cotyledonSize;
        }

        public void setCotyledonColor(String cotyledonColor) {
            this.cotyledonColor = cotyledonColor;
        }

        public void setCotyledonNumber(String cotyledonNumber) {
            this.cotyledonNumber = cotyledonNumber;
        }

        public void setCotyledonShape(String cotyledonShape) {
            this.cotyledonShape = cotyledonShape;
        }

        public void setColorOfHeartLeaf(String colorOfHeartLeaf) {
            this.colorOfHeartLeaf = colorOfHeartLeaf;
        }

        public void setTrueLeafColor(String trueLeafColor) {
            this.trueLeafColor = trueLeafColor;
        }

        public void setTrueLeafLength(String trueLeafLength) {
            this.trueLeafLength = trueLeafLength;
        }

        public void setTrueLeafWidth(String trueLeafWidth) {
            this.trueLeafWidth = trueLeafWidth;
        }

        public void setPlantType(String plantType) {
            this.plantType = plantType;
        }

        public void setPlantHeight(String plantHeight) {
            this.plantHeight = plantHeight;
        }

        public void setDevelopmentDegree(String developmentDegree) {
            this.developmentDegree = developmentDegree;
        }

        public void setNumberOfLeaves(String numberOfLeaves) {
            this.numberOfLeaves = numberOfLeaves;
        }

        public void setThicknessOfSoftLeaf(String thicknessOfSoftLeaf) {
            this.thicknessOfSoftLeaf = thicknessOfSoftLeaf;
        }

        public void setBladeLength(String bladeLength) {
            this.bladeLength = bladeLength;
        }

        public void setBladeWidth(String bladeWidth) {
            this.bladeWidth = bladeWidth;
        }

        public void setLeafShape(String leafShape) {
            this.leafShape = leafShape;
        }

        public void setLeafColor(String leafColor) {
            this.leafColor = leafColor;
        }

        public void setLeafLuster(String leafLuster) {
            this.leafLuster = leafLuster;
        }

        public void setLeafFluff(String leafFluff) {
            this.leafFluff = leafFluff;
        }

        public void setLeafMarginWavy(String leafMarginWavy) {
            this.leafMarginWavy = leafMarginWavy;
        }

        public void setLeafMarginSerrate(String leafMarginSerrate) {
            this.leafMarginSerrate = leafMarginSerrate;
        }

        public void setBladeSmooth(String bladeSmooth) {
            this.bladeSmooth = bladeSmooth;
        }

        public void setSizeOfVesicles(String sizeOfVesicles) {
            this.sizeOfVesicles = sizeOfVesicles;
        }

        public void setFreshnessOfLeafVein(String freshnessOfLeafVein) {
            this.freshnessOfLeafVein = freshnessOfLeafVein;
        }

        public void setBrightnessOfMiddleRib(String brightnessOfMiddleRib) {
            this.brightnessOfMiddleRib = brightnessOfMiddleRib;
        }

        public void setLeafCurl(String leafCurl) {
            this.leafCurl = leafCurl;
        }

        public void setLeafCurlPart(String leafCurlPart) {
            this.leafCurlPart = leafCurlPart;
        }

        public void setLeafTexture(String leafTexture) {
            this.leafTexture = leafTexture;
        }

        public void setIsBall(String isBall) {
            this.isBall = isBall;
        }

        public void setBud(String bud) {
            this.bud = bud;
        }

        public void setLateralBud(String lateralBud) {
            this.lateralBud = lateralBud;
        }

        public void setOuterLeafLength(String outerLeafLength) {
            this.outerLeafLength = outerLeafLength;
        }

        public void setOuterLeafWidth(String outerLeafWidth) {
            this.outerLeafWidth = outerLeafWidth;
        }

        public void setOuterLeafShape(String outerLeafShape) {
            this.outerLeafShape = outerLeafShape;
        }

        public void setColorOfMiddleRib(String colorOfMiddleRib) {
            this.colorOfMiddleRib = colorOfMiddleRib;
        }

        public void setThicknessOfMiddleRib(String thicknessOfMiddleRib) {
            this.thicknessOfMiddleRib = thicknessOfMiddleRib;
        }

        public void setLengthOfMiddleRib(String lengthOfMiddleRib) {
            this.lengthOfMiddleRib = lengthOfMiddleRib;
        }

        public void setWidthOfMiddleRib(String widthOfMiddleRib) {
            this.widthOfMiddleRib = widthOfMiddleRib;
        }

        public void setMiddleRibShape(String middleRibShape) {
            this.middleRibShape = middleRibShape;
        }

        public void setNumberOfOuterLeaves(String numberOfOuterLeaves) {
            this.numberOfOuterLeaves = numberOfOuterLeaves;
        }

        public void setBallTopClosedType(String ballTopClosedType) {
            this.ballTopClosedType = ballTopClosedType;
        }

        public void setBallTopHoldType(String ballTopHoldType) {
            this.ballTopHoldType = ballTopHoldType;
        }

        public void setBallTopShape(String ballTopShape) {
            this.ballTopShape = ballTopShape;
        }

        public void setUpperLeafBulbColor(String upperLeafBulbColor) {
            this.upperLeafBulbColor = upperLeafBulbColor;
        }

        public void setGreenDegreeOfUpperLeafBall(String greenDegreeOfUpperLeafBall) {
            this.greenDegreeOfUpperLeafBall = greenDegreeOfUpperLeafBall;
        }

        public void setLeafBallShape(String leafBallShape) {
            this.leafBallShape = leafBallShape;
        }

        public void setLeafBallHeight(String leafBallHeight) {
            this.leafBallHeight = leafBallHeight;
        }

        public void setLeafBallWidth(String leafBallWidth) {
            this.leafBallWidth = leafBallWidth;
        }

        public void setLeafBallMiddleWidth(String leafBallMiddleWidth) {
            this.leafBallMiddleWidth = leafBallMiddleWidth;
        }

        public void setLeafBulbEndWidth(String leafBulbEndWidth) {
            this.leafBulbEndWidth = leafBulbEndWidth;
        }

        public void setLeafBallCompactness(String leafBallCompactness) {
            this.leafBallCompactness = leafBallCompactness;
        }

        public void setInnerColorOfLeafBall(String innerColorOfLeafBall) {
            this.innerColorOfLeafBall = innerColorOfLeafBall;
        }

        public void setNumberOfBulbs(String numberOfBulbs) {
            this.numberOfBulbs = numberOfBulbs;
        }

        public void setSoftLeafRate(String softLeafRate) {
            this.softLeafRate = softLeafRate;
        }

        public void setLeafBallWeight(String leafBallWeight) {
            this.leafBallWeight = leafBallWeight;
        }

        public void setNetVegetableRate(String netVegetableRate) {
            this.netVegetableRate = netVegetableRate;
        }

        public void setCenterColumnShape(String centerColumnShape) {
            this.centerColumnShape = centerColumnShape;
        }

        public void setCenterColumnLength(String centerColumnLength) {
            this.centerColumnLength = centerColumnLength;
        }

        public void setMaturity(String maturity) {
            this.maturity = maturity;
        }

        public void setExtendedHarvestPeriodEarly(String extendedHarvestPeriodEarly) {
            this.extendedHarvestPeriodEarly = extendedHarvestPeriodEarly;
        }

        public void setExtendedHarvestPeriodMidLate(String extendedHarvestPeriodMidLate) {
            this.extendedHarvestPeriodMidLate = extendedHarvestPeriodMidLate;
        }

        public void setLossRate(String lossRate) {
            this.lossRate = lossRate;
        }

        public void setHalfPlantBudding(String halfPlantBudding) {
            this.halfPlantBudding = halfPlantBudding;
        }

        public void setTimeRequiredForTheFirstFlower(String timeRequiredForTheFirstFlower) {
            this.timeRequiredForTheFirstFlower = timeRequiredForTheFirstFlower;
        }

        public void setBudState(String budState) {
            this.budState = budState;
        }

        public void setBudShape(String budShape) {
            this.budShape = budShape;
        }

        public void setBudSize(String budSize) {
            this.budSize = budSize;
        }

        public void setPetalShape(String petalShape) {
            this.petalShape = petalShape;
        }

        public void setPetalSize(String petalSize) {
            this.petalSize = petalSize;
        }

        public void setPetalColor(String petalColor) {
            this.petalColor = petalColor;
        }

        public void setPetalNumber(String petalNumber) {
            this.petalNumber = petalNumber;
        }

        public void setBranchingAbility(String branchingAbility) {
            this.branchingAbility = branchingAbility;
        }

        public void setSterilityOfSingleFlower(String sterilityOfSingleFlower) {
            this.sterilityOfSingleFlower = sterilityOfSingleFlower;
        }

        public void setSterilityPerPlant(String sterilityPerPlant) {
            this.sterilityPerPlant = sterilityPerPlant;
        }

        public void setPopulationSterility(String populationSterility) {
            this.populationSterility = populationSterility;
        }

        public void setPopulationSterilePlantRate(String populationSterilePlantRate) {
            this.populationSterilePlantRate = populationSterilePlantRate;
        }

        public void setMaleSterile(String maleSterile) {
            this.maleSterile = maleSterile;
        }

        public void setSeedPodLength(String seedPodLength) {
            this.seedPodLength = seedPodLength;
        }

        public void setLongBeakOfSeedPod(String longBeakOfSeedPod) {
            this.longBeakOfSeedPod = longBeakOfSeedPod;
        }

        public void setSeedSize(String seedSize) {
            this.seedSize = seedSize;
        }

        public void setSeedShape(String seedShape) {
            this.seedShape = seedShape;
        }

        public void setSeedColor(String seedColor) {
            this.seedColor = seedColor;
        }

        public void setPoddingRate(String poddingRate) {
            this.poddingRate = poddingRate;
        }

        public void setNumberOfSeeds(String numberOfSeeds) {
            this.numberOfSeeds = numberOfSeeds;
        }

        public void setSeedSettingRate(String seedSettingRate) {
            this.seedSettingRate = seedSettingRate;
        }

        public void setSelfCompatibility(String selfCompatibility) {
            this.selfCompatibility = selfCompatibility;
        }
    }
}
