<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 25, 2008
  Time: 1:31:13 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Story: Families</title></head>
  <body>


  <h1>Your Family of Stories</h1>


  <p />

  <ul>
     <g:each var="fam" in="${families}">

        <li>
           <g:link action="family" controller="story" id="${fam.id}">${fam.name}</g:link>
        </li>

     </g:each>
  </ul>

    
  </body>
</html>