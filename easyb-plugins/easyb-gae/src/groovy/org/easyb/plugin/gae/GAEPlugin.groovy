package org.easyb.plugin.gae

import org.easyb.plugin.BasePlugin
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper

/**
 *
 */
class GAEPlugin extends BasePlugin{

  def ldsHelper

  String getName() {
    return "GAE";
  }

  def Object beforeStory(Binding binding) {
    ldsHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    ldsHelper.setUp()

	binding.ldsHelper = ldsHelper
  }

  def Object afterStory(Binding binding) {
    ldsHelper.tearDown()
  }


}
