// common.js 또는 상단에 전역 유틸로 정의

// 로그인 여부 확인
window.addEventListener("DOMContentLoaded", function () {
    const loginBtn = document.getElementById("loginBtn");
    const logoutBtn = document.getElementById("logoutBtn");

    let userNameSpan = document.createElement('span');
    userNameSpan.id = "userNameDisplay";
    userNameSpan.style.marginRight = "10px";
    logoutBtn.parentNode.insertBefore(userNameSpan, logoutBtn);

    fetch("/me", {
        method: "GET",
        credentials: "include"
    }).then(response => {
        if (response.status === 200) {
            return response.text().then(username => {
                userNameSpan.textContent = `${username} 님 환영합니다.`;
                loginBtn.style.display = "none";
                logoutBtn.style.display = "inline-block";
            });
        } else if (response.status === 403) {
            // 인증 실패 시 (로그인 안됨)
            userNameSpan.textContent = "";
            loginBtn.style.display = "inline-block";
            logoutBtn.style.display = "none";
        } else {
            throw new Error(`Unexpected status: ${response.status}`);
        }
    }).catch(error => {
        console.error("Error fetching /me:", error);
        userNameSpan.textContent = "";
        loginBtn.style.display = "inline-block";
        logoutBtn.style.display = "none";
    });
});

// url 내 쿼리 파라미터 가져오기
function getQueryParam(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// 로그아웃 처리
function logout() {
    fetch("/logout", {
        method: "POST",
        credentials: "include"
    }).then(response => {
        window.location.href = "/login";
    }).catch(error => {
        console.error("로그아웃 오류:", error);
        location.reload();
    });
}

function formatDate(isoString) {
  if (!isoString) return '';
  const date = new Date(isoString);
  const yyyy = date.getFullYear();
  const mm = String(date.getMonth() + 1).padStart(2, '0');
  const dd = String(date.getDate()).padStart(2, '0');
  const hh = String(date.getHours()).padStart(2, '0');
  const mi = String(date.getMinutes()).padStart(2, '0');
  return `${yyyy}.${mm}.${dd} ${hh}:${mi}`;
}
