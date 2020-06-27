package com.example.cabbage.data;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Author: xiemugan
 * Date: 2020/6/26
 * Description:
 **/
public class MaterialSuggestion implements SearchSuggestion {
    private String mMaterialId;
    private String mMaterialType;
    private boolean mIsHistory = false;

    public MaterialSuggestion(String materialId, String materialType) {
        this.mMaterialId = materialId;
        this.mMaterialType = materialType;
    }

    public MaterialSuggestion(Parcel source) {
        this.mMaterialId = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    public String getMaterialId() {
        return mMaterialId;
    }

    public String getMaterialType() {
        return mMaterialType;
    }

    @Override
    public String getBody() {
        return mMaterialId;
    }

    public static final Creator<MaterialSuggestion> CREATOR = new Creator<MaterialSuggestion>() {
        @Override
        public MaterialSuggestion createFromParcel(Parcel source) {
            return new MaterialSuggestion(source);
        }

        @Override
        public MaterialSuggestion[] newArray(int size) {
            return new MaterialSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMaterialId);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
