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
                    <h1 class="mt-4">Amazon EC2 On-Demand Pricing</h1> <br> <br> <br> <br>
                    <div style="text-align: center;">
					    <h2>On-Demand Pricing</h2>
					    <form action="/admin/admin_OnDemand" method="POST">
					    	Opetating System:
					    	<select id="operatingSys" name="operatingSys">
								<option value="none">===== 선택 =====</option>
								<option value="Linux"<c:if test="${selectedOs eq 'Linux'}">selected</c:if>>Linux</option>
								<option value="Windows"<c:if test="${selectedOs eq 'Windows'}">selected</c:if>>Windows</option>
							</select> &nbsp
							Region:
							<select id="regionname" name="regionname">
								<option value="none">======= 선택 =======</option>
								<c:forEach items="${regionnameList}" var="region">
									<option value="${region}"<c:if test="${region eq selectedRegionname}">selected</c:if>>${region}</option>
								</c:forEach>
							</select> &nbsp
						
							<input type="submit" value="조회">
					    </form> <br>
					    <div style="width:100%; height:800px; overflow:auto;">
					    <table style="width: 60%; margin: auto">
					        <tr>
					            <th style="width: 5%">instance type</th>
					            <th style="width: 1%">vCPU</th>
					            <th style="width: 5%">memory</th>
					            <th style="width: 10%">storage</th>
					            <th style="width: 10%">network performance</th>
					            <th style="width: 5%">price</th>
					            <th style="width: 3%">Customer set price</th>
					        </tr>
					        <c:forEach var="onDemand" items="${onDemandList}">
					            <tr>
					                <td><a href="getInstanceType?instanceType=${onDemand.instanceType}&operatingSys=${onDemand.operatingSys}&region=${onDemand.region}">${onDemand.instanceType}</a></td>
					                <td>${onDemand.ec2_vCPU}</td>
					                <td>${onDemand.ec2_memory}</td>
					                <td>${onDemand.ec2_storage}</td>
					                <td>${onDemand.networkPerformance}</td>
					                <td>${onDemand.price} $</td>
					                <td>${onDemand.customPrice} $</td>
					            </tr>
					        </c:forEach>
					    </table>
					    </div>
					    <br>
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