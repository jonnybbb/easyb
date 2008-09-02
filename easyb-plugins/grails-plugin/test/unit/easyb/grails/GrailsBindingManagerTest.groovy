package easyb.grails

import groovy.mock.interceptor.MockFor
import org.hibernate.FlushMode
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.springframework.orm.hibernate3.SessionHolder
import org.springframework.transaction.support.TransactionSynchronizationManager

class GrailsBindingManagerTest extends GroovyTestCase {

    GrailsBindingManager bindingManager
    def fakeSessionFactory = [:] as SessionFactory
    def sessionFactoryUtils
    def transactionSynchronizationManager

	void testFactoryMethodHandlesNull() {
		shouldFail(IllegalArgumentException) {
			GrailsBindingManager.newInstance(null)
		}
	}

    void testBind() {
        Session session = [
            setFlushMode:{ flushMode ->
                assertEquals(FlushMode.AUTO, flushMode)
            }] as Session

        sessionFactoryUtils = new MockFor(SessionFactoryUtils)
        sessionFactoryUtils.demand.getSession(1) { sessFactory, bool ->
            assertEquals(fakeSessionFactory, sessFactory)
            assertTrue(bool)
            return session
        }

        transactionSynchronizationManager = new MockFor(TransactionSynchronizationManager)
        transactionSynchronizationManager.demand.bindResource(1) { sessFactory, sessionHolder ->
            assertEquals(fakeSessionFactory, sessFactory)
            assertEquals(session, sessionHolder.session)
        }

        sessionFactoryUtils.use {
            transactionSynchronizationManager.use {
                bindingManager = GrailsBindingManager.newInstance(fakeSessionFactory)
                bindingManager.bind()
            }
        }
    }

    void testUnbind() {
        def fakeSession = [:] as Session

        transactionSynchronizationManager = new MockFor(TransactionSynchronizationManager)
        transactionSynchronizationManager.demand.unbindResource(1) { sessFactory ->
            assertEquals(fakeSessionFactory, sessFactory)
            return new SessionHolder(fakeSession)
        }

        sessionFactoryUtils = new MockFor(SessionFactoryUtils)
        sessionFactoryUtils.demand.closeSession(1) { session ->
            assertEquals(fakeSession, session)
        }
        sessionFactoryUtils.use {
            transactionSynchronizationManager.use {
                bindingManager = GrailsBindingManager.newInstance(fakeSessionFactory)
                bindingManager.unbind()
            }
        }
    }
}