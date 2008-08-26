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

      log.info "Scenario do_create success"
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

//      log.info "ScenarioController: do_edit{} "

      def scenario = Scenario.get(params.scenario_id)

      if (scenario == null) {
         flash.error = "No scenario with id: ${params.scenario_id}"
         redirect(action: 'mystories', controller: 'story')
         return
      }



      if (!process_clauses(scenario)) {
         render(view: 'edit', model:       [scenario: scenario, ordered_givens: scenario.ordered('givens'),
               ordered_conditions: scenario.ordered('conditions'),
               ordered_conclusions: scenario.ordered('conclusions')])
         return
      }


      if (scenario.hasErrors()) {
         flash.error = "There are errors in this scenario."
      } else if (!scenario.save()) {
         flash.error = "Unable to save scenario."
      } else {
         flash.message = "Scenario updated."
      }


      log.debug "##### scenario givens: ${scenario.givens}"
      log.debug "##### scenario conditions: ${scenario.conditions}"
      log.debug "##### scenario conclusions: ${scenario.conclusions}"


      def ordered_givens = scenario.ordered('givens')
      def ordered_conditions = scenario.ordered('conditions')
      def ordered_conclusions = scenario.ordered('conclusions')

      log.debug "##### successfully created and saved scenario.  About to render view."
      
      render( view: 'edit', model: [scenario: scenario,
            ordered_givens: ordered_givens,
            ordered_conditions: ordered_conditions,
            ordered_conclusions: ordered_conclusions])

   }



   def do_add_clause = {


      def obj = null

      def type = params.type

      def scenario = Scenario.get(params.scenario_id)



      if (scenario == null) {
         flash.error = "Unable to find the scenario with id: ${params.scenario_id}"
      } else {


         if (type == 'given') {
            obj = new Given()
            obj.scenario = scenario
            obj.sortOrder = next_sort_order(scenario.givens)
            scenario.givens << obj
         } else if (type == 'condition') {
            obj = new Condition()
            obj.scenario = scenario
            obj.sortOrder = next_sort_order(scenario.conditions)
            scenario.conditions << obj
         } else if (type == 'conclusion') {
            obj = new Conclusion()
            obj.scenario = scenario
            obj.sortOrder = next_sort_order(scenario.conclusions)
            scenario.conclusions << obj
         }
      }

      obj.text = " "

      if (!scenario.save()) {
         flash.error = "Unable to save scenario."
      }

      redirect( action: 'edit', controller: 'scenario', id: params.scenario_id)
      return
      

   }



    def do_delete_clause = {

       def obj = null

       def type = params.type

       if (type == 'given') {
          obj = Given.get(params.id)
       } else if (type == 'condition') {
          obj = Condition.get(params.id)
       } else if (type == 'conclusion') {
          obj = Conclusion.get(params.id)
       }


       if (obj == null) {
          flash.error = "Unable to find a ${type} clause with id: ${params.id}"
       }

       obj.delete()

       flash.message = "${type} clause removed from scenario."


       redirect( action: 'edit', controller: 'scenario', id: params.scenario_id)
       return


    }









   //----------------------------------------------------------------------------------------------
   // private methods



   def process_clauses( scenario ) {


      // here are all the existing elements.
      def givens = scenario.givens
      def conditions = scenario.conditions
      def conclusions = scenario.conclusions


      def error_str = null

      error_str = update_and_add_clauses( scenario, givens, "g", params, { new Given() } )

      if (error_str != null) {
         flash.error = error_str
         return false
      }


      error_str = update_and_add_clauses(scenario,  conditions, "w", params, { new Condition() } )
      if (error_str != null) {
         flash.error = error_str
         return false
      }

      error_str = update_and_add_clauses(scenario, conclusions, "t", params, { new Conclusion() } )
      if (error_str != null) {
         flash.error = error_str
         return false
      }


      log.info "#####  givens: ${givens}, conditions: ${conditions}, conclusions: ${conclusions}"

      return true
   }




   def update_and_add_clauses( scenario, clause_list, clause_keycode, params, creator_closure ) {

      log.info "#####  update_and_add_clauses: ${clause_list}"

      int i = 0;
      for (; i > -1; i++) {

         def text  = find_param(params, clause_keycode, "text", i)

         def code  = find_param(params, clause_keycode, "code", i)

         def idstr = find_param(params, clause_keycode, "id_", i)


         log.info "##### params: ${text}, ${code}, ${idstr}"

         if (text != null || code != null) {

            if (idstr != null) {

               int id = -1
               try {
                   id = Integer.parseInt(idstr)
               } catch( NumberFormatException nfe) {
               }               

               def clause = find_clause_with_id_in_list(clause_list, id)

               log.info "##### Found clause: ${clause} with id: ${id}"

               if (clause != null) {

                  if (text != null) { clause.text = text  }
                  if (code != null) { clause.code = code  }
               } else {
                  return "unable to find clause in ${clause_list} with id ${id}"
               }

            } else {


               def clause =  creator_closure()

               log.info "##### Creating clause: ${clause} text: ${text} ${code}"

               clause.text = text
               clause.code = code
               clause.scenario = scenario

               clause.sortOrder = next_sort_order(clause_list)

               log.info "##### adding clause: ${clause} to clause_list: ${clause_list}"

               clause_list << clause

            }
         } else {
            i = -1
            break;
         }
      }


      return null

   }



   def find_param( params, key_preamble, key_body, key_index) {

      String key = "${key_preamble}_${key_body}${key_index}"

      def res = params[key]

      if (res == null || res == "") {
         return null
      }

      return res

   }

   def find_clause_with_id_in_list( list, id) {

      log.debug("##### id: ${id}, ${0+id}")

      def clause = null

      list.each{ cl ->

         log.debug(" clause id: ${cl.id}, ${0+id}, ${id}")

         if (cl.id == id) {
            clause = cl
            return
         }
      }

      return clause

   }


