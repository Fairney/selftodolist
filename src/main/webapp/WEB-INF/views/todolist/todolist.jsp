<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var = "todolistToday" value = "${todolist.today}"/>
<c:set var = "todolistWeek" value = "${todolist.week}"/>
<script>
  const contextPath = "${contextPath}";
  console.log("${todolist}");
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel = "stylesheet" href = "${contextPath}/resources/css/todolist/todolist.css">
    <title>Self TodoList</title>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <table class="Calendar">
        <thead>
            <tr>
                <td onClick="prevCalendar();" style="cursor:pointer;">&#60;</td>
                <td colspan="5">
                    <span id="calYear"></span>년
                    <span id="calMonth"></span>월
                </td>
                <td onClick="nextCalendar();" style="cursor:pointer;">&#62;</td>
            </tr>
            <tr>
                <td>일</td>
                <td>월</td>
                <td>화</td>
                <td>수</td>
                <td>목</td>
                <td>금</td>
                <td>토</td>
            </tr>
        </thead>

        <tbody>
        </tbody>
    </table>
    <main class = "main-style">
        <section class = "main-div">
            <div>
                <div class = "plan-title">
                    <span class ="plan-title-text">Today</span>
                </div>
                <div class = "plan-mainTextDiv">
                    <!--todolist-->
                    <ul style = "width:92%;" id ="todayList">
                        
                            <c:choose>
                                <c:when test ="${empty todolistToday}">
                                    <div style = "display: flex; justify-content: center; align-items: center; padding-right: 40px;">
                                        <span> No List</span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var = "today" items = "${todolistToday}">
                                        <div class ="plan-mainText">
                                            <div style = "display: flex; align-items: end;"><input type = "checkbox" name = "todayContent" value = ${today.todolistContent}></div>
                                            <div class ="plan-main"><li>${today.todolistContent}</li></div>
                                            <div class = "btn-Field"><button onclick = deleteCheckContent();>삭제하기</button></div>
                                        </div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        
                    </ul>
                    <div class="addBtn-Field">
                        <button id = "btn-register">등록하기</button>
                        <button onclick = "deleteTodayCheckContent()">삭제하기</button>
                    </div>
                </div>
            </div>
            <div>
                <div class = "plan-title">
                    <span class ="plan-title-text">Week</span>
                </div>
                <div class = "plan-mainTextDiv">
                    <!--todolist-->
                    <ul style = "width:92%;" id="weekList">
                        
                            <c:choose>
                                <c:when test ="${empty todolistWeek}">
                                    <div class = "no-list">
                                        <span> No List</span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var = "week" items = "${todolistWeek}">
                                        <div class ="plan-mainText">
                                            <div style ="display:flex; align-items: end;"><input type = "checkbox" name = "checkContent" value = ${week.todolistContent}></div>
                                            <div class ="plan-main"><li>${week.todolistContent}</li></div>
                                            <div class = "btn-Field"><button>삭제하기</button></div>
                                        </div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        
                    </ul>
                    <div class="addBtn-Field">
                        <button id = "btn-weekregister">등록하기</button>
                        <button onclick = "deleteCheckContent()">삭제하기</button>
                    </div>
                </div>
            </div>
            
        </section>
    </main>
    <!-- 쪽지보내기 -->
<div id="popup" class="popup-overlay">
    <div class="popup-content">
        <h4>| TodayList<i class="fa-solid fa-envelope"></i></h4>
        <form action="${contextPath}/todolist/todayRegister" method = "post" onsubmit ="return letterValidate()">
                <div class="popup-table">
                    <table style = "width:100%;">
                        <tr>
                            <td>할 일 추가</td>
                            <td><input type="text" id="writerName" name="todolistContent"
                                maxlength="30" autocomplete="off"
                                required value = ${member.memberNick} readonly></td>
                        </tr>
                        <!-- 여기서는  session에 등록된 로그인 계정의 닉네임. -->
                        
                
                </table>
                <input class = "input-hidden" id = "todayDate" name = "choicedate">
                <input class = "input-hidden" id = "todayYear"  name = "choiceyear">
                <input class = "input-hidden" id = "todayMonth"  name = "choicemonth">
                </div>
                <div class="popupBtn-wrap">
                    <button class="letter-btn" id="Send" type="submit">전송하기</button>
                    <button class="letter-btn" type = "button" onclick="closePopup()">취소</button>
                </div>

                <input name = "receiverId" style ="display:none" value = ${member.memberId}>
                <c:if test = "${!empty loginMember}">   
                    <input name = "senderId" style ="display:none" value = ${loginMember.memberId}>
                </c:if>
        </form>
    </div>
</div>
<!-- 주간 목표 설정 -->
<div id="introPopup" class="popup-overlay">
    <div class="popup-content">
        
        <h4>| WeekList</h4>
        <form action="${contextPath}/todolist/weekRegister" method = "post" onsubmit ="return writerValidate()">
                <div class="popup-table">
                    <table style = "width:100%;">
                        <tr>
                            <td>할 일 추가</td>
                            <td><input type="text" id="writerName" name="todolistContent"
                                maxlength="30" autocomplete="off"
                                required></td>
                        </tr>
                        <!-- 보낼 내용 -->
                       
                </div>
                </table>
                <input class = "input-hidden" id = "weekDate" name = "date">
                <input class = "input-hidden" id = "weekYear"  name = "year">
                <input class = "input-hidden" id = "weekMonth"  name = "month">
                <div class="popupBtn-wrap">
                    <button class="letter-btn" type="submit">작성하기</button>
                    <button class="letter-btn" type = "button" onclick="closePopup2()">취소</button>
                </div>
            </form>
    </div>
</div>
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script src ="${contextPath}/resources/js/todolist/todolist.js"></script>
<script>
    $("#btn-register").click(function(){
        openPopup();
    });
    $("#btn-weekregister").click(function(){
        openPopup2();
    });
</script>
</html>