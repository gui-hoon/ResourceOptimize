<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
    </head>
    <body>
        <div class="d-flex" id="wrapper">
            <!-- Sidebar-->
            <div class="border-end bg-white" id="sidebar-wrapper">
                <div class="sidebar-heading border-bottom bg-light"><a class="nav-link" href="../home">
                	ResourceOptimizer</a></div>
                <div class="list-group list-group-flush">
                    <a class="list-group-item list-group-item-action list-group-item-light p-3" href="/home/accountID">AWS</a>
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
	                                		<a class="nav-link" href="../logout">로그아웃</a>
	                               		</c:when>
	                               		<c:otherwise>
	                                		<a class="nav-link" href="../login">로그인</a>
	                               		</c:otherwise>
	                               	</c:choose>
                             	</li>
                             	<li class="nav-item active">
                             		<c:choose>
                             			<c:when test="${member.userAuth eq 'ROLE_ADMIN'}">
                             				<a class="nav-link" href="../admin/admin_home">Admin</a>
                             			</c:when>
                             		</c:choose>
                             	</li>
                            </ul>
                        </div>
                    </div>
                </nav>
                
                <!-- Page content-->
                <div class="container-fluid">
                	<div>
                		<a href="/home/accountID" style="color: blue; text-decoration:none">home</a> &nbsp
              		</div>
                    <div><h1 class="mt-4">AWS Overview</h1></div> <br>
                    <div>
                    	<input type="button" value="MonthlyRate" onClick="location.href='/home/accountID/monthlyRate'">
                   	</div> <br>
                   	
                    <div>
	                    <form action="/home/accountID" method="POST" style="display: flex; justify-content: flex-start; padding: 10px 0;">
						    <input type="date" name="startDay" id="start" value="${startDay}">
						    ~
						    <input type="date" name="endDay" id="end" value="${endDay}"> &nbsp
						    <input type="submit" value="조회">
						</form> <br>
						<c:if test="${userTotalCost ne 0}">
							<table style="width:20%; float:left;">
								<th>Total Usage Cost</th><td>${userTotalCost} $</td>
						    </table> <br>
						</c:if>
				    </div> <br> <br>
					<!-- Account table -->	
					<c:forEach items="${accountCountMap}" var="m" varStatus="status">
						<div style="width:30%; height:200px; float:left; margin-right:30px;">
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>AccountID</th><th>Total Host</th><th>Problem</th><th>Instance Type</th><th>Usage Cost</th>
		                   			</tr>
	                  			</thead>
	                  			<tbody>
			                   		<tr>
			                    		<td><a href="/home/accountID/${m.key}" style="color: blue; text-decoration:none">${m.key}</a></td>
			                    		<c:forEach items="${m.value}" var="cm" varStatus="status">
				                    		<td>${cm.key}</td>
			                    			<td><a href="/home/accountID/${m.key}/problem" style="color: red; text-decoration:none">${cm.value}</a></td>
		                    			</c:forEach>
		                    			<td><a href="/home/accountID/${m.key}/problem/recommend" style="color: blue; text-decoration:none">status</a></td>
			                   			<td>${accountUsageCostMap[m.key]} $</td>
			                   		</tr>	       
	                    		</tbody>
		                   	</table>
	               		</div>
					</c:forEach>
                </div>
            </div>
        </div>
        
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="../js/scripts.js"></script>
    </body>
</html>