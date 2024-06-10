/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.services;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.springframework.stereotype.Service;

/**
 * Used to provide the papaya project template.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaProjectTemplateProvider implements IProjectTemplateProvider {

    public static final String SIRIUS_WEB_PROJECT_TEMPLATE_ID = "sirius-web";

    public static final String PAPAYA_NATURE = "siriusComponents://nature?kind=papaya";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        return List.of(new ProjectTemplate(SIRIUS_WEB_PROJECT_TEMPLATE_ID, "Sirius Web", "/project-templates/SiriusWeb-Template.png", List.of(new ProjectTemplateNature(PAPAYA_NATURE))));
    }
}
