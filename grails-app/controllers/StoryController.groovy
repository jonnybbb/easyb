class ScenarioController extends ControllerBase {


   def beforeInterceptor = [ action: this.&intercept ]


   def index = { }

   def create = {

      def parent = Story.get(params.story_id)

      if (parent == null) {
         log.error "Parent is null, unable to establish new scenario."
         flash.error = "No scenario with id: ${params.story_id}"
         redirect(action: mystories, controller: story)
         return
      }


      flash.context_help = [ title:  'Creating a Scenario',
            content: '''
             <p>Your scenario needs a title, and an optional description</p>
             <p>When you're done, hit 'Create', and you can build out the scenario's internal structure.</p>
       ''' ]


      [ parent: parent ]

   }


   def do_create = {

      def parent = Story.get(params.story_id)

      if (parent == null) {
         log.error "Parent is null"
         flash.error = "No story with id: ${params.story_id}"
         redirect( action: 'mystories', controller: story)
         return
      }

      def scenario = new Scenario(params)

      scenario.story = parent

      if (scenario.hasErrors()) {
         log.error "Scenario has errors"
         flash.error = "Errors in this scenario."
         render( view: 'create', model:[ parent: parent, scenario: scenario])
         return
      }

      if (!scenario.save()) {
         log.error "Unable to save scenario - parent: ${parent}"
         flash.error = "Unable to save scenario."
         render( view: 'create', model:[ scenario: scenario, parent : parent] )
         return
      }

      logger.info "Scenario do_create success"
      redirect(action:'edit', controller:'scenario', id: scenario.id)



   }


   def edit =  {

      def scenario = Scenario.get(params.id)

      if (scenario == null) {
         flash.error = "No scenario with id: ${params.id}"
         redirect(action: 'mystories', controller: 'story')
         return
      }


      flash.context_help = [ title:  'Filling out a Scenario',
            content: '''
<p>EasyB Scenarios consist of three parts:
<ul>
  <li>The Starting Conditions (<em>Given</em>)</li>
  <li>The Action that occurs (<em>When</em>)</li>
  <li>The Results (<em>Then</em>)</li>
</ul>
<p>For a longer explanation of Scenario structure... <em>tbd</em></p>
<p><strong>Note</strong> - you don't have to fill out the whole thing - just fill out the parts you want and click
<em>Save</em></p>
       ''' ]



      [scenario: scenario, ordered_givens: scenario.ordered('givens'), ordered_conditions: scenario.ordered('conditions'), ordered_conclusions: scenario.ordered('conclusions')]
   }



   def do_edit = {

      def scenario = Scenario.get(params.scenario_id)

      if (scenario == null) {
         flash.error = "No scenario with id: ${params.scenario_id}"
         redirect(action: 'mystories', controller: 'story')
         return
      }


      def givens = scenario.givens
      def conditions = scenario.conditions
      def conclusions = scenario.conclusions

      // iterate through the possible given, condition and conclusion fields and add as necessary to this
      // story.


      def given_texts = findParameters(params, "g_text")
      def given_codes = findParameters(params, "g_code")

      def condition_texts = findParameters(params, "w_text")
      def condition_codes = findParameters(params, "w_code")

      def conclusion_texts = findParameters(params, "t_text")
      def conclusion_codes = findParameters(params, "t_code")


      // validation
      def error_str = check_codes(given_texts, given_codes, 'Given')
      if (error_str != null) {
         flash.error = error_str
         render(view: edit, model:       [scenario: scenario, ordered_givens: scenario.ordered('givens'), ordered_conditions: scenario.ordered('conditions'), ordered_conclusions: scenario.ordered('conclusions')])
         return
      }

      error_str = check_codes(condition_texts, condition_codes, 'When')
      if (error_str != null) {
         flash.error = error_str
         render(view: edit, model:       [scenario: scenario, ordered_givens: scenario.ordered('givens'), ordered_conditions: scenario.ordered('conditions'), ordered_conclusions: scenario.ordered('conclusions')])
         return
      }

      error_str = check_codes(conclusion_texts, conclusion_codes, 'Then')
      if (error_str != null) {
         flash.error = error_str
         render(view: edit, model: [scenario: scenario, ordered_givens: scenario.ordered('givens'), ordered_conditions: scenario.ordered('conditions'), ordered_conclusions: scenario.ordered('conclusions')])
         return
      }


      def given_list = create_clauses(given_texts, given_codes)
      def cond_list = create_clauses(condition_texts, condition_codes)
      def concl_list = create_clauses(conclusion_texts, conclusion_codes)


      def i = 0;

      given_list.each {

         def g = new Given()

         update_clause(g, it, i)

         scenario.givens << g
         i++;
      }

      i = 0;
      cond_list.each {

         def c = new Condition()

         update_clause(c, it, i)

         scenario.conditions << c
         i++
      }

      i = 0
      concl_list.each {

         def c = new Conclusion()

         update_clause(c, it, i)

         scenario.conclusion << c
         i++
      }


      if (scenario.hasErrors()) {
         flash.error = "There are errors in this scenario."
         render( view: edit, model:       [scenario: scenario, ordered_givens: scenario.ordered('givens'), ordered_conditions: scenario.ordered('conditions'), ordered_conclusions: scenario.ordered('conclusions')])
      } else if (!scenario.save()) {
         flash.error = "Unable to save scenario."
         render( view: edit, model:       [scenario: scenario, ordered_givens: scenario.ordered('givens'), ordered_conditions: scenario.ordered('conditions'), ordered_conclusions: scenario.ordered('conclusions')])
      } else {
         flash.message = "Scenario updated."
      }


      redirect( action: edit, controller: 'scenario', id: scenario.id)      

   }


   def update_clause( dest, source, order) {
      dest.text = source.text
      dest.code = source.code
      dest.order = order

   }


   def check_codes( texts, codes, title) {

      if (codes?.size() > texts?.size()) {
         return "We require a text description for each code block in the ${title} section."
      }

      return null

   }


   def findParameters(params, preamble) {

      def list = []


      for (int i = 0; i > -1; i++) {

         String key = "${preamble}${i}"

         if (params[key] != null) {
            list << params[key]
         } else {
            i = -1
            break
         }


      }

      return null
   }



   boolean intercept() {
      if (!base_intercept()) {
         redirect(controller: 'main', action: 'index')
         return false
      }

      return true
   }


   def create_clauses( tlist, clist) {

      def list = []

      if (tlist == null || tlist.size() == 0) {
        return list
      }


      for (int i = 0; i < tlist.size(); i++) {

         def cl = new Clause()
         cl.text = tlist[i]
         cl.code = clist[i]

         list << cl


      }


      return list



   }


}
