/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Sep 2, 2008
 * Time: 3:51:08 PM
 * To change this template use File | Settings | File Templates.
 */
class Exporter {


   public String export_family( Family family, String topdir ) {


      FileUtil.createDirIfNeeded(topdir)

      family.stories.each { st ->

         def famdir = family.code

         export_story( st, "${topdir}/${famdir}")

      }

      return family.name


   }



   public String export_story( Story story, String dir) {


      FileUtil.createDirIfNeeded(dir)

      def fileName = FileUtil.getMixedCaseName(story.title)


      def fw = new FileWriter("${dir}/${fileName}.story", false) // false <-- overwrite


      if (story.packageText != null) {
         fw << "package ${story.packageText}\n\n"         
      }


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





}