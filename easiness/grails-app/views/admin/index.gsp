<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 8, 2008
  Time: 1:04:18 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Easiness: Admin</title></head>
  <body>
  You have the following admin capabilities:
  <ul>
     <li><g:link action="users" controller="admin">Update/Edit Users</g:link></li>
     <li><g:link action="families" controller="admin">Create/Manage Story Families</g:link></li>
  </ul>
  </body>
</html>