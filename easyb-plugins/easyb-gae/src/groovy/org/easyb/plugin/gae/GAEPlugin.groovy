package org.easyb.plugin.gae

import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.google.appengine.api.memcache.MemcacheServiceFactory
import org.easyb.plugin.BasePlugin
import com.google.appengine.api.users.UserServiceFactory
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig
import com.google.appengine.api.labs.taskqueue.QueueFactory

/**
 *
 */
class GAEPlugin extends BasePlugin {

  def localServiceHelper
  /**
   * 
   * @return String
   */
  String getName() {
    return "GAE"
  }
  /**
   *
   * @param binding
   * @return
   */
  def Object beforeStory(Binding binding) {
    localServiceHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
            new LocalTaskQueueTestConfig(), new LocalMemcacheServiceTestConfig(), new LocalUserServiceTestConfig())
    localServiceHelper.setUp()

    binding.localServiceHelper = localServiceHelper
    binding.datastore = DatastoreServiceFactory.getDatastoreService()
    binding.memcache = MemcacheServiceFactory.getMemcacheService()
    binding.user = UserServiceFactory.getUserService()
    binding.queue = QueueFactory.getDefaultQueue()
    binding.localTaskQueue = LocalTaskQueueTestConfig.getLocalTaskQueue()
  }
  /**
   * 
   * @param binding
   * @return
   */
  def Object afterStory(Binding binding) {
    localServiceHelper.tearDown()
  }


}
