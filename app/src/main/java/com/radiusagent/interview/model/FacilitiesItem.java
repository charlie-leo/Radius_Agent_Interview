package com.radiusagent.interview.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FacilitiesItem{

	@SerializedName("name")
	private String name;

	@SerializedName("options")
	private List<OptionsItem> options;

	@SerializedName("facility_id")
	private String facilityId;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOptions(List<OptionsItem> options){
		this.options = options;
	}

	public List<OptionsItem> getOptions(){
		return options;
	}

	public void setFacilityId(String facilityId){
		this.facilityId = facilityId;
	}

	public String getFacilityId(){
		return facilityId;
	}
}