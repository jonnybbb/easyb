/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Jul 21, 2008
 * Time: 2:42:07 PM
 * To change this template use File | Settings | File Templates.
 */
interface EasyAuthenticator {


   Map authenticate( String username, String password, Map options)

   Map findGroup( String id, Map options )


}