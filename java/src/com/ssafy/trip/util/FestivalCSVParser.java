package com.ssafy.trip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ssafy.trip.model.dto.FestivalDto;
import com.ssafy.trip.model.dto.NearStoreDto;

public class FestivalCSVParser {

	private List<FestivalDto> festivalInfo;
	private int size;
	
	public FestivalCSVParser() {
		loadData();
	}
	
	private void loadData() {
	    String FestivalFolderPath = "res/지역축제/축제.csv";
	    
	    festivalInfo = new ArrayList<FestivalDto>();
	    File file = new File(FestivalFolderPath);
	    
	    try (BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
	        String input;
	        int lineNumber = 1; // Initialize line number counter
	        
	        is.readLine(); // Read header line
	        
	        while ((input = is.readLine()) != null) {
	            String[] cms = input.split(",");
	            
	            if (cms.length < 5) {
	                System.err.println("Skipping malformed line: " + input + " (Line Number: " + lineNumber + ")");
	                lineNumber++;
	                continue;
	            }
	            
	            FestivalDto cur = new FestivalDto();
	            
	            cur.setFestivalNum(festivalInfo.size());  // 식별 번호 설정
	            cur.setFestivalRegion(cms[0]);
	            cur.setFestivalName(cms[1]);
	            cur.setFestivalType(cms[2]);
	            cur.setFestivalTime(cms[3]);
	            cur.setFestivalLocation(cms[4]);
	            
	            festivalInfo.add(cur);
	            lineNumber++; // Increment line number counter
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	
	public List<FestivalDto> getFestivalInfo() {
		return festivalInfo;
	}
	
}
