# 대전 5반 유건희, 백기찬 조 결과보고서

## 기본기능

### Task

1. searchBt (검색버튼 객체의 Click Event
   Handler 등록을 Lambda 식으로
   표현하세요)

2. search()를 Dao Layer 의 method 를
   호출하도록 완성하세요.

3. 화면 목록에서 선택된 항목 번호로 상세
   정보를 얻어 TripDto 객체로 return 하세요.

4. TouristDestinationSAXHandler 의 부분코드를
   완성하세요.

5. TouristDestinationSAXParser 에서
   xml 파일을 로드하도록 완성하세요.

### 실행화면

폴더에 특정한 장소의 이미지가 존재하는 경우 (0번 ~ 10번 장소에 해당) 화면 좌측에 해당 이미지를 표시합니다. <br>
만일 이미지가 존재하지 않는 경우, 빈칸 대신 no_image.png를 출력합니다.

![image](/uploads/8ca966e056fac0ae27ab7c79650c4237/image.png)
![image](/uploads/4f02620d4a92378e2eda7c29bf7ff8fd/image.png)

## 추가기능

### Task

**지역 축제 정보 분석** <br>

지역 축제 정보 데이터 파일을 다운로드 받아 분석하여 화면에 출력한다.

1. 선택한 관광지 주변에서 개최되는 지역 축제 정보를 보여줄 수 있다.

2. 별도 UI를 가지는 Application을 통해 보여줄 수 있다.

### 실행화면

기본기능에서 제작한 화면의 테이블에서 특정한 Element를 선택할 경우, 해당 Element가 속한 row의 고유 id를 이용해 여행지 정보를 저장하는 TripDto를 찾습니다. <br>
주어진 관광지 정보를 바탕으로 관련 축제들을 검색하는 기능을 수행합니다. 검색 과정에서 도로명 주소와 지번 주소에서 지역 정보를 추출하여 축제 지역과 비교하며, matches 플래그를 통해 결과를 필터링합니다.
<br>
이후, 특정 지역의 축제 정보들을 별도의 테이블에 표시합니다.

![image](/img/result.png)
![image](/img/result2.png)


## 심화기능

### Task

**주변 상권 정보 분석** <br>

Task
주변 상권 정보 분석
상권 정보 파일(csv) 을 읽고 화면에 보여준다. 제공되는 파일 중 일부 지역만 처리해도 무방<br>
**현재 Task에서는 전남, 전북, 대전, 강원에서 각 200여개씩 처리하였습니다.**

1. 관광지 정보와 연동하여 주변의 상권 정보를 보여줄 수 있다.

2. 별도 UI를 가지는 Application을 통해 보여줄 수 있다.

### 실행화면

기본기능에서 제작한 화면의 테이블에서 특정한 Element를 선택할 경우, 해당 Element가 속한 row의 고유 id를 이용해 여행지 정보를 저장하는 TripDto를 찾습니다. <br>
이후 해당 TripDto의 위도, 경도 정보와 전체 상가 정보 리스트의 위도, 경도 정보를 haversine function을 통해 비교함으로써 목표 관광지와 특정한 상가가 어느정도 거리 내에 위치해있는지 계산합니다. <br>
이후, 특정한 거리(현재 프로그램 내에서는 **10Km**) 내에 있는 상가들을 별도의 테이블에 표시합니다.

![image](/uploads/3d00f06a76561c80b0b186bae2477ae0/image.png)
![image](/uploads/436dc9d2df93e294c4cc083d28369177/image.png)
