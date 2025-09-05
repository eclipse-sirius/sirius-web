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

import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.SiriusWebLocalContextConstants;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;

/**
 * Data fetcher for the field ProjectCapabilities#canDuplicate.
 *
 * @author Arthur Daussy
 */
@QueryDataFetcher(type = "ProjectCapabilities", field = "canDuplicate")
public class ProjectCapabilitiesCanDuplicateDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    private final List<ICapabilityVoter> capabilityVoters;

    public ProjectCapabilitiesCanDuplicateDataFetcher(List<ICapabilityVoter> capabilityVoters) {
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String projectId = Optional.ofNullable(localContext.get(SiriusWebLocalContextConstants.PROJECT_ID)).map(Object::toString).orElse(null);
        if (projectId == null) {
            return false;
        }

        return this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.PROJECT, projectId, SiriusWebCapabilities.Project.DUPLICATE) == CapabilityVote.GRANTED);
    }
}
