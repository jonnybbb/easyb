package org.easyb.plugin.xmlunit

import org.custommonkey.xmlunit.XMLUnit
import org.easyb.plugin.BasePlugin
import org.easyb.plugin.xmlunit.XMLUnitCategories

class XMLUnitPlugin extends BasePlugin {

  public String getName() {
    return "xmlunit"
  }
  //key that you put public Object and not def!
  public Object beforeStory(Binding binding) {
    Object.mixin XMLUnitCategories
    XMLUnit.setIgnoreWhitespace(true)
    binding.XMLUnit = XMLUnit
  }
}