/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Jul 24, 2008
 * Time: 11:47:13 AM
 * To change this template use File | Settings | File Templates.
 */
class ControllerBase {


   public boolean admin_intercept() {
      if (!base_intercept()) { return false }

      def user = User.get(session.userId)

      if (user != null) {
         if (!user.hasRole("admin")) {
            flash.error = "Only Admins are authorized to access these pages."
            return false
         }

         return true
      }


      return false

   }

   public boolean base_intercept() {

      def error = warnIfInvalidUser()

      if (error != null) {
         flash.error = error
         return false
      }

      true

   }

   String warnIfInvalidUser() {
      if (!session.userId) {
          return "You must log in to use that function"
      }

      def user = User.get(session.userId)

      if (user != null) {
         flash.user = user
      }

      null
   }

}