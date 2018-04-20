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
        <c:if test="${not empty consultation}">
	        <h1>Consultation created and added successfully to Patient's Health Record:</h1>
        	<div id="table">
	        	<div id="row">
	        		<div id="cellHeader1">Id</div><div id="cellHeader2">DateTime</div><div id="cellHeader3">Description</div><div id="cellHeader4">Patient</div><div id="cellHeader5">Pharmacist(s)</div>
	        	</div>
	        	<div id="row">
	        		<div id="cell1">${consultation.id}:</div>
	        		<div id="cell2">${consultation.dateTimeString}</div>
        			<div id="cell3">${consultation.description}</div>
        			<div id="cell4">
        				<a href="controller.do?command=searchPatient&patientId=${patient.id}" title="view details">
     						${patient.name}
    					</a>
        			</div>
        			<div id="cell5">
	        			<c:forEach items="${consultation.pharmacistList}" var="pharmacist">
	        				<a href="controller.do?command=searchPharmacist&pharmacistId=${pharmacist.id}" title="view details">
	     						Dr. ${pharmacist.name}
	    					</a>
	        			</c:forEach>
        			</div>
	        	</div>
        	</div>
       	</c:if>
       	<c:if test="${empty consultation}">
       		<h3>Consultation not added to the Patient's Health Record!</h3>
       	</c:if>
        <a href="index.jsp" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
