import easyb.grails.GrailsBindingManager
import easyb.grails.IntegrationEnvironmentDecorator
import org.disco.easyb.BehaviorRunner
import org.disco.easyb.domain.GroovyShellConfiguration
import org.disco.easyb.listener.ResultsCollector
import org.disco.easyb.report.ReportWriter
import org.disco.easyb.report.TxtSpecificationReportWriter
import org.disco.easyb.report.TxtStoryReportWriter
import org.disco.easyb.report.XmlReportWriter
import org.disco.easyb.listener.ExecutionListener
import org.disco.easyb.report.HtmlReportWriter

/**
 * Gant script that runs the Grails easyb tests
 */

Ant.property(environment: "env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
grailsApp = null
appCtx = null

includeTargets << new File("${grailsHome}/scripts/Package.groovy")
includeTargets << new File("${grailsHome}/scripts/Bootstrap.groovy")

generateLog4jFile = true

target('default': "Run a Grails applications easyb tests") {
    depends(checkVersion, configureProxy, packageApp, classpath)
    classLoader = new URLClassLoader([classesDir.toURL()] as URL[], rootLoader)
    Thread.currentThread().setContextClassLoader(classLoader)
    loadApp()
    configureApp()

    if (grailsApp.parentContext == null) {
        grailsApp.applicationContext = appCtx
    }

    testApp()
}

target(testApp: "The test app implementation target") {

    testSources = Ant.path {
        fileset(dir: "${basedir}/test/behavior") {
            include(name: "**/*Story.groovy")
            include(name: "**/*.story")
            include(name: "**/*Specification.groovy")
            include(name: "**/*.specification")
        }
    }

    testDir = "${basedir}/test/reports"

    if (config.grails.testing.reports.destDir) {
        testDir = config.grails.testing.reports.destDir
    }

    Ant.mkdir(dir: testDir)
    Ant.mkdir(dir: "${testDir}/xml")
    Ant.mkdir(dir: "${testDir}/plain")

    reports = [
            new XmlReportWriter(location: "${testDir}/xml/easyb.xml"),
			new HtmlReportWriter(location: "${testDir}/html/easyb.html"),
            new TxtStoryReportWriter(location: "${testDir}/plain/stories.txt"),
            new TxtSpecificationReportWriter(location: "${testDir}/plain/specifications.txt")]

    ResultsCollector resultsCollector = new ResultsCollector()
	IntegrationEnvironmentDecorator environmentDecorator = new IntegrationEnvironmentDecorator(appCtx: appCtx)

	BehaviorRunner behaviorRunner = new BehaviorRunner(reports, [resultsCollector, environmentDecorator].toArray(new ExecutionListener[0]))  // from script

    GroovyShellConfiguration shellConfiguration = new GroovyShellConfiguration(classLoader, ["grailsApp": grailsApp, "appCtx": appCtx])

    List behaviors = BehaviorRunner.getBehaviors(shellConfiguration, testSources.list())

    GrailsBindingManager bindingManager = GrailsBindingManager.newInstance(appCtx.getBean('sessionFactory'))
    bindingManager.bind()
    behaviorRunner.runBehavior(behaviors)
    bindingManager.unbind()
    
    println (resultsCollector.behaviorCount > 1 ? resultsCollector.behaviorCount + " total behaviors run" : "1 behavior run")
    println "${resultsCollector.failedBehaviorCount } total failures" 

    reports.each {ReportWriter report ->
        report.writeReport resultsCollector
    }
}