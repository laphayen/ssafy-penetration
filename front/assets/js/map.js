let latlng = { lat: 36.35536, lng: 127.298294 };

var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(latlng.lat, latlng.lng),
    level: 3
};

var map = new kakao.maps.Map(container, options);

// 초기 시도 데이터를 불러옵니다.
sendRequest("sido-select", "*00000000");


const areas = [
    { province: '서울특별시', city: '강남구', dong: '역삼동', lat: 37.498, lng: 127.027 },
    { province: '부산광역시', city: '해운대구', dong: '중동', lat: 35.162, lng: 129.160 }
    // 추가 관심 지역 데이터 (위도와 경도 포함)
];

const areaList = document.getElementById('area-list');
const editAreaForm = document.getElementById('edit-area-form');
const deleteConfirmButton = document.getElementById('confirm-delete');
const mapContainer = document.getElementById('map');
let editingIndex = null;

function renderAreas() {
    areaList.innerHTML = '';
    areas.forEach((area, index) => {
        const row = document.createElement('tr');
        row.dataset.index = index; // 행에 인덱스를 저장
        row.innerHTML = `
            <td>${area.province}</td>
            <td>${area.city}</td>
            <td>${area.dong}</td>
            <td>
                <button class="btn btn-warning btn-sm mr-2 edit-button">수정</button>
                <button class="btn btn-danger btn-sm delete-button">삭제</button>
            </td>
        `;
        areaList.appendChild(row);
    });
}

function openEditModal(index) {
    const area = areas[index];
    document.getElementById('edit-index').value = index;
    document.getElementById('edit-province').value = area.province;
    document.getElementById('edit-city').value = area.city;
    document.getElementById('edit-dong').value = area.dong;
    $('#editAreaModal').modal('show');
}

function deleteArea(index) {
    areas.splice(index, 1);
    $('#deleteConfirmModal').modal('hide');
    renderAreas();
}

function showAreaOnMap(area) {
    const position = new kakao.maps.LatLng(area.lat, area.lng);

    if (marker) {
        marker.setMap(null); // 기존 마커 제거
    }

    marker = new kakao.maps.Marker({
        position: position,
        map: map
    });

    map.setCenter(position);
}

// function fetchEnvironmentalData(area) {
//     // API 호출 예시 (환경 데이터 API URL과 파라미터는 실제 사용에 맞게 수정해야 함)
//     fetch(`https://api.example.com/environment?province=${area.province}&city=${area.city}&dong=${area.dong}`)
//         .then(response => response.json())
//         .then(data => {
//             // 환경 데이터를 처리하는 코드
//             console.log('Environmental Data:', data);
//         })
//         .catch(error => console.error('Error fetching environmental data:', error));
// }

areaAddBtn.addEventListener('click', function (event) {
    event.preventDefault();

    let province = document.querySelector("#sido-select").options[document.querySelector("#sido-select").selectedIndex].text;
    let city = document.querySelector("#gugun-select").options[document.querySelector("#gugun-select").selectedIndex].text;
    let dong = document.querySelector("#dong-select").options[document.querySelector("#dong-select").selectedIndex].text;
    let lat, lng;

    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
    let address = city + " " + dong;
    geocoder.addressSearch(address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
            lat = result[0].y;
            lng = result[0].x;
            // 새 관심 지역 객체 생성
            const newArea = { 
                province: province, 
                city: city, 
                dong: dong, 
                lat: lat,
                lng: lng
            };

            // 관심 지역 배열에 새 지역 추가
            areas.push(newArea);

            // 관심 지역 목록 업데이트
            renderAreas();

            alert("관심지역 추가 완료!");
        }
    });
});

editAreaForm.addEventListener('submit', function (event) {
    event.preventDefault();

    const index = document.getElementById('edit-index').value;
    const province = document.getElementById('edit-province').value;
    const city = document.getElementById('edit-city').value;
    const dong = document.getElementById('edit-dong').value;

    // 수정된 관심 지역 객체 업데이트
    areas[index] = { province: province, city: city, dong: dong, lat: areas[index].lat, lng: areas[index].lng };

    // 모달 숨기기
    $('#editAreaModal').modal('hide');

    // 폼 필드 초기화
    editAreaForm.reset();

    // 관심 지역 목록 업데이트
    renderAreas();
});

