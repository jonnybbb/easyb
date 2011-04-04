package org.gradle.plugins.easyb

import org.gradle.api.Project
import org.gradle.api.internal.project.ProjectInternal

class EasybPluginConvention {
    String behaviorsDirName
    String reportsDirName
    List suffixes
    List reportFormats

    private final ProjectInternal project

    EasybPluginConvention(Project project) {
        this.project = project
        reportsDirName = 'easyb'
        behaviorsDirName = 'src/test/easyb'
        suffixes = ['.specification','.story','Story.groovy', 'Specification.groovy']
        reportFormats = ['story', 'spec', 'html' ,'xml', 'junit']
    }

    File getEasybBehaviorsDir() {
        project.file(behaviorsDirName)
    }

    File getEasybReportsDir() {
        project.fileResolver.withBaseDir(project.reportsDir).resolve(reportsDirName)
    }
}