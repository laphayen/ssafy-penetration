// user.js

function fillLoginForm() {
    // 로그인 모달 폼에 ssafy@ssafy.com과 1234를 미리 입력
    document.querySelector("#email").value = "ssafy@ssafy.com";
    document.querySelector("#password").value = "1234";
}

function fillSignupForm() {
    // 회원가입 모달 폼에 이름, 이메일, 비밀번호를 미리 입력
    document.querySelector("#signup-name").value = "싸피";
    document.querySelector("#signup-email").value = "ssafy@ssafy.com";
    document.querySelector("#signup-password").value = "1234";
    document.querySelector("#signup-confirm-password").value = "1234";
}

function openEditProfileModal() {
    // 회원 정보 수정 모달을 열 때 기존 정보를 미리 채워줍니다.
    document.querySelector("#edit-email").value = "ssafy@ssafy.com";
    document.querySelector("#edit-password").value = "1234";
    document.querySelector("#edit-confirm-password").value = "1234";
    $('#editProfileModal').modal('show');
}

function login() {
    // 로그인 폼의 값 가져오기
    var userid = document.querySelector("#email").value;
    var userpass = document.querySelector("#password").value;

    if (userid === "ssafy@ssafy.com" && userpass === "1234") {
        // Remove alert and replace with a console log or visual indication
        console.log("로그인 성공!!!");

        // id가 profile_img인 element의 src 속성의 값을 img/profile.png로 설정.
        document.querySelector("#profile_img").src = "img/profile.png";
        document.querySelector("#header_nav_confirm_on").style.display = "block";

        // 로그인/회원가입 버튼 숨기기
        document.querySelector("#login-btn").style.display = "none";
        document.querySelector("#signup-btn").style.display = "none";

        // 회원 정보 수정/로그아웃 버튼 표시
        document.querySelector("#profile-btn").style.display = "block";
        document.querySelector("#logout-btn").style.display = "block";

        // 로그인 모달 닫기
        $('#loginModal').modal('hide');
    } else {
        alert("아이디 또는 비밀번호 확인!!!");
    }
}

// Ensure the profile button opens the edit modal
document.querySelector("#profile-btn").addEventListener("click", openEditProfileModal);

function logout() {
    // 로그아웃 처리
    console.log("로그아웃 되었습니다.");

    // 로그인/회원가입 버튼 다시 표시
    document.querySelector("#login-btn").style.display = "block";
    document.querySelector("#signup-btn").style.display = "block";

    // 회원 정보 수정/로그아웃 버튼 숨기기
    document.querySelector("#profile-btn").style.display = "none";
    document.querySelector("#logout-btn").style.display = "none";

    // 프로필 이미지 숨기기 및 네비게이션 상태 초기화
    document.querySelector("#profile_img").src = "";
    document.querySelector("#header_nav_confirm_on").style.display = "none";
}

function updateProfile() {
    // 회원 정보 수정 처리
    var email = document.querySelector("#edit-email").value;
    var password = document.querySelector("#edit-password").value;
    var confirmPassword = document.querySelector("#edit-confirm-password").value;

    if (password !== confirmPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    alert("회원 정보가 수정되었습니다.");

    // 모달 닫기
    $('#editProfileModal').modal('hide');
}

function deleteAccount() {
    if (confirm("정말로 회원 탈퇴를 하시겠습니까?")) {
        alert("회원 탈퇴가 완료되었습니다.");
        logout();
        $('#editProfileModal').modal('hide');
    }
}

function findPassword() {
    var name = document.querySelector("#find-name").value;
    var email = document.querySelector("#find-email").value;

    if (name && email) {
        alert("임시 비밀번호를 발급했습니다.");
        $('#findPasswordModal').modal('hide');
    } else {
        alert("이름과 이메일을 입력해주세요.");
    }
}

function findId() {
    alert("아이디 찾기 기능이 아직 구현되지 않았습니다.");
    // 여기서 실제 아이디 찾기 로직을 추가할 수 있습니다.
}
