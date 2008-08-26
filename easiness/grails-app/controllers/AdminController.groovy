class AdminController extends ControllerBase {

   def beforeInterceptor = [ action: this.&intercept ]

   def index = { }

   def users = {

      do_user_help()

      users = User.findAll()

      return [ users: users]


   }

   def families = {

      do_family_help()

      families = Family.findAll()

      return [ families: families]
   }



   def edit_user = {

      do_user_help()

      def id = params.id

      def user = User.get(id)

      def users = User.findAll()

      if (user == null) {
         flash.error = "Unable to edit user with id: ${id}"
         render(view: 'users', model: [users: users])
         return [users: users]
      }

      return [ user: user, roles: Role.roleList]

   }
   

   def update_user_families = {

      do_user_help()

      def id = params.id

      def user = User.get(id)


      if (user == null) {
         flash.error = "Unable to find user with id: ${id}"
         render(view: 'edit_users', model: [users: User.list(), roles: Role.roleList])
         return 
      }

      return [user: user,  families: Family.list()]

   }




   
   def do_update_userfam = {

      do_user_help()

      def id = params.id

      def user = User.get(id)

      if (user == null) {
         flash.error = "Unable to find user with id: ${id}"
         render(view: 'edit_user', model: [user: user])
         return
      }


      user.families = []
      
      def family_ids = params.family_id

      family_ids.each { fid ->

         def f = Family.get(fid)

         user.families << f

      }

      user.save()



      flash.message = "User ${user.name} now belongs to families: ["


      user.families.each{ f ->
         flash.message += " ${f.name}, "
      }

      flash.message += " ]"

      render(view: 'edit_user', model: [user: user, roles: Role.roleList])

      return


   }
   


   def do_edit_user = {

      do_user_help()

      def id = params.id

      def user = User.get(id)

      if (user == null) {
         def users = User.findAll()
         flash.error = "Unable to find user with id: ${id}"
         render(view: 'edit_user', model: [user: user])
         return [user: user]
      }

      user.role = params.role
      user.name = params.name

      if (user.hasErrors()) {
         render(view: 'edit_user', model: [user: user, roles: Role.roleList ])
         return
      }

      if (!user.save()) {
         flash.error = "Unable to save user"
         render(view: 'edit_user', model: [user: user, roles: Role.roleList ])
         return
      }
      
      render(view: 'users', model: [users: User.findAll()])
      return

   }


   def do_add_family = {

      do_family_help()


      def families = Family.findAll()


      if (!(params.code =~ /^[A-Za-z0-9]+$/)) {
         flash.error = "Your family code most only include letters and numbers."
         render(view: 'families', model: [ families: families])
         return [ families: families]
      }

      if (Family.findByName(params.name) != null) {
         flash.error = "That family name is already in use."
         render(view: 'families', model: [ families: families])
         return [ families: families]
      }

      if (Family.findByCode(params.code) != null) {
         flash.error = "That family code is already in use."
         render(view: 'families', model: [ families: families])
         return [ families: families]         
      }


      def newfam = new Family(params)

      if (newfam.hasErrors()) {
         render(view: 'families', model: [ families: families])
         return [ families: families]
      }

      if (!newfam.save()) {
         flash.error = "Unable to add family."
         render(view: 'families', model: [ families: families])
         return [ families: families]
      }

      families << newfam

      flash.message = "Added new story family: ${newfam.name}"

      render(view: 'families', model: [ families: families])
      return

   }


   def do_delete_family = {

      do_family_help()

      def id = params.id

      def fam = Family.get(id)

      def families = Family.findAll()

      if (fam == null) {
         flash.error = "Unable to delete family"
         render(view: 'families', model: [ families: families])
         return [ families: families]
      }

      fam.delete(flush:true)

      families = Family.findAll()

      flash.message = "Family ${fam.name} deleted"

      render(view: 'families', model: [ families: families])
      return [ families: families]

   }




   def do_delete_user = {

      do_user_help()

      def id = params.id

      def user = User.get(id)

      def users = User.findAll()

      if (user == null) {
         flash.error = "Unable to delete user with id: ${id}"
         render(view: 'users', model: [users: users])
         return [users: users]
      }

      user.delete(flush:true)

      users = User.findAll()

      flash.message = "User ${user.name} deleted."

      render(view: 'users', model: [users: users])
      return [users: users]

   }





//============== Private Methods =======================


   void do_family_help() {
      flash.context_help = [ title:  'Story Families',
            content: '''
     <p>A story family is a way to 'group' stories together.  Users who belong to the same family as the story can edit
     and update the story.</p>''' ]


   }


   void do_user_help() {
      flash.context_help = [ title:  'User Administration',
            content: '''
     <p>You can edit the existing users - change roles, add/remove families, and even delete users as necessary.</p>''' ]


   }


   boolean intercept() {
      if (!admin_intercept()) {
         redirect(controller: 'main', action: 'index')
         return false
      }

      return true
   }


}
