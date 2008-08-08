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

      [ story: story]

   }


   def mystories = {

      def stories = Story.findAllByUser(flash.user)

      if (stories.size > 0) {
         flash.context_help = [ title:  'Selecting stories',
               content: '''
                <p>Click on a story to the left, and you'll be able to start creating stories for it.</p>
                <p>Or, click on the 'X' next to the story name to delete it.</p>
          ''' ]

      }

      return [ stories: stories]

   }


   def create = {

      flash.context_help = [ title:  'Creating a Story',
            content: '''
     <p>A story represents a set of stories, all organized around a common theme.  For examle, you can create a
     login story, and create a number of stories about different ways to log in (and also, how you might fail to
        log in).
     </p>
     <p>Once you create the story, you'll be able to create a series of stories for it.</p>''' ]


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

      if (story.hasErrors()) {
         render(view: create)
         return
      }

      if (!story.save()) {
         flash.error = "Unable to save story."
         render(view:create)
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
