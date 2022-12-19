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
        <link rel="icon" type="image/x-icon" href="../../../../assets/cloud.ico" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="../../../../css/styles.css" rel="stylesheet" />
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
                <div class="sidebar-heading border-bottom bg-light"><a class="nav-link" href="../../../../home">
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
	                                		<a class="nav-link" href="../../../../logout">로그아웃</a>
	                               		</c:when>
	                               		<c:otherwise>
	                                		<a class="nav-link" href="../../../../login">로그인</a>
	                               		</c:otherwise>
	                               	</c:choose>
                             	</li>
                             	<li class="nav-item active">
                             		<c:choose>
                             			<c:when test="${member.userAuth eq 'ROLE_ADMIN'}">
                             				<a class="nav-link" href="../../../../admin/admin_home">Admin</a>
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
                		<a href="/home/accountID" style="color: blue; text-decoration:none">home</a> &nbsp>
                		<a href="/home/accountID/${accountID}" style="color: blue; text-decoration:none">${accountID}</a> &nbsp>
                		<a href="/home/accountID/${accountID}/${instanceID}" style="color: blue; text-decoration:none">${instanceID}</a> &nbsp>
                		<a href="" style="color: blue; text-decoration:none">detail</a>
               	</div>
               	
                <div><h1 class="mt-4">${instanceID}</h1></div> <br>
                <div>
                    <table>
                   		<thead>
                    		<tr>
                    			<th>Instance-ID</th><th>Image-ID</th><th>Public IPv4 DNS</th><th>IP DNS Name</th>
                    			<th>Type</th><th>Architecture</th><th>Platform</th><th>Region</th>
                   			</tr>
                 			</thead>
                 			<tbody>
	                   		<tr>
	                    		<td>${aws.instanceID}</td>
	                    		<td>${aws.imageID}</td>
	                    		<td>${aws.publicDns}</td>
	                    		<td>${aws.privateDns}</td>
	                    		<td>${aws.instanceType}</td>
	                    		<td>${aws.architecture}</td>
	                    		<td>${aws.platform}</td>
	                    		<td>${aws.region}</td>
	                   		</tr>	       
                   		</tbody>
                   	</table> <br>
                </div>
               		
             	<form action="/home/accountID/${accountID}/${instanceID}/detail" method="POST" style="display: flex; justify-content: flex-start; padding: 10px 0;">
				    <input type="date" name="startDay" id="start" value="${startDay}">
				    ~
				    <input type="date" name="endDay" id="end" value="${endDay}"> &nbsp
				    
				    <input type="submit" value="조회"> &nbsp
				
				    <select id="resource" name="resource">
						<option value="none">===== 선택 =====</option>
						<option value="Cpu"<c:if test="${selectedResource eq 'Cpu'}">selected</c:if>>Cpu</option>
						<option value="Disk"<c:if test="${selectedResource eq 'Disk'}">selected</c:if>>Disk</option>
						<option value="Network"<c:if test="${selectedResource eq 'Network'}">selected</c:if>>Network</option>
						<option value="Memory"<c:if test="${selectedResource eq 'Memory'}">selected</c:if>>Memory</option>
					</select> &nbsp
					<select id="statistic" name="statistic">
						<option value="none">===== 선택 =====</option>
						<option value="Average"<c:if test="${selectedStatistic eq 'Average'}">selected</c:if>>Average</option>
						<option value="Minimum"<c:if test="${selectedStatistic eq 'Minimum'}">selected</c:if>>Minimum</option>
						<option value="Maximum"<c:if test="${selectedStatistic eq 'Maximum'}">selected</c:if>>Maximum</option>
					</select> &nbsp
				    <input type="submit" value="선택">
				</form>
				
				<!-- Detail table -->
				<c:choose>
				<c:when test="${selectedResource eq 'Disk'}">
					<c:forEach items="${awsDailyDiskMap}" var="ddm" varStatus="status">
					<h2 class="mt-4">${ddm.key}</h2>
						<c:forEach items="${ddm.value}" var="ddm_val" varStatus="status">
							<div style="width:30%; height:250px; float:left; margin-right:30px; overflow:auto">
			                    <table>
			                   		<thead>
			                    		<tr>
			                    			<th>Resource</th><th>Date Time</th><th>val</th>
			                   			</tr>
		                  			</thead>
		                  			<tbody>
		                  			<c:forEach items="${ddm_val.value}" var="val" varStatus="status">
				                   		<tr>
				                   			<td>${val.resource}</td>
				                   			<td>${val.dateTimes}</td>
				                   			<td>${val.val}<c:if test="${val.resource eq 'DiskFree'}"> %</c:if><c:if test="${val.resource ne 'DiskFree'}"> Bytes</c:if></td>
				                   		</tr>
			                   		</c:forEach>	       
		                    		</tbody>
			                   	</table>
		               		</div>
	               		</c:forEach>
             		</c:forEach>
				</c:when>
				
				<c:otherwise>
					<c:forEach items="${awsDailyDataMap}" var="dm" varStatus="status">
					<div style="width:40%; float:left; margin-right:50px;">
					<h2 class="mt-4">${dm.key}</h2>
						<div style="width:70%; height:300px; float:left; margin-right:200px; overflow:auto">
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>Date Time</th><th>val</th>
		                   			</tr>
	                  			</thead>
	                  			<tbody>
	                  			<c:forEach items="${dm.value}" var="dm_val" varStatus="status">
			                   		<tr>
			                   			<td>${dm_val.dateTimes}</td>
			                   			<td>${dm_val.val}<c:if test="${selectedResource eq 'Cpu' || selectedResource eq 'Memory'}"> %</c:if><c:if test="${selectedResource eq 'Network'}"> Bytes</c:if></td>
			                   		</tr>
		                   		</c:forEach>	       
	                    		</tbody>
		                   	</table>
	               		</div>
               		</div>
             		</c:forEach>
				</c:otherwise>
				</c:choose>
            </div>
        </div>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="../../../../js/scripts.js"></script>
    </body>
</html>