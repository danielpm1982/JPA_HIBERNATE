<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <h1>Order Delete FAILED!</h1>
        <h3>Order was not deleted from Customer at the DB!</h3>
        <h3>(check the Id and the Customer existence, as well as the Order name, and try again)</h3>
        <a href="index.html" title="Go Home"><img src="home.png" alt="homePage"><br>HOME</a>
    </body>
</html>
