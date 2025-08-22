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
package org.eclipse.sirius.web.application.studio.services.library;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.application.library.dto.ImportLibrariesInput;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handles the import of a library as a copy.
 *
 * @author gdaniel
 */
@Service
public class CopyLibraryImportHandler implements IEditingContextEventHandler {

    private final ILibrarySearchService librarySearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    private final IResourceLoader resourceLoader;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public CopyLibraryImportHandler(ILibrarySearchService librarySearchService, ISemanticDataSearchService semanticDataSearchService, IResourceLoader resourceLoader, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof ImportLibrariesInput importLibrariesInput
                && Objects.equals(importLibrariesInput.type(), "copy");
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), ImportLibrariesInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof ImportLibrariesInput importLibrariesInput && Objects.equals(importLibrariesInput.type(), "copy")) {
            if (editingContext instanceof EditingContext siriusWebEditingContext) {
                List<Library> libraries = this.librarySearchService.findAllById(importLibrariesInput.libraryIds().stream().map(UUID::fromString).toList());

                Set<SemanticData> semanticDataToCopy = new LinkedHashSet<>();
                for (Library library : libraries) {
                    this.semanticDataSearchService.findById(library.getSemanticData().getId())
                        .ifPresent(semanticData -> {
                            semanticDataToCopy.add(semanticData);
                            semanticDataToCopy.addAll(this.semanticDataSearchService.findAllDependenciesRecursivelyById(semanticData.getId()));
                        });
                }

                semanticDataToCopy.stream()
                    .flatMap(semanticData -> semanticData.getDocuments().stream())
                    .forEach(document -> this.resourceLoader.toResource(siriusWebEditingContext.getDomain().getResourceSet(), document.getId().toString(), document.getName(), document.getContent(),
                                this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(editingContext.getId())), false));

                payload = new SuccessPayload(input.id(), List.of(new Message("Libraries imported", MessageLevel.SUCCESS)));
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), importLibrariesInput);
            }
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
