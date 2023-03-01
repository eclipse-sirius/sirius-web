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
package org.eclipse.sirius.web.sample.papaya;

import java.util.List;

import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides a template for "Papaya Studio" (domain & view).
 *
 * @author pcdavid
 */
@Configuration
public class PapayaStudioTemplateProvider implements IProjectTemplateProvider {

    public static final String STUDIO_TEMPLATE_ID = "papaya-studio-template";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        // @formatter:off
        var studioTemplate = ProjectTemplate.newProjectTemplate(STUDIO_TEMPLATE_ID)
                .label("Papaya Studio")
                .imageURL("/images/Papaya-Template.svg")
                .build();
        // @formatter:on
        return List.of(studioTemplate);
    }

}
