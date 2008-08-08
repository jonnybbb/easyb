<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 8, 2008
  Time: 12:57:07 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Easiness: Story Execution</title></head>



  <body>
  <h1>Your Stories</h1>


  <g:render template="storymenubar" />
  <p />

  <g:if test="${stories?.size > 0}">
     <span class="note">Total Stories: ${stories.size}</span>
  </g:if>

  <p></p>
  
  Instructions and functionality to run your stories will be placed here.
  </body>

</html>