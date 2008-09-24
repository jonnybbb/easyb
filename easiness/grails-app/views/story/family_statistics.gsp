<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Sep 5, 2008
  Time: 5:45:10 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Easiness: Family Statistics</title></head>
<body>
<h1>Your Families</h1>


<g:render template="familymenubar" />
<p />


<p></p>

<g:each var="fam" in="${families}">

   <h2>${fam.name}</h2>
   <div class="story-list">
      <table class="story-table" border="0">
         <tr style="font-weight: bold;">
            <td>Story</td>
            <td>Failed</td>
            <td>Pending</td>
            <td>Success</td>
         </tr>
         <g:each var="st" in="${fam.stories}">
         <tr>
            <g:if test="${st.lastReport() != null}">
               <td>${st.title}</td>
               <td style="text-align: center;">${st.lastReport().failure}</td>
               <td style="text-align: center;">${st.lastReport().pending}</td>
               <td style="text-align: center;">${st.lastReport().success}</td>
            </g:if>
            <g:if test="${st.lastReport() == null}">
               <td class="story_error">${st.title}</td>
               <td colspan="3" style="text-align: center;" class="info"> not running properly </td>
            </g:if>

         </tr>
         </g:each>
      </table>
   </div>

</g:each>

</body>
</html>