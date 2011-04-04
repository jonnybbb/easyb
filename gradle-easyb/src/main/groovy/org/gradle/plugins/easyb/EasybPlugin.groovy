package org.gradle.plugins.easyb

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class EasybPlugin implements Plugin<Project> {
    static final String EASYB_CONFIGURATION_NAME = 'easyb'
    Project project

    void apply(Project project) {
        this.project = project
        project.plugins.apply(JavaPlugin.class)
        EasybPluginConvention easybConvention = new EasybPluginConvention(project)
        project.convention.plugins.easyb = easybConvention
        configureEasybTask(project, easybConvention)
        configureEasybDefaults(project, easybConvention)
    }



    private void configureEasybTask(Project project, EasybPluginConvention pluginConvention) {
        def easyb = project.tasks.add('easyb', Easyb)


        def test = project.tasks["test"]
        test.description = "Executes easyb tests"
        test.dependsOn easyb

    }

     private void configureEasybDefaults(Project project, EasybPluginConvention pluginConvention) {
        project.tasks.withType(Easyb) { Easyb easyb ->
        easyb.conventionMapping.behaviorsDir = {pluginConvention.easybBehaviorsDir}
        easyb.conventionMapping.reportsDir = {pluginConvention.easybReportsDir }
        easyb.conventionMapping.suffixes = {pluginConvention.suffixes}
        easyb.conventionMapping.reportFormats = {pluginConvention.reportFormats}
        easyb.conventionMapping.classpath = { project.sourceSets.test.runtimeClasspath }
        }
    }

}