<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 25, 2008
  Time: 1:35:04 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Story: Family</title></head>
  <body>

     <h1>Family: '${family.name}' </h1>

     <g:render template="familymenubar" />

     <p />
     <g:if test="${stories?.size() > 0}">
        <span class="note">Total Stories: ${stories.size()}</span>
        <div id="story-list">
        <table class="story-table" border="0">
           <g:each var="story" in="${stories}">
              <tr>
                 <td width="70%"><g:link action="expand" controller="story" id="${story.id}">
                     ${story.title}
                 </g:link></td>
                 <td width="15%"> run </td>
                 <td width="15%"> <g:link action="edit" controller="story" id="${story.id}">
                    <ezi:hasRights type="context">
                       edit
                    </ezi:hasRights>
                    <ezi:hasRights type="context" not="true">
                       view
                    </ezi:hasRights>
                 </g:link> </td>
              </tr>
           </g:each>
        </table>
        </div>
     </g:if>
     <g:if test="${stories == null || stories.size == 0}">
        <em>There are no stories yet in family ${family.name}. Why not <g:link action="create" controller="story">create one</g:link>?</em>
     </g:if>



  </body>
</html>