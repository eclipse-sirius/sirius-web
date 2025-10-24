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
package org.eclipse.sirius.web.application.capability.controllers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.SiriusWebLocalContextConstants;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.dto.ProjectSettingsTabCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;

/**
 * Data fetcher for the field ProjectSettingsCapabilities#tabs.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "ProjectSettingsCapabilities", field = "tabs")
public class ProjectSettingsCapabilitiesTabsDataFetcher implements IDataFetcherWithFieldCoordinates<List<ProjectSettingsTabCapabilities>> {

    private static final String TAB_IDS_ARGUMENT = "tabIds";

    private final ICapabilityEvaluator capabilityEvaluator;

    public ProjectSettingsCapabilitiesTabsDataFetcher(ICapabilityEvaluator capabilityEvaluator) {
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
    }

    @Override
    public List<ProjectSettingsTabCapabilities> get(DataFetchingEnvironment environment) throws Exception {
        List<String> tabIds = environment.getArgument(TAB_IDS_ARGUMENT);
        Map<String, Object> localContext = environment.getLocalContext();
        if (tabIds == null || localContext == null) {
            return List.of();
        }

        List<ProjectSettingsTabCapabilities> projectSettingsTabCapabilities = List.of();
        Optional<String> projectId = Optional.ofNullable(localContext.get(SiriusWebLocalContextConstants.PROJECT_ID)).map(Object::toString);
        if (projectId.isPresent()) {
            projectSettingsTabCapabilities = tabIds.stream()
                .map(tabId -> this.toTabCapabilities(tabId, projectId.get()))
                .toList();
        }
        return projectSettingsTabCapabilities;
    }

    private ProjectSettingsTabCapabilities toTabCapabilities(String tabId, String projectId) {
        String type = SiriusWebCapabilities.PROJECT_SETTINGS + '#' + tabId;
        boolean canViewTab = this.capabilityEvaluator.hasCapability(type, projectId, SiriusWebCapabilities.ProjectSettingsTab.VIEW);
        return new ProjectSettingsTabCapabilities(tabId, canViewTab);
    }
}