areaList.addEventListener('click', function (event) {
    const tr = event.target.closest('tr');
    const index = tr ? tr.dataset.index : null;

    if (event.target.classList.contains('edit-button') && index !== null) {
        openEditModal(index);
    }

    if (event.target.classList.contains('delete-button') && index !== null) {
        $('#deleteConfirmModal').modal('show');
        deleteConfirmButton.setAttribute('data-index', index);
    }

    if (tr && !event.target.classList.contains('btn')) {
        // tr 클릭 시
        if (index !== null) {
            showAreaOnMap(areas[index]);
            // fetchEnvironmentalData(areas[index]);
        }
    }
});

deleteConfirmButton.addEventListener('click', function () {
    const index = deleteConfirmButton.getAttribute('data-index');
    deleteArea(index);
});

// 초기 렌더링
renderAreas();

let date = new Date();

let yearEl = document.querySelector("#year-select");
let yearOpt = `<option value="">매매년도선택</option>`;
let year = date.getFullYear();
for (let i = year; i > year - 20; i--) {
    yearOpt += `<option value="${i}">${i}년</option>`;
}
yearEl.innerHTML = yearOpt;

document.querySelector("#year-select").addEventListener("change", function () {
    let month = date.getMonth() + 1;
    let monthEl = document.querySelector("#month-select");
    let monthOpt = `<option value="">매매월선택</option>`;
    let yearSel = document.querySelector("#year-select");
    let m = yearSel[yearSel.selectedIndex].value == date.getFullYear() ? month : 13;
    for (let i = 1; i < m; i++) {
        monthOpt += `<option value="${i < 10 ? "0" + i : i}">${i}월</option>`;
    }
    monthEl.innerHTML = monthOpt;
});

document.querySelector("#sido-select").addEventListener("change", function () {
    if (this[this.selectedIndex].value) {
        let regcode = this[this.selectedIndex].value.substr(0, 2) + "*00000";
        sendRequest("gugun-select", regcode);
    } else {
        initOption("gugun-select");
        initOption("dong-select");
    }
});

document.querySelector("#gugun-select").addEventListener("change", function () {
    if (this[this.selectedIndex].value) {
        let regcode = this[this.selectedIndex].value.substr(0, 5) + "*";
        sendRequest("dong-select", regcode);
    } else {
        initOption("dong-select");
    }
});

function sendRequest(selid, regcode) {
    const url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes";
    let params = "regcode_pattern=" + regcode + "&is_ignore_zero=true";
    fetch(`${url}?${params}`)
        .then((response) => response.json())
        .then((data) => addOption(selid, data));
}

function addOption(selid, data) {
    let opt = ``;
    initOption(selid);
    switch (selid) {
        case "sido-select":
            opt += `<option value="">시도선택</option>`;
            data.regcodes.forEach(function (regcode) {
                opt += `
                  <option value="${regcode.code}">${regcode.name}</option>
                  `;
            });
            break;
        case "gugun-select":
            opt += `<option value="">구군선택</option>`;
            for (let i = 0; i < data.regcodes.length; i++) {
                if (i != data.regcodes.length - 1) {
                    if (
                        data.regcodes[i].name.split(" ")[1] == data.regcodes[i + 1].name.split(" ")[1] &&
                        data.regcodes[i].name.split(" ").length !=
                        data.regcodes[i + 1].name.split(" ").length
                    ) {
                        data.regcodes.splice(i, 1);
                        i--;
                    }
                }
            }
            let name = "";
            data.regcodes.forEach(function (regcode) {
                if (regcode.name.split(" ").length == 2) name = regcode.name.split(" ")[1];
                else name = regcode.name.split(" ")[1] + " " + regcode.name.split(" ")[2];
                opt += `
                  <option value="${regcode.code}">${name}</option>
                  `;
            });
            break;
        case "dong-select":
            opt += `<option value="">동선택</option>`;
            let idx = 2;
            data.regcodes.forEach(function (regcode) {
                if (regcode.name.split(" ").length != 3) idx = 3;
                opt += `
                  <option value="${regcode.code}">${regcode.name.split(" ")[idx]}</option>
                  `;
            });
    }
    document.querySelector(`#${selid}`).innerHTML = opt;
}

