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
            <c:if test="${insertResult==true}">
                <p id="insertResultTrue">INSERTED SUCCESSFULLY !</p>
            </c:if>
            <c:if test="${insertResult==false}">
                <p id="insertResultFalse">INSERT FAILED !</p>
            </c:if>
            <p id="insertResult">Client:</p>
            <h2>Client ID: ${client.id}</h2>
            <h2>Name: ${client.name}</h2>
            <h2>Address: ${address.street}, ${address.streetNumber}</h2>
            <h2>Neighborhood: ${address.neighborhood}</h2>
            <h2>City: ${address.city} - State: ${address.state}</h2>
            <h2>Country: ${address.country}</h2>
            <br><br>
        </div>
        <div id="link">
            <a href="ListClientController">Click to list all Clients</a><br>
            <a href="listSingleClient.jsp">Click to list a Client</a>
        </div>
    </body>
</html>
