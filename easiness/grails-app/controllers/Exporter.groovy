/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Sep 2, 2008
 * Time: 3:51:08 PM
 * To change this template use File | Settings | File Templates.
 */
class Exporter {


   public String export_story( Story story, String dir) {


      createDirIfNeeded(dir)

      def fileName = getMixedCaseName(story.title)


      def fw = new FileWriter("${dir}/${fileName}.story", false) // false <-- overwrite


      fw << "package ${story.packageText}\n\n"
      fw << story.imports+"\n\n"

      if (story.description) {
         fw << "description = '''\n"
         fw << story.description+"\n"
         fw << "'''\n\n"
      }

      fw << story.setUp+"\n"



      story.scenarios.each { sc ->

         fw << "scenario \"${sc.title}\", {\n"


         sc.ordered('givens').each { given ->

            fw << "   given \"${given.text}\", { \n"
            fw << "      ${given.code}\n"
            fw << "   }\n"
         }

         sc.ordered('conditions').each { cond ->

            fw << "   when \"${cond.text}\", { \n"
            fw << "      ${cond.code}\n"
            fw << "   }\n"
         }


         sc.ordered('conclusions').each { concl ->

            fw << "   then \"${concl.text}\", { \n"
            fw << "      ${concl.code}\n"
            fw << "   }\n"
         }

      fw << "}\n\n"

      }

      fw << story.tearDown+"\n"


      fw.flush()
      
      fw.close()

      return fileName

   }


   public String getMixedCaseName( String title ) {

      if (title.indexOf(' ') != -1) {

         def mixedCase = title.toLowerCase().split(' ').collect {
            it.substring(0,1).toUpperCase() + it.substring(1) }.join('')


         // if we did this, it would lowercase the first letter, making it camelCase
         //camelCase.substring(0,1).toLowerCase() + camelCase.substring(1)

         return mixedCase
      }

      return title
   }


   private boolean createDirIfNeeded( String dir ) {

      def dd = new File(dir)

      if (dd.exists()) {
         return true
      }

      dd.mkdir()

      return false

   }

}