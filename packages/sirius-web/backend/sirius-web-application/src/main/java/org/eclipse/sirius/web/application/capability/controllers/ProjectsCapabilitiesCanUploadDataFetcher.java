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
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field ProjectsCapabilities#canUpload.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "ProjectsCapabilities", field = "canUpload")
public class ProjectsCapabilitiesCanUploadDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    private final List<ICapabilityVoter> capabilityVoters;

    public ProjectsCapabilitiesCanUploadDataFetcher(List<ICapabilityVoter> capabilityVoters) {
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        return this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.PROJECT, null, SiriusWebCapabilities.Project.UPLOAD) == CapabilityVote.GRANTED);
    }
}
