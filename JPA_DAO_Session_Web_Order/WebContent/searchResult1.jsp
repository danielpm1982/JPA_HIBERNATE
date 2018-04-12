<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>StudentAppWeb</title>
        <style>
            body{
                font-family: sans-serif;
                text-align: center;
                background-color: black;
                color: blanchedalmond;
                margin-top: 5em;
                margin-bottom: 5em;
            }
            div#table{
            	width: 80%;
            	margin: auto;
            	margin-top: 2em;
            	padding: 0.5em;
            	font-size: 1.5em;
            	display: table;
            	border-collapse: separate;
            	border-spacing: 0.5em;
            }
            div#row{
            	display: table-row;
            }
            div#cell1{
            	background-color: green;
            	display: table-cell;
            	text-align: right;
            }
            div#cell2, div#cell3, div#cell4{
            	background-color: teal;
            	display: table-cell;
            	text-align: left;
            }
            div#cellHeader1, div#cellHeader2, div#cellHeader3, div#cellHeader4{
            	display: table-cell;
            	text-align: center;
            	background-color: maroon;
            	font-size: 1.5em;
            	font-weight: bold;
            }
            div#cellHeader1{
            	width: 10%;
            }
            div#cellHeader2{
            	width: 15%;
            }
            div#cellHeader3{
            	width: 40%;
            }
            div#cellHeader4{
            }
            img{
            	margin-top: 5em;
            	width: 5%;
            }
            a:link {
			    color: aqua;
			}
			a:visited {
			    color: green;
			}
			a:hover {
			    color: lime;
			}
			a:active {
			    color: blue;
			}
			h1#allCustomers{
				margin-top: 2em;
				margin-bottom: -1em;
			}
        </style>
    </head>
    <body>
        <h1>Search successful for id=${lastCustomer.id}:</h1>
        <h3>${lastCustomer}</h3>
        <h1 id="allCustomers">All Customers:</h1>
        <div id="table">
        	<div id="row">
        		<div id="cellHeader1">Id</div><div id="cellHeader2">Name</div><div id="cellHeader3">Address</div><div id="cellHeader4">Orders</div>
        	</div>
        	<c:forEach items="${allCustomersList}" var="item">
	        	<div id="row">
	        		<div id="cell1">${item.id}:</div>
	        		<div id="cell2">${item.name}</div>
	        		<div id="cell3">${item.address}</div>
	        		<div id="cell4">
	        			<c:choose>
	        				<c:when test="${not empty item.order}">${item.order}</c:when>
	        				<c:otherwise>No Orders!</c:otherwise>
        				</c:choose>
        			</div>
	        	</div>
        	</c:forEach>
        </div>
        <a href="index.html" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
