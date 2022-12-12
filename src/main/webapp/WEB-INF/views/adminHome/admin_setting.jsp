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
        <link rel="icon" type="image/x-icon" href="../../assets/cloud.ico" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="../../css/styles.css" rel="stylesheet" />
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
                	<a class="list-group-item list-group-item-action list-group-item-light p-3" href="admin_setting">setting</a>
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
                <h1 class="mt-4">Setting</h1> <br>
                    <div style="text-align: center;">
                    	<h2 class="mt-4">Threshold</h2> <br>
	                    <table style="width: 20%; margin: auto;">
	                   		<thead>
	                    		<tr>
	                    			<th>low_cpu</th><th>high_cpu</th>
	                   			</tr>
                  			</thead>
                  			<tbody>
		                   		<tr>
		                   			<td><a href="getThreshold?th_name=thLowCpu&val=${problemThreshold.thLowCpu}">${problemThreshold.thLowCpu}</a> %</td>
		                    		<td><a href="getThreshold?th_name=thHighCpu&val=${problemThreshold.thHighCpu}">${problemThreshold.thHighCpu}</a> %</td>
		                   		</tr>	       
                    		</tbody>
	                   	</table> <br>
	                   	
	                   	<table style="width: 20%; margin: auto;">
	                   		<thead>
	                    		<tr>
	                    			<th>low_network in</th><th>high_network in</th>
	                   			</tr>
                  			</thead>
                  			<tbody>
		                   		<tr>
		                   			<td><a href="getThreshold?th_name=thLowNetI&val=${problemThreshold.thLowNetI}">${problemThreshold.thLowNetI}</a> bytes</td>
		                    		<td><a href="getThreshold?th_name=thHighNetI&val=${problemThreshold.thHighNetI}">${problemThreshold.thHighNetI}</a> bytes</td>
		                   		</tr>	       
                    		</tbody>
	                   	</table> <br>
	                   	
	                   	<table style="width: 20%; margin: auto;">
	                   		<thead>
	                    		<tr>
	                    			<th>low_network out</th><th>high_network out</th>
	                   			</tr>
                  			</thead>
                  			<tbody>
		                   		<tr>
		                   			<td><a href="getThreshold?th_name=thLowNetO&val=${problemThreshold.thLowNetO}">${problemThreshold.thLowNetO}</a> bytes</td>
		                    		<td><a href="getThreshold?th_name=thHighNetO&val=${problemThreshold.thHighNetO}">${problemThreshold.thHighNetO}</a> bytes</td>
		                   		</tr>	       
                    		</tbody>
	                   	</table> <br>
	                   	
	                   	<table style="width: 20%; margin: auto;">
	                   		<thead>
	                    		<tr>
	                    			<th>low_memory usage</th><th>high_memory usage</th>
	                   			</tr>
                  			</thead>
                  			<tbody>
		                   		<tr>
		                   			<td><a href="getThreshold?th_name=thLowMemU&val=${problemThreshold.thLowMemU}">${problemThreshold.thLowMemU}</a> %</td>
		                    		<td><a href="getThreshold?th_name=thHighMemU&val=${problemThreshold.thHighMemU}">${problemThreshold.thHighMemU}</a> %</td>
		                   		</tr>	       
                    		</tbody>
	                   	</table> <br>
	                   	
	                   	<table style="width: 20%; margin: auto;">
	                   		<thead>
	                    		<tr>
	                    			<th>low_disk read</th><th>high_disk read</th>
	                   			</tr>
                  			</thead>
                  			<tbody> 
		                   		<tr>
		                   			<td><a href="getThreshold?th_name=thLowDiskR&val=${problemThreshold.thLowDiskR}">${problemThreshold.thLowDiskR}</a> bytes</td>
		                    		<td><a href="getThreshold?th_name=thHighDiskR&val=${problemThreshold.thHighDiskR}">${problemThreshold.thHighDiskR}</a> bytes</td>
		                   		</tr>	       
                    		</tbody>
	                   	</table> <br>
	                   	
	                   	<table style="width: 20%; margin: auto;">
	                   		<thead>
	                    		<tr>
	                    			<th>low_disk write</th><th>high_disk write</th>
	                   			</tr>
                  			</thead>
                  			<tbody>
		                   		<tr>
		                   			<td><a href="getThreshold?th_name=thLowDiskW&val=${problemThreshold.thLowDiskW}">${problemThreshold.thLowDiskW}</a> bytes</td>
		                    		<td><a href="getThreshold?th_name=thHighDiskW&val=${problemThreshold.thHighDiskW}">${problemThreshold.thHighDiskW}</a> bytes</td>
		                   		</tr>	       
                    		</tbody>
	                   	</table> <br>
	                   	
	                   	<table style="width: 20%; margin: auto;">
	                   		<thead>
	                    		<tr>
	                    			<th>low_disk free</th>
	                   			</tr>
                  			</thead>
                  			<tbody>
		                   		<tr>
		                   			<td><a href="getThreshold?th_name=thLowDiskF&val=${problemThreshold.thLowDiskF}">${problemThreshold.thLowDiskF}</a> %</td>
		                   		</tr>	       
                    		</tbody>
	                   	</table> <br>
	            	</div>
              	</div>
            </div>
        </div>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="../../js/scripts.js"></script>
    </body>
</html>