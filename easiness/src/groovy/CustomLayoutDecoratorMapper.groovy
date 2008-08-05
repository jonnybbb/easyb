/**
 * Created by IntelliJ IDEA.
 * User: johnbr
 * Date: Jul 23, 2008
 * Time: 4:28:36 PM
 * To change this template use File | Settings | File Templates.
 */
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import org.codehaus.groovy.grails.web.sitemesh.GrailsLayoutDecoratorMapper
import com.opensymphony.module.sitemesh.*
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.codehaus.groovy.grails.web.metaclass.ControllerDynamicMethods

class CustomLayoutDecoratorMapper extends GrailsLayoutDecoratorMapper {

   public Decorator getDecorator(HttpServletRequest request, Page page) {
       Decorator decorator = super.getDecorator(request, page)
       if (decorator == null) {
           decorator = getNamedDecorator(request, "main")
       }
       return decorator
   }
}

