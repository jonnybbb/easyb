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

    function changeLayout(value, block1, block2, block3, block4, block5) {

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
        <g:renderErrors bean="${scenario}" as="list"/>
    </div>
</g:hasErrors>


<h3>Edit Scenario: ${scenario.title}</h3>
<h4 style="padding-left: 20px; margin-top: 5px;">of Story: <g:link action="expand" controller="story" id="${scenario.story.id}">${scenario.story.title}</g:link></h4>

<div id="formdiv">
<g:form action="do_edit">
    <input type="hidden" name="scenario_id" value="${scenario.id}"/>


    <div id="main_area_show" style="display: inline">
    <p />   &nbsp; <br />

<!-- start given code -->

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

            <input type="hidden" name="${iname}" value="${given.id}"/>

            <ezi:hasRights type="context">
                <input type="text" size="44" maxlength="128" name="${tname}" value="${given.text}"/>


                <g:link action="do_add_clause" controller="scenario" params="[scenario_id: scenario.id, type: 'given']">
                    <img src="${createLinkTo(dir: 'images', file: 'add_icon.gif')}" alt="add" title="add another given"/>
                </g:link>

                <g:link action="do_delete_clause" controller="scenario" id="${given.id}"
                        params="[type:'given',scenario_id: scenario.id]">
                    <img src="${createLinkTo(dir: 'images', file: 'delete_icon.gif')}" alt="view" title="delete this given"/>
                </g:link>

            </ezi:hasRights>
            <ezi:hasRights type="context" not="true"><span class="info">${given.text}</span>
            </ezi:hasRights>

            </p>
           <div id="${cname}_show" style="display: none;">

                    <p>&nbsp;&nbsp;&nbsp;
            <ezi:hasRights type="code">
                <textarea rows="5" cols="62" name="${cname}">${given.code}</textarea> <br/>
            </ezi:hasRights>
            <ezi:hasRights type="code" not="true">
                <p class="code">${given.code}</p>
            </ezi:hasRights>
            <a href="#" onclick='changeLayout(1, "${cname}_show", "${cname}_noshow");
            return false;'>
                <!--<p class="note">hide the source</p>-->
                <img src="${createLinkTo(dir: 'images', file: 'hide_icon.gif')}" alt="hide code" title="hide source code"/>
            </a>
            </p>
            </div>
            <div id="${cname}_noshow" style="display: inline;">
                <p><em>
                    <a href="#" onclick='changeLayout(0, "${cname}_show", "${cname}_noshow");
                    return false;'>
                        <!--<p class="note"><view the source</p>-->
                        <img src="${createLinkTo(dir: 'images', file: 'code_icon.gif')}" alt="view code" title="view source code"/>
                    </a>
                </em></p>
            </div>




            <p></p>

            <g:if test="${i > 0}">
                </div>
            </g:if>



            <% i++ %>
        </g:each>

    </g:if>
    <g:if test="${ordered_givens== null || ordered_givens?.size() == 0}">

        <input type="text" size="44" maxlength="128" name="g_text0"/>
        <g:link action="do_add_clause" controller="scenario" params="[scenario_id: scenario.id, type: 'given']">
            <img src="${createLinkTo(dir: 'images', file: 'add_icon.gif')}" alt="add" title="add another given"/>
        </g:link>

        </p>
        <div id="g_code0_show" style="display: none;">
            <p>&nbsp;&nbsp;&nbsp;
                <ezi:hasRights type="code">
                    <textarea rows="5" cols="62" name="g_code0"></textarea> <br/>
                </ezi:hasRights>
                <a href="#" onclick="changeLayout(1, 'g_code0_show', 'g_code0_noshow');
                return false;">
                    <p class="note">Click here to hide the source</p></a>
            </p>
        </div>
        <div id="g_code0_noshow" style="display: inline;">
            <p><em>
                <a href="#" onclick="changeLayout(0, 'g_code0_show', 'g_code0_noshow');
                return false;">
                    <p class="note">Click here to view the source</p></a>
            </em></p>
        </div>

        <p></p>
    </g:if>



    <!-- end given code -->

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

            <input type="hidden" name="${iname}" value="${cond.id}"/>


            <ezi:hasRights type="context">
                <input type="text" size="64" maxlength="128" name="${tname}" value="${cond.text}"/>
            </ezi:hasRights>
            <ezi:hasRights type="context" not="true"><span class="info">${cond.text}</span>
            </ezi:hasRights>



            </p>
           <div id="${cname}_show" style="display: none;">
                    <p>&nbsp;&nbsp;&nbsp;
            <ezi:hasRights type="code">
                <textarea rows="5" cols="62" name="${cname}">${cond.code}</textarea> <br/>
            </ezi:hasRights>
            <ezi:hasRights type="code" not="true">
                <p class="code">${cond.code}</p>
            </ezi:hasRights>

            <a href="#" onclick='changeLayout(1, "${cname}_show", "${cname}_noshow");
            return false;'>
                <p class="note">Click here to hide the source</p></a>
            </p>
            </div>
            <div id="${cname}_noshow" style="display: inline;">
                <p><em>
                    <a href="#" onclick='changeLayout(0, "${cname}_show", "${cname}_noshow");
                    return false;'>
                        <p class="note">Click here to view the source</p></a>
                </em></p>
            </div>
            <ezi:hasRights type="context">
                <div style="float:right; padding-right: 20px;">
                    <g:link action="do_delete_clause" controller="scenario" id="${cond.id}"
                            params="[type:'condition',scenario_id: scenario.id]">
                        <span class="delete_clause">Delete</span></g:link>
                </div>
            </ezi:hasRights>
            <p></p>


            <g:if test="${i > 0}">
                </div>
            </g:if>

            <% i++ %>
        </g:each>

    </g:if>
    <g:if test="${ordered_conditions == null || ordered_conditions?.size() == 0}">
        <input type="text" size="64" maxlength="128" name="w_text0"/>
        </p>
        <div id="w_code0_show" style="display: none;">
            <p>&nbsp;&nbsp;&nbsp;
                <ezi:hasRights type="code">
                    <textarea rows="5" cols="62" name="w_code0"></textarea><br/>
                </ezi:hasRights>
                <a href="#" onclick="changeLayout(1, 'w_code0_show', 'w_code0_noshow');
                return false;">
                    <p class="note">Click here to hide the source</p></a>
            </p>
        </div>
        <div id="w_code0_noshow" style="display: inline;">
            <p><em>
                <a href="#" onclick="changeLayout(0, 'w_code0_show', 'w_code0_noshow');
                return false;">
                    <p class="note">Click here to view the source</p></a>
            </em></p>
        </div>
        <p></p>
    </g:if>
    <ezi:hasRights type="context">
        <g:link action="do_add_clause" controller="scenario"
                params="[scenario_id: scenario.id, type: 'condition']">
            <span class="add_clause">add another When</span></g:link>
    </ezi:hasRights>


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

            <input type="hidden" name="${iname}" value="${concl.id}"/>

            <ezi:hasRights type="context">
                <input type="text" size="64" maxlength="128" name="${tname}" value="${concl.text}"/>
            </ezi:hasRights>
            <ezi:hasRights type="context" not="true"><span class="info">${concl.text}</span>
            </ezi:hasRights>


            <p/>
            <div id="${cname}_show" style="display: none;">
            <p>&nbsp;&nbsp;&nbsp;
                <ezi:hasRights type="code">
                    <textarea rows="5" cols="62" name="${cname}">${concl.code}</textarea> <br/>
                </ezi:hasRights>
                <ezi:hasRights type="code" not="true">
                    <p class="code">${concl.code}</p>
                </ezi:hasRights>

                <a href="#" onclick='changeLayout(1, "${cname}_show", "${cname}_noshow");
                return false;'>
                    <p class="note"><p class="note">Click here to hide the source</p></p></a>
                      </p>
                 </div>
            <div id="${cname}_noshow" style="display: inline;">
                <p><em>
                    <a href="#" onclick='changeLayout(0, "${cname}_show", "${cname}_noshow");
                    return false;'>
                        <p class="note">Click here to view the source</p></a>
                </em></p>
            </div>
            <ezi:hasRights type="context">
                <div style="float:right; padding-right: 20px;">
                    <g:link action="do_delete_clause" controller="scenario" id="${concl.id}"
                            params="[type:'conclusion',scenario_id: scenario.id]">
                        <span class="delete_clause">Delete</span></g:link>
                </div>
            </ezi:hasRights>
            <p></p>

            <g:if test="${i > 0}">
                </div>
            </g:if>


            <% i++ %>
        </g:each>

    </g:if>
    <g:if test="${ordered_conclusions == null || ordered_conclusions?.size() == 0}">
        <input type="text" size="64" maxlength="128" name="t_text0"/>
        <p/>

        <div id="t_code0_show" style="display: none;">
        <p>&nbsp;&nbsp;&nbsp;
        <ezi:hasRights type="code">
            <textarea rows="5" cols="62" name="t_code0"></textarea><br/>
        </ezi:hasRights>
        <a href="#" onclick="changeLayout(1, 't_code0_show', 't_code0_noshow'); return false;">
              <p class="note"><p class="note">Click here to hide the source</p></p></a>
           </p>
        </div>
        <div id="t_code0_noshow" style="display: inline;">
           <p><em>
              <a href="#" onclick="changeLayout(0, 't_code0_show', 't_code0_noshow'); return false;">
              <p class="note"><p class="note">Click here to view the source</p></p></a>
           </em></p>
        </div>
        <p></p>
    </g:if>

    <ezi:hasRights type="context">
        <g:link action="do_add_clause" controller="scenario"
                params="[scenario_id: scenario.id, type: 'conclusion']">
            <span class="add_clause">add another Then</span></g:link></p>
    </ezi:hasRights>


    <p class="action-button"><input type="submit" value="Save"/></p>
    </div>

</g:form>

</div>

</body>
</html>