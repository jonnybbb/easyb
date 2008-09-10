package easyb.grails

import grails.util.GrailsWebUtil
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.disco.easyb.BehaviorStep
import org.disco.easyb.domain.Behavior
import org.disco.easyb.listener.ExecutionListenerAdaptor
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
		setupMockWebRequest(behavior)
	}

    public void stopBehavior(BehaviorStep behaviorStep, Behavior behavior) {
        RequestContextHolder.setRequestAttributes(null)
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
}