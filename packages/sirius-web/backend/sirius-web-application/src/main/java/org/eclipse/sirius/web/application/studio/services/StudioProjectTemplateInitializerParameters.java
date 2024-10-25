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
package org.eclipse.sirius.web.application.studio.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Bundles the bean dependencies that {@link StudioProjectTemplateInitializer} needs into a single object for convenience.
 *
 * @author gcoutable
 */
@Service
public record StudioProjectTemplateInitializerParameters(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramCreationService diagramCreationService, IRepresentationMetadataPersistenceService representationMetadataPersistenceService, IRepresentationPersistenceService representationPersistenceService) {

    public StudioProjectTemplateInitializerParameters {
        Objects.requireNonNull(representationDescriptionSearchService);
        Objects.requireNonNull(diagramCreationService);
        Objects.requireNonNull(representationMetadataPersistenceService);
        Objects.requireNonNull(representationPersistenceService);
    }
}
