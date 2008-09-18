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


     <g:render template="storymenubar" />


     <p />
     <g:if test="${stories?.size > 0}">
        <span class="note">Total Stories: ${stories.size}</span>
        <div id="story-list">
        <table class="story-table" border="0">
           <g:each var="story" in="${stories}">
              <tr>
                 <td width="70%"><g:link action="expand" controller="story" id="${story.id}">
                     ${story.title}
                 </g:link></td>
                 <td width="15%"> <g:link action="do_run" controller="story" id="${story.id}">run</g:link> </td>
                 <td width="15%"> <g:link action="edit" controller="story" id="${story.id}">edit</g:link> </td>
              </tr>
           </g:each>
        </table>
        </div>
     </g:if>
     <g:if test="${stories == null || stories.size == 0}">
        <em>You don't have any stories.  Why not <g:link action="create" controller="story">create one</g:link>?</em>
     </g:if>


  </body>
</html>