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


  <h1>Your Story Families</h1>


  <g:render template="familymenubar" />


  <p />

  <div id="story-list">
     <table border="0">
        <g:each var="fam" in="${families}">
              <tr>
                 <td><g:link action="family" controller="story" id="${fam.id}">${fam.name}</g:link></td>
              </tr>

        </g:each>

     </table>
  </div>

    
  </body>
</html>