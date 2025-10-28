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
package org.eclipse.sirius.web.papaya.projecttemplates;

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

    public static final String EMPTY_PROJECT_TEMPLATE_ID = "papaya-empty";

    public static final String SIRIUS_WEB_PROJECT_TEMPLATE_ID = "papaya-sirius-web";

    public static final String BENCHMARK_PROJECT_TEMPLATE_ID = "papaya-benchmark";

    public static final String PAPAYA_NATURE = "siriusComponents://nature?kind=papaya";

    public static final String BENCHMARK_PROJECT_TEMPLATE_NAME = "Papaya - Performance";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        return List.of(
                new ProjectTemplate(EMPTY_PROJECT_TEMPLATE_ID, "Papaya - Blank", "/project-templates/Retro-Papaya.jpeg", List.of(new ProjectTemplateNature(PAPAYA_NATURE))),
                new ProjectTemplate(SIRIUS_WEB_PROJECT_TEMPLATE_ID, "Papaya - Example", "/project-templates/Smart-Papaya.jpeg", List.of(new ProjectTemplateNature(PAPAYA_NATURE))),
                new ProjectTemplate(BENCHMARK_PROJECT_TEMPLATE_ID, BENCHMARK_PROJECT_TEMPLATE_NAME, "/project-templates/Cosmic-Papaya.jpeg", List.of(new ProjectTemplateNature(PAPAYA_NATURE)))
        );
    }
}
