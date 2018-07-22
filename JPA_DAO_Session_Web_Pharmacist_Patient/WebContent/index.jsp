<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>PharmacistWebApp</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css.css" rel="stylesheet" type="text/css">
    </head>
    <body>
		<header>
			<img id="pharmacy" src="header.png" />
<!--         <h1>PHARMACIST_PATIENT_WEB_APP</h1> -->
		</header>
        <form action="controller.do?command=searchPatient" method="post">
        	<h1>Search Patient</h1>
        	<div class="formRow">
	        	<label for="searchId">Id:</label>
	        	<input type="number" min="1" name="patientId" title="insert the Id to search for" id="searchId" placeholder="Insert the Id to search for"><br>
        	</div>
        	<h2>or</h2>
        	<div class="formRow">
	        	<h3>Type nothing to list All Patients</h3>
        	</div>
        	<input type="submit" value="SUBMIT">
        </form>
        <form action="controller.do?command=insertPatient" method="post">
        	<h1>Insert Patient</h1>
        	<div class="formRow">
	        	<label for="name">Name:</label>
	        	<input type="text" name="name" title="Insert the name of the Patient" id="name" placeholder="Insert the name of the Patient" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="street">Street:</label>
	        	<input type="text" name="street" title="Insert the street name" id="street" placeholder="Insert the street name" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="streetNumber">Street Number:</label>
	        	<input type="number" min="1" name="streetNumber" title="Insert the street number" id="street" placeholder="Insert the street number" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="city">City:</label>
	        	<input type="text" name="city" title="Insert the city" id="city" placeholder="Insert the city" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="state">State:</label>
	        	<input type="text" name="state" title="Insert the state" id="state" placeholder="Insert the state" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="country">Country:</label>
	        	<input type="text" name="country" title="Insert the country" id="country" placeholder="Insert the country" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="email">Email:</label>
	        	<input type="text" name="email" title="Insert the email" id="email" placeholder="Insert the email" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone1">Phone1:</label>
	        	<input type="text" name="phone1" title="Phone1" id="phone1" placeholder="Phone1" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone2">Phone2:</label>
	        	<input type="text" name="phone2" title="Phone2" id="phone2" placeholder="Phone2"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone3">Phone3:</label>
	        	<input type="text" name="phone3" title="Phone3" id="phone3" placeholder="Phone3"><br>
        	</div>
        	<input type="submit" value="SUBMIT">
        </form>
        <form action="controller.do?command=updatePatient" method="post">
        	<h1>Update Patient</h1>
        	<div class="formRow">
	        	<label for="updateId">Id:</label>
	        	<input type="number" min="1" name="updateId" title="Insert the Id of the Patient" id="updateId" placeholder="Insert the Id of the Patient" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="name">Name:</label>
	        	<input type="text" name="name" title="Insert the name of the Patient" id="name" placeholder="Insert the name of the Patient" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="street">Street:</label>
	        	<input type="text" name="street" title="Insert the street name" id="street" placeholder="Insert the street name" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="streetNumber">Street Number:</label>
	        	<input type="number" min="1" name="streetNumber" title="Insert the street number" id="street" placeholder="Insert the street number" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="city">City:</label>
	        	<input type="text" name="city" title="Insert the city" id="city" placeholder="Insert the city" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="state">State:</label>
	        	<input type="text" name="state" title="Insert the state" id="state" placeholder="Insert the state" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="country">Country:</label>
	        	<input type="text" name="country" title="Insert the country" id="country" placeholder="Insert the country" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="email">Email:</label>
	        	<input type="text" name="email" title="Insert the email" id="email" placeholder="Insert the email" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone1">Phone1:</label>
	        	<input type="text" name="phone1" title="Phone1" id="phone1" placeholder="Phone1" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone2">Phone2:</label>
	        	<input type="text" name="phone2" title="Phone2" id="phone2" placeholder="Phone2"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone3">Phone3:</label>
	        	<input type="text" name="phone3" title="Phone3" id="phone3" placeholder="Phone3"><br>
        	</div>
        	<input type="submit" value="SUBMIT">
        </form>
        <form action="controller.do?command=deletePatient" method="post">
        	<h1>Delete Patient</h1>
        	<div class="formRow">
	        	<label for="deleteId">Id:</label>
	        	<input type="number" min="1" name="patientId" title="insert the Id to delete" id="deleteId" placeholder="Insert the Id to delete" required="required"><br>
        	</div>
        	<input type="submit" value="SUBMIT">
        </form>
        <form action="controller.do?command=searchPharmacist" method="post">
        	<h1>Search Pharmacist</h1>
        	<div class="formRow">
	        	<label for="searchId">Id:</label>
	        	<input type="number" min="1" name="pharmacistId" title="insert the Id to search for" id="searchId" placeholder="Insert the Id to search for"><br>
        	</div>
        	<h2>or</h2>
        	<div class="formRow">
	        	<h3>Type nothing to list All Pharmacists</h3>
        	</div>
        	<input type="submit" value="SUBMIT">
        </form>
        <form action="controller.do?command=insertPharmacist" method="post">
        	<h1>Insert Pharmacist</h1>
        	<div class="formRow">
	        	<label for="name">Name:</label>
	        	<input type="text" name="name" title="insert the Name of the Pharmacist" id="name" placeholder="Insert the Name of the Pharmacist"><br>
        	</div>
			<div class="formRow">
	        	<label for="street">Street:</label>
	        	<input type="text" name="street" title="Insert the street name" id="street" placeholder="Insert the street name" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="streetNumber">Street Number:</label>
	        	<input type="number" min="1" name="streetNumber" title="Insert the street number" id="street" placeholder="Insert the street number" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="city">City:</label>
	        	<input type="text" name="city" title="Insert the city" id="city" placeholder="Insert the city" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="state">State:</label>
	        	<input type="text" name="state" title="Insert the state" id="state" placeholder="Insert the state" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="country">Country:</label>
	        	<input type="text" name="country" title="Insert the country" id="country" placeholder="Insert the country" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="email">Email:</label>
	        	<input type="text" name="email" title="Insert the email" id="email" placeholder="Insert the email" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone1">Phone1:</label>
	        	<input type="text" name="phone1" title="Phone1" id="phone1" placeholder="Phone1" required="required"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone2">Phone2:</label>
	        	<input type="text" name="phone2" title="Phone2" id="phone2" placeholder="Phone2"><br>
        	</div>
        	<div class="formRow">
	        	<label for="phone3">Phone3:</label>
	        	<input type="text" name="phone3" title="Phone3" id="phone3" placeholder="Phone3"><br>
        	</div>
        	<div class="formRow">
	        	<label for="spacialty">Specialty:</label>
	        	<select required="required" name="specialty">
	        		<optgroup>
		        		<option value="" disabled="disabled" selected="selected">Please select a specialty</option>
	    				<c:forEach items="${specialtyType}" var="specialty">
	    					<option value="${specialty}">${specialty}</option>
	    				</c:forEach>
    				</optgroup>
	        	</select>
        	</div>
        	<input type="submit" value="SUBMIT">
        </form>
        <form action="controller.do?command=addConsultation" method="post">
        	<h1>Insert Consultation and Add to Patient</h1>
        	<div class="formRow">
        		<div class="formRow">
		        	<label for="patientId">Patient Id:</label>
		        	<input type="number" min="1" name="patientId" title="insert the Id to delete" id="patientId" placeholder="Insert the patient Id to add the Consultation" required="required"><br>
        		</div>
        		<div class="formRow">
		        	<label for="description">Consultation Description:</label>
		        	<input type="text" name="description" title="insert the Description" id="description" placeholder="Insert the Description" required="required"><br>
        		</div>
        		<div class="formRow">
		        	<label for="pharmacistId">Pharmacist Id:</label>
		        	<input type="number" min="1" name="pharmacistId" title="insert the Pharmacist Id" id="pharmacistId" placeholder="Insert the Pharmacist Id"><br>
        		</div>
        	</div>
        	<input type="submit" value="SUBMIT">
        </form>
    </body>
</html>
