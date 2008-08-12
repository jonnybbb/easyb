<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 12, 2008
  Time: 12:39:18 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Admin: User Families</title></head>
  <body>
     <h2>User Families</h2>
     <p class="info">Use the select box below to choose the families for: ${user.name}</p>

     <g:form action="do_update_userfam" controller="admin" method="post">
        <g:hiddenField name="id" value="${user.id}" />
        <p class="prompt">Select Families:</p>
        <g:multiselect multiple="true" name="family_id" from="${families}" value="${user.families}"
              optionKey="id" optionValue="name" />
        <p />
        <g:submitButton name="Update" value="Update" />
     </g:form>
  </body>
</html>