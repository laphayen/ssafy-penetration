package com.ssafy.trip.model.dao;

import java.util.LinkedList;
import java.util.List;
import com.ssafy.trip.model.dto.FestivalDto;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.trip.util.FestivalCSVParser;

public class FestivalDaoImpl implements FestivalDao {

    private List<FestivalDto> festivalInfo;

    public FestivalDaoImpl() {
        loadData();
    }

    @Override
    public void loadData() {
        FestivalCSVParser parser = new FestivalCSVParser();
        festivalInfo = parser.getFestivalInfo();
    }

    @Override
    public List<FestivalDto> searchFestival(TripDto tripDto) {
        List<FestivalDto> finds = new LinkedList<>();

        String streetAddress = tripDto.getStreetAddress(); // 도로명 주소
        String lotAddress = tripDto.getLotAddress(); // 지번 주소
        String touristDestination = tripDto.getTouristDestination(); // 관광지명
        

        for (FestivalDto festival : festivalInfo) {
            boolean matches = true;

            // StreetAddress와 LotAddress의 지역 정보를 추출
            String festivalRegion = festival.getFestivalRegion().toLowerCase().strip();
            String addressRegion = "";
            
            // 도로명 주소와 지번 주소에서 지역 정보 추출
            if (streetAddress != null && !streetAddress.isEmpty()) {
                addressRegion = streetAddress.split(" ")[0].toLowerCase();
            }
            if (lotAddress != null && !lotAddress.isEmpty()) {
                String lotAddressRegion = lotAddress.split(" ")[0].toLowerCase();
                if (!addressRegion.equals(lotAddressRegion)) {
                    addressRegion = lotAddressRegion;
                }
            }
            

            // addressRegion이 festivalRegion과 일치하는지 확인
            if (!festivalRegion.contains(addressRegion)) {
                matches = false;
            }
        	System.out.println("1" + matches);

            // 관광지명 조건 추가
        	/*
            if (matches && touristDestination != null && !touristDestination.isEmpty()) {
                if (festival.getFestivalName().toLowerCase().contains(touristDestination.toLowerCase())) {
                    matches = false;
                }
            }
            */

            if (matches) {
                finds.add(festival);
            }
        }
    	System.out.println(finds.size());

        return finds;
    }

}
