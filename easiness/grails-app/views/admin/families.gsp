<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 8, 2008
  Time: 1:09:20 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Easiness: Manage Story Families</title></head>
  <body>

      <h2>Manage Families</h2>
      <p class="info">This page lists the existing Story Families, and allows you to add new families and delete old ones.</p>

      <g:if test="${families.size() == 0}">
        <p class="note">You don't have any families.  Why not add one?</p>
      </g:if>



      <table border="0" class="listing-table">


         <g:each var="fam" in="${families}">

            <tr>
               <td width="60%">${fam.name}</td>
               <td width="30%">${fam.code}</td>
               <td width="10%"><g:link action="do_delete_family" controller="admin" id="${fam.id}"><strong>X</strong></g:link></td>
            </tr>
         
         </g:each>


      </table>

      <p>&nbsp;</p>

      <h3>Add A Family</h3>
      <p class="info">Use this form to add a new family to the system.</p>
      <g:form action="do_add_family" controller="admin">

         <input type="text" name="name" width="60" />  <span class="note">The human-friendly name for this family</span><br/>
         <input type="text" name="code" width="10" /> <span class="note">The URL-friendly name (no spaces, etc)</span>
         <p></p>
         <g:submitButton name="Add" value="Add"/>

      </g:form>



  </body>
</html>