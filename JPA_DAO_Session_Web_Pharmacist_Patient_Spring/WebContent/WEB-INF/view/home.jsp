<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
    <head>
        <title>PharmacistWebApp</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/resources/css/css.css" rel="stylesheet" type="text/css">
    </head>
    <body>
		<header>
			<img id="pharmacy" src="${pageContext.request.contextPath}/resources/img/header.png" />
        <h1>PHARMACIST_PATIENT_WEB_APP</h1>
		</header>
        <form action="searchPatientResult" method="get">
        	<h1>Search Patient</h1>
        	<div class="formRow">
	        	<label for="searchId">Id:</label>
	        	<input type="number" min="1" name="patientId" title="insert the Id to search for" id="searchId" placeholder="Insert the Id to search for" />
        	</div>
        	<h2>or</h2>
        	<div class="formRow">
	        	<h3>Type nothing to list All Patients</h3>
        	</div>
        	<input type="submit" value="SUBMIT">
        </form>
        <form action="insertPatientResult" method="post">
        	<h1>Insert Patient</h1>
        	<div class="formRow">
	        	<label for="name">Name:</label>
	        	<input type="text" name="name" title="Insert the name of the Patient" id="name" placeholder="Insert the name of the Patient" required="required" value="${patient.name}" />
	        	<h1>Address</h1>
        		<c:if test="${not empty address.street}">
        			<h3>${address}</h3>
        		</c:if>
        		<c:if test="${empty address.street}">
		        	<div class="formRow">
		        		<h3>Empty address! (required)</h3>
		        	</div>
	        	</c:if>
	        	<input type="button" value="Insert/Update Address" onclick="window.location.href = 'addressForm';" />
	        	<h1>Contact</h1>
        		<c:if test="${not empty contactList}">
        			<h3>${contactList[0]} <br> ${contactList[1]} <br> ${contactList[2]}</h3>
        		</c:if>
        		<c:if test="${empty contactList}">
		        	<div class="formRow">
		        		<h3>Empty contactList! (optional)</h3>
		        	</div>
	        	</c:if>
	        	<input type="button" value="Insert/Update ContactList" onclick="window.location.href = 'contactListForm';" />
        	</div>
        	<input type="submit" value="SUBMIT">
        	<input type="button" value="RESET" onclick="window.location.href = 'newPatient';" />
        </form>
    </body>
</html>
