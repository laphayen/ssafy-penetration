package com.ssafy.trip.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.ssafy.trip.model.dao.NearStoreDao;
import com.ssafy.trip.model.dto.FestivalDto;
import com.ssafy.trip.model.dto.NearStoreDto;
import com.ssafy.trip.model.dto.TripDto;
import com.ssafy.trip.model.dto.TripSearchDto;
import com.ssafy.trip.model.service.FestivalService;
import com.ssafy.trip.model.service.FestivalServiceImpl;
import com.ssafy.trip.model.service.NearStoreService;
import com.ssafy.trip.model.service.NearStoreServiceImpl;
import com.ssafy.trip.model.service.TripService;
import com.ssafy.trip.model.service.TripServiceImpl;

public class TripInfoView {

	/** model들 */
	private TripService tripService;
	private NearStoreService nearStoreService;
	private FestivalService festivalService;

	/** main 화면 */
	private JFrame frame;
	
	/** 주변 상권 화면 */
	private JFrame ns_frame;
	
	/** 주변 축 화면 */
	private JFrame fs_frame;

	/** 관광지 이미지 표시 Panel */
	private JLabel imgL;
	private JLabel[] tripInfoL;

	/** 조회 조건 */
	private JComboBox<String> findC;
	private JTextField wordTf;
	private JButton searchBt;

	/** 조회 내용 표시할 table */
	private DefaultTableModel tripModel;
	private JTable tripTable;
	private JScrollPane tripPan;
	private String[] title = { "번호", "관광지명", "도로명주소", "지번주소", "전화번호" };

	/** 조회 내용 표시할 table */
	private DefaultTableModel nearModel;
	private JTable nearTable;
	private JScrollPane nearPan;
	private String[] ntitle = { "번호", "상호명", "도로명주소", "지번주소"};
	
	/** 조회 내용 표시할 table */
	private DefaultTableModel festivalModel;
	private JTable festivalTable;
	private JScrollPane festivalPan;
	private String[] festivalTitle = { "번호", "축제명", "광역자치단체명", "축제유형"};

	/** 검색 조건 */
	private String key;
	private String[] choice = { "검색조건선택", "관광지명", "주소" };
	/** 검색할 단어 */
	private String word;

	/** 화면에 표시하고 있는 주택 */
	private TripDto curTrip;

	public TripInfoView() {
		/* Service들 생성 */
		tripService = new TripServiceImpl();
		nearStoreService = new NearStoreServiceImpl();
		System.out.println("!!");
		festivalService = new FestivalServiceImpl();
		System.out.println("!!");

		/* 메인 화면 설정 */
		frame = new JFrame("Enjoy! Trip - 즐거운 여행");
		ns_frame = new JFrame("주변 상권 정보");
		fs_frame = new JFrame("주변 축제 정보");
//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e){
//				frame.dispose();
//			}
//		});

		setMain();

		frame.setSize(1200, 800);
		frame.setResizable(true);
		frame.setVisible(true);
		showTripInfo(0);
		
		ns_frame.setSize(600, 800);
		ns_frame.setResizable(true);
		ns_frame.setVisible(true);
		
		fs_frame.setSize(600, 800);
		fs_frame.setResizable(true);
		fs_frame.setVisible(true);
	}

	private void showTripInfo(int num) {
		curTrip = tripService.search(num);

		tripInfoL[0].setText("");
		tripInfoL[1].setText("");
		tripInfoL[2].setText(curTrip.getTouristDestination());
		tripInfoL[3].setText(curTrip.getStreetAddress());
		tripInfoL[4].setText(curTrip.getLotAddress());
		tripInfoL[5].setText(curTrip.getLat() + "");
		tripInfoL[6].setText(curTrip.getLng() + "");
		tripInfoL[7].setText(curTrip.getTel());
		tripInfoL[8].setText(curTrip.getInfo());
		tripInfoL[9].setText("");

		ImageIcon icon = null;
		if (curTrip.getImg() != null && curTrip.getImg().trim().length() != 0) {
			String img = curTrip.getImg();
			File file = new File("img", img);

			if (!file.exists())
				img = "no_image.jpg";
			icon = new ImageIcon("img/" + img);

		} else {
			icon = new ImageIcon("img/no_image.jpg");
		}
		Image image = icon.getImage();
		Image changeImage = image.getScaledInstance(570, 470, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImage);
		imgL.setIcon(changeIcon);
	}

	private void showNearStoreInfo(int num) {
		curTrip = tripService.search(num);
		List<NearStoreDto> nears = nearStoreService.searchNear(curTrip);
		nearModel.setNumRows(0);
		System.out.println(nears.size());
		if (nears.size() > 0) {
			int i = 0;
			String[][] data = new String[nears.size()][6];
			for (NearStoreDto near : nears) {
				data[i][0] = "" + near.getNum();
				data[i][1] = near.getStoreName();
				data[i][2] = near.getStreetAddress();
				data[i++][3] = near.getLotAddress();
			}
			nearModel.setDataVector(data, ntitle);
		}
	}
	
	private void showFestivalInfo(int num) {
		 curTrip = tripService.search(num);
	        List<FestivalDto> festivals = festivalService.searchFestival(curTrip);
	        festivalModel.setNumRows(0);
	        if (festivals.size() > 0) {
	            int i = 0;
	            String[][] data = new String[festivals.size()][6];
	            for (FestivalDto festival : festivals) {
	                data[i][0] = "" + festival.getFestivalNum();
	                data[i][1] = festival.getFestivalName();
	                data[i][2] = festival.getFestivalRegion();
	                data[i][3] = festival.getFestivalType();
	                data[i][4] = festival.getFestivalTime();
	                data[i++][5] = festival.getFestivalLocation();
	            }
	            festivalModel.setDataVector(data, festivalTitle);
	        }
	    }

