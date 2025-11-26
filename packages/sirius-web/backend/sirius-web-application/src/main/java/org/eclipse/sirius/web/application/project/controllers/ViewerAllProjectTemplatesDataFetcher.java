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
package org.eclipse.sirius.web.application.project.controllers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateApplicationService;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#allProjectTemplates.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "Viewer", field = "allProjectTemplates")
public class ViewerAllProjectTemplatesDataFetcher implements IDataFetcherWithFieldCoordinates<List<ProjectTemplateDTO>> {

    private final ICapabilityEvaluator capabilityEvaluator;

    private final IProjectTemplateApplicationService projectTemplateApplicationService;

    public ViewerAllProjectTemplatesDataFetcher(ICapabilityEvaluator capabilityEvaluator, IProjectTemplateApplicationService projectTemplateApplicationService) {
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.projectTemplateApplicationService = Objects.requireNonNull(projectTemplateApplicationService);
    }

    @Override
    public List<ProjectTemplateDTO> get(DataFetchingEnvironment environment) throws Exception {
        var hasCapability = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, null, SiriusWebCapabilities.Project.CREATE);
        if (!hasCapability) {
            return List.of();
        }

        return this.projectTemplateApplicationService.findAll();
    }
}
