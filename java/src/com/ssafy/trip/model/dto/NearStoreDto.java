package com.ssafy.trip.model.dto;

public class NearStoreDto {
	/** 상가 업소 번호 */
	private int num;
	/** 상호명 */
	private String storeName;
	/** 관광지 도로명주소 */
	private String streetAddress;
	/** 관광지 지번주소 */
	private String lotAddress;
	/** 관광지 위도 */
	private double lat;
	/** 관광지 경도 */
	private double lng;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getLotAddress() {
		return lotAddress;
	}
	public void setLotAddress(String lotAddress) {
		this.lotAddress = lotAddress;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public NearStoreDto() {
		super();
	}
	
	public NearStoreDto(int num, String storeName, String streetAddress, String lotAddress, double lat, double lng) {
		super();
		this.num = num;
		this.storeName = storeName;
		this.streetAddress = streetAddress;
		this.lotAddress = lotAddress;
		this.lat = lat;
		this.lng = lng;
	}
	
	@Override
	public String toString() {
		return "NearStoreDto [num=" + num + ", storeName=" + storeName + ", streetAddress=" + streetAddress
				+ ", lotAddress=" + lotAddress + ", lat=" + lat + ", lng=" + lng + "]";
	}
}
