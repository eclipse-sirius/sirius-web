/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.studio.services;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.eclipse.sirius.web.application.studio.OnStudioEnabled;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide the project templates for studios.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioEnabled.class)
public class StudioProjectTemplateProvider implements IProjectTemplateProvider {

    public static final String STUDIO_NATURE = "siriusComponents://nature?kind=studio";

    public static final String STUDIO_TEMPLATE_ID = "studio-template";

    public static final String STUDIO_TEMPLATE_NAME = "Studio";

    public static final String BLANK_STUDIO_TEMPLATE_ID = "blank-studio-template";

    public static final String BLANK_STUDIO_TEMPLATE_NAME = "Blank Studio";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var studioImageURL = "/project-templates/Studio-Template.png";
        var studioNatures = List.of(new ProjectTemplateNature(STUDIO_NATURE));

        var studioTemplate = new ProjectTemplate(STUDIO_TEMPLATE_ID, STUDIO_TEMPLATE_NAME, studioImageURL, studioNatures);
        var blankStudioTemplate = new ProjectTemplate(BLANK_STUDIO_TEMPLATE_ID, BLANK_STUDIO_TEMPLATE_NAME, studioImageURL, studioNatures);

        return List.of(studioTemplate, blankStudioTemplate);
    }
}