function initOption(selid) {
    let options = document.querySelector(`#${selid}`);
    options.length = 0;
}

///////////////////////// 아파트 매매 정보 /////////////////////////
document.querySelector("#selection-search-btn").addEventListener("click", function () {
    let gugun = document.querySelector("#gugun-select");

    let url = "https://apis.data.go.kr/1613000/RTMSDataSvcAptTradeDev/getRTMSDataSvcAptTradeDev";
    let regCode = gugun[gugun.selectedIndex].value.substr(0, 5);
    let yearSel = document.querySelector("#year-select");
    let year = yearSel[yearSel.selectedIndex].value;
    let monthSel = document.querySelector("#month-select");
    let month = monthSel[monthSel.selectedIndex].value;
    let dealYM = year + month;
    let queryParams = encodeURIComponent("serviceKey") + "=" + serviceKey; /*Service Key*/
    queryParams +=
        "&" +
        encodeURIComponent("LAWD_CD") +
        "=" +
        encodeURIComponent(regCode); /*아파트소재 구군*/
    queryParams +=
        "&" + encodeURIComponent("DEAL_YMD") + "=" + encodeURIComponent(dealYM); /*조회년월*/
    queryParams +=
        "&" + encodeURIComponent("pageNo") + "=" + encodeURIComponent("1"); /*페이지번호*/
    queryParams +=
        "&" + encodeURIComponent("numOfRows") + "=" + encodeURIComponent("30"); /*페이지당건수*/

    fetch(`${url}?${queryParams}`)
        .then((response) => response.text())
        .then((data) => makeList(data));
    
        document.getElementById("areaAddBtn").setAttribute("style", "display: inline-block");
});

function makeList(data) {
    document.querySelector("table").setAttribute("style", "display: ;");
    let tbody = document.querySelector("#aptlist");
    let parser = new DOMParser();
    const xml = parser.parseFromString(data, "application/xml");
    initTable();
    let apts = xml.querySelectorAll("item");
    apts.forEach((apt) => {
        let tr = document.createElement("tr");

        let nameTd = document.createElement("td");
        nameTd.appendChild(document.createTextNode(apt.querySelector("aptNm").textContent));
        tr.appendChild(nameTd);

        let floorTd = document.createElement("td");
        floorTd.appendChild(document.createTextNode(apt.querySelector("floor").textContent));
        tr.appendChild(floorTd);

        let areaTd = document.createElement("td");
        areaTd.appendChild(document.createTextNode(apt.querySelector("excluUseAr").textContent));
        tr.appendChild(areaTd);

        let dongTd = document.createElement("td");
        dongTd.appendChild(document.createTextNode(apt.querySelector("umdNm").textContent));
        tr.appendChild(dongTd);

        let priceTd = document.createElement("td");
        priceTd.appendChild(
            document.createTextNode(getEok(apt.querySelector("dealAmount").textContent) + "억원")
        );
        priceTd.classList.add("text-end");
        tr.appendChild(priceTd);

        let address =
            apt.querySelector("umdNm").textContent +
            " " +
            apt.querySelector("jibun").textContent;
        tr.addEventListener("click", function () {
            showApt(apt.querySelector("aptNm").textContent, apt.querySelector("floor").textContent, apt.querySelector("excluUseAr").textContent, apt.querySelector("umdNm").textContent, getEok(apt.querySelector("dealAmount").textContent));
            viewMap(apt.querySelector("aptNm").textContent, getEok(apt.querySelector("dealAmount").textContent), address);
        });

        tbody.appendChild(tr);
    });
}

