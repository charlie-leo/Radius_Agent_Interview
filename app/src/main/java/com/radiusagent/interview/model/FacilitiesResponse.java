package com.radiusagent.interview.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FacilitiesResponse{

	@SerializedName("exclusions")
	private List<List<ExclusionsItemItem>> exclusions;

	@SerializedName("facilities")
	private List<FacilitiesItem> facilities;

	public void setExclusions(List<List<ExclusionsItemItem>> exclusions){
		this.exclusions = exclusions;
	}

	public List<List<ExclusionsItemItem>> getExclusions(){
		return exclusions;
	}

	public void setFacilities(List<FacilitiesItem> facilities){
		this.facilities = facilities;
	}

	public List<FacilitiesItem> getFacilities(){
		return facilities;
	}
}