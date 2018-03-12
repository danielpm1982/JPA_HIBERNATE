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
            <form action="ListSingleClientControllerById" method="post">
                <fieldset id="field1">
                    <input type="text" name="idClient" placeholder="Insert Id" title="Insert Id to list Client">
                    <input type="submit" value="List Client">
                </fieldset>
            </form>
            <form action="ListSingleClientControllerByName" method="post">
                <fieldset id="field1">
                    <input type="text" name="clientName" placeholder="Insert Name" title="Insert Name to list Client">
                    <input type="submit" value="List Client">
                </fieldset>
            </form>
        <div id="link">
            <a href="listSingleClient.jsp">Click to reset fields</a><br>
            <a href="ListClientController">Click to list all Clients</a><br>
        </div>
    </body>
</html>
