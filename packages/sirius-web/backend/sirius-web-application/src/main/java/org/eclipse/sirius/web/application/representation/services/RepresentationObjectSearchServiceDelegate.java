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
package org.eclipse.sirius.web.application.representation.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchServiceDelegate;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.springframework.stereotype.Service;

/**
 * The object search service delegate used to find representation metadata.
 *
 * @author gcoutable
 */
@Service
public class RepresentationObjectSearchServiceDelegate implements IObjectSearchServiceDelegate {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    public RepresentationObjectSearchServiceDelegate(IRepresentationMetadataSearchService representationMetadataSearchService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String objectId) {
        return new UUIDParser().parse(objectId)
                .map(this.representationMetadataSearchService::existsById)
                .orElse(Boolean.FALSE);
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        return new UUIDParser().parse(objectId)
                .flatMap(this.representationMetadataSearchService::findMetadataById);
    }
}
