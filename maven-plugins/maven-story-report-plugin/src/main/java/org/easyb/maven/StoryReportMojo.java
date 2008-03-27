package org.easyb.maven;

import java.util.Locale;
import java.io.File;

import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

/**
 * Execute story specifications defined with easyb
 *
 * @goal report
 * @phase site
 */
public class StoryReportMojo extends AbstractMavenReport {
    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    @SuppressWarnings("UnusedDeclaration")
    private MavenProject project;

    /**
     * <i>Maven Internal</i>: The Doxia Site Renderer.
     *
     * @component
     */
    @SuppressWarnings("UnusedDeclaration")
    private Renderer siteRenderer;

    /**
     * @parameter expression="${project.build.directory}/easyb-stories"
     * @required
     */
    @SuppressWarnings("UnusedDeclaration")
    private File outputDirectory;

    protected void executeReport(Locale locale) throws MavenReportException {
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }
    }

    protected Renderer getSiteRenderer() {
        return siteRenderer;
    }

    protected String getOutputDirectory() {
        return outputDirectory.getAbsolutePath();
    }

    protected MavenProject getProject() {
        return project;
    }

    public String getOutputName() {
        return "easyb-stories";
    }

    public String getName(Locale locale) {
        return "Easyb Story Reports";
    }

    public String getDescription(Locale locale) {
        return "Easyb Story Reports";
    }
}