package com.ssafy.trip.model.dao;

import java.util.List;
import com.ssafy.trip.model.dto.FestivalDto;
import com.ssafy.trip.model.dto.TripDto;

public interface FestivalDao {
    /**
     * 축제 정보를 CSV 파일에서 로딩하는 기능
     */
    public void loadData();
    
    /**
     * TripDto에 해당하는 축제 정보(FestivalDto)를 검색해서 반환.
     */
    public List<FestivalDto> searchFestival(TripDto tripDto);
}
