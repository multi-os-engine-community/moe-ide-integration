/*
Copyright (C) 2016 Migeran

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.moe.idea.utils;

import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.ModuleAdapter;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import org.moe.idea.MOESdkPlugin;
import org.moe.idea.runconfig.configuration.MOERunConfiguration;
import org.moe.idea.runconfig.configuration.MOERunConfigurationType;
import org.moe.idea.utils.logger.LoggerFactory;

import java.util.List;

public class ModuleObserver implements ModuleListener {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleObserver.class);

    @Override
    public void moduleAdded(@NotNull Project project, @NotNull Module module) {
        if (MOESdkPlugin.isValidMoeModule(module)) {
            LOG.debug("Check run configuration for the module " + module.getName()
                    + " in the project " + project.getName());
            // Find run config for the module
            RunManager runManager = RunManager.getInstance(project);
            for (RunConfiguration runConfig : runManager.getConfigurationsList(MOERunConfigurationType.getInstance())) {
                MOERunConfiguration moeRunConfig = (MOERunConfiguration) runConfig;
                moeRunConfig.moduleName(moeRunConfig.moduleName());// Set module in the config
                if (module.getName().equals(moeRunConfig.module().getName())) {
                    return;
                };
            }
            // Create MOE run config
            RunnerAndConfigurationSettings settings = null;
            try {
                settings = MOERunConfiguration.createRunConfiguration(project, module);
                runManager.addConfiguration(settings, false);
                runManager.setSelectedConfiguration(settings);
            } catch (Exception ee) {
                LOG.error("Unable to create run configuration", ee);
            }
        }
    }

    @Override
    public void beforeModuleRemoved(@NotNull Project project, @NotNull Module module) {

    }

    @Override
    public void moduleRemoved(@NotNull Project project, @NotNull Module module) {

    }

    @Override
    public void modulesRenamed(@NotNull Project project, @NotNull List<Module> list, @NotNull Function<Module, String> function) {

    }

}