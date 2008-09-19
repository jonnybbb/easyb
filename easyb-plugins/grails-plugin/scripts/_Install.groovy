//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'Ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
// Ant.mkdir(dir:"C:\Projects\easyb/easybtest/grails-app/jobs")
//

Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
testDir = "${basedir}/test"

includeTargets << new File("${grailsHome}/scripts/Compile.groovy")
includeTargets << new File("${grailsHome}/scripts/Init.groovy")
includeTargets << new File("${grailsHome}/scripts/Bootstrap.groovy")

['behavior', 'reports/html', 'reports/plain', 'reports/xml'].each {
	File requiredDir = new File("${testDir}/${it}")
	if(!requiredDir.exists()) {
		Ant.mkdir(dir:requiredDir)
	}
}

checkVersion()
configureProxy()
createStructure()
packagePlugins()
compile()
