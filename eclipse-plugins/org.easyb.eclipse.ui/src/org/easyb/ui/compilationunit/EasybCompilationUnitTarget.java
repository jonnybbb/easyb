package org.easyb.ui.compilationunit;

/*import org.codehaus.jdt.groovy.extension.IGroovyCompilationUnitTarget;



AWAITING ECLISPE GROOVY PLUGIN TO HAVE SVN PATCH APPLIED BEFORE
THIS CAN BE USED
public class EasybCompilationUnitTarget implements IGroovyCompilationUnitTarget {
	
	public char[] getContents(char[] contents) {
		
		String strContents = new String(contents);
		
		strContents = strContents.replaceAll("(\\{*\\s)as a([^\\}]*\\})", "$1as_a$2")
        						.replaceAll("(\\{*\\s)i want([^\\}]*\\})", "$1i_want$2")
        						.replaceAll("(\\{*\\s)so that([^\\}]*\\})", "$1so_that$2")
								.replaceAll("shared behavior\\b", "shared_behavior")
								.replaceAll("it behaves as\\b", "it_behaves_as");
		
		return strContents.toCharArray();
	}
}*/
