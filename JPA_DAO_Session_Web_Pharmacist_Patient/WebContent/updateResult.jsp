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
        <c:if test="${updatedBoolean}">
	        <h1>Update successful!</h1>
	        <h1>Old Patient State:</h1>
        	<div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div>
	        	</div>
	        	<div id="row">
	        		<div id="cell1">${oldPatient.id}:</div>
	        		<div id="cell2">${oldPatient.name}</div>
        			<div id="cell3">${oldPatient.address}</div>
        			<div id="cell4">${oldPatient.contactList[0]} ${oldPatient.contactList[1]} ${oldPatient.contactList[2]}</div>
	        	</div>
        	</div>
	        <h1>New Patient State:</h1>
	        <div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div>
	        	</div>
	        	<div id="row">
	        		<div id="cell1">${newPatient.id}:</div>
	        		<div id="cell2">${newPatient.name}</div>
        			<div id="cell3">${newPatient.address}</div>
        			<div id="cell4">${newPatient.contactList[0]} ${newPatient.contactList[1]} ${newPatient.contactList[2]}</div>
	        	</div>
        	</div>
        </c:if>
        <c:if test="${not updatedBoolean}">
	        <h1>Update failed! Probably because no Patient exists for the passed Id !</h1>
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
		        		<div id="cell4">${item.contactList}</div>
		        	</div>
	        	</c:forEach>
	        </div>
        </c:if>
        <c:if test="${not updatedBoolean && empty allPatientsList}">
        	<h1>No Patients to update at the DataBase!</h1>
        </c:if>
        <a href="index.jsp" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
