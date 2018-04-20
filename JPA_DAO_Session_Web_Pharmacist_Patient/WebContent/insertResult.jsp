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
        <c:if test="${not empty lastPatient}">
	        <h1>Insert successful!</h1>
	        <h1 id="resultPatient">Patient inserted:</h1>
        	<div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div><div id="cellHeader5">HR</div>
	        	</div>
	        	<div id="row">
	        		<div id="cell1">${lastPatient.id}:</div>
	        		<div id="cell2">${lastPatient.name}</div>
        			<div id="cell3">${lastPatient.address}</div>
        			<div id="cell4">${lastPatient.contactList[0]} ${hrPatient.contactList[1]} ${hrPatient.contactList[2]}</div>
        			<div id="cell5"><a href="controller.do?command=hr&patientId=${lastPatient.id}" title="Go To HealthRecord"><img src="healthRecord.png" alt="health record" title="health record" id="healthRecord" /></a></div>
	        	</div>
        	</div>
        </c:if>
        <c:if test="${empty lastPatient}">
	        <h1>Insert failed!</h1>
	        <h1 id="allPatients">All Patients:</h1>
	        <div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div><div id="cellHeader5">HR</div>
	        	</div>
	        	<c:forEach items="${allPatientsList}" var="item">
		        	<div id="row">
		        		<div id="cell1">${item.id}:</div>
		        		<div id="cell2">${item.name}</div>
		        		<div id="cell3">${item.address}</div>
		        		<div id="cell4">${item.contactList[0]} ${item.contactList[1]} ${item.contactList[2]}</div>
		        		<div id="cell5"><a href="controller.do?command=hr&patientId=${lastPatient.id}" title="Go To HealthRecord"><img src="healthRecord.png" alt="health record" title="health record" id="healthRecord" /></a></div>
		        	</div>
	        	</c:forEach>
	        </div>
        </c:if>
        <c:if test="${empty lastPatient && empty allPatientsList}">
        	<h1>No Patients at the DataBase!</h1>
        </c:if>
        <a href="index.jsp" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
