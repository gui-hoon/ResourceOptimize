<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Resource Optimizer</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="../assets/cloud.ico" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="../css/styles.css" rel="stylesheet" />
        <style>
			table {
		    	width: 100%;
		    	border: 1px solid #444444;
		  	}
		  	th {
    			border: 1px solid #444444;
    			padding: 3px;
    			text-align: center;
    			position: sticky;
			    top: 0px;
			    background-color: skyblue;
    		}
	   		td {
	   			border: 1px solid #444444;
	   			padding: 3px;
	   			text-align: right;
	   		}
		</style>
    </head>
    <body>
        <div class="d-flex" id="wrapper">
            <!-- Sidebar-->
            <div class="border-end bg-white" id="sidebar-wrapper">
                <div class="sidebar-heading border-bottom bg-light"><a class="nav-link" href="../../home">
                	ResourceOptimizer</a></div>
                <div class="list-group list-group-flush">
                	<a class="list-group-item list-group-item-action list-group-item-light p-3" href="admin_member">회원 관리</a>
                    <a class="list-group-item list-group-item-action list-group-item-light p-3" href="admin_aws">AWS Config</a>
                    <a class="list-group-item list-group-item-action list-group-item-light p-3" href="admin_dyna">Dynatrace Config</a>
                    <a class="list-group-item list-group-item-action list-group-item-light p-3" href="admin_OnDemand">On-Demand Pricing</a>
                	<a class="list-group-item list-group-item-action list-group-item-light p-3" href="admin_log">LOG</a>
                </div>
            </div>
            <!-- Page content wrapper-->
            <div id="page-content-wrapper">
                <!-- Top navigation-->
                <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
                    <div class="container-fluid">
                        <button class="btn btn-primary" id="sidebarToggle">Menu</button>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav ms-auto mt-2 mt-lg-0">
                                <li class="nav-item active">
	                				<c:choose>
	                					<c:when test="${not empty member}">
	                                		<a class="nav-link" href="../../logout">로그아웃</a>
	                               		</c:when>
	                               		<c:otherwise>
	                                		<a class="nav-link" href="../../login">로그인</a>
	                               		</c:otherwise>
	                               	</c:choose>
                             	</li>
                             	<li class="nav-item active">
                             		<c:choose>
                             			<c:when test="${member.userAuth eq 'ROLE_ADMIN' }">
                             				<a class="nav-link" href="admin_home">Admin</a>
                             			</c:when>
                             		</c:choose>
                             	</li>
                            </ul>
                        </div>
                    </div>
                </nav>
                <!-- Page content-->
                <div class="container-fluid">
                	<c:if test="${awsDuplicateFlag eq 1}">
                		<script>
					    	alert("${message}");
					    </script>
               		</c:if>
                    <h1 class="mt-4">Managing AWS IAM Users</h1> <br> <br> <br> <br>
                    <div style="text-align: center;">
					    <h2>AWS</h2>
					    <table style="width: 80%; margin: auto">
					        <tr>
					            <th style="width: 10%">번호</th>
					            <th style="width: 20%">accountID</th>
					            <th style="width: 25%">accessKey</th>
					            <th style="width: 35%">secretKey</th>
					            <th style="width: 10%">region</th>
					        </tr>
					        <c:forEach var="aws" items="${awsConfigKeyList}">
					            <tr>
					                <td>${aws.num}</td>
					                <td><a href="getAwsKey?num=${aws.num}&accountID=${aws.accountID}">${aws.accountID}</a></td>
					                <td>${aws.accessKey}</td>
					                <td>${aws.secretKey}</td>
					                <td>${aws.region}</td>
					            </tr>
					        </c:forEach>
					    </table>
					    <br>
						<input type="button" value="계정 등록" onclick="location.href='insertAwsKeyView'">
					</div>
                </div>
            </div>
        </div>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="../js/scripts.js"></script>
    </body>
</html>