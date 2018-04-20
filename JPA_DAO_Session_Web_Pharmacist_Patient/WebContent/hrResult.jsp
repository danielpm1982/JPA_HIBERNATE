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
        <c:if test="${not empty hrPatient}">
	        <h1>Patient:</h1>
	        <c:if test="${not empty hrPatient}">
	        	<div id="table">
		        	<div id="row">
		        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div>
		        	</div>
		        	<div id="row">
		        		<div id="cell1">${hrPatient.id}:</div>
		        		<div id="cell2">${hrPatient.name}</div>
	        			<div id="cell3">${hrPatient.address}</div>
	        			<div id="cell4">${hrPatient.contactList[0]} ${hrPatient.contactList[1]} ${hrPatient.contactList[2]}</div>
		        	</div>
	        	</div>
        	</c:if>
	        <h1>Health Record:</h1>
	        <c:if test="${not empty hrPatient.healthRecord}">
	        	<div id="table">
		        	<div id="row">
		        		<div id="cellHeader1">Id</div><div id="cellHeader2">Patient</div>
		        	</div>
		        	<div id="row">
		        		<div id="cell1">${hrPatient.healthRecord.id}:</div>
		        		<div id="cell2">${hrPatient.healthRecord.patient}</div>
		        	</div>
	        	</div>
        	</c:if>
        	<c:if test="${empty hrPatient.healthRecord}">
        		<h3>Patient has no HealthRecord!</h3>
        	</c:if>
        	<h1>Consultations:</h1>
        	<c:if test="${not empty consultationList}">
	        	<div id="table">
		        	<div id="row">
		        		<div id="cellHeader1">Id</div><div id="cellHeader2">DateTime</div><div id="cellHeader3">Pharmaceutical Doctor</div><div id="cellHeader4">Description</div>
		        	</div>
		        	<c:forEach items="${consultationList}" var="itemConsultation">
			        	<div id="row">
			        		<div id="cell1">${itemConsultation.id}:</div>
			        		<div id="cell2">${itemConsultation.dateTimeString}</div>
			        		<c:if test="${not empty itemConsultation.pharmacistList}">
			        			<div id="cell3">
			        				<c:forEach items="${itemConsultation.pharmacistList}" var="itemPharmacist">
				        				<a id="pharmacist" href="controller.do?command=searchPharmacist&pharmacistId=${itemPharmacist.id}" title="view details">
				        					Dr. ${itemPharmacist.name}
			        					</a>
		        					</c:forEach>
								</div>
			        		</c:if>
			        		<c:if test="${empty itemConsultation.pharmacistList}">
			        			<div id="cell3">Consultation has no Pharmacists!</div>
			        		</c:if>
			        		<div id="cell4">${itemConsultation.description}</div>
			        	</div>
		        	</c:forEach>
	        	</div>
        	</c:if>
        	<c:if test="${empty consultationList}">
        		<h3>Patient has no Consultations!</h3>
        	</c:if>
        </c:if>
        <c:if test="${empty hrPatient}">
	        <h1>No specific Patient found for the search argument passed!</h1>
        </c:if>
        <a href="index.jsp" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
