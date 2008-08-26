<html>
    <head>
        <title><g:layoutTitle default="Easiness" /></title>
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'easiness.css')}"  />
        <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />				
    </head>
    <body>
        <div id="header">
           <h1><span><a href="/">Easiness</a></span></h1>
           <h2>&nbsp;&nbsp;-- BDD, web-style</h2>
        </div>


        <div id="page">
           <div id="sidebar" >
              <ul>
                 <li>
                    <h2>Account</h2>
                    <ul>
                       <ezi:ifUser not="true">
                         <li><g:link action="login" controller="main">Login</g:link></li>
                         <li><g:link action="register" controller="main">Register</g:link></li>
                       </ezi:ifUser>
                       <ezi:ifUser>
                         <li><g:link action="do_logout" controller="main">Logout</g:link></li>
                       </ezi:ifUser>
                    </ul>
                 </li>
                 <ezi:ifUser>
                 <li>
                    <h2>Actions</h2>
                     <ul>
                       <li><g:link action="create" controller="story">Create A Story</g:link></li>
                       <li><g:link action="mystories" controller="story">My Stories</g:link></li>
                       <li><g:link action="viewfamilies" controller="story">My Story Families</g:link></li>

                        <g:if test="${session.current_story != null}">
                           <li><g:link action="edit" controller="story" id="${session.current_story.id}">Current Story</g:link></li>
                        </g:if>
                     </ul>



                 </li>
                 </ezi:ifUser>
                 <ezi:hasRole role="admin">                     
                 <li>
                    <h2>Admin</h2>
                     <ul>
                       <li><g:link action="users" controller="admin">Manage Users</g:link></li>
                       <li><g:link action="families" controller="admin">Manage Families</g:link></li>
                     </ul>
                 </li>
                 </ezi:hasRole>
              </ul>
           </div>

           <div id="content">

              <g:if test="${flash.error}">
                 <div class="errorbox">
                    ${flash.error}
                 </div>
              </g:if>


              <g:if test="${flash.message}">
                 <div class="messagebox">
                    ${flash.message}
                 </div>
              </g:if>


              <g:layoutBody />
           </div>

           <g:if test="${flash.context_help}">
              <div id="context-help">
                 <h1>${flash.context_help.title}</h1>
                 ${flash.context_help.content}
              </div>
           </g:if>

        </div>
    </body>
</html>