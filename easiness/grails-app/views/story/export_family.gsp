<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Sep 5, 2008
  Time: 5:48:25 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Easiness: Story Family Export</title></head>
<body>

<h1>Your Story Families</h1>


<g:render template="familymenubar" />
<p />


<p class="info">Select the story familiy you'd like to export below, and click 'Export', and the stories will be written to the filesystem.</p>
<div id="story-list">
   <g:form controller="story" action="do_export_families">
   <table class="story-table" border="0">
      <g:each var="family" in="${families}">
         <tr>
            <td><g:checkBox name="family_${family.id}" /></td>
            <td width="90%"><g:link action="family" controller="story" id="${family.id}">
                ${family.name}
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