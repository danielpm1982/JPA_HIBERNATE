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
        <h1>Search successful!</h1>
        <c:if test="${not empty lastCustomer}">
	        <h1 id="resultCustomer2">Customer found for id = ${lastCustomer.id}:</h1>
	        <h3>Customer: ${lastCustomer}</h3>
	        <h3>This Customer has ${lastCustomer.order.size()} order(s):</h3>
	        <c:if test="${not empty lastCustomer.order}">
		        <div id="table">
		        	<div id="row">
		        		<div id="cellHeader">Order Id</div><div id="cellHeader">Order name</div><div id="cellHeader">DateTime</div><div id="cellHeader">Items</div><div id="cellHeader">Total Price</div>
		        	</div>
		        	<c:forEach items="${lastCustomer.order}" var="order">
			        	<div id="row">
			        		<div id="cell1">${order.id}:</div>
			        		<div id="cell2">${order.name}</div>
			        		<div id="cell3">${order.dateTime}</div>
			        		<div id="cell4">
			        			<c:choose>
			        				<c:when test="${not empty order.itemList}">${order.itemList}</c:when>
			        				<c:otherwise>No Items!</c:otherwise>
		        				</c:choose>
		        			</div>
		        			<div id="cell5">$${order.totalPrice}</div>
			        	</div>
		        	</c:forEach>
		        </div>
	        </c:if>
	        <c:if test="${empty lastCustomer.order}">
	        	<h3>No Orders!</h3>
	        </c:if>
        </c:if>
        <h1 id="allCustomers">All Customers:</h1>
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
        <a href="index.html" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
