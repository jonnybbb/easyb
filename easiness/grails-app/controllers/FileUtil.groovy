/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Sep 17, 2008
 * Time: 9:29:32 PM
 * To change this template use File | Settings | File Templates.
 */
class FileUtil {


   static public boolean createDirIfNeeded( String dir ) {

      def dd = new File(dir)

      if (dd.exists()) {
         return true
      }

      dd.mkdir()

      return false

   }



   static public String getMixedCaseName( String title ) {

      if (title.indexOf(' ') != -1) {

         def mixedCase = title.toLowerCase().split(' ').collect {
            it.substring(0,1).toUpperCase() + it.substring(1) }.join('')


         // if we did this, it would lowercase the first letter, making it camelCase
         //camelCase.substring(0,1).toLowerCase() + camelCase.substring(1)

         return mixedCase
      }

      return title
   }


}