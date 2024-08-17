package com.ssafy.trip.model.service;

import java.util.List;
import com.ssafy.trip.model.dao.FestivalDao;
import com.ssafy.trip.model.dao.FestivalDaoImpl;
import com.ssafy.trip.model.dto.FestivalDto;
import com.ssafy.trip.model.dto.TripDto;

public class FestivalServiceImpl implements FestivalService {
    
    private FestivalDao festivalDao;
    
    public FestivalServiceImpl() {
        festivalDao = new FestivalDaoImpl();
    }

    @Override
    public List<FestivalDto> searchFestival(TripDto tripDto) {
        return festivalDao.searchFestival(tripDto);
    }
}
