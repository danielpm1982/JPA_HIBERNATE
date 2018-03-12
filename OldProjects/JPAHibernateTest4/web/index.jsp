<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="css.css">
    </head>
    <body>
        <form action="AddClientController" method="post">
            <fieldset name="field1" id="field1">
                <table>
                    <tr>
                        <td><label for="clientName">Name:</label></td>
                        <td colspan="3"><input type="text" name="name" id="clientName" autofocus required></td>
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
                <input type="submit" value="Send">
            </fieldset>
            <div id="link">
                <a href="ListClientController">Click to list all Clients</a><br>
                <a href="listSingleClient.jsp">Click to list a Client</a>
            </div>
        </form>
    </body>
</html>
