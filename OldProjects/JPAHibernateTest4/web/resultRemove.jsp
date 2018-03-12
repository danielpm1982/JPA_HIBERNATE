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
            <c:if test="${removeResult==true}">
                <p id="insertResultTrue">REMOVED SUCCESSFULLY !</p>
            </c:if>
            <c:if test="${removeResult==false}">
                <p id="insertResultFalse">REMOVE FAILED !</p>
            </c:if>
        </div>
        <div id="link">
            <a href="ListClientController">Click to list all Clients</a><br>
            <a href="listSingleClient.jsp">Click to list a Client</a>
        </div>
    </body>
</html>
