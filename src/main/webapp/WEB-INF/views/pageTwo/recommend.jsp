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
	    <script type="text/javascript">	
	       	var resourceList = '${resourceList}';
       </script>
       
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
                		<a href="/home/accountID/${accountID}/problem" style="color: blue; text-decoration:none">problem</a> &nbsp>
                		<a href="" style="color: blue; text-decoration:none">recommend</a>
              	</div>
              	
              	<div><h1 class="mt-4">Recommend</h1></div> <br>
              	
              	<!-- Benefit -->
              	<c:if test="${toBeSetFlag eq 1}">
              		<h3>Benefit</h3>
	                <div style="width:30%;">
	                	<table>
	                   		<thead>
	                    		<tr>
	                    			<th>Linux Instance Benefit</th><th>Windows Instance Benefit</th><th>Total Benefit</th>
	                   			</tr>
	                 		</thead>
	                 		<tbody>
		                   		<tr>
		                   			<td <c:if test="${linuxBenefit > 0}">style="color:red;"</c:if> <c:if test="${linuxBenefit < 0}">style="color:blue;"</c:if>>${linuxBenefit} $</td>
		                    		<td <c:if test="${windowBenefit > 0}">style="color:red;"</c:if> <c:if test="${windowBenefit < 0}">style="color:blue;"</c:if>>${windowBenefit} $</td>
		                   			<td <c:if test="${totalBenefit > 0}">style="color:red;"</c:if> <c:if test="${totalBenefit < 0}">style="color:blue;"</c:if>>${totalBenefit} $</td>
		                   		</tr>
	                   		</tbody>
			        	</table>
	                </div> <br><br>
				</c:if>
				                
                <!-- recommend page -->
                <script language="javaScript">
		        	var asIsLList = new Array();
		        	var asIsLCount = new Array();
		        	var toBeLList = new Array();
		        	var toBeLCount = new Array();
		        	var asIsWList = new Array();
		        	var asIsWCount = new Array();
		        	var toBeWList = new Array();
		        	var toBeWCount = new Array();
        		</script>
        		
				<c:if test="${asIsLinuxEc2List.size() ne 0}">
					<div style="width:40%; height:300px; overflow:auto; float:left; margin-right:80px;">
						<h3>AS-IS</h3>
	              		<div>
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>instance type</th><th>OS</th><th>region</th><th>price</th><th>count</th>
		                   			</tr>
		                 		</thead>
		                 		<tbody>
		                 			<c:forEach items="${asIsLinuxEc2List}" var="litc" varStatus="status">
			                   		<tr>
			                   			<td>${litc.instanceType}</td>
			                    		<td>${litc.operatingSys}</td>
			                    		<td>${litc.region}</td>
			                    		<td>${litc.price} $</td>
			                    		<td>${litc.count}</td>
			                   		</tr>
			                   		<script language="javaScript">
			                   			asIsLList.push("${litc.instanceType}");
			                   			asIsLCount.push("${litc.count}");
					        		</script>
		                  			</c:forEach>
		                   		</tbody>
		                   	</table>
	              		</div> <br>
	              		<div style="float:right;">
	              			<table>
	                    		<tr>
	                    			<th>Total Cost</th><td>${asIsLinuxTotalCost} $</td>
	                   			</tr>
		                   	</table>	
		            	</div>
	              	</div>
             	</c:if>
              	
				<c:if test="${toBeLinuxEc2List.size() ne 0}">
					<div style="width:40%; height:300px; overflow:auto; float:left; margin-right:80px;">
						<h3>TO-BE</h3>
	              		<div>
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>instance type</th><th>OS</th><th>region</th><th>price</th><th>count</th>
		                   			</tr>
		                 			</thead>
		                 			<tbody>
		                 			<c:forEach items="${toBeLinuxEc2List}" var="litc" varStatus="status">
			                   		<tr>
			                   			<td>${litc.instanceType}</td>
			                    		<td>${litc.operatingSys}</td>
			                    		<td>${litc.region}</td>
			                    		<td>${litc.price} $</td>
			                    		<td>${litc.count}</td>
			                   		</tr>
			                   		<script language="javaScript">
			                   			toBeLList.push("${litc.instanceType}");
			                   			toBeLCount.push("${litc.count}");
					        		</script>	
		                  			</c:forEach>
		                   		</tbody>
		                   	</table>
	              		</div> <br>
	              		<div style="float:right;">
	              			<table>
	                    		<tr>
	                    			<th>Total Cost</th><td>${toBeLinuxTotalCost} $</td>
	                   			</tr>
		                   	</table>	
		            	</div>
	              	</div>
             	</c:if>
             	
             	<c:if test="${asIsLinuxEc2List.size() ne 0}">
             		<div style="float:left; margin-right:300px;"><canvas id="asis-l-pie-chart" width="400" height="400"></canvas></div>
             	</c:if>
             	<c:if test="${toBeLinuxEc2List.size() ne 0}">
             		<div style="float:left;"><canvas id="tobe-l-pie-chart" width="400" height="400"></canvas></div>
             	</c:if>
             	
				<c:if test="${asIsWindowEc2List.size() ne 0}">
					<div style="width:40%; height:300px; overflow:auto; float:left; margin-right:80px;">
						<br><br><h3>AS-IS</h3>
	              		<div>
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>instance type</th><th>OS</th><th>region</th><th>price</th><th>count</th>
		                   			</tr>
		                 			</thead>
		                 			<tbody>
		                 			<c:forEach items="${asIsWindowEc2List}" var="witc" varStatus="status">
			                   		<tr>
			                   			<td>${witc.instanceType}</td>
			                    		<td>${witc.operatingSys}</td>
			                    		<td>${witc.region}</td>
			                    		<td>${witc.price} $</td>
			                    		<td>${witc.count}</td>
			                   		</tr>	
			                   		<script language="javaScript">
			                   			asIsWList.push("${witc.instanceType}");
			                   			asIsWCount.push("${witc.count}");
					        		</script>
		                  			</c:forEach>
		                   		</tbody>
		                   	</table>
	              		</div> <br>
	              		<div style="float:right;">
	              			<table>
	                    		<tr>
	                    			<th>Total Cost</th><td>${asIsWindowTotalCost} $</td>
	                   			</tr>
		                   	</table>	
		            	</div>
	              	</div>
             	</c:if>
				
                <c:if test="${toBeWindowEc2List.size() ne 0}">
					<div style="width:40%; height:300px; overflow:auto; float:left; margin-right:80px;">
						<br><br><h3>TO-BE</h3>
	              		<div>
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>instance type</th><th>OS</th><th>region</th><th>price</th><th>count</th>
		                   			</tr>
		                 			</thead>
		                 			<tbody>
		                 			<c:forEach items="${toBeWindowEc2List}" var="witc" varStatus="status">
			                   		<tr>
			                   			<td>${witc.instanceType}</td>
			                    		<td>${witc.operatingSys}</td>
			                    		<td>${witc.region}</td>
			                    		<td>${witc.price} $</td>
			                    		<td>${witc.count}</td>
			                   		</tr>
			                   		<script language="javaScript">
				                   		toBeWList.push("${witc.instanceType}");
				                   		toBeWCount.push("${witc.count}");
					        		</script>
		                  			</c:forEach>
		                   		</tbody>
		                   	</table>
	              		</div> <br>
	              		<div style="float:right;">
	              			<table>
	                    		<tr>
	                    			<th>Total Cost</th><td>${toBeWindowTotalCost} $</td>
	                   			</tr>
		                   	</table>	
		            	</div>
	              	</div>
             	</c:if>
             	
             	<c:if test="${asIsWindowEc2List.size() ne 0}">
             		<div style="float:left; margin-right:300px;"><canvas id="asis-w-pie-chart" width="400" height="400"></canvas></div>
             	</c:if>
             	<c:if test="${toBeWindowEc2List.size() ne 0}">
             		<div style="float:left;"><canvas id="tobe-w-pie-chart" width="400" height="400"></canvas></div>
             	</c:if>
             	
                </div>
            </div>
        </div>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="../../../../js/scripts.js"></script>
        <script src="../../../../js/piechart.js"></script>
    </body>
</html>