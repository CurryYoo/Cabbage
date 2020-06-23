package com.example.cabbage.data;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Author: bytedance
 * Date: 2020/6/21
 * Description:
 **/

@Entity
public class SurveyData {
    @Id public long surveyId;
    public String cotyledonSize;
    public String cotyledonColor;
    public String cotyledonCount;
    public String cotyledonShape;
    public String heartLeafColor;
    public String trueLeafColor;
    public String trueLeafLength;
    public String trueLeafWidth;
}
