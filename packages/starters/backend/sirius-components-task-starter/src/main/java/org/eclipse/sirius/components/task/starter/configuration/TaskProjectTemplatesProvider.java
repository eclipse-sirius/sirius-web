/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.task.starter.configuration;

import java.util.List;

import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides Task-specific project templates.
 *
 * @author lfasani
 */
@Configuration
public class TaskProjectTemplatesProvider implements IProjectTemplateProvider {

    public static final String TASK_EXAMPLE_TEMPLATE_ID = "task-template";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var flowTemplate = ProjectTemplate.newProjectTemplate(TASK_EXAMPLE_TEMPLATE_ID)
                .label("Task")
                .imageURL("/images/Task-Template.png")
                .natures(List.of())
                .build();
        return List.of(flowTemplate);
    }

}
