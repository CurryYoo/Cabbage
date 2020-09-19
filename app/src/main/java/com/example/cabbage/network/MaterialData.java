package com.example.cabbage.network;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

/**
 * Author:Kang
 * Date:2020/9/19
 * Description:
 */
public class MaterialData {
    public MaterialData(){
        materialName="null";
        year="null";
        season="null";
        origin="null";
        feature="null";
        oddLeeds="null";
        experiment="null";
        paternal="null";
        maternal="null";
        userId=0;
    }
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

    @NotNull
    @Override
    public String toString() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("materialNumber", materialNumber);
        jsonObject.addProperty("materialType", materialType);
        jsonObject.addProperty("materialName", materialName);
        jsonObject.addProperty("year", year);
        jsonObject.addProperty("season", season);
        jsonObject.addProperty("origin", origin);
        jsonObject.addProperty("feature", feature);
        jsonObject.addProperty("oddLeeds", oddLeeds);
        jsonObject.addProperty("experiment", experiment);
        jsonObject.addProperty("paternal", paternal);
        jsonObject.addProperty("maternal", maternal);
        jsonObject.addProperty("userId", userId);

        return jsonObject.toString();
    }
}
