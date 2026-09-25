/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.projects.stylecustomizations.application.controllers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.StyleCustomizationDTO;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IProjectStyleCustomizationSearchApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Project#styleCustomizations.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "Project", field = "styleCustomizations")
public class ProjectStyleCustomizationsDataFetcher implements IDataFetcherWithFieldCoordinates<List<StyleCustomizationDTO>> {

    private final ICapabilityEvaluator capabilityEvaluator;

    private final IProjectStyleCustomizationSearchApplicationService styleCustomizationSearchApplicationService;

    private final Logger logger = LoggerFactory.getLogger(ProjectStyleCustomizationsDataFetcher.class);

    public ProjectStyleCustomizationsDataFetcher(ICapabilityEvaluator capabilityEvaluator,
            IProjectStyleCustomizationSearchApplicationService styleCustomizationSearchApplicationService) {
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.styleCustomizationSearchApplicationService = Objects.requireNonNull(styleCustomizationSearchApplicationService);
    }

    @Override
    public List<StyleCustomizationDTO> get(DataFetchingEnvironment environment) {
        ProjectDTO project = environment.getSource();
        boolean hasCapability = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, project.id(), SiriusWebCapabilities.Project.LIST_STYLE_CUSTOMIZATIONS);
        if (!hasCapability) {
            this.logger.atWarn()
                    .setMessage("Access denied to style customizations for project {}")
                    .addArgument(project.id())
                    .addKeyValue("projectId", project.id())
                    .addKeyValue("capabilityType", SiriusWebCapabilities.PROJECT)
                    .addKeyValue("capability", SiriusWebCapabilities.Project.LIST_STYLE_CUSTOMIZATIONS)
                    .log();
            return List.of();
        }

        var styleCustomizations = this.styleCustomizationSearchApplicationService.getStyleCustomizations(project.id());

        this.logger.atInfo()
                .setMessage("{} style customization(s) of project {} retrieved")
                .addArgument(styleCustomizations.size())
                .addArgument(project.id())
                .log();

        return styleCustomizations;
    }
}
