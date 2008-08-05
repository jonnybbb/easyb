<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Jul 22, 2008
  Time: 4:01:57 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Login</title></head>
  <body>


  <g:hasErrors bean="${user}">
      <div class="errorbox">
          <g:renderErrors bean="${user}" as="list" />        
      </div>
  </g:hasErrors>    

     <g:form action="do_login">
        <div class="formdiv">
        <table>
           <tr>
              <td>Username</td>
              <td><input type="text" size="24" maxlength="64" name="name"/></td>
           </tr>
           <tr>
              <td>Password</td>
              <td><input type="password" name="password" size="24" maxlength="64"/></td>

           </tr>
           <tr>
              <td colspan="2" style="text-align: center;">
                 <input type="hidden" name="url" value="${createLink(params)}"/>
                 <span class="login-button"><input type="submit" value="Log in"/></span>
              </td>

           </tr>
        </table>
        </div>
     </g:form>

  </body>
</html>