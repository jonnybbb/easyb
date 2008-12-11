<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 8, 2008
  Time: 12:57:27 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Easiness: Story Statistics</title></head>
  <body>
  <h1>Your Stories</h1>


  <g:render template="storymenubar" />
  <p />
  

  <g:if test="${stories?.size > 0}">
     <span class="note">Total Stories: ${stories.size}</span>
  </g:if>

  <p></p>


  <div id="story-list">
     <table class="story-table" border="0">
        <tr style="font-weight: bold;">
           <td>Story</td>
           <td>Failed</td>
           <td>Pending</td>
           <td>Success</td>
        </tr>
        <g:each var="st" in="${stories}">
        <tr>
           <g:if test="${st.lastReport() != null}">
              <td>${st.title}</td>
              <td style="text-align: center;">${st.lastReport().failures}</td>
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

  </body>
</html>