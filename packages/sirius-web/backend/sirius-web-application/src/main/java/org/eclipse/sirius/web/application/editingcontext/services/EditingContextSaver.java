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

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextPersistenceFilter;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSaver;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Used to save an editing context.
 *
 * @author mcharfadi
 */
@Service
public class EditingContextSaver implements IEditingContextSaver {

    private static final String TIMER_NAME = "siriusweb_editingcontext_save";

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IResourceToDocumentService resourceToDocumentService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final List<IEditingContextPersistenceFilter> persistenceFilters;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    private final Timer timer;

    private final Logger logger = LoggerFactory.getLogger(EditingContextSaver.class);

    public EditingContextSaver(ISemanticDataUpdateService semanticDataUpdateService, IResourceToDocumentService resourceToDocumentService, IProjectSemanticDataSearchService projectSemanticDataSearchService1, List<IEditingContextPersistenceFilter> persistenceFilters, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates, IProjectSemanticDataSearchService projectSemanticDataSearchService, MeterRegistry meterRegistry) {
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.persistenceFilters = Objects.requireNonNull(persistenceFilters);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
        this.timer = Timer.builder(TIMER_NAME).register(meterRegistry);
    }

    @Transactional
    @Override
    public void save(ICause cause, IEditingContext editingContext) {
        long start = System.currentTimeMillis();

        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var applyMigrationParticipants = this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(emfEditingContext.getId()));

            new UUIDParser().parse(editingContext.getId())
                    .ifPresent(semanticDataUUID -> {
                        AggregateReference<SemanticData, UUID> semanticDataId = AggregateReference.to(semanticDataUUID);

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

                        this.semanticDataUpdateService.updateDocuments(cause, semanticDataId, documents, domainUris);
                    });
        }

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        this.logger.atDebug()
                .setMessage("EditingContext {}: {}ms to persist the semantic data")
                .addArgument(editingContext.getId())
                .addArgument(() -> String.format("%1$6s", end - start))
                .log();
    }

    @Override
    public boolean canHandle(String editingContextId) {
        return this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(UUID.fromString(editingContextId))).isPresent();
    }
}
