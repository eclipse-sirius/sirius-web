/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.e2e.tests.templates;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Provides the template to create a Papaya test project with a dependency to a library.
 *
 * @author gdaniel
 */
@Profile("test")
@Service
public class LibraryPapayaTemplatesProvider implements IProjectTemplateProvider {

    public static final String LIBRARY_PAPAYA_TEMPLATE_ID = "papaya-library-template";

    public static final String PAPAYA_NATURE = "siriusComponents://nature?kind=papaya";

    public static final String PAPAYA_PROJECT_NAME = "PapayaLibrary";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var papayaTemplate = new ProjectTemplate(LIBRARY_PAPAYA_TEMPLATE_ID, PAPAYA_PROJECT_NAME, "/project-templates/Papaya.png", List.of(new ProjectTemplateNature(PAPAYA_NATURE)));
        return List.of(papayaTemplate);
    }

}
