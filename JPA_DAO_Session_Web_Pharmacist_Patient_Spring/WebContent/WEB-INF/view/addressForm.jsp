<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Spring MVC Web</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/css.css" />
	</head>
	<body>
		<h1>Address formulary</h1>
		<form:form action="addressFormResult" method="POST" modelAttribute="address">
			<div class="table">
				<div class="tableRow">
					<div class="tableCellLeft">
						<form:label path="street">Street: </form:label>
					</div>
					<div class="tableCellRight">
						<form:input path="street" title="Type your street name!" id="street" required="required"/>
					</div>
				</div>
				<div class="tableRow">
					<div class="tableCellLeft">
						<form:label path="streetNumber">Number: </form:label>
					</div>
					<div class="tableCellRight">
						<form:input path="streetNumber" title="Type your street number!" id="streetNumber" required="required" />
					</div>
				</div>
				<div class="tableRow">
					<div class="tableCellLeft">
						<form:label path="city">City: </form:label>
					</div>
					<div class="tableCellRight">
						<form:input path="city" title="Type your city name!" id="city" required="required"/>
					</div>
				</div>
				<div class="tableRow">
					<div class="tableCellLeft">
						<form:label path="state">State: </form:label>
					</div>
					<div class="tableCellRight">
						<form:input path="state" title="Type your state name!" id="state" required="required"/>
					</div>
				</div>
				<div class="tableRow">
					<div class="tableCellLeft">
						<form:label path="country">Country: </form:label>
					</div>
					<div class="tableCellRight">
						<form:input path="country" title="Type your country name!" id="country" required="required"/>
					</div>
				</div>
				<div class="tableRow">
					<div class="tableCellLeft">
						<form:label path="email">Email: </form:label>
					</div>
					<div class="tableCellRight">
						<form:input path="email" title="Type your email!" id="email" required="required"/>
					</div>
				</div>
			</div>
			<input type="submit" value="SUBMIT">
		</form:form>
	</body>
</html>
