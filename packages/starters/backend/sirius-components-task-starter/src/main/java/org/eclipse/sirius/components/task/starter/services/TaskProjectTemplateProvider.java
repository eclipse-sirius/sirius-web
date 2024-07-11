/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.task.starter.services;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.springframework.stereotype.Service;

/**
 * Provides Task-specific project templates.
 *
 * @author lfasani
 */
@Service
public class TaskProjectTemplateProvider implements IProjectTemplateProvider {

    public static final String TASK_EXAMPLE_TEMPLATE_ID = "task-template";

    public static final String TASK_NATURE = "siriusWeb://nature?kind=task";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var taskTemplate = new ProjectTemplate(TASK_EXAMPLE_TEMPLATE_ID, "Task", "/project-templates/Task-Template.png", List.of(new ProjectTemplateNature(TASK_NATURE)));
        return List.of(taskTemplate);
    }

}
