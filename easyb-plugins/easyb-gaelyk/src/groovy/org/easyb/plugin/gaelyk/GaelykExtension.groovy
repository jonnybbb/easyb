package org.easyb.plugin.gaelyk

import groovyx.gaelyk.GaelykCategory
import org.easyb.plugin.SyntaxExtension

class GaelykExtension implements SyntaxExtension{

	def boolean autoLoad() {
	    return false
	  }

	  def String getName() {
	    return "gaelykCategory"
	  }

	  def Map<String, Closure> getSyntax() {
	    return [:]
	  }

	  def Class[] getExtensionCategories() {
	    return [GaelykCategory.class]
	  }
}
