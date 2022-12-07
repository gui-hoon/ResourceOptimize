<!-- users/login.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--로그인 페이지-->
<!-- login.html-->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="icon" type="image/x-icon" href="assets/cloud.ico" />
	<title>Resource Optimizer</title>
</head>
<body>
<div class="container">
    <h1>로그인</h1> <br>
    <form action="/login_proc" method="post">
        <div class="form-group">
            <label for="username">아이디</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="아이디를 입력해주세요">
        </div>
        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호를 입력해주세요">
        </div>
        <button type="submit" class="btn btn-primary">로그인</button>
        <button type="button" class="btn btn-primary" onClick="location.href='signup'">회원 가입</button>
    </form>
    <br/>
</div>
</body>
</html>