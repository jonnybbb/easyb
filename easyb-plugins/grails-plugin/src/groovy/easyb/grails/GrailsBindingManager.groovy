package easyb.grails

import org.hibernate.FlushMode
import org.hibernate.Session
import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.springframework.orm.hibernate3.SessionHolder
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.hibernate.SessionFactory

class GrailsBindingManager {

	static GrailsBindingManager newInstance(SessionFactory sessionFactory) {
		if (!sessionFactory) throw new IllegalArgumentException("sessionFactory cannot be null")
		return new GrailsBindingManager(sessionFactory)
	}

	private final SessionFactory sessionFactory

	private GrailsBindingManager(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory
	}

	void bind() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true)
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        session.setFlushMode(FlushMode.AUTO)
    }

    void unbind() {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory)
        SessionFactoryUtils.closeSession(sessionHolder.session)
    }
}