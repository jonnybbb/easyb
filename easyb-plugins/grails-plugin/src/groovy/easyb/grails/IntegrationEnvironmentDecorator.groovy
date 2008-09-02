package easyb.grails

import grails.util.GrailsWebUtil
import org.codehaus.groovy.grails.support.PersistenceContextInterceptor
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.disco.easyb.BehaviorStep
import org.disco.easyb.domain.Behavior
import org.disco.easyb.listener.ExecutionListenerAdaptor
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import org.springframework.web.context.request.RequestContextHolder

class IntegrationEnvironmentDecorator extends ExecutionListenerAdaptor {

	def appCtx
	def interceptor

	private GrailsWebRequest setupMockWebRequest(Behavior behavior) {
		def webRequest = GrailsWebUtil.bindMockWebRequest(appCtx)
		webRequest.getServletContext().setAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT, appCtx)
        if (behavior.getFile().name.contains('Controller')) {
            webRequest.controllerName = GrailsClassUtils.getLogicalPropertyName(calcSubjectClassname(behavior.getFile().name), 'Controller')
        } else {
            webRequest.controllerName = 'test'
        }
    }

	public void startBehavior(Behavior behavior) {
        def beanNames = appCtx.getBeanNamesForType(PersistenceContextInterceptor.class)
        if (beanNames.size() > 0) interceptor = appCtx.getBean(beanNames[0])

        try {
            interceptor?.init()
        } catch (Exception ignore) {}

		setupMockWebRequest(behavior)
	}

    public void stopBehavior(BehaviorStep behaviorStep, Behavior behavior) {
        try {
            interceptor?.destroy()
        } catch (Exception ignore) {}
	}

    static String calcSubjectClassname(String behaviorFilename) {
        String result
        ['.specification', '.story'].each {
            if (!result && behaviorFilename.toLowerCase().endsWith(it)) {
                result = behaviorFilename.substring(0, behaviorFilename.indexOf(it))
            }
        }

        if (!result && behaviorFilename.toLowerCase().endsWith('.groovy')) {
            ['Specification', 'Story'].each {
                if (!result && behaviorFilename.contains(it)) {
                    result = behaviorFilename.substring(0, behaviorFilename.indexOf(it))
                }
            }
        }

        return result
    }

	public void stopStep() {
		RequestContextHolder.setRequestAttributes(null)
	}
}