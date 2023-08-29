<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${ !empty message }">
    <script>
        alert("${message}");
        // EL 작성 시 scope를 지정하지 않으면
        // page -> request -> session -> application 순서로 검색하여
        // 일치하는 속성이 있으면 출력
    </script>
</c:if> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>TodoList</title>
<link rel = "stylesheet" text="text/css" href = "${contextPath}/resources/css/common/main.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <main class = "main-style">
        <section style = "height:50px;"></section>
        <section>
            <div class = "flexPage">
                <div class = "formDiv">
                    <div style = "display:flex; justify-content: center; margin-bottom:30px;">
                        <span class = "todolist_Title">Self TodoList</span>
                    </div>
                    <form method="post" action = "${contextPath}/member/login" name="login">
                        <fieldset id="id-pw_area">
                            <div class = "id-pw_div">
                                <div>아이디 : </div>
                            <input type="text" name="memberId" id="inputEmail"
                                placeholder="아이디(이메일)" value="${cookie.saveId.value}">
                            </div>
                            <div class = "id-pw_div" style = "margin-top:20px">
                                <div>비밀번호 : </div>
                            <input type="password"
                                name="memberPw" id="inputPw" placeholder="비밀번호">
                            </div>
                        </fieldset>
                        <div class="Btn_area">
                            <button id="loginBtn">Login</button>
                        </div>
                        <div class="check_area">
                            <p style = "padding-right:10px;"><input class="form-check-input" type="checkbox" id="saveId" name="saveId" ${saveId != null ? 'checked="checked"' : ''}> 아이디저장</p>
                                <p></label> <a href="${contextPath}/member/searchIdPw">아이디/비밀번호찾기</a></p>
                        </div>
                        
                    
                    <div class="snsLogin_area">
                        <h3>SNS 계정으로 로그인/회원가입</h3>
                        <div class="socialLoginDiv" >
                            <button class="kakaoBtn" style = "cursor:pointer;" id="kakao_id_login" onclick="redirectToKakao()" type="button">
                            <svg width="18" height="17" viewBox="0 0 18 17" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M9 0C4.0294 0 0 3.09858 0 6.92081C0 9.39196 1.68456 11.5603 4.21858 12.7847C4.08072 13.2484 3.33268 15.7676 3.30291 15.9656C3.30291 15.9656 3.285 16.1144 3.38374 16.1711C3.48248 16.2277 3.59862 16.1837 3.59862 16.1837C3.88177 16.1452 6.88214 14.0897 7.40137 13.7327C7.92017 13.8044 8.45446 13.8416 9 13.8416C13.9706 13.8416 18 10.7431 18 6.92081C18 3.09858 13.9706 0 9 0Z" fill="black"></path></svg><p>빠른시작</p>
                            </button>
                            <button class="round" style = "visibility: hidden;"id="naver_id_login" type="button" onclick="redirectToNaver()">
                <img src="${contextPath}/resources/img/member/login/naver_logo.png" width="50px"; >
                            </button>
    
    
                                <button class="round" style = "cursor:pointer;" id="google_id_login" type="button" onclick="window.location.href='${contextPath}/member/getGoogleAuthUrl'">
                                    <img src="${contextPath}/resources/img/member/login/google_logo.png" width="50px" style="border: 1px solid gray; border-radius: 50%;">
    
                            </button>
                        
                    </div>
                    </div>
                    <div class="sign_area">
                        <button type="button" onclick="location.href='${contextPath}/member/signUp'" id="signUp-btn">일반 회원가입</button>
    
                    </div>

                    </form>
                </div>
            </div>
        </section>
    </main>
    <footer></footer>
</body> 
</html>