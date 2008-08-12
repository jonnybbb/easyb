<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 8, 2008
  Time: 1:09:12 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Admin: Users</title></head>
  <body>
  <h2>Admin: Users</h2>
  <p class="note">You can update existing or delete existing users from here.</p>

  <table border="0">
     <tr>
        <th>User</th>
        <th>Role</th>
        <th>Stories</th>
        <th>Families</th>
     </tr>

     <g:each var="user" in="${users}">

        <tr>

           <td><g:link action="edit_user" controller="admin" id="${user.id}">${user.name}</g:link></td>
           <td>${user.role}</td>
           <td align="center">${user.stories.size()}</td>
           <td>
              <g:each var="fam" in="${user.families}">
                 ${fam.name}
              </g:each>
           </td>


           <td>
              <g:if test="${!user.isAdmin()}">
                 <g:link action="do_delete_user" controller="admin" id="${user.id}">X</g:link>
              </g:if>                 
           </td>

        </tr>

     </g:each>
  </table>


  </body>
</html>