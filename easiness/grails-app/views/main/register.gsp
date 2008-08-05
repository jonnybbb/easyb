<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Jul 22, 2008
  Time: 4:27:16 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>New User Registration</title></head>
  <body>


  <g:hasErrors bean="${user}">
      <div class="errorbox">
          <g:renderErrors bean="${user}" as="list" />
      </div>
  </g:hasErrors>

  <div class="formdiv">
  <g:form action="do_register">
  <table>
      <tr>
         <td valign="top" class="name">
             <label for="name">Name:</label>
         </td>
         <td valign="top" class="value ${hasErrors(bean: user, field: 'name', 'errors')}">
             <input type="text" size="40" maxlength="40" id="name" name="name" value="${fieldValue(bean: user, field: 'name')}"/>
         </td>
     </tr>
     <tr>
         <td valign="top" class="name">
             <label for="password">Password:</label>
         </td>
         <td valign="top" class="value ${hasErrors(bean: user, field: 'password', 'errors')}">
             <input type="password" size="40" maxlength="40" id="password" name="password" value="${fieldValue(bean: user, field: 'password')}"/>
         </td>
     </tr>
     <tr>
         <td valign="top" class="name">
             <label for="password">Password confirmation:</label>
         </td>
         <td valign="top" class="value ${hasErrors(bean: user, field: 'passwordConfirmation', 'errors')}">
             <input type="password" size="40" maxlength="40" id="passwordConfirmation" name="passwordConfirmation" value="${fieldValue(bean: user, field: 'passwordConfirmation')}"/>
         </td>
     </tr>
     <tr>
         <td valign="top" style="text-align: center;" colspan="2">
             <span class="login-button"><input type="submit" value="Create" /></span>
         </td>
     </tr>
  </table>
  </g:form>  
  </div>

</html>