package org.gradle.plugins.easyb;

import groovy.lang.GroovyClassLoader;
import org.easyb.BehaviorRunner;
import org.easyb.Configuration;
import org.easyb.report.*;
import org.gradle.api.GradleException;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.*;

import java.io.File;
import java.util.*;

public class Easyb extends ConventionTask implements VerificationTask {
    private boolean ignoreFailures;
    private File behaviorsDir;
    private File reportsDir;
    private List<String> suffixes;
    private List<String> reportFormats;

    // private AntEasyb antEasyb = new AntEasyb();

    private static final Map<String, ReportConfig> REPORT_CONFIGURATION = new LinkedHashMap<String, ReportConfig>();

    static {
        REPORT_CONFIGURATION.put("story", new ReportConfig("plain", "easyb-stories.txt", "TxtStoryReportWriter"));
        REPORT_CONFIGURATION.put("spec", new ReportConfig("plain", "easyb-specifications.txt", "TxtSpecificationReportWriter"));
        REPORT_CONFIGURATION.put("html", new ReportConfig("html", "easyb-reports.html", "HtmlReportWriter"));
        REPORT_CONFIGURATION.put("xml", new ReportConfig("xml", "easyb-reports.xml", "XmlReportWriter"));
        REPORT_CONFIGURATION.put("junit", new ReportConfig("junit", "", "JUnitReportWriter"));
    }

    @TaskAction
    public void easyb() {
        String[] filePaths = filePaths();
        Configuration config = new Configuration(filePaths, reportWriters());
        BehaviorRunner runner = new BehaviorRunner(config);
        try {
            runner.runBehaviors(BehaviorRunner.getBehaviors(filePaths, config));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GradleException("I'm sorry Dave, I can't let you do that.", e);
        }

        // getLogging().captureStandardOutput(LogLevel.INFO);
        // antEasyb.execute(getProject(), getAnt(), getBehaviorsDir(), getReportsDir(), getReportFormats(), getSuffixes(), isIgnoreFailures());
    }

    private List<ReportWriter> reportWriters() {
        List<ReportWriter> reportWriters = new ArrayList<ReportWriter>();
        for (String reportFormat : reportFormats) {
            ReportConfig reportConfig = REPORT_CONFIGURATION.get(reportFormat);
            reportWriters.add(reportConfig.newInstance(getClass().getClassLoader(), reportsDir));
        }

        return reportWriters;
    }

    private String[] filePaths() {
        List<String> paths = new ArrayList<String>();
        listFiles(behaviorsDir, paths);
        return paths.toArray(new String[paths.size()]);
    }

    private void listFiles(File dir, List<String> files) {
        //check whether this is a valid directory at all
        File[] filesInDirectory = dir.listFiles();
        if (filesInDirectory != null) {
            for (File file : filesInDirectory) {
                if (file.isDirectory()) {
                    listFiles(file, files);
                } else if (isBehavior(file)) {
                    files.add(file.getAbsolutePath());
                }
            }
        } else {
            // TODO use logger, fail build?!
            System.err.println("Could not find any behaviour/story files in " + dir + ", maybe IDE prefix (working dir) is wrong?");
        }
    }

    private boolean isBehavior(File file) {
        for (String pattern : suffixes) {
            if (file.getName().endsWith(pattern)) return true;
        }
        return false;
    }

    public List<String> getReportFormats() {
        return reportFormats;
    }

    public void setReportFormats(List<String> reportFormats) {
        this.reportFormats = reportFormats;
    }

    @OutputDirectory
    public File getReportsDir() {
        return reportsDir;
    }

    public void setReportsDir(File reportsDir) {
        this.reportsDir = reportsDir;
    }

    @Input
    public List<String> getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(List<String> suffixes) {
        this.suffixes = suffixes;
    }

    @InputDirectory
    public File getBehaviorsDir() {
        return behaviorsDir;
    }

    public void setBehaviorsDir(File behaviorsDir) {
        this.behaviorsDir = behaviorsDir;
    }

    public Easyb setIgnoreFailures(boolean ignoreFailures) {
        this.ignoreFailures = ignoreFailures;
        return this;
    }

    public boolean isIgnoreFailures() {
        return ignoreFailures;
    }

    private static class ReportConfig {
        private final String dirName;
        private final String fileName;
        private final String reportClassName;

        private ReportConfig(String dirName, String fileName, String reportClassName) {
            this.dirName = dirName;
            this.fileName = fileName;
            this.reportClassName = reportClassName;
        }

        public ReportWriter newInstance(ClassLoader classLoader, File reportsDir) {
            File reportDir = new File(reportsDir.getAbsolutePath(), dirName);
            reportDir.mkdirs();
            try {
                GroovyClassLoader gcl = new GroovyClassLoader(classLoader);
                Class<? extends ReportWriter> reportClass = (Class<? extends ReportWriter>) gcl.loadClass("org.easyb.report." + reportClassName);
                return reportClass.getDeclaredConstructor(String.class).newInstance(reportDir.getAbsolutePath() + File.separator + fileName);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}