/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services.configuration;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.stereotype.Service;

/**
 * Bundles injected services for convenience.
 *
 * @author Jerome Gout
 */
@Service
public class ExplorerDescriptionProviderConfiguration {

    private final IObjectService objectService;

    private final IURLParser urlParser;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public ExplorerDescriptionProviderConfiguration(IObjectService objectService, IURLParser urlParser, IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    public IObjectService getObjectService() {
        return this.objectService;
    }

    public IURLParser getUrlParser() {
        return this.urlParser;
    }

    public IRepresentationMetadataSearchService getRepresentationMetadataSearchService() {
        return this.representationMetadataSearchService;
    }
}
