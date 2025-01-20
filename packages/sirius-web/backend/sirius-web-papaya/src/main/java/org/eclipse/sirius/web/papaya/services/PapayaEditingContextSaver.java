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
package org.eclipse.sirius.web.papaya.services;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextPersistenceFilter;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSaver;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Used to load a Papaya editing context.
 *
 * @author mcharfadi
 */
@Service
public class PapayaEditingContextSaver implements IEditingContextSaver {

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IResourceToDocumentService resourceToDocumentService;

    private final List<IEditingContextPersistenceFilter> persistenceFilters;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    private final ISemanticDataCreationService semanticDataCreationService;

    private final ISemanticDataSearchService semanticDataSearchService;

    public PapayaEditingContextSaver(ISemanticDataUpdateService semanticDataUpdateService, IResourceToDocumentService resourceToDocumentService, IEditingContextSearchService editingContextSearchService, List<IEditingContextPersistenceFilter> persistenceFilters, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates, ISemanticDataCreationService semanticDataCreationService, ISemanticDataSearchService semanticDataSearchService) {
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.persistenceFilters = Objects.requireNonNull(persistenceFilters);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
    }

    @Transactional
    @Override
    public void save(ICause cause, IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var applyMigrationParticipants = this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(emfEditingContext.getId()));
            var normalizedId = editingContext.getId().split("\\+");
            AggregateReference<Project, String> projectId = AggregateReference.to(normalizedId[0]);

            var documentData = emfEditingContext.getDomain().getResourceSet().getResources().stream()
                    .filter(resource -> IEMFEditingContext.RESOURCE_SCHEME.equals(resource.getURI().scheme()))
                    .filter(resource -> this.persistenceFilters.stream().allMatch(filter -> filter.shouldPersist(resource)))
                    .map(resource -> this.resourceToDocumentService.toDocument(resource, applyMigrationParticipants))
                    .flatMap(Optional::stream)
                    .collect(Collectors.toSet());

            var documents = new LinkedHashSet<Document>();
            var domainUris = new LinkedHashSet<String>();

            documentData.forEach(data -> {
                documents.add(data.document());
                domainUris.addAll(data.ePackageEntries().stream().map(EPackageEntry::nsURI).toList());
            });

            var semanticDataUUID = new UUIDParser().parse(normalizedId[1]);
            if (semanticDataUUID.isPresent() && semanticDataSearchService.findById(semanticDataUUID.get()).isEmpty()) {
                this.semanticDataCreationService.add(cause, semanticDataUUID.get(), projectId);
            }
            semanticDataUUID.ifPresent(uuid -> this.semanticDataUpdateService.updateDocuments(cause, uuid, documents, domainUris));
        }
    }

    @Override
    public boolean canHandle(IEditingContext editingContext) {
        var normalizedId = editingContext.getId().split("\\+");
        return normalizedId.length == 2 && new UUIDParser().parse(normalizedId[0]).isPresent() && new UUIDParser().parse(normalizedId[1]).isPresent();
    }

}