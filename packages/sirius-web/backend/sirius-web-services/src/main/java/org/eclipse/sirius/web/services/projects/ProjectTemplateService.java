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
package org.eclipse.sirius.web.services.projects;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateService;
import org.springframework.stereotype.Service;

/**
 * Aggregates all the project templates related beans into a single service to reduce the number of dependencies needed.
 *
 * @author pcdavid
 */
@Service
public class ProjectTemplateService implements IProjectTemplateService {

    private final List<IProjectTemplateProvider> projectTemplateProviders;

    private final List<IProjectTemplateInitializer> projectTemplateInitializers;

    public ProjectTemplateService(List<IProjectTemplateProvider> projectTemplateProviders, List<IProjectTemplateInitializer> projectTemplateInitializers) {
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
        this.projectTemplateInitializers = Objects.requireNonNull(projectTemplateInitializers);
    }

    @Override
    public List<IProjectTemplateProvider> getProjectTemplateProviders() {
        return this.projectTemplateProviders;
    }

    @Override
    public List<IProjectTemplateInitializer> getProjectTemplateInitializers() {
        return this.projectTemplateInitializers;
    }

}
