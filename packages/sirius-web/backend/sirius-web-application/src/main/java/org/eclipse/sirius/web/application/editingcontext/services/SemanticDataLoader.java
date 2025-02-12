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
package org.eclipse.sirius.web.application.editingcontext.services;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.EditingContextCrossReferenceAdapter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.ISemanticDataLoader;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Used to load semantic data into editing context.
 *
 * @author mcharfadi
 */
@Service
public class SemanticDataLoader implements ISemanticDataLoader {

    private final Logger logger = LoggerFactory.getLogger(SemanticDataLoader.class);

    private final ISemanticDataSearchService semanticDataSearchService;

    private final IResourceLoader resourceLoader;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    public SemanticDataLoader(ISemanticDataSearchService semanticDataSearchService, IResourceLoader resourceLoader, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates) {
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
    }

    @Override
    public void load(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            this.semanticDataSearchService.findById(UUID.fromString(editingContext.getId()))
                    .ifPresent(semanticData -> {
                        ResourceSet resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
                        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

                        semanticData.getDocuments().forEach(document -> this.resourceLoader.toResource(resourceSet, document.getId().toString(), document.getName(), document.getContent(),
                                this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(editingContext.getId()))));

                        // The ECrossReferenceAdapter must be set after the resource loading because it needs to resolve proxies in case
                        // of inter-resources references
                        resourceSet.eAdapters().add(new EditingContextCrossReferenceAdapter());

                        this.logger.debug("{} documents loaded for the editing context {}", resourceSet.getResources().size(), editingContext.getId());
                    });
        }
    }
}
