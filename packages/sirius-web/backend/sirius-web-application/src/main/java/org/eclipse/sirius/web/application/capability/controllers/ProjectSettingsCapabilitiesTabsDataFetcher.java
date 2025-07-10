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

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.SiriusWebLocalContextConstants;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.dto.ProjectSettingsTabCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field ProjectSettingsCapabilities#tabs.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "ProjectSettingsCapabilities", field = "tabs")
public class ProjectSettingsCapabilitiesTabsDataFetcher implements IDataFetcherWithFieldCoordinates<List<ProjectSettingsTabCapabilities>> {

    private static final String TAB_IDS_ARGUMENT = "tabIds";

    private final List<ICapabilityVoter> capabilityVoters;

    public ProjectSettingsCapabilitiesTabsDataFetcher(List<ICapabilityVoter> capabilityVoters) {
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
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
        boolean canViewTab = this.capabilityVoters.stream().allMatch(voter -> voter.vote(
                tabId, projectId, SiriusWebCapabilities.ProjectSettingsTab.VIEW) == CapabilityVote.GRANTED);
        return new ProjectSettingsTabCapabilities(tabId, canViewTab);
    }
}