	/** 메인 화면인 관광지 목록을 위한 화면 셋팅하는 메서드 */
	public void setMain() {

		/* 왼쪽 화면을 위한 설정 */
		JPanel left = new JPanel(new BorderLayout());
		JPanel leftCenter = new JPanel(new BorderLayout(0, 10));
		JPanel leftR = new JPanel(new GridLayout(10, 2));
		leftR.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		String[] info = { "", "", "관광지명", "도로명주소", "지번주소", "위도", "경도", "전화번호", "관광지정보", "" };
		int size = info.length;
		JLabel infoL[] = new JLabel[size];
		tripInfoL = new JLabel[size];
		for (int i = 0; i < size; i++) {
			infoL[i] = new JLabel(info[i]);
			tripInfoL[i] = new JLabel("");
			leftR.add(infoL[i]);
			leftR.add(tripInfoL[i]);
		}
		imgL = new JLabel();
		leftCenter.add(imgL, "Center");
		leftCenter.add(leftR, "South");

		left.add(new JLabel("관광지 정보", JLabel.CENTER), "North");
		left.add(leftCenter, "Center");

		/* 오른쪽 화면을 위한 설정 */
		JPanel right = new JPanel(new BorderLayout());
		JPanel rightTop = new JPanel(new GridLayout(4, 2));

		JPanel rightTop2 = new JPanel(new GridLayout(1, 3));
		String[] item = { "검색조건선택", "관광지명", "주소" };
		findC = new JComboBox<String>(item);
		wordTf = new JTextField();
		searchBt = new JButton("검색");

		rightTop2.add(findC);
		rightTop2.add(wordTf);
		rightTop2.add(searchBt);

		rightTop.add(new Label(""));
		rightTop.add(new Label(""));
		rightTop.add(rightTop2);
		rightTop.add(new Label(""));

		JPanel rightCenter = new JPanel(new BorderLayout());
		tripModel = new DefaultTableModel(title, 20);
		tripTable = new JTable(tripModel);
		tripPan = new JScrollPane(tripTable);
		tripTable.setColumnSelectionAllowed(true);
		rightCenter.add(new JLabel("관광지 정보", JLabel.CENTER), "North");
		rightCenter.add(tripPan, "Center");

		right.add(rightTop, "North");
		right.add(rightCenter, "Center");

		JPanel mainP = new JPanel(new GridLayout(1, 2));

		mainP.add(left);
		mainP.add(right);

		mainP.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
		frame.add(mainP, "Center");

		tripTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = tripTable.getSelectedRow();
				int code = Integer.parseInt(((String) tripModel.getValueAt(row, 0)).trim());
				showTripInfo(code);
				showNearStoreInfo(code);
				showFestivalInfo(code);
			}
		});
		
		
		// Near View implementation

		nearModel = new DefaultTableModel(ntitle, 20);
		nearTable = new JTable(nearModel);
		nearPan = new JScrollPane(nearTable);
		nearTable.setColumnSelectionAllowed(true);
		ns_frame.add(nearPan);
		
		
		// Festival
		festivalModel = new DefaultTableModel(festivalTitle, 20);
		festivalTable = new JTable(festivalModel);
		festivalPan = new JScrollPane(festivalTable);
		festivalTable.setColumnSelectionAllowed(true);
		fs_frame.add(festivalPan);
		
		

		// complete code #01
		// 아래의 코드를 참조하여 아래 라인을 uncomment 하고 searchBt.addActionList() 를 Lambda 표현식으로 바꾸세요.
		ActionListener buttonHandler = (e) -> {searchTrips();};

		// 참조코드 시작 - 위 코드를 완성 후 삭제 또는 comment 처리하세요.
//		ActionListener buttonHandler = new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				searchTrips();
//			}
//		};
		
		searchBt.addActionListener( buttonHandler );
		// 참조코드 종료

		showTrips();
	}

	/** 검색 조건에 맞는 관광지 검색 */
	private void searchTrips() {
		word = wordTf.getText().trim();
		key = choice[findC.getSelectedIndex()];
		showTrips();
	}

	/**
	 * 관광지 목록을 갱신하기 위한 메서드
	 */
	public void showTrips() {
		TripSearchDto tripSearchDto = new TripSearchDto();
		if (key != null) {
			if (key.equals("관광지명")) {
				tripSearchDto.setTouristDestination(word);
			} else if (key.equals("주소")) {
				tripSearchDto.setSido(word);
			}
		}

		if (word == null || word.trim().length() == 0)
			findC.setSelectedIndex(0);

		List<TripDto> trips = tripService.searchAll(tripSearchDto);
		if (trips != null) {
			int i = 0;
			String[][] data = new String[trips.size()][5];
			for (TripDto trip : trips) {
				data[i][0] = "" + trip.getNum();
				data[i][1] = trip.getTouristDestination();
				data[i][2] = trip.getStreetAddress();
				data[i][3] = trip.getLotAddress();
				data[i++][4] = trip.getTel();
			}
			tripModel.setDataVector(data, title);
		}
	}
	

//	public static void main(String[] args) {
//		new TripInfoView();
//	}
}
