package com.ssafy.trip.model.dao;

import java.util.List;

import com.ssafy.trip.model.dto.NearStoreDto;
import com.ssafy.trip.model.dto.TripDto;

public interface NearStoreDao {

	/**
	 * 상가 정보를 csv 파일에서 로딩하는 기능
	 */
	public void loadData();

	/**
	 * 검색 조건(key) 검색 단어(word)에 해당하는 관광지 정보(NearStoreDao)를 검색해서 반환.
	 * 
	 * @param 특정한 TipSearchDto
	 * @return 파라미터 Dto의 경도, 위도 상 근처에 위치하는 NearStoreDao 반환
	 */
	public List<NearStoreDto> searchNear(TripDto tripDto);
}
