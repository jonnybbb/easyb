<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Aug 26, 2008
  Time: 11:10:19 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>${story.title}</title></head>
  <body>



<pre>

<g:if test="${story.packageText != null}">
   package ${story.packageText}
</g:if>

<p/>

${story.imports}

<g:if test="${story.description}">
description = '''
  ${story.description}
'''
</g:if>

${story.setUp}


<g:each var="sc" in="${story.scenarios}">

scenario "${sc.title}", {
   <g:each var="given" in="${sc.ordered('givens')}">
   given "${given.text}", {
      ${given.code}
   }
   </g:each>

   <g:each var="cond" in="${sc.ordered('conditions')}">
   when "${cond.text}", {
      ${cond.code}
   }
   </g:each>

   <g:each var="concl" in="${sc.ordered('conclusions')}">
   then "${concl.text}", {
      ${concl.code}
   }
   </g:each>

}


</g:each>



${story.tearDown}
</pre>

  </body>
</html>