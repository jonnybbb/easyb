<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 11, 2008
  Time: 2:44:14 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Story: Edit</title></head>
  <body>
     <g:hasErrors bean="${story}">
         <div class="errorbox">
             <g:renderErrors bean="${story}" as="list" />
         </div>
     </g:hasErrors>

    <h1>Story Editor</h1>
     <g:form action="do_edit">
        <div class="formdiv">
           <g:hiddenField name="id" value="${story.id}" />

           <p><span class="prompt">Title:</span> <input type="text" size="45" maxlength="64" name="title" value="${story.title}"/></p>
           <p><span class="prompt">Description:</span></p>
           <p><textarea rows="5" cols="62" name="description">${story.description}</textarea></p>
           <p><span class="prompt">Story Family:</span><g:select name="family_id" from="${families}" value="${story.family.id}" optionKey="id" optionValue="name" /></p>

           <p />
           <p class="action-button"><input type="submit" value="Update"/></p>
        </div>
     </g:form>

  </body>
</html>