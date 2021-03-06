package com.example.cabbage.network;

import java.util.List;

/**
 * Author: xiemugan
 * Date: 2020/6/24
 * Description:
 **/
public class HistoryInfo extends NormalInfo {
//    public List<data> data;
//
//    public List<HistoryInfo.data> getData() {
//        return data;
//    }
//
//    public void setData(List<HistoryInfo.data> data) {
//        this.data = data;
//    }
    public data data;

    public class data {

        public int pageSize;
        public int total;
        public List<Info> list;
        public int pageNum;
        public int totalPage;
        public class Info {
            public String observationId;
            public String materialType;
            public String materialNumber;
            public String plantNumber;
            public String location;
            public String investigatingTime;
            public String investigator;
            public String obsPeriod;
            public String year;
            public String season;
            public String origin;
            public String feature;
            public String oddLeeds;
            public String experiment;
            public String paternal;
            public String maternal;

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getSeason() {
                return season;
            }

            public void setSeason(String season) {
                this.season = season;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getFeature() {
                return feature;
            }

            public void setFeature(String feature) {
                this.feature = feature;
            }

            public String getOddLeeds() {
                return oddLeeds;
            }

            public void setOddLeeds(String oddLeeds) {
                this.oddLeeds = oddLeeds;
            }

            public String getExperiment() {
                return experiment;
            }

            public void setExperiment(String experiment) {
                this.experiment = experiment;
            }

            public String getPaternal() {
                return paternal;
            }

            public void setPaternal(String paternal) {
                this.paternal = paternal;
            }

            public String getMaternal() {
                return maternal;
            }

            public void setMaternal(String maternal) {
                this.maternal = maternal;
            }

            public String getObservationId() {
                return observationId;
            }

            public void setObservationId(String observationId) {
                this.observationId = observationId;
            }

            public String getMaterialType() {
                return materialType;
            }

            public void setMaterialType(String materialType) {
                this.materialType = materialType;
            }

            public String getMaterialNumber() {
                return materialNumber;
            }

            public void setMaterialNumber(String materialNumber) {
                this.materialNumber = materialNumber;
            }

            public String getPlantNumber() {
                return plantNumber;
            }

            public void setPlantNumber(String plantNumber) {
                this.plantNumber = plantNumber;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getInvestigatingTime() {
                return investigatingTime;
            }

            public void setInvestigatingTime(String investigatingTime) {
                this.investigatingTime = investigatingTime;
            }

            public String getInvestigator() {
                return investigator;
            }

            public void setInvestigator(String investigator) {
                this.investigator = investigator;
            }

            public String getObsPeriod() {
                return obsPeriod;
            }

            public void setObsPeriod(String obsPeriod) {
                this.obsPeriod = obsPeriod;
            }
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Info> getList() {
            return list;
        }

        public void setList(List<Info> list) {
            this.list = list;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    }
}