function showApt(name, floor, area, dong, price) {
    document.getElementsByClassName("nav-item")[2].setAttribute("style", "display: block");
    let tbody = document.querySelector("#aptinfolist");
    tbody.innerHTML = "";
    let tr = document.createElement("tr");

    let nameTd = document.createElement("td");
    nameTd.appendChild(document.createTextNode(name));
    tr.appendChild(nameTd);

    let floorTd = document.createElement("td");
    floorTd.appendChild(document.createTextNode(floor));
    tr.appendChild(floorTd);

    let areaTd = document.createElement("td");
    areaTd.appendChild(document.createTextNode(area));
    tr.appendChild(areaTd);

    let dongTd = document.createElement("td");
    dongTd.appendChild(document.createTextNode(dong));
    tr.appendChild(dongTd);

    let priceTd = document.createElement("td");
    priceTd.appendChild(
        document.createTextNode(price + "억원")
    );
    priceTd.classList.add("text-end");
    tr.appendChild(priceTd);

    tbody.appendChild(tr);
    // document.getElementsByClassName("tab-pane")[2].setAttribute("class", "show active");
}

var marker, infowindow;
function viewMap(apt, dealAmount, address) {
    if (marker != null && infowindow != null) {
        marker.setMap(null);
        infowindow.close();
    }
    // 주소-좌표 변환 객체를 생성합니다
    var geocoder = new kakao.maps.services.Geocoder();

    // 주소로 좌표를 검색합니다
    geocoder.addressSearch(address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 결과값으로 받은 위치를 마커로 표시합니다
            marker = new kakao.maps.Marker({
                map: map,
                position: coords,
            });

            // 인포윈도우로 장소에 대한 설명을 표시합니다
            infowindow = new kakao.maps.InfoWindow({
                content: `<div style="width:150px;text-align:center;padding:6px 0;">${apt}, ${dealAmount}억원</div>`,
            });
            infowindow.open(map, marker);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);
        }
    });
}

function initTable() {
    let tbody = document.querySelector("#aptlist");
    let len = tbody.rows.length;
    for (let i = len - 1; i >= 0; i--) {
        tbody.deleteRow(i);
    }
}

function getEok(price) {
    var newprice = parseFloat(price.replace(",","")) / 10000;
    return newprice;
}

function showHospital() {
    // 택시 정류장을 검색하기 위한 장소 검색 객체를 생성합니다
    var ps = new kakao.maps.services.Places();
    
    // 현재 지도의 중심좌표를 기준으로 반경 1km 이내의 병원 검색
    ps.keywordSearch('병원', placesSearchCB, {location: map.getCenter(), radius: 1000});
    
    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            // 병원 위치에 마커와 인포윈도우를 표시합니다
            for (var i = 0; i < data.length; i++) {
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: new kakao.maps.LatLng(data[i].y, data[i].x)
                });

                // 인포윈도우에 표시할 내용
                var infoWindowContent = `<div style="width:150px;text-align:center;padding:6px 0;">${data[i].place_name}</div>`;

                // 인포윈도우 생성
                var infowindow = new kakao.maps.InfoWindow({
                    content: infoWindowContent
                });

                // 마커 위에 인포윈도우 표시 (항상 열려 있음)
                infowindow.open(map, marker);
            }
        }
    }
}


function showSubways() {
    // 지하철 정보를 검색하기 위한 장소 검색 객체를 생성합니다
    var ps = new kakao.maps.services.Places();
    
    // 현재 지도의 중심좌표를 기준으로 반경 1km 이내의 지하철역 검색
    ps.keywordSearch('지하철역', placesSearchCB, {location: map.getCenter(), radius: 1000});
    
    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            // 지하철역 위치에 마커와 인포윈도우를 표시합니다
            for (var i = 0; i < data.length; i++) {
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: new kakao.maps.LatLng(data[i].y, data[i].x)
                });

                // 인포윈도우에 표시할 내용
                var infoWindowContent = `<div style="width:150px;text-align:center;padding:6px 0;">${data[i].place_name}</div>`;

                // 인포윈도우 생성
                var infowindow = new kakao.maps.InfoWindow({
                    content: infoWindowContent
                });

                // 마커 위에 인포윈도우 표시 (항상 열려 있음)
                infowindow.open(map, marker);
            }
        }
    }
}
