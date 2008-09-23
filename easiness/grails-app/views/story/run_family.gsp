<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Sep 5, 2008
  Time: 5:46:44 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Easiness: Story Family Execution</title></head>



<body>
<h1>Your Families</h1>


<g:render template="familymenubar" />
<p />

<p></p>

<p class="info">Select the story families you'd like to run below, and click 'Run', and the stories will be executed.</p>
<div id="story-list">
   <g:form controller="story" action="do_run_families">
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
      <g:submitButton name="Run" value="Run" />
   </g:form>
</div>
</body>
</html>