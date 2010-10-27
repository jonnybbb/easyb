package org.easyb.plugin.gaelyk

import org.easyb.plugin.BasePlugin
import groovyx.gaelyk.GaelykCategory
/**
 *
 */
class GaelykPlugin extends BasePlugin{
  public Object beforeStory(Binding binding) {
    Object.mixin GaelykCategory
  }


  public String getName() {
    return "gaelyk"
  }
}
