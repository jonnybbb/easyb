class AdminController extends ControllerBase {

   def beforeInterceptor = [ action: this.&intercept ]

   def index = { }

   def users = { }

   def families = { }



//============== Private Methods =======================

   boolean intercept() {
      if (!admin_intercept()) {
         redirect(controller: 'main', action: 'index')
         return false
      }

      return true
   }


}
