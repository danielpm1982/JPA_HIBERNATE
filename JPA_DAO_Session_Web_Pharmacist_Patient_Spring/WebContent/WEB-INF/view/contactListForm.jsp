<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Spring MVC Web</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/css.css" />
	</head>
	<body>
		<h1>ContactList formulary</h1>
		<form action="contactListFormResult" method="POST">
			<div class="table">
				<div class="tableRow">
					<div class="tableCellLeft">
						<label>Phone1: </label>
					</div>
					<div class="tableCellRight">
						<input type="text" name="phone1" title="Type your phone 1!" id="phone1" value="${contactList[0]}"/>
					</div>
				</div>
				<div class="tableRow">
					<div class="tableCellLeft">
						<label>Phone2: </label>
					</div>
					<div class="tableCellRight">
						<input type="text" name="phone2" title="Type your phone 2!" id="phone2" value="${contactList[1]}" />
					</div>
				</div>
				<div class="tableRow">
					<div class="tableCellLeft">
						<label>Phone3: </label>
					</div>
					<div class="tableCellRight">
						<input type="text" name="phone3" title="Type your phone 3!" id="phone3" value="${contactList[2]}" />
					</div>
				</div>
			</div>
			<input type="submit" value="SUBMIT">
		</form>
	</body>
</html>
