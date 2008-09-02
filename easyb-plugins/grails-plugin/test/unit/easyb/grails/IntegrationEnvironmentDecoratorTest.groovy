package easyb.grails

import grails.util.GrailsWebUtil
import groovy.mock.interceptor.MockFor
import org.codehaus.groovy.grails.commons.spring.GrailsApplicationContext
import org.codehaus.groovy.grails.orm.hibernate.support.HibernatePersistenceContextInterceptor
import org.codehaus.groovy.grails.support.PersistenceContextInterceptor
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.disco.easyb.domain.Specification
import org.disco.easyb.domain.Story
import org.springframework.context.ApplicationContext
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockServletContext
import org.springframework.web.context.request.RequestContextHolder

class IntegrationEnvironmentDecoratorTest extends GroovyTestCase {

    def applicationContext
    GrailsWebRequest gwr

    IntegrationEnvironmentDecorator contextDecorator

    void setUp() {
        applicationContext = new MockFor(GrailsApplicationContext)
        gwr = new GrailsWebRequest(new MockHttpServletRequest(), new MockHttpServletResponse(), new MockServletContext())
    }


    private void assertBehaviorStartInitsInterceptorAndMockWebRequest(String filename) {
        def interceptor = new MockFor(HibernatePersistenceContextInterceptor)
        interceptor.demand.init(1) {}

        applicationContext.demand.getBeanNamesForType(1) { Class clazz ->
            assertEquals(PersistenceContextInterceptor, clazz)
            return ['anOtherInterceptorBean']
        }

        applicationContext.demand.getBean(1) { beanName ->
            assertEquals('anOtherInterceptorBean', beanName)
            return new HibernatePersistenceContextInterceptor()
        }
        
        def grailsWebUtil = new MockFor(GrailsWebUtil)
        grailsWebUtil.demand.bindMockWebRequest(1) { ApplicationContext appCtx ->
            assertNotNull(appCtx)
            return gwr
        }


        interceptor.use {
            applicationContext.use {
                grailsWebUtil.use {
                    contextDecorator = new IntegrationEnvironmentDecorator(appCtx: new GrailsApplicationContext())
                    contextDecorator.startBehavior(new Story(null, 'Some story', new File(filename)))
                }
            }
        }

        assertEquals(gwr.servletContext.getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT), contextDecorator.appCtx)
    }

    void testBehaviorStartForNonController() {
        assertBehaviorStartInitsInterceptorAndMockWebRequest('FooBarServiceSpecification.groovy')
        assertEquals('test', gwr.controllerName)
    }

    void testBehaviorStartForControllerAlsoAddsControllerNameToWebRequest() {
        assertBehaviorStartInitsInterceptorAndMockWebRequest('FooBarControllerSpecification.groovy')

        assertEquals('fooBar', gwr.controllerName)
    }

	void testBehaviorStopDestroysInterceptor() {
		def interceptor = new MockFor(HibernatePersistenceContextInterceptor)
		interceptor.demand.destroy(1) {}

		interceptor.use {
			contextDecorator = new IntegrationEnvironmentDecorator()
			contextDecorator.interceptor = new HibernatePersistenceContextInterceptor()
			contextDecorator.stopBehavior(null, new Specification(null, "Some story", new File("FooBarController.specification")))
		}

	}

    void testDeriveTargetClassnameFromBehaviorFilename() {
        assertBehaviorName('FooBarController',
                ['FooBarController.specification', 'FooBarControllerSpecification.groovy',
                 'FooBarControllerStory.groovy', 'FooBarController.story'])

        assertBehaviorName('FooBar',
                ['FooBar.specification', 'FooBarSpecification.groovy',
                 'FooBarStory.groovy', 'FooBar.story'])

        assertNull(IntegrationEnvironmentDecorator.calcSubjectClassname('SomethingElse.txt'))
    }

    private void assertBehaviorName(String expectedName, List filenames) {
        filenames.each {
            assertEquals(expectedName, IntegrationEnvironmentDecorator.calcSubjectClassname(it))
        }
    }

	void testBehaviorStepStopFlushesRequestAttributes() {
		def requestContextHolder = new MockFor(RequestContextHolder)
		requestContextHolder.demand.setRequestAttributes(1) {
			assertNull(it)
		}

		requestContextHolder.use {
			contextDecorator = new IntegrationEnvironmentDecorator(appCtx: new GrailsApplicationContext())
			contextDecorator.stopStep()
		}
	}
}
