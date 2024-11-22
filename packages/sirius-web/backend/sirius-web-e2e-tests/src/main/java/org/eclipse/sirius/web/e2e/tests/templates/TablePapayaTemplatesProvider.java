/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
 * Provides Papaya project templates.
 *
 * @author frouene
 */
@Profile("test")
@Service
public class TablePapayaTemplatesProvider implements IProjectTemplateProvider {

    public static final String TABLE_PAPAYA_TEMPLATE_ID = "papaya-table-template";

    public static final String PAPAYA_NATURE = "siriusComponents://nature?kind=papaya";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        var flowTemplate = new ProjectTemplate(TABLE_PAPAYA_TEMPLATE_ID, "PapayaTable", "/project-templates/Papaya.png", List.of(new ProjectTemplateNature(PAPAYA_NATURE)));
        return List.of(flowTemplate);
    }

}
