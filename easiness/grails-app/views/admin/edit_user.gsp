<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 12, 2008
  Time: 12:39:00 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Admin: Edit User</title></head>
  <body>
       <g:form action="do_edit_user" method="POST" controller="admin">

          <span class="prompt">Name</span><g:textField name="name" value="${user.name}" /><p />
          <span class="prompt">Role</span><g:select name="role" from="${roles}" value="${user.role}" /><p></p>
          <span class="prompt">Families:</span> (<g:link class="note" action="update_user_families" controller="admin" id="${user.id}">Update List</g:link>)
          <p>
             <ul>
             <g:each var="fam" in="${user.families}">
                <li>${fam.name}</li>
             </g:each>
             </ul>
          </p>
          <p></p>
          <g:hiddenField name="id" value="${user.id}" />


          <g:submitButton name="submit" value="Update" />

       </g:form>
  </body>
</html>