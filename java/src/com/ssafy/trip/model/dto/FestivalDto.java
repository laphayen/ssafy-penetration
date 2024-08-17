package com.ssafy.trip.model.dto;

public class FestivalDto {
	
	// 필드
	// 축제 식별 번호
	private int festivalNum;
	// 지역
	private String festivalRegion;
	// 축제명
	private String festivalName;
	// 축제 종류
	private String festivalType;
	// 축제 기간
	private String festivalTime;
	// 축제 장소
	private String festivalLocation;
	

	// get, set ---------------------------------------------
	
	public int getFestivalNum() {
	    return festivalNum;
	}
	public void setFestivalNum(int festivalNum) {
	    this.festivalNum = festivalNum;
	}
	public String getFestivalRegion() {
		return festivalRegion;
	}
	public void setFestivalRegion(String festivalRegion) {
		this.festivalRegion = festivalRegion;
	}
	public String getFestivalName() {
		return festivalName;
	}
	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}
	public String getFestivalType() {
		return festivalType;
	}
	public void setFestivalType(String festivalType) {
		this.festivalType = festivalType;
	}
	public String getFestivalTime() {
		return festivalTime;
	}
	public void setFestivalTime(String festivalTime) {
		this.festivalTime = festivalTime;
	}
	public String getFestivalLocation() {
		return festivalLocation;
	}
	public void setFestivalLocation(String festivalLocation) {
		this.festivalLocation = festivalLocation;
	}
	
	//
	//-------------------------------------------
	
	public FestivalDto() {
		super();
	}
	public FestivalDto(int fastivalNum, String festivalRegion, String festivalName, String festivalType,
			String festivalTime, String festivalLocation) {
		super();
		this.festivalNum = fastivalNum;
		this.festivalRegion = festivalRegion;
		this.festivalName = festivalName;
		this.festivalType = festivalType;
		this.festivalTime = festivalTime;
		this.festivalLocation = festivalLocation;
	}
	
	// toString ----------------------------------------------
	
	@Override
	public String toString() {
		return "FestivalDto [fastivalNum=" + festivalNum + ", festivalRegion=" + festivalRegion + ", festivalName="
				+ festivalName + ", festivalType=" + festivalType + ", festivalTime=" + festivalTime
				+ ", festivalLocation=" + festivalLocation + "]";
	}
}
