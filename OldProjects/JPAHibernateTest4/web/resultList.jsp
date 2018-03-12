<%@page import="sun.tools.jar.resources.jar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RESULT Page</title>
        <link rel="stylesheet" type="text/css" href="css.css">
    </head>
    <body>
        <div id="divResult2">
            <div>
                <table class="t2">
                    <tr>
                        <th class="th2">Client_Id</th>
                        <th class="th2">Name</th>
                        <th class="th2">Address_Id</th>
                        <th class="th2">Street</th>
                        <th class="th2">Street_Number</th>
                        <th class="th2">Neighborhood</th>
                        <th class="th2">City</th>
                        <th class="th2">State</th>
                        <th class="th2">Country</th>
                    </tr>
                    <c:forEach items="${allClientsList}" var="client">
                        <tr>
                            <td id="2" class="d01">${client.id}</td>
                            <td id="2" class="d02">${client.name}</td>
                            <td id="2" class="d01">${client.address.id}</td>
                            <td id="2" class="d02">${client.address.street}</td>
                            <td id="2" class="d01">${client.address.streetNumber}</td>
                            <td id="2" class="d02">${client.address.neighborhood}</td>
                            <td id="2" class="d01">${client.address.city}</td>    
                            <td id="2" class="d02">${client.address.state}</td>    
                            <td id="2" class="d01">${client.address.country}</td>    
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <form action="RemoveClientController" method="post">
                <input type="text" name="idClient" placeholder="Insert Id" title="Insert Id to remove Client" required>
                <input type="submit" value="Remove Client By Id">
            </form>
            
            <div id="link">
                <p id="date">last update: ${now}</p>
                <a href="ListClientController">Update List</a><br>
                <a href="listSingleClient.jsp">Click to list a Client</a><br>
                <a href="index.jsp">Click to add a Client</a><br>
                <a href="updateClient.jsp">Click to update a Client</a>
            </div>
        </div>
    </body>
</html>
