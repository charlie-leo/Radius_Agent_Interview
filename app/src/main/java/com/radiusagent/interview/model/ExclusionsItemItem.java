package com.radiusagent.interview.model;

import com.google.gson.annotations.SerializedName;

public class ExclusionsItemItem{

	@SerializedName("options_id")
	private String optionsId;

	@SerializedName("facility_id")
	private String facilityId;

	public void setOptionsId(String optionsId){
		this.optionsId = optionsId;
	}

	public String getOptionsId(){
		return optionsId;
	}

	public void setFacilityId(String facilityId){
		this.facilityId = facilityId;
	}

	public String getFacilityId(){
		return facilityId;
	}
}