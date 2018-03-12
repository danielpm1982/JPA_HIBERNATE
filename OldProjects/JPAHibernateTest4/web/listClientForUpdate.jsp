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
        <div id="divResult3">
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
                    <tr>
                        <td id="2" class="d01">${clientResult.id}</td>
                        <td id="2" class="d02">${clientResult.name}</td>
                        <td id="2" class="d01">${clientResult.address.id}</td>
                        <td id="2" class="d02">${clientResult.address.street}</td>
                        <td id="2" class="d01">${clientResult.address.streetNumber}</td>
                        <td id="2" class="d02">${clientResult.address.neighborhood}</td>
                        <td id="2" class="d01">${clientResult.address.city}</td>    
                        <td id="2" class="d02">${clientResult.address.state}</td>    
                        <td id="2" class="d01">${clientResult.address.country}</td>    
                    </tr>
                </table>
            </div>
        </div>
        <form action="UpdateClientController" method="post">
            <input type="text" name="idClient" value="${clientResult.id}" hidden>
            <fieldset name="field2" id="field2">
                <h3>Please fill out in the fields of which information you would like to update:</h3>
                <table>
                    <tr>
                        <td><label for="clientName">Name:</label></td>
                        <td colspan="3"><input type="text" name="name" id="clientName" autofocus></td>
                    </tr>
                    <tr>
                        <td><label for="addressStreet">Street:</label></td>
                        <td><input type="text" name="street" id="addressStreet"></td>
                        <td><label for="addressStreetNumber">Number:</label></td>
                        <td><input type="text" name="streetNumber" id="addressStreetNumber"></td>
                    </tr>
                    <tr>
                        <td><label for="addressNeighborhood">Neighborhood:</label></td>
                        <td><input type="text" name="neighborhood" id="addressNeighborhood"></td>
                        <td><label for="addressCity">City:</label></td>
                        <td><input type="text" name="city" id="addressCity"></td>
                    </tr>
                    <tr>
                        <td><label for="addressState">State:</label></td>
                        <td><input type="text" name="state" id="addressState"></td>
                        <td><label for="addressCountry">Country:</label></td>
                        <td><input type="text" name="country" id="addressCountry"></td>
                    </tr>
                </table>
                <br>
                <input type="submit" value="Update">
            </fieldset>
            <div id="link">
                <a href="ListClientController">Click to list all Clients</a><br>
                <a href="listSingleClient.jsp">Click to list a Client</a>
            </div>
        </form>
    </body>
</html>
