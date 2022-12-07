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
                		<a href="" style="color: blue; text-decoration:none">problem</a>
              	</div>
               		
                <div><h1 class="mt-4">Problem</h1></div> <br>
                <div>
                   	<input type="button" value="Recommend" onClick="location.href='/home/accountID/${accountID}/problem/recommend'">
                </div> <br>
                
                <form action="/home/accountID/${accountID}/problem" method="POST" style="display: flex; justify-content: flex-start; padding: 10px 0;">
					    <input type="date" name="startDay" id="start" value="${startDay}">
					    ~
					    <input type="date" name="endDay" id="end" value="${endDay}"> &nbsp
					    <input type="submit" value="조회">
				</form>
					
                <!-- CPU problem page -->
                <c:choose>
	                <c:when test="${cpuUProblemList.size() ne 0}">
					<div><h2 class="mt-4">CPU</h2></div>
						<div style="width:70%; height:200px; overflow:auto;">
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>problem</th><th>instnace ID</th>
		                    			<th>cpu</th><th>platform</th><th>region</th><th>instance type</th><th>current price</th><th>recommend</th><th>recommend price</th>
		                   			</tr>
		                 		</thead>
		                		<tbody>
		                			<c:forEach items="${cpuUProblemList}" var="cpl" varStatus="status">
				                   		<tr>
				                   			<c:if test="${cpl.cpuUVal lt 10}">
				                   				<td>cpu 사용률 적음</td>
				                    			<td><a href="/home/accountID/${accountID}/${cpl.instanceID}" style="color: blue; text-decoration:none">${cpl.instanceID}</a></td>
				                    			<td style="color: green">${cpl.cpuUVal} %</td>
				                    			<td>${cpl.platform}</td>
				                    			<td>${cpl.region}</td>
				                    			<td>${cpl.instanceType}</td>
				                    			<td style="color: red">${cpl.hourlyCost} $</td>
				                    			<td>${cpl.recommend}</td>
				                    			<td style="color: red">${cpl.recommendPrice} $</td>
			                    			</c:if>
			                    			<c:if test="${cpl.cpuUVal gt 80}">
			                    				<td>cpu 사용률 많음</td>
			                    				<td><a href="/home/accountID/${accountID}/${cpl.instanceID}" style="color: blue; text-decoration:none">${cpl.instanceID}</a></td>
				                    			<td style="color: red">${cpl.cpuUVal} %</td>
				                    			<td>${cpl.platform}</td>
				                    			<td>${cpl.region}</td>
				                    			<td>${cpl.instanceType}</td>
				                    			<td style="color: red">${cpl.hourlyCost} $</td>
				                    			<td>${cpl.recommend}</td>
				                    			<td style="color: red">${cpl.recommendPrice} $</td>
			                    			</c:if>
				                   		</tr>
		                   		    </c:forEach>
		                   		</tbody>
		                   	</table>
		            	</div>
	            	</c:when>
            	</c:choose>
            	
            	<!-- Disk problem page -->
                <c:choose>
	                <c:when test="${diskRProblemList.size() ne 0 || diskWProblemList.size() ne 0 || diskFProblemList.size() ne 0}">
					<div><h2 class="mt-4">Disk</h2></div>
						<div style="width:70%; height:200px; overflow:auto;">
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>problem</th><th>instnace ID</th><th>disk ID</th>
		                    			<th>disk read byte</th><th>disk write byte</th><th>disk 남은 용량</th><th>recommend</th>
		                   			</tr>
		                 		</thead>
		                 		
		                		<tbody>
		                			<c:forEach items="${diskRProblemList}" var="drpl" varStatus="status">
				                   		<tr>
					                   		<c:if test="${drpl.diskRVal lt 1000}">
					                   			<td>disk read byte 적음</td>
				                    			<td><a href="/home/accountID/${accountID}/${drpl.instanceID}" style="color: blue; text-decoration:none">${drpl.instanceID}</a></td>
				                    			<td>${drpl.diskID}</td>
				                    			<td style="color: green">${drpl.diskRVal} bytes</td>
				                    			<td>${drpl.diskWVal} bytes</td>
				                    			<c:if test="${drpl.diskID eq 'DEFAULT'}">
			                    					<td>-</td>
			                    				</c:if>
			                    				<c:if test="${drpl.diskID ne 'DEFAULT'}">
				                    				<td>${drpl.diskFVal} %</td>
			                    				</c:if>
				                    			<td>downdrage disk</td>
				                    		</c:if>
				                    		<c:if test="${drpl.diskRVal gt 100000}">
				                    			<td>disk read byte 많음</td>
				                    			<td><a href="/home/accountID/${accountID}/${drpl.instanceID}" style="color: blue; text-decoration:none">${drpl.instanceID}</a></td>
				                    			<td>${drpl.diskID}</td>
				                    			<td style="color: red">${drpl.diskRVal} bytes</td>
				                    			<td>${drpl.diskWVal} bytes</td>
				                    			<c:if test="${drpl.diskID eq 'DEFAULT'}">
			                    					<td>-</td>
			                    				</c:if>
			                    				<c:if test="${drpl.diskID ne 'DEFAULT'}">
				                    				<td>${drpl.diskFVal} %</td>
			                    				</c:if>
				                    			<td>upgrade disk</td>
				                    		</c:if>
				                   		</tr>
		                   		    </c:forEach>
		                   		    <c:forEach items="${diskWProblemList}" var="dwpl" varStatus="status">
				                   		<tr>
				                   			<c:if test="${dwpl.diskWVal lt 1000}">
					                   			<td>disk write byte 적음</td>
				                    			<td><a href="/home/accountID/${accountID}/${dwpl.instanceID}" style="color: blue; text-decoration:none">${dwpl.instanceID}</a></td>
				                    			<td>${dwpl.diskID}</td>
				                    			<td>${dwpl.diskRVal} bytes</td>
				                    			<td style="color: green">${dwpl.diskWVal} bytes</td>
				                    			<c:if test="${dwpl.diskID eq 'DEFAULT'}">
			                    					<td>-</td>
			                    				</c:if>
			                    				<c:if test="${dwpl.diskID ne 'DEFAULT'}">
				                    				<td>${dwpl.diskFVal} %</td>
			                    				</c:if>
				                    			<td>downgrade disk</td>
				                    		</c:if>
				                    		<c:if test="${dwpl.diskWVal gt 100000}">
				                    			<td>disk write byte 많음</td>
				                    			<td><a href="/home/accountID/${accountID}/${dwpl.instanceID}" style="color: blue; text-decoration:none">${dwpl.instanceID}</a></td>
				                    			<td>${dwpl.diskID}</td>
				                    			<td>${dwpl.diskRVal} bytes</td>
				                    			<td style="color: red">${dwpl.diskWVal} bytes</td>
				                    			<c:if test="${dwpl.diskID eq 'DEFAULT'}">
			                    					<td>-</td>
			                    				</c:if>
			                    				<c:if test="${dwpl.diskID ne 'DEFAULT'}">
				                    				<td>${dwpl.diskFVal} %</td>
			                    				</c:if>
				                    			<td>upgrade disk</td>
				                    		</c:if>
				                   		</tr>
		                   		    </c:forEach>
		                   		    <c:forEach items="${diskFProblemList}" var="dupl" varStatus="status">
				                   		<tr>
				                   		<c:if test="${dupl.diskFVal lt 10}">
				                   			<td>disk 여유 공간 부족</td>
			                    			<td><a href="/home/accountID/${accountID}/${dupl.instanceID}" style="color: blue; text-decoration:none">${dupl.instanceID}</a></td>
			                    			<td>${dupl.diskID}</td>
			                    			<td>${dupl.diskRVal} bytes</td>
			                    			<td>${dupl.diskWVal} bytes</td>
			                    			<c:if test="${dupl.diskID eq 'DEFAULT'}">
			                    				<td>-</td>
		                    				</c:if>
		                    				<c:if test="${dupl.diskID ne 'DEFAULT'}">
			                    				<td style="color: red">${dupl.diskFVal} %</td>
		                    				</c:if>
			                    			<td>disk 용량 확보 필요</td>
		                    			</c:if>
				                   		</tr>
		                   		    </c:forEach>
		                   		</tbody>
		                   	</table>
		            	</div>
	            	</c:when>
            	</c:choose>
            	
            	<!-- Net problem page -->
                <c:choose>
	                <c:when test="${netIProblemList.size() ne 0 || netOProblemList.size() ne 0}">
					<div><h2 class="mt-4">Network</h2></div>
						<div style="width:70%; height:200px; overflow:auto;">
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>problem</th><th>instnace ID</th><th>network in</th><th>network out</th><th>recommend</th>
		                   			</tr>
		                 		</thead>
		                 		
		                		<tbody>
		                			<c:forEach items="${netIProblemList}" var="nipl" varStatus="status">
				                   		<tr>
					                   		<c:if test="${nipl.netIVal lt 1000}">
					                   			<td>network in byte 적음</td>
				                    			<td><a href="/home/accountID/${accountID}/${nipl.instanceID}" style="color: blue; text-decoration:none">${nipl.instanceID}</a></td>
				                    			<td style="color: green">${nipl.netIVal} Bytes/s</td>
				                    			<td>${nipl.netOVal} Bytes/s</td>
				                    			<td>downgrade network performance</td>
			                    			</c:if>
			                    			<c:if test="${nipl.netIVal gt 100000}">
			                    				<td>network in byte 많음</td>
			                    				<td><a href="/home/accountID/${accountID}/${nipl.instanceID}" style="color: blue; text-decoration:none">${nipl.instanceID}</a></td>
				                    			<td style="color: red">${nipl.netIVal} Bytes/s</td>
				                    			<td>${nipl.netOVal} Bytes/s</td>
				                    			<td>upgrade network performance</td>
			                    			</c:if>
				                   		</tr>
		                   		    </c:forEach>
		                   		    
		                   		    <c:forEach items="${netOProblemList}" var="nopl" varStatus="status">
				                   		<tr>
					                   		<c:if test="${nopl.netOVal lt 1000}">
					                   			<td>network out byte 적음</td>
				                    			<td><a href="/home/accountID/${accountID}/${nopl.instanceID}" style="color: blue; text-decoration:none">${nopl.instanceID}</a></td>
				                    			<td>${nopl.netIVal} Bytes/s</td>
				                    			<td style="color: green">${nopl.netOVal} Bytes/s</td>
				                    			<td>downgrade network performance</td>
			                    			</c:if>
			                    			<c:if test="${nopl.netOVal gt 100000}">
			                    				<td>network out byte 적음</td>
			                    				<td><a href="/home/accountID/${accountID}/${nopl.instanceID}" style="color: blue; text-decoration:none">${nopl.instanceID}</a></td>
				                    			<td>${nopl.netIVal} Bytes/s</td>
				                    			<td style="color: red">${nopl.netOVal} Bytes/s</td>
				                    			<td>upgrade network performance</td>
			                    			</c:if>
				                   		</tr>
		                   		    </c:forEach>
		                   		</tbody>
		                   	</table>
		            	</div>
	            	</c:when>
            	</c:choose>
            	
            	<!-- Memory problem page -->
                <c:choose>
	                <c:when test="${memUProblemList.size() ne 0}">
					<div><h2 class="mt-4">Memory</h2></div>
						<div style="width:70%; height:200px; overflow:auto;">
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>problem</th><th>instnace ID</th><th>memory used</th><th>recommend</th>
		                   			</tr>
		                 		</thead>
		                 		
		                		<tbody>
		                			<c:forEach items="${memUProblemList}" var="mupl" varStatus="status">
				                   		<tr>
					                   		<c:if test="${mupl.memUVal lt 10}">
					                   			<td>memory 사용량 적음</td>
				                    			<td><a href="/home/accountID/${accountID}/${mupl.instanceID}" style="color: blue; text-decoration:none">${mupl.instanceID}</a></td>
				                    			<td style="color: green">${mupl.memUVal} %</td>
				                    			<td>downgrade memory</td>
				                    		</c:if>
			                    			<c:if test="${mupl.memUVal gt 80}">
			                    				<td>memory 사용량 많음</td>
			                    				<td><a href="/home/accountID/${accountID}/${mupl.instanceID}" style="color: blue; text-decoration:none">${mupl.instanceID}</a></td>
				                    			<td style="color: red">${mupl.memUVal} %</td>
				                    			<td>upgrade memory</td>
			                    			</c:if>
				                   		</tr>
		                   		    </c:forEach>
		                   		</tbody>
		                   	</table>
		            	</div>
	            	</c:when>
            	</c:choose>
            	
                <!-- stop instance page -->
                <c:choose>
	                <c:when test="${stopProblemList.size() ne 0}">
					<div><h2 class="mt-4">Stop</h2></div>
						<div style="width:30%; height:200px; overflow:auto;">
		                    <table>
		                   		<thead>
		                    		<tr>
		                    			<th>problem</th><th>instnace ID</th>
		                   			</tr>
		                 		</thead>
		                 		
		                		<tbody>
		                			<c:forEach items="${stopProblemList}" var="stop" varStatus="status">
				                   		<tr>
		                    				<td>Not working</td>
		                    				<td><a href="/home/accountID/${accountID}/${stop.instanceID}" style="color: blue; text-decoration:none">${stop.instanceID}</a></td>
				                   		</tr>
		                   		    </c:forEach>
		                   		</tbody>
		                   	</table>
		            	</div>
	            	</c:when>
            	</c:choose>
            	
                </div>
            </div>
        </div>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="../../../../js/scripts.js"></script>
    </body>
</html>