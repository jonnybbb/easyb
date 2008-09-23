<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Jul 24, 2008
  Time: 1:01:54 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Story Expansion</title></head>
  <body>

     <h1><g:link action="edit" controller="story" id="${story.id}">Story: ${story.title}</g:link></h1>

     <div id="story-table">
        <div style="text-align: right; padding-right: 20px;">
           <ezi:hasRights type="context">
               <p class="info">
                  <g:link action="do_run" controller="story" id="${story.id}">Run</g:link>
                  &nbsp;
                  &nbsp;
                  <g:link action="export_story" controller="story" id="${story.id}">Export</g:link>
                  &nbsp;
                  &nbsp;
                  <g:link action="edit" controller="story" id="${story.id}">Edit</g:link>
               </p>
           </ezi:hasRights>
           <ezi:hasRights type="context" not="true">
              <p class="info">
                 <g:link action="export_story" controller="story" id="${story.id}">Export</g:link>
              </p>
           </ezi:hasRights>

        </div>

        <h2>Scenarios: ${story.scenarios.size()}</h2>
        <g:if test="${story?.scenarios?.size() > 0}">
           <ul>
           <g:each var='scenario' in="${story.scenarios}">
              <li><p><g:link action="edit" controller="scenario" id='${scenario.id}'>${scenario.title}</g:link></p></li>
           </g:each>
           </ul>
           <ezi:hasRights type="context">
              <p class="info"><g:link action='create' controller='scenario' params='[story_id: story.id]'>Create New Scenario</g:link></p>
           </ezi:hasRights>
        </g:if>

        <g:if test="${story?.scenarios?.size() == 0}">
           <ezi:hasRights type="context">
              <p class="info">You don't have any scenarios yet.  Why not <g:link action='create' controller='scenario' params='[story_id: story.id]'>create one</g:link>?</p>
           </ezi:hasRights>
           <ezi:hasRights type="context" not="true">
              <p class="info">No scenarios have been created for this story.</p>
           </ezi:hasRights>
        </g:if>

     </div>

    </body>
</html>