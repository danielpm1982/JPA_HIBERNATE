<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RESULT Page</title>
        <link rel="stylesheet" type="text/css" href="css.css">
    </head>
    <body>
        <div id="divResult">
            <c:if test="${updateResult==true}">
                <p id="insertResultTrue">UPDATED SUCCESSFULLY !</p>
            </c:if>
            <c:if test="${updateResult==false}">
                <p id="insertResultFalse">UPDATE FAILED !</p>
            </c:if>
            <p id="insertResult">Client Updated:</p>
            <h2>Client ID: ${client2.id}</h2>
            <h2>Name: ${client2.name}</h2>
            <h2>Address: ${address2.street}, ${address2.streetNumber}</h2>
            <h2>Neighborhood: ${address2.neighborhood}</h2>
            <h2>City: ${address2.city} - State: ${address2.state}</h2>
            <h2>Country: ${address2.country}</h2>
            <br><br>
        </div>
        <div id="link">
            <a href="ListClientController">Click to list all Clients</a><br>
            <a href="listSingleClient.jsp">Click to list a Client</a>
        </div>
    </body>
</html>
