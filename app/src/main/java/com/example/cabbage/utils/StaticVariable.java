package com.example.cabbage.utils;

public class StaticVariable {

    // 页面的状态
    public static final int STATUS_NEW = 0;    // 新建
    public static final int STATUS_READ = 1;   // 只读
    public static final int STATUS_WRITE = 2;  // 修改
    public static final int STATUS_COPY = 3;  // 复制创建
    public static final int STATUS_CACHE = 4;  // 缓存数据

    // 观测时期
    public static final String SURVEY_PERIOD_GERMINATION = "发芽期";
    public static final String SURVEY_PERIOD_SEEDLING = "幼苗期";
    public static final String SURVEY_PERIOD_ROSETTE = "莲座期";
    public static final String SURVEY_PERIOD_HEADING = "结球期";
    public static final String SURVEY_PERIOD_HARVEST = "收获期";
    public static final String SURVEY_PERIOD_STORAGE = "储藏期";
    public static final String SURVEY_PERIOD_FLOWERING= "现蕾开花期";
    public static final String SURVEY_PERIOD_SEED_HARVEST= "结荚与种子收获期";

    //分隔符
    public static final String SEPARATOR = "/";
    public static final String SEPARATOR_2 = ",";

    //重复属性和额外属性最大数目
    public static final int COUNT_EXTRA = 1;  // 额外属性限制1个
    public static final int COUNT_REPEAT = 2;  // 重复属性限制2个
}
