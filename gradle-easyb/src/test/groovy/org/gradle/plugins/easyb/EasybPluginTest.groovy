package org.gradle.plugins.easyb

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import static org.junit.Assert.assertTrue

class EasybPluginTest {

    @Test
    public void greeterPluginAddsGreetingTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: EasybPlugin

        assertTrue(project.tasks.easyb instanceof Easyb)
    }


}
