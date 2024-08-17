package com.ssafy.trip.model.service;

import java.util.LinkedList;
import java.util.List;

import com.ssafy.trip.model.dao.NearStoreDao;
import com.ssafy.trip.model.dao.NearStoreDaoImpl;
import com.ssafy.trip.model.dao.TripDao;
import com.ssafy.trip.model.dao.TripDaoImpl;
import com.ssafy.trip.model.dto.NearStoreDto;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.trip.model.dto.TripSearchDto;
import com.ssafy.trip.util.NearStoreCSVParser;
import com.ssafy.trip.util.TouristDestinationSAXParser;

public class NearStoreServiceImpl implements NearStoreService {

	private NearStoreDao nearDao;

	public NearStoreServiceImpl() {
		nearDao = new NearStoreDaoImpl();
	}
	@Override
	public List<NearStoreDto> searchNear(TripDto tripDto) {
		// TODO Auto-generated method stub
		return nearDao.searchNear(tripDto);
	}

}

