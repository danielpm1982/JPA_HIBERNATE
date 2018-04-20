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
        <c:if test="${not empty pharmacist}">
	        <h1>Pharmacist:</h1>
        	<div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div><div id="cellHeader5">Clinical Specialty</div>
	        	</div>
        		<div id="cell1">${pharmacist.id}:</div>
        		<div id="cell2">Dr. ${pharmacist.name}</div>
       			<div id="cell3">${pharmacist.address}</div>
        		<div id="cell4">${pharmacist.contactList[0]} ${pharmacist.contactList[1]} ${pharmacist.contactList[2]}</div>
        		<div id="cell5">${pharmacist.specialty}</div>
        	</div>
        	<c:if test="${not empty consultationList}">
	        	<h1>Consultations:</h1>
	        	<div id="table">
		        	<div id="row">
		        		<div id="cellHeader1">Id</div><div id="cellHeader2">DateTime</div><div id="cellHeader3">Pharmaceutical Doctor</div><div id="cellHeader4">Description</div><div id="cellHeader5">HR</div>
		        	</div>
		        	<c:forEach items="${consultationList}" var="itemConsultation">
			        	<div id="row">
			        		<div id="cell1">${itemConsultation.id}:</div>
			        		<div id="cell2">${itemConsultation.dateTimeString}</div>
			        		<c:if test="${not empty itemConsultation.pharmacistList}">
			        			<div id="cell3">
			        				<c:forEach items="${itemConsultation.pharmacistList}" var="itemPharmacist">
				        				<a href="controller.do?command=searchPharmacist&pharmacistId=${itemPharmacist.id}" title="view details">
				        					Dr. ${itemPharmacist.name}
			        					</a>
		        					</c:forEach>
								</div>
			        		</c:if>
			        		<c:if test="${empty itemConsultation.pharmacistList}">
			        			<div id="cell3">Consultation has no Pharmacists!</div>
			        		</c:if>
			        		<div id="cell4">${itemConsultation.description}</div>
			        		<div id="cell5">
			        			<a id="healthRecord" href="controller.do?command=hr2&hrId=${itemConsultation.healthRecordId}" title="view details">
			        				<img src="healthRecord.png" alt="health record" title="health record" id="healthRecord2" />
	        					</a>
			        		</div>
			        	</div>
		        	</c:forEach>
	        	</div>
       		</c:if>
        	<c:if test="${empty consultationList}">
	        	<h1>No consultations for this Pharmacist!</h1>
        	</c:if>
        	<h1 id="allPharmacists">All Pharmacists:</h1>
	        <div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div><div id="cellHeader5">Clinical Specialty</div>
	        	</div>
	        	<c:forEach items="${allPharmacistsList}" var="pharmacist">
	        		<div id="row">
		        		<div id="cell1">${pharmacist.id}:</div>
		        		<div id="cell2">
		        			<a href="controller.do?command=searchPharmacist&pharmacistId=${pharmacist.id}" title="view details">
	        					Dr. ${pharmacist.name}
        					</a>
		        		</div>
		       			<div id="cell3">${pharmacist.address}</div>
		        		<div id="cell4">${pharmacist.contactList[0]} ${pharmacist.contactList[1]} ${pharmacist.contactList[2]}</div>
		        		<div id="cell5">${pharmacist.specialty}</div>
	        		</div>
        		</c:forEach>
        	</div>	
        </c:if>
        <c:if test="${empty pharmacist}">
	        <h1>No specific Pharmacist found or inserted !</h1>
	        <h1 id="allPharmacists">All Pharmacists:</h1>
	        <div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Contact</div><div id="cellHeader5">Clinical Specialty</div>
	        	</div>
	    		<c:if test="${!empty allPharmacistsList}">
		        	<c:forEach items="${allPharmacistsList}" var="pharmacist">
		        		<div id="row">
			        		<div id="cell1">${pharmacist.id}:</div>
			        		<div id="cell2">
			        			<a href="controller.do?command=searchPharmacist&pharmacistId=${pharmacist.id}" title="view details">
		        					Dr. ${pharmacist.name}
	        					</a>
			        		</div>
			       			<div id="cell3">${pharmacist.address}</div>
			        		<div id="cell4">${pharmacist.contactList[0]} ${pharmacist.contactList[1]} ${pharmacist.contactList[2]}</div>
			        		<div id="cell5">${pharmacist.specialty}</div>
		        		</div>
	        		</c:forEach>
        		</c:if>
        	</div>
        	<c:if test="${empty pharmacist && empty allPharmacistsList}">
        		<h1>No Pharmacists at the DataBase!</h1>	
        	</c:if>
        </c:if>
        <a href="index.jsp" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
