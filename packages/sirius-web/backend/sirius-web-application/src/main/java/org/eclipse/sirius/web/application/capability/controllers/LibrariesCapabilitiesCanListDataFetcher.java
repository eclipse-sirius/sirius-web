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

import java.util.Objects;

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;

/**
 * Data fetcher for the field LibrariesCapabilities#canList.
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "LibrariesCapabilities", field = "canList")
public class LibrariesCapabilitiesCanListDataFetcher implements IDataFetcherWithFieldCoordinates<Boolean> {

    private final ICapabilityEvaluator capabilityEvaluator;

    public LibrariesCapabilitiesCanListDataFetcher(ICapabilityEvaluator capabilityEvaluator) {
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        return this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.LIBRARY, null, SiriusWebCapabilities.Library.LIST);
    }
}