/*

      // iterate through the possible given, condition and conclusion fields and add as necessary to this
      // story.

      def given_texts = findParameters(params, "g_text")
      def given_codes = findParameters(params, "g_code")
      def given_ids = findParameters(params, "g_id_")

      def condition_texts = findParameters(params, "w_text")
      def condition_codes = findParameters(params, "w_code")
      def condition_ids = findParameters(params, "w_id_")

      def conclusion_texts = findParameters(params, "t_text")
      def conclusion_codes = findParameters(params, "t_code")
      def conclusion_ids = findParameters(params, "t_id_")


      // validation
      def error_str = check_codes(givens, given_texts, given_codes, 'Given')
      if (error_str != null) {
         flash.error = error_str
         return false
      }

      error_str = check_codes(conditions, condition_texts, condition_codes, 'When')
      if (error_str != null) {
         flash.error = error_str
         return false
      }

      error_str = check_codes(conclusions, conclusion_texts, conclusion_codes, 'Then')
      if (error_str != null) {
         flash.error = error_str
         return false
      }


      def given_list = create_clauses(given_texts, given_codes, given_ids, givens)
      def cond_list = create_clauses(condition_texts, condition_codes, condition_ids, conditions)
      def concl_list = create_clauses(conclusion_texts, conclusion_codes, conclusion_ids, conclusions)

//      log.info "Analyzing clauses."
      log.debug "::::: - Given list: ${given_list}, cond_list: ${cond_list}, concl_list: ${concl_list}"


      def i = 0;

      given_list.each {

         // first we see if there's an existing clause with the same title.  if so, we update that one,
         // instead of creating a new one.

         def g = find_and_update_clause(givens, it)

         if (g == null) {

           log.debug("### Creating a new Given record for: ${it}")

           g = new Given()

           update_clause(g, it, i)

           g.scenario = scenario

           scenario.givens << g
         }
         i++;
      }

      i = 0;
      cond_list.each {

         def c = find_and_update_clause(conditions, it)

         if (c == null) {

            log.debug("#### Creating a new Condition record for: ${it}")
            c = new Condition()

            update_clause(c, it, i)

            c.scenario = scenario

            scenario.conditions << c

         }
         i++
      }



      i = 0
      concl_list.each {

         def c = find_and_update_clause(conclusions, it)

         log.debug("::: - c: ${c}")

         if (c == null) {

            log.debug("#### Creating a new Conclusion record for ${it}")
            c = new Conclusion()

            update_clause(c, it, i)

            c.scenario = scenario

            scenario.conclusions << c
         }
         i++
      }

     log.debug "scenario clauses: ${scenario.givens}, ${scenario.conditions}, ${scenario.conclusions}"

      return true

   }

      */




   /*
   def find_and_update_clause( current, pNew) {

      def found = null


      log.debug("%%% - pNew: ${pNew}")

      if (pNew.id != null) {

         current.each { cl ->

            log.debug("%%% cl: ${cl}, ${0+cl.id}, ${0+pNew.id}")


            // for some reason, these don't match with == - perhaps one is a number and the other is a string.
            if (cl.id == pNew.id) {

               log.debug("&&&& found match between ${cl.id} and ${pNew.id}")

               cl.text = pNew.text
               cl.code = pNew.code
               log.debug(">>> updating code for clause: '${cl.text}' - cl: ${cl}")
               found = cl
            } else {
               log.debug("&&&& no match between ${cl.id} and ${pNew.id}")

            }
         }

      }

//      current.each { cl ->
//
//         if (pNew.text == cl.text) {
//            cl.code = pNew.code
//            found = cl
//         }
//      }

      return found

   }

   */

   /*
   def update_clause( dest, source, order) {
      dest.text = source.text
      dest.code = source.code
      dest.sortOrder = order

   }
   */

   /*
   def check_codes( h_list, texts, codes, title) {


      if (h_list == null || h_list.size() == 0 ) {
         if (codes?.size() > texts?.size()) {
            return "We require a text description for each code block in the ${title} section."
         }
      } else {

         def list = []

         h_list.each { list << it }

         for (def i = 0; i < list.size(); i++) {

            if (list[i].text == null && texts[i] == null && codes[i] != null) {
               return "We require a text description for each code block in the ${title} section."
            }

         }
         
      }
      return null

   }
   */

   /*
   def findParameters(params, preamble) {

      def list = []


      for (int i = 0; i > -1; i++) {

         String key = "${preamble}${i}"

         if (params[key] != null && params[key] != "") {
            list << params[key]
         } else {
            i = -1
            break
         }


      }

      return list
   }
   */



   /*
   def create_clauses( tlist, clist, ilist, existing_list) {

      def list = []

      if (existing_list == null || existing_list.size() == 0) {
         if (tlist == null || tlist.size() == 0) {
           return list
         }
      }

      for (int i = 0; i < tlist.size(); i++) {

         def cl = [:]
         cl.text = tlist[i]
         cl.code = clist[i]

         int id = -1

         try {
             id = Integer.parseInt(ilist[i])
         } catch( NumberFormatException nfe) {
             id = -1
         }

         if (id > -1) {
           cl.id = id
         }

         list << cl


      }


      return list



   }
   */

   def next_sort_order( arr ) {

      if (arr == null || arr.size() == 0) {
         return 0
      }

      def tmp = []

      arr.each{ tmp << it }

      return tmp[-1].sortOrder + 1
   }




   boolean intercept() {
      if (!base_intercept()) {
         redirect(controller: 'main', action: 'index')
         return false
      }

      return true
   }

}
