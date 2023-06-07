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
import org.eclipse.sirius.web.services.api.projects.Nature;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides Studio-specific project templates.
 *
 * @author pcdavid
 */
@Configuration
public class StudioProjectTemplatesProvider implements IProjectTemplateProvider {

    public static final String STUDIO_TEMPLATE_ID = "studio-template";

    public static final String BLANK_STUDIO_TEMPLATE_ID = "blank-studio-template";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var studioTemplate = ProjectTemplate.newProjectTemplate(STUDIO_TEMPLATE_ID)
                .label("Studio")
                .imageURL("/images/Studio-Template.png")
                .natures(List.of(new Nature("siriusComponents://nature?kind=studio")))
                .build();
        var blankStudioTemplate = ProjectTemplate.newProjectTemplate(BLANK_STUDIO_TEMPLATE_ID)
                .label("Blank Studio")
                .imageURL("/images/Studio-Template.png")
                .natures(List.of(new Nature("siriusComponents://nature?kind=studio")))
                .build();
        return List.of(studioTemplate, blankStudioTemplate);
    }

}
