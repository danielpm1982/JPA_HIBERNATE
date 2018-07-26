<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OderAppWeb</title>
		<link href="css.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h1>Search FAILED!</h1>
        <h3>Customer not found for the Id or Customer name at the DB!</h3>
        <h1 id="allCustomers">All Customers</h1>
        <c:if test="${not empty allCustomersList}">
	        <div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Orders</div>
	        	</div>
	        	<c:forEach items="${allCustomersList}" var="item">
		        	<div id="row">
		        		<div id="cell1">${item.id}:</div>
		        		<div id="cell2">${item.name}</div>
		        		<div id="cell3">${item.address}</div>
		        		<div id="cell4">
		        			<c:choose>
		        				<c:when test="${not empty item.order}">${item.order}</c:when>
		        				<c:otherwise>No Orders!</c:otherwise>
	        				</c:choose>
	        			</div>
		        	</div>
	        	</c:forEach>
	        </div>
        </c:if>
        <c:if test="${empty allCustomersList}"><h3 id="noCustomers">No Customers!</h3></c:if>
        <a href="index.html" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
