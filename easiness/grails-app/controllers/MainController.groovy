class MainController extends ControllerBase {

   def beforeInterceptor = [ action: this.&intercept, except: [ 'login', 'do_login', 'register', 'do_register', 'do_logout', 'index' ] ]


   
   def index = {}
   def login = {}


   def register = {

      flash.context_help = [ title: 'Why Register?', content: '''
      <p>Registering allows you to create EasyB stories, and then add code to them so they can run against your
applications.</p>
      <p>By registering, we can ensure that no one changes your stories without your permission</p>
      <p>Note that all registration data is stored locally on your server, nothing is ever sent out on the Internet</p>
'''
]


   }


   /* ------------------------------ */

   def do_logout = {

      session.userId = null

      render(view: 'index')

   }



   def do_login = {

      def user = User.findByName(params.name)

      if (user == null) {
         flash.error = "Unknown user or invalid password: ${params.name}"
         //user.errors.reject("unknown.user.or.invalid.password", "Unknown user or invalid password: ${params.name}")
         render(view: 'login')
         return
      }

      if (user.password?.trim() != params.password?.trim()) {
         user.errors.reject("unknown.user.or.invalid.password", "Unknown user or invalid password: ${params.name}")
         render(view: 'login', model: [ user: user])
         return
      }

      session.userId = user.id
      session.user = user

      redirect(controller: 'story', action: 'mystories')
                         

   }

   def do_register = {

      def user = new User(params)


      def password = params.password?.trim()
      def passwordConfirmation = params.passwordConfirmation?.trim()

      if (password && password != passwordConfirmation) {
         user.errors.reject("passwords.no.match", "Passwords Do Not Match.")
         render(view:'register')
         return 
      }


      String role = "author"

      def users = User.list()

      if (users.size == 0) {

         role = "admin"

      }

      user.role = role
      
      if (user.hasErrors()) {
         render(view: 'register')
         return  null
      }

      user.families  = [Family.getDefault()]



      if (user.save()) {
         session.userId = user.id
         session.user = user
         flash.message = "User ${user.name} created"
         redirect(controller: 'story', action: 'mystories')
      }
      else {
         render(view: 'register', model: [ user: user ])
      }

      return  [ user: user]

   }


   boolean intercept() {
      if (!base_intercept()) {
         redirect(controller: 'main', action: 'index')
         return false
      }

      return true
   }
   




}
