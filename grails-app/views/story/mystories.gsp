<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Jul 22, 2008
  Time: 4:37:56 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Easiness</title></head>
  <body>

     <h1>Your Stories</h1>

     <g:if test="${stories?.size > 0}">
        <span class="info">Total Stories: ${stories.size}</span>
        <div id="story-list">
        <g:each var="story" in="${stories}">
           <p ><g:link action="edit" controller="story" id="${story.id}">
               ${story.title}
           </g:link></p>
        </g:each>
        </div>
     </g:if>
     <g:if test="${stories == null || stories.size == 0}">
        <em>You don't have any stories.  Why not <g:link action="create" controller="story">create one</g:link>?</em>
     </g:if>


  </body>
</html>