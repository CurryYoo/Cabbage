package com.example.cabbage.data;

import android.content.Context;

import com.example.cabbage.network.MaterialInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Author: xiemugan
 * Date: 2020/6/26
 * Description:
 **/
public class DataHelper {

    public static List<MaterialSuggestion> toSuggestionList(List<MaterialInfo.Data.list> list) {
        List<MaterialSuggestion> materialSuggestionList = new ArrayList<>();
        for (MaterialInfo.Data.list materialData : list) {
            MaterialSuggestion materialSuggestion = new MaterialSuggestion(materialData.materialNumber, materialData.materialType);
            materialSuggestionList.add(materialSuggestion);
        }
        return materialSuggestionList;
    }

}
