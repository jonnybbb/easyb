<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Jul 24, 2008
  Time: 1:54:24 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Scenario: Edit</title></head>
  <body>



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


  <g:hasErrors bean="${scenario}">
      <div class="errorbox">
          <g:renderErrors bean="${scenario}" as="list" />
      </div>
  </g:hasErrors>


  <h3>Edit Scenario: ${scenario.title}</h3>
  <h4 style="padding-left: 20px; margin-top: 5px;">of Story: ${scenario.story.title}</h4>

  <div id="formdiv">
     <g:form action="do_edit">
        <input type="hidden" name="scenario_id" value="${scenario.id}" />


        <div id="menu-bar">
           <a href="#" onclick='changeLayout(0, "main_area_show", "import_area_show", "setup_area_show", "teardown_area_show"); return false;'><em>Main</em></a>
           |
           <a href="#" onclick='changeLayout(1, "main_area_show", "import_area_show", "setup_area_show", "teardown_area_show"); return false;'><em>Imports</em></a>
           |
           <a href="#" onclick='changeLayout(2, "main_area_show", "import_area_show", "setup_area_show", "teardown_area_show"); return false;'><em>Setup</em></a>
           |
           <a href="#" onclick='changeLayout(3, "main_area_show", "import_area_show", "setup_area_show", "teardown_area_show"); return false;'><em>Teardown</em></a>
        </div>

        <div id="import_area_show" style="display: none;">
           <p class="tab-label">Imports</p>
           <textarea rows="5" cols="62" name="imports">${scenario.imports}</textarea>
           <p />
           <p class="note">Use this area to add the import statements you need for your scenario.</p>
           <p class="action-button"><input type="submit" value="Save"/></p>
        </div>


        <div id="setup_area_show" style="display: none;">
           <p class="tab-label">Setup</p>
           <textarea rows="5" cols="62" name="setup">${scenario.setUp}</textarea>
           <p />
           <p class="note">Use this area to add any setup code (groovy) you need for your scenario</p>
           <p class="action-button"><input type="submit" value="Save"/></p>
        </div>


        <div id="teardown_area_show" style="display: none;">
           <p class="tab-label">Teardown</p>
           <textarea rows="5" cols="62" name="teardown">${scenario.tearDown}</textarea>
           <p />
           <p class="note">Use this area to add any teardown code (groovy) you need for your scenario</p>
           <p class="action-button"><input type="submit" value="Save"/></p>
        </div>

        
        <div id="main_area_show" style="display: inline">
           <p />   &nbsp; <br />

           <strong>Given</strong>

           <g:if test="${ordered_givens?.size() > 0}">

              <% def i = 0 %>

              <g:each var="given" in="${ordered_givens}">

                 <% def tname = "g_text${i}" %>
                 <% def cname = "g_code${i}" %>
                 <% def iname = "g_id_${i}" %>

                 <g:if test="${i > 0}">
                    <strong>AND</strong>
                    <div style="padding-left: 10px; margin-left: 10px;">
                 </g:if>

                 <input type="hidden" name="${iname}" value="${given.id}" />
                 <input type="text" size="64" maxlength="128" name="${tname}" value="${given.text}" />   {
                 </p>
                 <div id="${cname}_show" style="display: none;">
                    <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="${cname}">${given.code}</textarea> <br/>
                         <a href="#" onclick='changeLayout(1, "${cname}_show", "${cname}_noshow"); return false;'><p class="note">Click here to hide the source</p></a>
                      </p>
                 </div>
                 <div id="${cname}_noshow" style="display: inline;">
                    <p><em>
                       <a href="#" onclick='changeLayout(0, "${cname}_show", "${cname}_noshow"); return false;'><p class="note">Click here to view the source</p></a>
                    </em></p>
                 </div>
                 <div style="float:right; padding-right: 20px;">
                    <g:link action="do_delete_clause" controller="scenario" id="${given.id}" params="[type:'given',scenario_id: scenario.id]"><span class="delete_clause">Delete</span></g:link>
                 </div>
                 <p>}</p>

                 <g:if test="${i > 0}">
                    </div>
                 </g:if>



                 <% i++ %>
              </g:each>

           </g:if>
           <g:if test="${ordered_givens== null || ordered_givens?.size() == 0}">
              <input type="text" size="64" maxlength="128" name="g_text0" />   {
              </p>
              <div id="g_code0_show" style="display: none;">
                   <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="g_code0"></textarea> <br/>
                      <a href="#" onclick="changeLayout(1, 'g_code0_show', 'g_code0_noshow'); return false;"><p class="note">Click here to hide the source</p></a>
                   </p>
              </div>
              <div id="g_code0_noshow" style="display: inline;">
                 <p><em>
                    <a href="#" onclick="changeLayout(0, 'g_code0_show', 'g_code0_noshow'); return false;"><p class="note">Click here to view the source</p></a>
                 </em></p>
              </div>

              <p>}</p>
           </g:if>
           <g:link action="do_add_clause" controller="scenario" params="[scenario_id: scenario.id, type: 'given']"><span class="add_clause">add another Given</span></g:link>


           <p>
           <strong>When</strong>
           <g:if test="${ordered_conditions?.size() > 0}">

              <% i = 0 %>

              <g:each var="cond" in="${ordered_conditions}">

                 <% tname = "w_text${i}" %>
                 <% cname = "w_code${i}" %>
                 <% iname = "w_id_${i}" %>


                 <g:if test="${i > 0}">
                    <strong>AND</strong>
                    <div style="padding-left: 10px; margin-left: 10px;">
                 </g:if>

                 <input type="hidden" name="${iname}" value="${cond.id}" />
                 <input type="text" size="64" maxlength="128" name="${tname}" value="${cond.text}" />   {
                 </p>
                 <div id="${cname}_show" style="display: none;">
                    <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="${cname}">${cond.code}</textarea> <br/>
                         <a href="#" onclick='changeLayout(1, "${cname}_show", "${cname}_noshow"); return false;'><p class="note">Click here to hide the source</p></a>
                      </p>
                 </div>
                 <div id="${cname}_noshow" style="display: inline;">
                    <p><em>
                       <a href="#" onclick='changeLayout(0, "${cname}_show", "${cname}_noshow"); return false;'><p class="note">Click here to view the source</p></a>
                    </em></p>
                 </div>
                 <div style="float:right; padding-right: 20px;">
                    <g:link action="do_delete_clause" controller="scenario" id="${cond.id}" params="[type:'condition',scenario_id: scenario.id]"><span class="delete_clause">Delete</span></g:link>
                 </div>
                 <p>}</p>


                 <g:if test="${i > 0}">
                    </div>
                 </g:if>

                 <% i++ %>
              </g:each>


           </g:if>
           <g:if test="${ordered_conditions == null || ordered_conditions?.size() == 0}">
              <input type="text" size="64" maxlength="128" name="w_text0" />   {
              </p>
              <div id="w_code0_show" style="display: none;">
                 <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="w_code0"></textarea><br/>
                      <a href="#" onclick="changeLayout(1, 'w_code0_show', 'w_code0_noshow'); return false;"><p class="note">Click here to hide the source</p></a>
                 </p>
              </div>
              <div id="w_code0_noshow" style="display: inline;">
                 <p><em>
                    <a href="#" onclick="changeLayout(0, 'w_code0_show', 'w_code0_noshow'); return false;"><p class="note">Click here to view the source</p></a>
                 </em></p>
              </div>
              <p>}</p>
           </g:if>
           <g:link action="do_add_clause" controller="scenario" params="[scenario_id: scenario.id, type: 'condition']"><span class="add_clause">add another When</span></g:link>



           <p>
           <strong>Then</strong>
           <g:if test="${ordered_conclusions?.size() > 0}">

              <% i = 0 %>

              <g:each var="concl" in="${ordered_conclusions}">

                 <% tname = "t_text${i}" %>
                 <% cname = "t_code${i}" %>
                 <% iname = "t_id_${i}" %>


                 <g:if test="${i > 0}">
                    <strong>AND</strong>
                    <div style="padding-left: 10px; margin-left: 10px;">
                 </g:if>

                 <input type="hidden" name="${iname}" value="${concl.id}" />
                 <input type="text" size="64" maxlength="128" name="${tname}" value="${concl.text}" />   {
                 <p />
                 <div id="${cname}_show" style="display: none;">
                    <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="${cname}">${concl.code}</textarea> <br/>
                         <a href="#" onclick='changeLayout(1, "${cname}_show", "${cname}_noshow"); return false;'><p class="note"><p class="note">Click here to hide the source</p></p></a>
                      </p>
                 </div>
                 <div id="${cname}_noshow" style="display: inline;">
                    <p><em>
                       <a href="#" onclick='changeLayout(0, "${cname}_show", "${cname}_noshow"); return false;'><p class="note">Click here to view the source</p></a>
                    </em></p>
                 </div>
                 <div style="float:right; padding-right: 20px;">
                    <g:link action="do_delete_clause" controller="scenario" id="${concl.id}" params="[type:'conclusion',scenario_id: scenario.id]"><span class="delete_clause">Delete</span></g:link>
                 </div>

                 <p>}</p>

                 <g:if test="${i > 0}">
                     </div>
                  </g:if>


                 <% i++ %>
              </g:each>

           </g:if>
           <g:if test="${ordered_conclusions == null || ordered_conclusions?.size() == 0}">
              <input type="text" size="64" maxlength="128" name="t_text0" />   {
              <p />

              <div id="t_code0_show" style="display: none;">
                 <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="t_code0"></textarea><br />
                    <a href="#" onclick="changeLayout(1, 't_code0_show', 't_code0_noshow'); return false;"><p class="note"><p class="note">Click here to hide the source</p></p></a>
                 </p>
              </div>
              <div id="t_code0_noshow" style="display: inline;">
                 <p><em>
                    <a href="#" onclick="changeLayout(0, 't_code0_show', 't_code0_noshow'); return false;"><p class="note"><p class="note">Click here to view the source</p></p></a>
                 </em></p>
              </div>
              <p>}</p>
           </g:if>
           <g:link action="do_add_clause" controller="scenario" params="[scenario_id: scenario.id, type: 'conclusion']"><span class="add_clause">add another Then</span></g:link></p>


           <p class="action-button"><input type="submit" value="Save"/></p>
        </div>

     </g:form>

  </div>



  </body>
</html>