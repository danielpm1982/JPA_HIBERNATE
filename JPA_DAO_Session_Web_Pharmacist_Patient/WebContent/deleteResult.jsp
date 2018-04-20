<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PharmacistWebApp</title>
		<link href="css.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <c:if test="${deletedBoolean}">
	        <h1>Delete successful!</h1>
	        <h1 id="deletedPatient">Patient deleted for id=${deletedPatient.id}:</h1>
	        <div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div>
	        	</div>
	        	<div id="row">
	        		<div id="cell1">${deletedPatient.id}:</div>
	        		<div id="cell2">${deletedPatient.name}</div>
        			<div id="cell3">${deletedPatient.address}</div>
        			<div id="cell4">${deletedPatient.contactList[0]} ${hrPatient.contactList[1]} ${hrPatient.contactList[2]}</div>
	        	</div>
        	</div>
	        <h1 id="allPatients">All Patients:</h1>
	        <div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div>
	        	</div>
	        	<c:forEach items="${allPatientsList}" var="item">
		        	<div id="row">
		        		<div id="cell1">${item.id}:</div>
		        		<div id="cell2">${item.name}</div>
		        		<div id="cell3">${item.address}</div>
		        		<div id="cell4">${item.contactList[0]} ${item.contactList[1]} ${item.contactList[2]}</div>
		        	</div>
	        	</c:forEach>
	        </div>
        </c:if>
        <c:if test="${not deletedBoolean}">
	        <h1>Delete failed! No specific Patient found for the search argument passed!</h1>
	        <h1 id="allPatients">All Patients:</h1>
	        <div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div>
	        	</div>
	        	<c:forEach items="${allPatientsList}" var="item">
		        	<div id="row">
		        		<div id="cell1">${item.id}:</div>
		        		<div id="cell2">${item.name}</div>
		        		<div id="cell3">${item.address}</div>
		        		<div id="cell4">${item.contactList[0]} ${item.contactList[1]} ${item.contactList[2]}</div>
		        	</div>
	        	</c:forEach>
	        </div>
        </c:if>
        <c:if test="${not deletedBoolean && empty allPatientsList}">
        	<h1 id="noPatients">No Patients to delete at the DataBase!</h1>
        </c:if>
        <a href="index.jsp" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
