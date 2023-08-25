<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var = "loginMember" value = "${loginMember}"/>
<script>
    alert("${loginMemberId}");
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${contextPath}/resources/css/common/header.css">
    <title>Document</title>
</head>
<body>
    <header>
        <section>
            <div class = "header_style">
                <div>
                    <span class = "mainTitle">Self TodoList</span>
                </div>
                <div></div>
                <c:choose>
                    <c:when test ="${empty loginMember}">
                        <div><button style = "cursor:pointer;" class ="login_Btn" onclick = "location.href = '${contextPath}/main'">login</button></div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <div><span>${loginMember.memberId}</span></div>
                        </div>
                    </c:otherwise>
                </c:choose>
                
            </div>
        </section>
    </header>
</body>
</html>
