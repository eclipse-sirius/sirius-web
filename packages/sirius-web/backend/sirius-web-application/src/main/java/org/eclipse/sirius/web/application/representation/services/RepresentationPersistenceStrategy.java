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
package org.eclipse.sirius.web.application.representation.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceStrategy;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceStrategy}.
 *
 * @author fbarbin
 */
@Service
public class RepresentationPersistenceStrategy implements IRepresentationPersistenceStrategy {

    private final IRepresentationContentSearchService representationContentSearchService;

    private final RepresentationPersistenceService representationPersistenceService;

    public RepresentationPersistenceStrategy(IRepresentationContentSearchService representationContentSearchService, RepresentationPersistenceService representationPersistenceService) {
        this.representationContentSearchService = Objects.requireNonNull(representationContentSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
    }

    @Override
    public void applyPersistenceStrategy(ICause cause, IEditingContext editingContext, IRepresentation representation) {
        var optionalSemanticDataId = new UUIDParser().parse(editingContext.getId());
        var optionalRepresentationId = new UUIDParser().parse(representation.getId());
        if (optionalSemanticDataId.isPresent() && optionalRepresentationId.isPresent()) {
            var semanticDataId = optionalSemanticDataId.get();
            var representationId = optionalRepresentationId.get();
            var exists = this.representationContentSearchService.existsById(AggregateReference.to(semanticDataId), AggregateReference.to(representationId));
            if (exists) {
                // Delegate to the real persistence service which will perform the save.
                this.representationPersistenceService.save(cause, editingContext, representation);
            }
        }
    }

}
