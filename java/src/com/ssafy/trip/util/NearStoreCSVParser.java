package com.ssafy.trip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ssafy.trip.model.dto.NearStoreDto;
import com.ssafy.trip.model.dto.TripDto;


public class NearStoreCSVParser {

	private List<NearStoreDto> nearInfo;
	private int size;
	/**
	 * 식당 정보를 식별하기 위한 번호로 차후 DB에서는 primary key로 대체하지만 현재 버전에서는 0번부터 순차 부여한다.
	 */
	public int num;
	
	public NearStoreCSVParser() {
		loadData();
	}
	
	private void loadData() {
		String nearStoreFolderPath = "res/소상공인시장진흥공단/근처가게.csv";
		System.out.println("!");
		nearInfo = new ArrayList<NearStoreDto>();
		File file = new File(nearStoreFolderPath);
		
		try(BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(file), "euc-kr"))) {
			String input = is.readLine();
			while((input = is.readLine()) != null) {
				String[] cms = input.split(",");
				NearStoreDto cur = new NearStoreDto();
				
				cur.setNum(nearInfo.size());
				cur.setStoreName(cms[0]);
				cur.setStreetAddress(cms[1]);
				cur.setLotAddress(cms[2]);
				cur.setLat(Double.parseDouble(cms[3]));
				cur.setLng(Double.parseDouble(cms[4]));
				
				nearInfo.add(cur);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<NearStoreDto> getNearInfo() {
		return nearInfo;
	}
}
