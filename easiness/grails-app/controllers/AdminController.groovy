class AdminController extends ControllerBase {

   def beforeInterceptor = [ action: this.&intercept ]

   def index = { }

   def users = { }

   def families = {

      do_help()

      families = Family.findAll()

      return [ families: families]
   }



   def do_add_family = {

      do_help()


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

      do_help()

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



//============== Private Methods =======================


   void do_help() {
      flash.context_help = [ title:  'Story Families',
            content: '''
     <p>A story family is a way to 'group' stories together.  Users who belong to the same family as the story can edit
     and update the story.</p>''' ]


   }


   boolean intercept() {
      if (!admin_intercept()) {
         redirect(controller: 'main', action: 'index')
         return false
      }

      return true
   }


}
