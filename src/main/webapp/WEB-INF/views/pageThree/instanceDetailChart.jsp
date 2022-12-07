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
                <!-- Page content -->
                <div class="container-fluid">
                <div>
                		<a href="/home/accountID" style="color: blue; text-decoration:none">home</a> &nbsp>
                		<a href="/home/accountID/${accountID}" style="color: blue; text-decoration:none">${accountID}</a> &nbsp>
                		<a href="" style="color: blue; text-decoration:none">${instanceID}</a>
               	</div>
               	
               	<!-- Host property -->
                <div><h1 class="mt-4">${instanceID}</h1></div> <br>
                <div>
                    <table style="width:100%;">
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
               	
               	<!-- Day set -->
             	<form action="/home/accountID/${accountID}/${instanceID}" method="POST" style="display: flex; justify-content: flex-start; padding: 10px 0;">
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
				    <input type="submit" value="선택"> &nbsp
				    <input type="button" value="Detail" onClick="location.href='/home/accountID/${accountID}/${instanceID}/detail'">
				</form>
				
				<!-- Detail chart -->
				<script language="javaScript">
					var instanceID = "${instanceID}";
					var diskID = "${selectedDiskID}";
	          		var cpuTimeList = new Array();
	          		var cpuDataList = new Array();
	          		
	          		var diskRTimeList = new Array();
	          		var diskWTimeList = new Array();
	          		var diskFTimeList = new Array();
	          		var diskRDataList = new Array();
	          		var diskWDataList = new Array();
	          		var diskFDataList = new Array();
	          		
	          		var memTimeList = new Array();
	          		var memDataList = new Array();
	          		
	          		var netITimeList = new Array();
	          		var netIDataList = new Array();
	          		var netOTimeList = new Array();
	          		var netODataList = new Array();
				</script>
				
				<!-- Cpu chart -->
				<c:if test="${selectedResource eq 'Cpu'}">
					<c:if test="${awsDailyDataMap.CPUUtilization.size() eq 0}">
	             		<h1>No Data</h1>
	             	</c:if>
					<script language="javaScript">
	               		<c:forEach items="${awsDailyDataMap}" var="aws">
	               			<c:forEach items="${aws.value}" var="aws_val">
		               			cpuTimeList.push("${aws_val.dateTimes}");
		               			cpuDataList.push("${aws_val.val}");
	               			</c:forEach>
	           			</c:forEach>
	              	</script>
	              	<div><br><canvas id="cpuChart" width="1400" height="400"></canvas></div>
              	</c:if>
              	
              	<!-- Disk chart -->
				<c:if test="${selectedResource eq 'Disk'}">
					<c:if test="${awsDailyDiskMap.size() eq 0}">
             			<h1>No Data</h1>
             		</c:if>
             		<form action="/home/accountID/${accountID}/${instanceID}" method="GET" style="display: flex; justify-content: flex-start; padding: 10px 0;">
             			<select id="diskID" name="diskID">
							<option value="none">======= 선택 =======</option>
							<c:forEach items="${instanceDiskList}" var="diskID">
								<option value="${diskID}"<c:if test="${diskID eq selectedDiskID}">selected</c:if>>${diskID}</option>
							</c:forEach>
						</select> &nbsp
             			<input type="submit" value="선택">
             		</form>
             			
					<script language="javaScript">
						<c:forEach items="${diskIDResourceMap.DiskReadBytes}" var="diskR">
		           			diskRTimeList.push("${diskR.dateTimes}");
		           			diskRDataList.push("${diskR.val}");
		       			</c:forEach>
		       			
		       			<c:forEach items="${diskIDResourceMap.DiskWriteBytes}" var="diskW">
	           				diskWTimeList.push("${diskW.dateTimes}");
	           				diskWDataList.push("${diskW.val}");
	       				</c:forEach>
	       				
	       				<c:forEach items="${diskIDResourceMap.DiskFree}" var="diskF">
           					diskFTimeList.push("${diskF.dateTimes}");
           					diskFDataList.push("${diskF.val}");
       					</c:forEach>
	              	</script>
	              	<div><br><canvas id="diskRChart" width="1400" height="400"></canvas></div>
	              	<div><br><canvas id="diskWChart" width="1400" height="400"></canvas></div>
	              	<div><br><canvas id="diskFChart" width="1400" height="400"></canvas></div>
              	</c:if>
              	
              	<!-- Network chart -->
				<c:if test="${selectedResource eq 'Network'}">
					<c:if test="${awsDailyDataMap.NetworkIn.size() eq 0 || awsDailyDataMap.NetworkOut.size() eq 0}">
	             		<h1>No Data</h1>
	             	</c:if>
					<script language="javaScript">
               			<c:forEach items="${awsDailyDataMap.NetworkIn}" var="aws_val">
	               			netITimeList.push("${aws_val.dateTimes}");
	               			netIDataList.push("${aws_val.val}");
               			</c:forEach>
               			
               			<c:forEach items="${awsDailyDataMap.NetworkOut}" var="aws_val">
	               			netOTimeList.push("${aws_val.dateTimes}");
	               			netODataList.push("${aws_val.val}");
             			</c:forEach>
	              	</script>
	              	<div><br><canvas id="netIChart" width="1400" height="400"></canvas></div>
	              	<div><br><canvas id="netOChart" width="1400" height="400"></canvas></div>
              	</c:if>
              	
              	<!-- Memory chart -->
				<c:if test="${selectedResource eq 'Memory'}">
					<c:if test="${awsDailyDataMap.MemoryUsed.size() eq 0}">
	              		<h1>No Data</h1>
	              	</c:if>
					<script language="javaScript">
	               		<c:forEach items="${awsDailyDataMap}" var="aws">
	               			<c:forEach items="${aws.value}" var="aws_val">
		               			memTimeList.push("${aws_val.dateTimes}");
		               			memDataList.push("${aws_val.val}");
	               			</c:forEach>
	           			</c:forEach>
	              	</script>
	              	<div><br><canvas id="memChart" width="1400" height="400"></canvas></div>
              	</c:if>
              	
            </div>
        </div>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="../../../../js/scripts.js"></script>
        <script src="../../../../js/linechart.js"></script>
    </body>
</html>