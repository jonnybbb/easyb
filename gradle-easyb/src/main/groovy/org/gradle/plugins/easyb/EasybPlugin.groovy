package org.gradle.plugins.easyb

import org.gradle.api.Plugin
import org.gradle.api.Project

class EasybPlugin implements Plugin<Project> {
    static final String EASYB_CONFIGURATION_NAME = 'easyb'

    void apply(Project project) {
        EasybPluginConvention easybConvention = new EasybPluginConvention(project)
        project.convention.plugins.easyb = easybConvention

        //project.getConfigurations().add(EASYB_CONFIGURATION_NAME).setVisible(false).setTransitive(false).
        //        setDescription("The easyb libraries to be used for this project.");

        // configureEasybDefaults(project, easybConvention)
        configureEasybTask(project, easybConvention)

        /*
        project.plugins.withType(JavaBasePlugin) {
            configureForPlugin(project, easybConvention)
        }
        project.plugins.withType(GroovyBasePlugin) {
            configureForPlugin(project, easybConvention)
        }
        */
    }

    /*
    private void configureEasybDefaults(Project project, EasybPluginConvention pluginConvention) {
        project.tasks.withType(Easyb) { Easyb easyb ->
            easyb.conventionMapping.behaviorsDir = { pluginConvention.easybBehaviorsDir }
            easyb.conventionMapping.reportsDir = { pluginConvention.easybReportsDir }
            easyb.conventionMapping.suffixes = { pluginConvention.suffixes }
            easyb.conventionMapping.reportFormats = { pluginConvention.reportFormats }
        }
    }
    */

    private void configureEasybTask(Project project, EasybPluginConvention pluginConvention) {
        def easyb = project.tasks.add('easyb', Easyb)
        easyb.behaviorsDir = pluginConvention.easybBehaviorsDir
        easyb.reportsDir = pluginConvention.easybReportsDir
        easyb.suffixes = pluginConvention.suffixes
        easyb.reportFormats = pluginConvention.reportFormats

        def test = project.tasks["test"]
        test.description = "Executes easyb tests"
        test.dependsOn easyb
    }

    /*
    private void configureForPlugin(Project project, EasybPluginConvention pluginConvention) {
        project.convention.getPlugin(EasybPluginConvention).sourceSets.all {SourceSet set ->
            def checkstyle = project.tasks.add(set.getTaskName("checkstyle", null), Checkstyle)
            checkstyle.description = "Runs Checkstyle against the $set.name Java source code."
            checkstyle.conventionMapping.defaultSource = { set.allJava }
            checkstyle.conventionMapping.configFile = { pluginConvention.checkstyleConfigFile }
            checkstyle.conventionMapping.resultFile = { new File(pluginConvention.checkstyleResultsDir, "${set.name}.xml") }
            checkstyle.conventionMapping.classpath = { set.compileClasspath }
        }
    }
    */
}