package com.example.cabbage.entity;

public class HistoryEntity {
    public String observationId;
    public String materialType;
    public String materialNumber;
    public String plantNumber;
    public String location;
    public String investigatingTime;
    public String investigator;
    public String obsPeriod;

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

    public String getInvestigatingTime() {
        return investigatingTime;
    }

    public void setInvestigatingTime(String investigatingTime) {
        this.investigatingTime = investigatingTime;
    }
}
