package org.gradle.plugins.easyb

import org.easyb.ant.BehaviorRunnerTask
import org.gradle.api.Project
import org.apache.tools.ant.BuildException
import org.gradle.api.GradleException

class AntEasyb {
    static final Map FORMAT_CONFIG = [
            story: [dirName: 'plain', fileName: 'easyb-stories.txt'],
            spec: [dirName: 'spec', fileName: 'easyb-specifications.txt'],
            html: [dirName: 'html', fileName: 'easyb-report.html'],
            xml: [dirName: 'xml', fileName: 'easyb-report.xml'],
            junit: [dirName: 'junit', fileName: '']
    ]

    def execute(Project project, AntBuilder ant, File behaviorsDir, File reportsDir, List<String> reportFormats, List<String> suffixes, boolean ignoreFailures) {
        ant.project.addTaskDefinition('easyb', BehaviorRunnerTask)
        try {
            for (format in reportFormats) {
                project.mkdir(reportsDir.absolutePath + File.separator + FORMAT_CONFIG[format].dirName)
            }

            ant.easyb(failureProperty: 'easyb.failed') {
                classpath {
                    pathelement(path: project.configurations.runtime.asPath)
                    // pathelement(path: project.configurations.easyb.asPath)
                    pathelement(location: sourceSets.main.classesDir.absolutePath)
                }
                for (format in reportFormats) {
                    Map formatConfig = FORMAT_CONFIG[format]
                    report(format: format, location: reportsDir.absolutePath + File.separator + formatConfig.dirName + File.separator + formatConfig.fileName)
                }

                behaviors(dir: behaviorsDir) {
                    for (suffix in suffixes) {
                        include(name: "**/*${suffix}")
                    }
                }
            }
        } catch (BuildException e) {
            if (e.message.matches('Exceeded maximum number of priority \\d* violations.*')) {
                if (ignoreFailures) {
                    return
                }
                throw new GradleException("CodeNarc check violations were found in $source. See the report at $reportFile.", e)
            }
            throw e
        }
    }
}