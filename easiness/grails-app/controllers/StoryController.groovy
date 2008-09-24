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


      session.current_story = story

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



      flash.context_help = [ title:  'Expanding a Story',
            content: '''
     <p>You can use this page to add scenarios to your story.  Click on the story name to edit the story details.''' ]


      session.current_story = story

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

      flash.context_help = story_export_help()

      return [ stories: stories]


   }


   def run = {

      def stories = Story.findAllByUser(flash.user)

      flash.context_help = [ title: 'Story Execution',
            content: '''
      <p>You can run one or all of your stories from this page. </p>
<p>To include your code libraries into Easiness, place the jar files in the easiness/lib directory, and restart easiness.</p> 
      ''']

      return [ stories: stories]
      
   }






   def viewfamilies = {

      def families = flash.user.families

      flash.context_help = [ title: 'Story Families',
            content: '''
      <p>You can edit and update stories that belong to your families.  Click on a family to view and edit the stories.</p>
      ''']


      return [families: families]

   }



   def family = {

      def fam = Family.get(params.id)

      if (fam == null) {
         flash.error = "No such family: ${params.id}"
         redirect(controller: 'story', action: 'mystories')
         return
      }


      def stories = []

      fam.stories.each { s ->
         stories << s
      }


      flash.context_help = [ title: 'Story Family',
            content: '<p>Here are all the stories in family <strong>'+fam.name+'</strong>.  Click on a story to expand or edit it.</p>']


      return ['family': fam, 'stories': stories]
   }





   def export_story = {
      def story = Story.get(params.id)

      if (story == null) {
         flash.error = "No such story ${params.id}"
         redirect(controller:'story', action:'mystories')
         return
      }


      return [story: story]

   }


   def export_family = {
      def families = flash.user.families

      flash.context_help = family_export_help()


      return [families: families]
   }



   def run_family = {

      def families = flash.user.families

      flash.context_help = [ title: 'Family Execution',
            content: '''
      <p>You can run the stories in one or more of your story families from this page. </p>
      ''']

      return [ families: families]

   }



   def family_statistics = {
      flash.context_help = [ title: 'Family Statistics',
            content: '''
      <p>This page shows you the latest statistics on the stories in each of your families - the most recent pass/fail/incomplete numbers and
      the time and date of the last run.</p>
      ''']

      return [ families: flash.user.families]

      
   }



   //-------------------[ Actions ]---------------------------------


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



      story.packageText = params.packageText
      story.imports = params.imports
      story.setUp = params.setup
      story.tearDown = params.teardown


      if (story.hasErrors()) {
         render(view: 'edit')
         return
      }

      if (!story.save()) {
         flash.error = "Unable to update story."
         render(view: 'edit')
         return
      }

      session.current_story = story
      

      redirect(controller: 'story', action: 'mystories')
      return

   }



   def do_export_stories = {

      def stories = flash.user.stories

      def list = gather_object_ids("story", stories, params)



      def exporter = new Exporter()


      def namelist = []

      list.each { st ->

         //log.debug "###### exporting: ${st.title}"
         namelist << exporter.export_story(st, "story")

      }


      def msg =  "Export successful for the following stories:  <ul>"

      namelist.each { name ->
         msg += " <li>${name}</li>"
      }

      msg += "</ul>"

      flash.message = msg

      flash.context_help = story_export_help()

      render('view': 'export', model: [ stories: flash.user.stories ])
   }




   def do_export_family = {

      
      def list = []

      list << Family.get(params.id)

      flash.message = export_family_list(list)

      render(view: 'viewfamilies', model: [ families: flash.user.families ])

   }


   def do_export_families = {

      def families = flash.user.families

      def list = gather_object_ids( "family", families, params )


      flash.message = export_family_list(list)


      flash.context_help = family_export_help()

      render(view: 'family_export', model: [ families: flash.user.families ])
   }




   def do_run = {




      def story = Story.get(params.id)


      def exporter = new Exporter()


      def story_analysis_list = []

      def analyzer = new EasybXmlAnalyzer( story_analysis_list )


      def story_filename = exporter.export_story(story, "story/")



      def story_exec = new EasybExecutor()



      def msg = story_exec.exec_story(story: story, src: "story", out: "test/reports", analyzer: analyzer)

      if (msg != null) {
         flash.error = msg
      } else {
         flash.message = "Execution of story: ${story.title}: Success"
      }


      create_analysis_report( story_analysis_list )



      redirect(action: 'mystories', controller: 'story')


   }



   def do_run_family = {

      def families = flash.user.families

      def list = [ Family.get(params.id) ]

      def msg_list = exec_family_list( list )

      def results = "<ul>\n"

      msg_list.each { msg ->
         results += "  <li>${msg}</li>\n"
      }

      results += "</ul>\n"

      flash.message = results


      redirect(action: 'viewfamilies', controller: 'story' )



   }



   def do_run_families = {

      def families = flash.user.families

      def list = gather_object_ids( "family", families, params )

      def msg_list = exec_family_list( list )

      def results = "<ul>\n"
      msg_list.each { msg ->
         results += "  <li>${msg}</li>\n"
      }

      results += "</ul>\n"

      flash.message = results


      redirect(action: 'viewfamilies', controller: 'story' )
      



   }




   //----------------- [ private ] -------------------------------

   boolean intercept() {
      if (!base_intercept()) {
         redirect(controller: 'main', action: 'index')
         return false
      }

      return true
   }


   def story_export_help() {
     return [ title: 'Story Export',
            content: '''
       <p>
           You can export one or all of your stories here - check the stories you want to export, and click the
           'export' button.
       </p>
       <p>
           The stories will be generated in the <tt>story/</tt> subdirectory of the <tt>easiness/</tt> project directory.
       </p>
      ''']

   }


   def family_export_help() {

      return [ title: 'Story Family Export',
            content: '''
      <p>Click the checkbox next to each family you'd like to export to the fileystem, and then click the 'Export' button. </p> <p>
            The <tt>.story</tt> files will be generated in the <tt>story/&lt;family_name&gt; subdirectory of the <tt>easiness/</tt> project directory</p>
      ''']

   }

   def gather_object_ids( prefix, obj_list, params ) {


      def list = []

      obj_list.each { obj ->

         def key = "${prefix}_${obj.id}"

         def value = params[key]

         if (value != null) {

            list << obj

         }
      }


      return list
   }



   def export_family_list( list ) {
      def exporter = new Exporter()


      def namelist = []

      list.each { fm ->

         namelist << exporter.export_family(fm, "story")
      }


      def msg =  "Export successful for the following families:  <ul>"

      namelist.each { name ->
         msg += " <li>${name}</li>"
      }

      msg += "</ul>"


      return msg
   }



   def exec_family_list( list ) {


      def exporter = new Exporter()

      def namelist = []

      list.each { fm ->

         namelist << exporter.export_family(fm, "story")
      }



      def exec = new EasybExecutor()


      def story_report_list = []

      def analyzer = new EasybXmlAnalyzer( story_report_list )


      def messages = exec.exec_family(families: list, src: "story", out: "test/reports", analyzer: analyzer )


      create_analysis_report( story_report_list )


      return messages

   }


   def create_analysis_report( list ) {

      list.each { data ->

         def rep = new RunReport(data)

         data.story.reports << rep

         data.story.save()
      }


   }

}
