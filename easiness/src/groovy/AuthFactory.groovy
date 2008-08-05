/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Jul 21, 2008
 * Time: 2:46:10 PM
 * To change this template use File | Settings | File Templates.
 */
class AuthFactory {

   private static _authenticator = new SimpleAuthenticator()

   private AuthFactory() {}


   public static EasyAuthenticator getAuthenticator() {
      return _authenticator
   }


}