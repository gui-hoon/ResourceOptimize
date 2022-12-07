<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!--회원가입 페이지-->
<!--signup.html-->
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
<form method="post" action="/signup">
    <div class="container">
        <h1>회원가입</h1>
        <div class="form-group">
            <label for="userName">userName</label>
            <input type="text" class="form-control" id="userName" name="userName" placeholder="사용자 이름">
        </div>
        
        <div class="form-group">
            <label for="userId">userId</label>
            <input type="text" class="form-control" id="userId" name="userId" placeholder="사용자 아이디">
        </div>
        
        <div class="form-group">
            <label for="userPw">password</label>
            <input type="password" class="form-control" id="userPw" name="userPw" placeholder="사용자 비밀번호">
        </div>
        <div class="form-group">
        <label for="userAuth">authorization</label>
        	<div>
		        <label><input type="radio" name="userAuth" value="ROLE_USER" checked>USER</label>
		        <label><input type="radio" name="userAuth" value="ROLE_ADMIN">ADMIN</label>
	        </div>
        </div>
        <button type="submit" class="btn btn-primary">가입 완료</button>
    </div>
</form>
</body>
</html>