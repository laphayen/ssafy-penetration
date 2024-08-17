package com.ssafy.trip.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.trip.model.dto.NearStoreDto;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.trip.model.dto.TripSearchDto;
import com.ssafy.trip.util.NearStoreCSVParser;

public class NearStoreDaoImpl implements NearStoreDao {

	private List<NearStoreDto> nearInfo;
	private static double limiter = 10; // Up to 10 km
	
	public NearStoreDaoImpl() {
		loadData();
	}
	
	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		NearStoreCSVParser parser = new NearStoreCSVParser();
		nearInfo = parser.getNearInfo();
	}

	@Override
	public List<NearStoreDto> searchNear(TripDto tripDto) {
		// TODO Auto-generated method stub
		double ts_lng = tripDto.getLng();
		double ts_lat = tripDto.getLat();
		
		
		List<NearStoreDto> sel = new ArrayList<>();
		
		for(int i = 0; i < nearInfo.size(); i++) {
			NearStoreDto cur = nearInfo.get(i);
			
			double lng = cur.getLng();
			double lat = cur.getLat();
			
			double dist = haversine(lat, lng, ts_lat, ts_lng);
			if(dist <= limiter) {
				sel.add(cur);
			}
		}
		return sel;
	}
	
    private double haversine(double lat1, double lng1, double lat2, double lng2) {
        lat1 = Math.toRadians(lat1);
        lng1 = Math.toRadians(lng1);
        lat2 = Math.toRadians(lat2);
        lng2 = Math.toRadians(lng2);

        //return distance as km
        return 6371 * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng1 - lng2));
    }
}
