/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Sep 17, 2008
 * Time: 9:21:44 PM
 * To change this template use File | Settings | File Templates.
 */

import org.disco.easyb.ant.Report
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.disco.easyb.report.XmlReportWriter
import org.disco.easyb.ConsoleReporter
import org.disco.easyb.BehaviorRunner
import org.disco.easyb.domain.BehaviorFactory;


class EasybExecutor {


   def exec_family( args ) {

      def list = args.families
      def storyTopDir = args.src
      def outputTopDir = args.out
      def analyzer = args.analyzer


      def results = []

      // execute each story inside each family.
      //
      list.each{ fm ->

         def familyStoryDir = "${storyTopDir}/${fm.code}"
         def familyOutDir = "${outputTopDir}/${fm.code}"

         FileUtil.createDirIfNeeded(familyStoryDir)
         FileUtil.createDirIfNeeded(familyOutDir)


         fm.stories.each{ st ->


            def res = exec_story( story: st, src: familyStoryDir, out: familyOutDir, analyzer: analyzer)

            if (res == null) {
               res = "${st.title} : Success"
            }

            results << res

         }
         
      }

      return results

   }


   def exec_story( args ) {

      def s = args.story
      def storyDir = args.src
      def outputDir = args.out
      def analyzer = args.analyzer

      

      def appCtx = [:]


      def story_filename = FileUtil.getMixedCaseName(s.title)

      FileUtil.createDirIfNeeded(outputDir)

      def story_basename = FileUtil.getMixedCaseName(s.title)

      //def reports = [new Report(location:"${outputDir}/${story_file}.xml",format:"xml",type:"easyb")]

      def outputFilename = "${outputDir}/${story_basename}.xml"

      def fileList = [ outputFilename ]

      def reports = []

      fileList.each{  file ->

         reports << new XmlReportWriter( file )
      }


      def runner = new BehaviorRunner( reports, new ConsoleReporter())


      def behaviors = []

      String story_fullName = "${storyDir}/${story_filename}.story"

      //behaviors << BehaviorFactory.createBehavior(new GroovyShellConfiguration(), "${storyDir}/${story_filename}")
      behaviors << BehaviorFactory.createBehavior(new File(story_fullName))


      try {

         runner.runBehavior( behaviors )


         analyzer.build_and_store_report( s, outputFilename )


      } catch (Exception e) {

         def fw = new FileWriter(outputFilename)

         fw << "<easyb sucess='false'>\n"
         fw << "   <error><![CDATA["
         fw << "      ${e}"
         fw << "   ]]></error>"
         fw << "</easyb>\n"

         fw.close()

         return "Story: ${s.title} Failed: ${e.message}"
      }


//      BehaviorRunner br = new BehaviorRunner(reports, appCtx, ApplicationHolder.application);
//      br.runBehavior(testSource)

      return null


      
   }


}