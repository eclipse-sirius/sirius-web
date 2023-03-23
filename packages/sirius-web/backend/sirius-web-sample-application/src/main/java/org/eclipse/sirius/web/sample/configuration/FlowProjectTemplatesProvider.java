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
package org.eclipse.sirius.web.sample.configuration;

import java.util.List;

import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides Flow-specific project templates.
 *
 * @author pcdavid
 */
@Configuration
public class FlowProjectTemplatesProvider implements IProjectTemplateProvider {

    public static final String FLOW_TEMPLATE_ID = "flow-template";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        // @formatter:off
        var flowTemplate = ProjectTemplate.newProjectTemplate(FLOW_TEMPLATE_ID)
                .label("Flow")
                .imageURL("/images/Flow-Template.png")
                .natures(List.of())
                .build();
        // @formatter:on
        return List.of(flowTemplate);
    }

}
