/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Jul 22, 2008
 * Time: 3:48:23 PM
 * To change this template use File | Settings | File Templates.
 */
class EasinessTagLib {

   static namespace = "ezi"

   // if user logged in...    
   def ifUser = { attrs, body ->
       boolean valid = session.userId != null
       if (attrs.not) {
           valid = !valid
       }
       if (valid) {
           out << body()
       }
   }



   def hasRole = { attrs, body ->

      boolean valid = false;

      if (session.userId != null) {

         User u = User.get(session.userId)

         if (u != null && u.hasRole(attrs.role))  {
            valid = true
         }

      }

      if (attrs.not) {
         valid = !valid
      }

      if (valid) {
         out << body()
      }
   }

   def hasRights = { attrs, body ->

      boolean valid = false

      if (session.userId != null) {

         User u = User.get(session.userId)

         if (u.hasRole("admin") || u.hasRole("author & developer")) {
            valid = true
         } else {

            if (attrs.type == "context" ) {
               if (u.hasRole("author")) {
                  valid = true
               }
            } else if (attrs.type == "code") {
               if (u.hasRole("developer")) {
                  valid = true
               }
            }

         }

         if (attrs.not) {
            valid = !valid
         }

      }

      if (valid) {
         out << body()
      }

   }

}