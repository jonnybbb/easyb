class StoryController extends ControllerBase {

   def beforeInterceptor = [ action: this.&intercept ]


    def index = { }


   def edit = {

      def story = Story.get(params.id)

      if (story == null) {
         flash.error = "No such story ${params.id}"
         redirect(controller:story, action:mystorys)
         return
      }


      flash.context_help = [ title:  'Editing a Story',
            content: '''
     <p>On this page, you can update the title, description and family for your existing story.''' ]


      [ story: story, families: Family.findAll()]

   }


   def expand = {

      def story = Story.get(params.id)

      if (story == null) {
         flash.error = "No such story ${params.id}"
         redirect(controller:story, action:mystorys)
         return
      }

      [ story: story]

   }


   def mystories = {

      def stories = Story.findAllByUser(flash.user)

      if (stories.size > 0) {
         flash.context_help = [ title:  'Selecting stories',
               content: '''
                <p>Click on a story name to the left, and you'll be able to start creating scenarios for it.</p>
                <p>Click on the <em>'run'</em> link to execute the scenarios.</p>
                <p>Click on the <em>'edit'</em> link to update the story name, description and/or family</p>
          ''' ]

      }

      return [ stories: stories]

   }


   def create = {

      flash.context_help = [ title:  'Creating a Story',
            content: '''
     <p>A story represents a set of scenarios, all organized around a common theme.  For examle, you can create a
     login story, and create a number of scenarios about different ways to log in (and also, how you might fail to
        log in).
     </p>
     <p>In this step, you provide the name, description and <em>family</em> for the story.  The family you choose
        will define which users have the rights to edit this story.</p>
     <p>Once you create the story, you'll be able to create a series of scenarios for it.</p>''' ]


      //log.debug("%%% - families: ${Family.findAll()}")

      [families: Family.findAll()]

   }



   def statistics = {

      def stories = Story.findAllByUser(flash.user)

      flash.context_help = [ title: 'Story Statistics',
            content: '''
      <p>This page shows you the latest statistics on your stories - the most recent pass/fail/incomplete numbers and
      the time and date of the last run.</p>
      ''']

      return [ stories: stories]

   }


   def export = {

      def stories = Story.findAllByUser(flash.user)

      flash.context_help = [ title: 'Story Export',
            content: '''
       <p>
           You can export one or all of your stories here - just provide the appropriate directory name, and click the
           'export' button.
       </p>
      ''']

      return [ stories: stories]


   }


   def run = {

      def stories = Story.findAllByUser(flash.user)

      flash.context_help = [ title: 'Story Execution',
            content: '''
      <p>You can run one or all of your stories from this page. </p>
      ''']

      return [ stories: stories]
      
   }



   def do_create = {

      def story = new Story(params)


      story.user = flash.user
      story.family = Family.get(params.family_id)

      if (story.hasErrors()) {
         render(view: 'create')
         return
      }

      if (!story.save()) {
         flash.error = "Unable to save story."
         render(view:'create')
         return
      }

      redirect(controller: 'story', action: 'mystories')
      return

   }


   def do_edit = {


      def story = Story.get(params.id)

      if (story == null) {
         flash.error = "No story found with id: ${params.id}"
         render(view: 'edit')
         return
      }

      story.family = Family.get(params.family_id)

      if (story.hasErrors()) {
         render(view: 'edit')
         return
      }

      if (!story.save()) {
         flash.error = "Unable to update story."
         render(view: 'edit')
         return
      }

      redirect(controller: 'story', action: 'mystories')
      return

   }

   boolean intercept() {
      if (!base_intercept()) {
         redirect(controller: 'main', action: 'index')
         return false
      }

      return true
   }

}
