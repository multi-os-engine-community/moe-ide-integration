/*
Copyright 2014-2016 Intel Corporation

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

package org.moe.idea.runconfig.configuration;

import com.intellij.execution.RunManager;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import res.MOEIcons;

import javax.swing.*;

class MOERunConfigurationFactory extends ConfigurationFactory {
    MOERunConfigurationFactory(MOERunConfigurationTypeBase configurationType) {
        super (configurationType);
    }

    @Override
    public Icon getIcon() {
        return MOEIcons.MOESmall;
    }

    @NotNull @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        ConfigurationType type = getType();

        if (type instanceof MOERunConfigurationType) {
            return new MOERunConfiguration(project, this);
        }

        return super.createTemplateConfiguration(project, RunManager.getInstance(project));
    }

    @NotNull
    @Override
    public String getId() {
        return "Multi-OS Engine iOS Application";
    }
}
