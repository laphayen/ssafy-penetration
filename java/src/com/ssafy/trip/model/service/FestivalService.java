package com.ssafy.trip.model.service;

import java.util.List;
import com.ssafy.trip.model.dto.FestivalDto;
import com.ssafy.trip.model.dto.TripDto;

public interface FestivalService {
    /**
     * 검색 조건(key) 검색 단어(word)에 해당하는 관광지 정보(FestivalDto)를 검색해서 반환.
     * 
     * @param TripDto 파라미터 Dto의 주소에 맞는 FestivalDto 반환
     */
    public List<FestivalDto> searchFestival(TripDto tripDto);
}
