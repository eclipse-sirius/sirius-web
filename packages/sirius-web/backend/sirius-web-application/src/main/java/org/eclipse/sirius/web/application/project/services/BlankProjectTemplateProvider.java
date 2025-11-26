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
package org.eclipse.sirius.web.application.project.services;

import java.util.List;

import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplate;
import org.springframework.stereotype.Service;

/**
 * Used to provide the blank project template.
 *
 * @author sbegaudeau
 */
@Service
public class BlankProjectTemplateProvider implements IProjectTemplateProvider {

    public static final String BLANK_PROJECT_TEMPLATE_ID = "blank-project";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        return List.of(new ProjectTemplate(BLANK_PROJECT_TEMPLATE_ID, "Blank Project", "", List.of()));
    }
}
