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
            	width: 30%;
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
            div#cellLeft{
            	background-color: green;
            	display: table-cell;
            	width: 22%;
            	text-align: right;
            }
            div#cellRight{
            	background-color: teal;
            	display: table-cell;
            	text-align: left;
            }
            div#cellLeftHeader{
            	display: table-cell;
            	width: 22%;
            	text-align: center;
            	background-color: maroon;
            	font-size: 1.5em;
            	font-weight: bold;
            }
            div#cellRightHeader{
            	display: table-cell;
            	text-align: center;
            	background-color: maroon;
            	font-size: 1.5em;
            	font-weight: bold;
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
        </style>
    </head>
    <body>
        <h1>Search successful! Student(s) found:</h1>
        <h3>${lastSearchedStudents}</h3>
        <div id="table">
        	<div id="row">
        		<div id="cellLeftHeader">Id</div><div id="cellRightHeader">Name</div>
        	</div>
        	<c:forEach items="${allStudentList}" var="item">
	        	<div id="row">
	        		<div id="cellLeft">${item.id}:</div><div id="cellRight">${item.name}</div>
	        	</div>
        	</c:forEach>
        </div>
        <a href="index.html" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
