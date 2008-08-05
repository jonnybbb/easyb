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

     function changeLayout( value, block1, block2 ) {

         var b1 = document.getElementById(block1);
         var b2 = document.getElementById(block2);

         if (value == 0) {
             b2.style.display = "inline";
             b1.style.display = "none";
         } else {
             b2.style.display = "none";
             b1.style.display = "inline";
         }

     }

  </script>


  <g:hasErrors bean="${scenario}">
      <div class="errorbox">
          <g:renderErrors bean="${scenario}" as="list" />
      </div>
  </g:hasErrors>


  <h2>Edit: ${scenario.title}</h2>

  <div id="formdiv">
     <g:form action="do_edit">
        <input type="hidden" name="scenario_id" value="${scenario.id}" />


        
        <p>
        <strong>Given</strong>

        <g:if test="${ordered_givens?.size() > 0}">
        </g:if>
        <g:if test="${ordered_givens== null || ordered_givens?.size() == 0}">
           <input type="text" size="64" maxlength="128" name="g_text0" />   {
           </p>
           <div id="g_code0_show" style="display: none;">
                <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="g_code0"></textarea> <br/>
                   <a href="javascript:description()" onclick="changeLayout(0, 'g_code0_show', 'g_code0_noshow')">Click here to hide the source</a>
                </p>
           </div>
           <div id="g_code0_noshow" style="display: inline;">
              <p><em>
                 <a href="javascript:description()" onclick="changeLayout(1, 'g_code0_show', 'g_code0_noshow')">Click here to view the source</a>
              </em></p>
           </div>
           <p>}</p>
        </g:if>


        <p>
        <strong>When</strong>
        <g:if test="${ordered_conditions?.size() > 0}">
        </g:if>
        <g:if test="${ordered_conditions == null || ordered_conditions?.size() == 0}">
           <input type="text" size="64" maxlength="128" name="w_text0" />   {
           </p>
           <div id="w_code0_show" style="display: none;">
              <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="w_code0"></textarea><br/>
                   <a href="javascript:description()" onclick="changeLayout(0, 'w_code0_show', 'w_code0_noshow')">Click here to hide the source</a>
              </p>
           </div>
           <div id="w_code0_noshow" style="display: inline;">
              <p><em>
                 <a href="javascript:description()" onclick="changeLayout(1, 'w_code0_show', 'w_code0_noshow')">Click here to view the source</a>
              </em></p>
           </div>
           <p>}</p>
        </g:if>



        <p>
        <strong>Then</strong>
        <g:if test="${ordered_conclusions?.size() > 0}">


        </g:if>
        <g:if test="${ordered_conclusions == null || ordered_conclusions?.size() == 0}">
           <input type="text" size="64" maxlength="128" name="t_text0" />   {
           </p>

           <div id="t_code0_show" style="display: none;">
              <p>&nbsp;&nbsp;&nbsp;<textarea rows="5" cols="62" name="t_code0"></textarea><br />
                 <a href="javascript:description()" onclick="changeLayout(0, 't_code0_show', 't_code0_noshow')">Click here to hide the source</a>
              </p>
           </div>
           <div id="t_code0_noshow" style="display: inline;">
              <p><em>
                 <a href="javascript:description()" onclick="changeLayout(1, 't_code0_show', 't_code0_noshow')">Click here to view the source</a>
              </em></p>
           </div>
           <p>}</p>
        </g:if>


        <p class="action-button"><input type="submit" value="Save"/></p>


     </g:form>

  </div>



  </body>
</html>