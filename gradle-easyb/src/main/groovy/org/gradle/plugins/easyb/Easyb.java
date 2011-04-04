package org.gradle.plugins.easyb;

import org.easyb.BehaviorRunner;
import org.easyb.Configuration;
import org.easyb.domain.GroovyShellConfiguration;
import org.easyb.report.*;
import org.gradle.api.GradleException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class Easyb extends ConventionTask implements VerificationTask {
    private boolean ignoreFailures;
    private File behaviorsDir;
    private File reportsDir;
    private List<String> suffixes;
    private List<String> reportFormats;
    FileCollection classpath;


    private static final Map<String, ReportConfig> REPORT_CONFIGURATION = new LinkedHashMap<String, ReportConfig>();


    static {
        REPORT_CONFIGURATION.put("story", new ReportConfig("plain", "easyb-stories.txt", TxtStoryReportWriter.class));
        REPORT_CONFIGURATION.put("spec", new ReportConfig("plain", "easyb-specifications.txt", TxtSpecificationReportWriter.class));
        REPORT_CONFIGURATION.put("html", new ReportConfig("html", "easyb-reports.html", HtmlReportWriter.class));
        REPORT_CONFIGURATION.put("xml", new ReportConfig("xml", "easyb-reports.xml", XmlReportWriter.class));
        REPORT_CONFIGURATION.put("junit", new ReportConfig("junit", "", JUnitReportWriter.class));
    }

    @TaskAction
    public void easyb() {
        String[] filePaths = filePaths();

        List<URL> urls = new ArrayList<URL>();
        for (File file : getClasspath()) {
            try {
                URL url = file.toURI().toURL();
                urls.add(url);
            } catch (MalformedURLException e) {
                getLogger().error("could not get URL for file "+ file.getAbsolutePath(), e);

            }
        }
        try {
            ClassLoader easybAndRuntime = new URLClassLoader(urls.toArray(new URL[urls.size()]),EasybPlugin.class.getClassLoader());
            GroovyShellConfiguration gsh = new GroovyShellConfiguration(easybAndRuntime, Collections.<String, Object>emptyMap());
            Configuration config = new Configuration(filePaths, reportWriters(easybAndRuntime));
            BehaviorRunner runner = new BehaviorRunner(config);

            runner.runBehaviors(BehaviorRunner.getBehaviors(gsh, filePaths, config));
        } catch (Exception e) {
            throw new GradleException("BehaviourExecution failed", e);
        }
    }

    private List<ReportWriter> reportWriters(ClassLoader parentClassloader) {
        List<ReportWriter> reportWriters = new ArrayList<ReportWriter>();
        for (String reportFormat : getReportFormats()) {
            ReportConfig reportConfig = REPORT_CONFIGURATION.get(reportFormat);
            reportWriters.add(reportConfig.newReportWriterInstance(reportConfig.getWriterClass(), getReportsDir(), parentClassloader));
        }

        return reportWriters;
    }

    private String[] filePaths() {
        List<String> paths = new ArrayList<String>();
        System.out.println("behaviour dir is " + String.valueOf(getBehaviorsDir()));
        listFiles(getBehaviorsDir(), paths);
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
        for (String pattern : getSuffixes()) {
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

      /**
     * Returns the classpath containing the compiled classes for the source files to be inspected.
     *
     * @return The classpath.
     */
      @InputFiles
    public FileCollection getClasspath() {
        return classpath;
    }

    /**
     * Specified the classpath containing the compiled classes for the source file to be inspected.
     *
     * @param classpath The classpath. Must not be null.
     */
    public void setClasspath(FileCollection classpath) {
        getLogger().info("setting classpath for easyb", classpath);
        this.classpath = classpath;
    }

    private static class ReportConfig {
        private final String dirName;
        private final String fileName;
        private final Class<? extends ReportWriter> reportClassName;

        private ReportConfig(String dirName, String fileName, Class<? extends ReportWriter> reportClassName) {
            this.dirName = dirName;
            this.fileName = fileName;
            this.reportClassName = reportClassName;
        }

        public ReportWriter newReportWriterInstance(Class<? extends ReportWriter> reportClass, File reportsDir, final ClassLoader parentClassloader) {
            File reportDir = new File(reportsDir.getAbsolutePath(), dirName);
            reportDir.mkdirs();
            try {
                ReportWriter reportWriter = reportClass.getDeclaredConstructor(String.class).newInstance(reportDir.getAbsolutePath() + File.separator + fileName);
                Method[] declaredMethods = reportWriter.getClass().getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                     if(Arrays.asList(declaredMethod.getParameterTypes()).contains(ClassLoader.class)){
                         declaredMethod.invoke(reportWriter,parentClassloader);
                     }
                }

                return reportWriter;
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        public Class<? extends ReportWriter> getWriterClass() {
            return reportClassName;
        }
    }
}