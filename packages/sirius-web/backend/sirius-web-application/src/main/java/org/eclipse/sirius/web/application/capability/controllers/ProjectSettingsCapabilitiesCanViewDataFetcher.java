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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.SiriusWebLocalContextConstants;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;

/**
 * Data fetcher for the field ProjectSettingsCapabilities#canView.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "ProjectSettingsCapabilities", field = "canView")
public class ProjectSettingsCapabilitiesCanViewDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    private final ICapabilityEvaluator capabilityEvaluator;

    public ProjectSettingsCapabilitiesCanViewDataFetcher(ICapabilityEvaluator capabilityEvaluator) {
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String projectId = Optional.ofNullable(localContext.get(SiriusWebLocalContextConstants.PROJECT_ID)).map(Object::toString).orElse(null);
        if (projectId == null) {
            return false;
        }

        return this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT_SETTINGS, projectId, SiriusWebCapabilities.ProjectSettings.VIEW);
    }
}
