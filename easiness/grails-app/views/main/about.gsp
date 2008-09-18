<%--
  Created by IntelliJ IDEA.
  User: johnbr
  Date: Sep 18, 2008
  Time: 8:51:41 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>About Easiness</title></head>
  <body>


  <h2>Motivation</h2>

  <p>
     Easiness began as a discussion in July of 2008.   Andy Glover, the creator of EasyB, had received some feedback
     that a more 'user-friendly' version of EasyB would be very useful. Specifically, the following points were discussed:
  </p>
  <ul>
     <li>Separate the writing of the story from the writing of the code</li>
     <li>Make it easier to focus on the content, rather than the structure of EasyB stories</li>
     <li>Let people collaborate on story generation, even over long distances</li>
     <li>Ideally, make it so people could write entire test suites without having to use a command line </li>
  </ul>

  <h2>Vision</h2>
  <p>
     We want to make it even easier to produce BDD test suites than it is now.   IDEs make writing software easier -
     why wouldn't we want the same thing for BDD?  Even as simple as they are, the formatting for EasyB stories can
     be confusing to some.  What if we could get rid of that concern altogether, and let the users focus on what the
     story is supposed to say, and do?
  </p>
  <p>
     We want to make it possible to run EasyB stories from CI environments, automatically.  If a Hudson or Cruise Control
     task can send a request to Easiness, and that causes Easiness to run a set of stories, and report back the results, we've
     successfully integrated EasyB into the build cycle, without (necessarily) having to pull everything into source control.
  </p>
  <p>
     Having said that, if you want to put everything into source control, you can do that by exporting the stories out
     of Easiness, whenever you want.
  </p>


  <h2>Comparisons</h2>

  <h3>Fitnesse</h3>
  <p>
    Fitnesse is a fine tool, but it isn't narrative based, like EasyB, and it is not ideal for creating code artifacts that
    can be stored in SCM.  We like Fitnesse, but we wanted to produce something that was even more build-process-friendly.
  </p>
  <h3>Selenium</h3>
  <p>
     Selenium and Selenium IDE makes it easy to write web-based tests, and it also makes them fairly easy to automate.  But
     it doesn't have the narrative structure, doesn't separate the roles and responsibilities, and isn't designed to support
     non-web testing.  Again, we like Selenium a lot, but it doesn't seem like it solves the same problems as EasyB/Easiness,
     and there's room for both.
   </p>
  <h3>More to Come</h3>
   <p>
      If you have any questions or want additional information about Easiness, feel free to <a href="mailto:johnbr@gmail.com">drop me a line</a>
   </p>
  </body>
</html>