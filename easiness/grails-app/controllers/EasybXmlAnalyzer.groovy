/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Sep 23, 2008
 * Time: 4:19:16 PM
 * To change this template use File | Settings | File Templates.
 */
class EasybXmlAnalyzer {

   private List  result_list = []


   public EasybXmlAnalyzer( pResultList ) {

      result_list = pResultList

   }


   def build_report(Story story, String file_path ) {

      def results = [:]

      def data = new XmlSlurper().parse( new File(file_path))

      results.total = Integer.parseInt(data.stories.@scenarios.text())
      results.failures = Integer.parseInt(data.stories.@failedscenarios.text())
      results.pending = Integer.parseInt(data.stories.@pendingscenarios.text())

      results.success = results.total - (results.failures + results.pending)


      results.failed_scenarios = []
      results.failure_reasons = [:]

      data.stories.story.each { st ->
         st.scenario.each { sc ->
            if (sc.@result.text() == "failure") {
               results.failed_scenarios << sc.@name.text()

               sc.then.each { th ->

                  if (th.@result.text() == "failure") {

                     results.failure_reasons[sc.@name.text()] += "" + th.failure.@message.text()
                  }
               }
            }
         }
      }


      results.story = story


      return results

   }

   def build_and_store_report( Story story, String file_path ) {


      result_list << build_report(story, file_path )


   }





}