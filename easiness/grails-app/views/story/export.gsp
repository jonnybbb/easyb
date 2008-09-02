<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 8, 2008
  Time: 12:57:18 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Easiness: Story Export</title></head>
  <body>

  <h1>Your Stories</h1>


  <g:render template="storymenubar" />
  <p />

  <g:if test="${stories?.size() > 0}">
     <span class="note">Total Stories: ${stories.size()}</span>
  </g:if>

  <p class="info">Select the stories you'd like to export below, and click 'Export', and they will be written to the filesystem.</p>
  <div id="story-list">
     <g:form controller="story" action="do_export_stories">
     <table class="story-table" border="0">
        <g:each var="story" in="${stories}">
           <tr>
              <td><g:checkBox name="story_${story.id}" /></td>
              <td width="90%"><g:link action="expand" controller="story" id="${story.id}" title="${story.description}">
                  ${story.title}
              </g:link></td>
           </tr>
        </g:each>
     </table>
        <p />
        <g:submitButton name="Export" value="Export" />
     </g:form>
  </div>
  </body>
</html>