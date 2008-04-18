package org.easyb.components.runner;

import java.util.Arrays;
import java.util.Collection;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.ConfigurationPerRunnerSettings;
import com.intellij.execution.configurations.ModuleBasedConfiguration;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationModule;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.execution.runners.RunnerInfo;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizer;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class EasybSpecRunConfiguration extends ModuleBasedConfiguration {
    private EasybSpecConfigurationFactory factory;
    private String specificationPath;

    public EasybSpecRunConfiguration(EasybSpecConfigurationFactory factory, Project project, String name) {
        super(name, new RunConfigurationModule(project, true), factory);
        this.factory = factory;
    }

    public Collection<Module> getValidModules() {
        return Arrays.asList(ModuleManager.getInstance(getProject()).getModules());
    }

    public void readExternal(Element element) throws InvalidDataException {
        super.readExternal(element);
        readModule(element);
        specificationPath = JDOMExternalizer.readString(element, "specificationPath");
    }

    public void writeExternal(Element element) throws WriteExternalException {
        super.writeExternal(element);
        writeModule(element);
        JDOMExternalizer.write(element, "specificationPath", specificationPath);
    }

    protected ModuleBasedConfiguration createInstance() {
        return new EasybSpecRunConfiguration(factory, getConfigurationModule().getProject(), getName());
    }

    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new EasybRunConfigurationEditor();
    }

    public RunProfileState getState(DataContext context, RunnerInfo runnerInfo, RunnerSettings runnerSettings,
            ConfigurationPerRunnerSettings configurationSettings) throws ExecutionException {
        return new EasybRunProfileState(runnerSettings, configurationSettings, getModule(), specificationPath);
    }

    public String getSpecificationPath() {
        return specificationPath;
    }

    public void setSpecificationPath(String specificationPath) {
        this.specificationPath = specificationPath;
    }

    public Module getModule() {
        return getConfigurationModule().getModule();
    }
}
