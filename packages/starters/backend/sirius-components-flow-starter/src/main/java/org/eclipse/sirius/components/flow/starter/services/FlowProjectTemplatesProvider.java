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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.springframework.stereotype.Service;

/**
 * Provides Flow-specific project templates.
 *
 * @author pcdavid
 */
@Service
public class FlowProjectTemplatesProvider implements IProjectTemplateProvider {

    public static final String FLOW_TEMPLATE_ID = "flow-template";

    public static final String FLOW_NATURE = "siriusWeb://nature?kind=flow";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var flowTemplate = new ProjectTemplate(FLOW_TEMPLATE_ID, "Flow", "/project-templates/Flow-Template.png", List.of(new ProjectTemplateNature(FLOW_NATURE)));
        return List.of(flowTemplate);
    }

}
