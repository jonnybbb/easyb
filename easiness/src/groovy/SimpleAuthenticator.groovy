/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Jul 21, 2008
 * Time: 2:43:36 PM
 * To change this template use File | Settings | File Templates.
 */
class SimpleAuthenticator implements EasyAuthenticator {

   public Map authenticate(String username, String password, Map other  = [:]) {


      Map results = [ 'success': false, 'error' : 'invalid authentication.' ]


      User tmp = User.findByName(username)

      println "username: ${username}, password: ${password}"

      if (username == password && tmp != null) {

         results['success'] = true

         results['groups'] = ['aaa', 'bbb', 'ccc']

         results['user'] = tmp
      } else {
         println "No such user: ${username}"
      }

      return results            

   }

   public Map findGroup(String id, Map other = [:]) {

      Map results = [:]

      results['name'] = id

      return results

   }


}