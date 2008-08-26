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


  <!-- need to move this into a shared script area -->
  <script type="text/javascript" language="javascript">

     function changeLayout( value, block1, block2, block3, block4, block5 ) {

         var b1 = document.getElementById(block1);
         var b2 = document.getElementById(block2);
         var b3 = document.getElementById(block3);
         var b4 = document.getElementById(block4);
         var b5 = document.getElementById(block5);

        arr = [b1,b2,b3,b4,b5]

        for (i = 0; i < 5; i++) {
           if (i == value) {
              arr[i].style.display = "inline";
           } else {
              if (arr[i] != null) {
                 arr[i].style.display = "none";
              }
           }
        }


     }

  </script>



    <h1>Story Editor</h1>
     <g:form action="do_edit">
        <div class="formdiv">


           <div id="menu-bar">
              <a href="#" onclick='changeLayout(0, "main_area_show", "import_area_show", "setup_area_show", "teardown_area_show"); return false;'><span class="tab-label">Main</span></a>
              |
              <a href="#" onclick='changeLayout(1, "main_area_show", "import_area_show", "setup_area_show", "teardown_area_show"); return false;'><span class="tab-label">Imports</span></a>
              |
              <a href="#" onclick='changeLayout(2, "main_area_show", "import_area_show", "setup_area_show", "teardown_area_show"); return false;'><span class="tab-label">Setup</span></a>
              |
              <a href="#" onclick='changeLayout(3, "main_area_show", "import_area_show", "setup_area_show", "teardown_area_show"); return false;'><span class="tab-label">Teardown</span></a>
              |
              <span class="tab-label"><g:link action="expand" controller="story" id="${story.id}">Scenarios</g:link></span>
           </div>

           <div id="import_area_show" style="display: none;">

              <p class="tab-label">Package</p>
              <ezi:hasRights type="code">
                 <g:textField name="packageText" size="60" maxlength="256" value="${story.packageText}" /> \
                 <p class="note">Enter your story's package here.  Do <strong>not</strong> include the word 'package'.</p>
              </ezi:hasRights>
              <ezi:hasRights type="code" not="true">
                 ${story.packageText}
              </ezi:hasRights>

              <p class="tab-label">Imports</p>

              <ezi:hasRights type="code">
                 <textarea rows="5" cols="62" name="imports">${story.imports}</textarea>
                 <p />
                 <p class="note">Use this area to add the import statements you need for your story.</p>
                 <p class="action-button"><input type="submit" value="Save"/></p>
              </ezi:hasRights>
              <ezi:hasRights not="true" type="code">
                 <p class="info">${story.imports}</p>
              </ezi:hasRights>
           </div>


           <div id="setup_area_show" style="display: none;">
              <p class="tab-label">Setup</p>
              <ezi:hasRights type="code">

                 <textarea rows="5" cols="62" name="setup">${story.setUp}</textarea>
                 <p />
                 <p class="note">Use this area to add any setup code (<em>groovy</em>) you need for your story</p>
                 <p class="action-button"><input type="submit" value="Save"/></p>
              </ezi:hasRights>
              <ezi:hasRights type="code" not="true">
                 <p class="info">${story.setUp}</p>
              </ezi:hasRights>

           </div>


           <div id="teardown_area_show" style="display: none;">

              <p class="tab-label">Teardown</p>
              <ezi:hasRights type="code">
                 <textarea rows="5" cols="62" name="teardown">${story.tearDown}</textarea>
                 <p />
                 <p class="note">Use this area to add any teardown code (<em>groovy</em>) you need for your story</p>
                 <p class="action-button"><input type="submit" value="Save"/></p>
              </ezi:hasRights>
              <ezi:hasRights type="code" not="true">
                 <p class="info">${story.tearDown}</p>
              </ezi:hasRights>

           </div>


           <div id="main_area_show" style="display: inline">


              <g:hiddenField name="id" value="${story.id}" />

              <p><span class="prompt">Title:</span>

                 <ezi:hasRights type="context">

                    <input type="text" size="45" maxlength="64" name="title" value="${story.title}"/>
                 </ezi:hasRights>
                 <ezi:hasRights type="context" not="true">
                    ${story.title}
                 </ezi:hasRights>
              </p>
              <p><span class="prompt">Description:</span></p>
              <p>
                 <ezi:hasRights type="context">
                    <textarea rows="5" cols="62" name="description">${story.description}</textarea>
                 </ezi:hasRights>
                 <ezi:hasRights type="context" not="true">
                    ${story.description}
                 </ezi:hasRights>
              </p>
              <p><span class="prompt">Story Family:</span>
                 <ezi:hasRights type="context">
                     <g:select name="family_id" from="${families}" value="${story.family.id}" optionKey="id" optionValue="name" />
                 </ezi:hasRights>
                 <ezi:hasRights type="context" not="true">
                    ${story.family.name}
                 </ezi:hasRights>
              </p>

              <ezi:hasRights type="context">
                 <p />

                 <p class="action-button"><input type="submit" value="Update"/></p>
              </ezi:hasRights>
           </div>
        </div>
     </g:form>

  </body>
</html>